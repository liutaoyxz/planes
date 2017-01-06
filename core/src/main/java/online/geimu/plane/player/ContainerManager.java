package online.geimu.plane.player;

import com.alibaba.fastjson.JSON;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import online.geimu.plane.handler.Operator;
import online.geimu.plane.handler.OperatorHandler;
import online.geimu.plane.protocol.Head;
import online.geimu.plane.player.pojo.Plane;
import online.geimu.plane.protocol.ResBody;
import online.geimu.plane.protocol.WCResponse;
import online.geimu.plane.player.pojo.map.TestMap;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ltlxy on 2016/11/28.
 */
public class ContainerManager {

    private static final ContainerManager cm = new ContainerManager();

    private ContainerManager() {

    }

    public static ContainerManager getManager() {
        return cm;
    }

    private final Logger log = Logger.getLogger(ContainerManager.class);
    @SuppressWarnings("unchecked")
    private final ConcurrentHashMap<String,PlayerContainer> idps = new ConcurrentHashMap();

    /**
     * 已经准备的玩家的id集合
     */
    @SuppressWarnings("unchecked")
    private CopyOnWriteArrayList<String> readyIds = new CopyOnWriteArrayList();

    /**
     * id和player 映射
     */
    @SuppressWarnings("unchecked")
    private final ConcurrentHashMap<String, Plane> ids = new ConcurrentHashMap();


    /**
     * 游戏开始需要的玩家数量
     */
    private final int start_num = 2;

    /**
     * 图片数量
     */
    private final int img_num = 4;

    /**
     * 图片编号
     */
    private final AtomicInteger lastIndex = new AtomicInteger(0);

    private PlayerContainer pc = new PlayerContainer();

    /**
     * 飞机颜色
     */
    private final String[] colors = {"blue","pink","red","yellow"};

    /**
     * 刚刚连接上的玩家请求id
     * @param id
     */
    public void addPlayerAndStart(String id, String request, SocketChannel sc){
        final int imgIndex = lastIndex.getAndAdd(1)%img_num;
        Plane plane = new Plane();
        plane.setId(id);
        plane.setColor(colors[imgIndex]);
        plane.setImgIndex(imgIndex);
        Head head = new Head();
        head.setId(id);
        head.setType(Operator.NEW_PLAYER.code());
        ResBody body = new ResBody();
        body.setPlane(plane);
        WCResponse response = new WCResponse(head,body);
        String str = JSON.toJSONString(response);
        log.debug(OperatorHandler.SEND+str);
        sc.writeAndFlush(new TextWebSocketFrame(str));
    }

    /**
     * 移动
     * @param forward
     */
    public void move(String id,String forward){
        PlayerContainer pc = idps.get(id);
        pc.move(id,forward);
    }

    /**
     * 停止一个方向的移动
     * @param id
     * @param forward
     */
    public void stopMove(String id,String forward){
        PlayerContainer pc = idps.get(id);
        pc.stopMove(id,forward);
    }



    /**
     * 准备
     * @param sc
     * @param imgIndex 飞机索引
     * @param mapx 地图x
     * @param mapy 地图y
     * @param width  飞机宽度
     * @param height  飞机长度
     * @param speed  飞机速度
     */
    public void ready(SocketChannel sc,int imgIndex,int mapx,int mapy,int width,int height,int speed){
        final String id = sc.id().toString();
        TestMap map = new TestMap(mapx,mapy);
        Plane plane =new Plane(id,imgIndex,speed,0,0);
        plane.setSc(sc);
        plane.setWidth(width);
        plane.setMap(map);
        plane.setHeight(height);
        plane.setColor(colors[plane.getImgIndex()]);
        ids.put(id,plane);
        readyIds.add(id);
        if (readyIds.size() >= start_num){
            for (String cid : readyIds){
                pc.addPlane(ids.get(cid));
                idps.put(cid,pc);
            }
            readyIds.clear();
            pc.castMsg(Operator.READY_START.code());
            pc.start();
            pc = new PlayerContainer();
        }

    }

    /**
     * 开枪
     * @param id
     */
    public void shoot(String id){
        PlayerContainer pc = idps.get(id);
        pc.shoot(id);
    }


    /**
     * 停止游戏,移除关系映射
     * @param id
     */
    public void stop(String id){
        readyIds.remove(id);
        PlayerContainer pc = idps.get(id);
        if (pc != null){
            List<String> ids = pc.getIds();
            pc.stop();
            for (String pid : ids){
                idps.remove(pid);
                this.ids.remove(pid);
            }
        }
    }


}
