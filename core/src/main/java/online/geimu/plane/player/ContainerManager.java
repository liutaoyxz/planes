package online.geimu.plane.player;

import com.alibaba.fastjson.JSON;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import online.geimu.plane.handler.Operator;
import online.geimu.plane.handler.OperatorHandler;
import online.geimu.plane.player.pojo.Head;
import online.geimu.plane.player.pojo.Plane;
import online.geimu.plane.player.pojo.ResBody;
import online.geimu.plane.player.pojo.WCResponse;
import online.geimu.plane.player.pojo.map.TestMap;
import org.apache.log4j.Logger;

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

    private final ConcurrentHashMap<String,PlayerContainer> idps = new ConcurrentHashMap();

    /**
     * 已经准备的玩家的id集合
     */
    private CopyOnWriteArrayList<String> readyIds = new CopyOnWriteArrayList();

    /**
     * id和player 映射
     */
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
        log.debug("进入移动方法, 移动的方向是   ==> "+forward);
        Plane p = ids.get(id);
        PlayerContainer pc = idps.get(id);
        if (forward.toLowerCase().equals("w")){
            p.setMy(-1);
        }else if (forward.toLowerCase().equals("s")){
            p.setMy(1);
        }else if (forward.toLowerCase().equals("a")){
            p.setMx(-1);
        }else if (forward.toLowerCase().equals("d")){
            p.setMx(1);
        }
    }

    /**
     * 停止一个方向的移动
     * @param id
     * @param forword
     */
    public void stopMove(String id,String forword){
        Plane p = ids.get(id);
        if (forword.toLowerCase().equals("w")){
            p.setMy(0);
        }else if (forword.toLowerCase().equals("s")){
            p.setMy(0);
        }else if (forword.toLowerCase().equals("a")){
            p.setMx(0);
        }else if (forword.toLowerCase().equals("d")){
            p.setMx(0);
        }
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
     * 停止游戏,移除关系映射
     * @param id
     */
    public void stop(String id){
        PlayerContainer pc = idps.get(id);
        pc.stop();
        ids.remove(id);
        idps.remove(id);
    }


}
