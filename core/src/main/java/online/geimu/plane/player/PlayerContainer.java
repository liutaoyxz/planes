package online.geimu.plane.player;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import online.geimu.plane.handler.Operator;
import online.geimu.plane.handler.OperatorHandler;
import online.geimu.plane.player.pojo.*;
import org.apache.log4j.Logger;


import java.util.*;
import java.util.concurrent.*;

/**
 * Created by ltlxy on 2016/11/28.
 */
public class PlayerContainer {


    private static final Logger log = Logger.getLogger(PlayerContainer.class);

    /**
     * 延迟测量
     */
    private final ScheduledExecutorService delay = Executors.newScheduledThreadPool(1);

    /**
     * 屏幕画面发送
     */
    private final ScheduledExecutorService frame = Executors.newScheduledThreadPool(1);

    //本局飞机数组
    private final List<Plane> plist = new ArrayList();

    /**
     * id 玩家映射,没有使用{@link ConcurrentHashMap},{@link ConcurrentHashMap}在存储数据较少的情况下性能感觉不比同步的Map高.
     * 在内部对map操作的时候采用同步,暂时不涉及到频繁的修改操作
     */
    @SuppressWarnings("unchecked")
    private final Map<String, Plane> idps = new HashMap();

    private volatile String content;

    private SocketChannel sc;

    public PlayerContainer(SocketChannel sc) {
        this.sc = sc;
    }

    public PlayerContainer() {
    }

    /**
     * 开始本局游戏
     *
     * @param
     */
    public void start() {
        for (Plane p : plist) {
            p.move();
        }
        frame.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                castMsg(Operator.FRAME_INFO.code());
            }
        }, 0l, OperatorHandler.INTERVAL, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止本局游戏
     */
    public void stop() {
        frame.shutdownNow();
        for (Plane p : plist) {
            p.stopMove();
            castMsg(Operator.GAME_STOP.code());
        }
    }


    /**
     * 向所有玩家广播当前的情况
     */
    public void castMsg(int type) {
        Head head = new Head();
        WCResponse response = new WCResponse();
        head.setType(type);
        ResBody body = new ResBody();
        body.setPlanes(plist);
        response.setHead(head);
        response.setBody(body);
        for (Plane p : plist) {
            head.setId(p.getId());
            String jstr = JSON.toJSONString(response,SerializerFeature.DisableCircularReferenceDetect);
            log.debug(OperatorHandler.SEND+jstr);
            p.getSc().writeAndFlush(new TextWebSocketFrame(jstr));
        }
    }

    public void setContent(String request) {
        this.content = request;
    }


    public void sendMsg() {
        sc.writeAndFlush(new TextWebSocketFrame(content));
    }

    /**
     * 添加玩家,保证map的同步
     */
    public void addPlane(Plane plane) {
        synchronized (idps) {
            final String id = plane.getId();
            if (id == null && idps.get(id) == null)
                throw new IllegalArgumentException("参数错误");
            idps.put(id, plane);
            plist.add(plane);
        }
    }

    public void move(String id,String forward){

    }

}
