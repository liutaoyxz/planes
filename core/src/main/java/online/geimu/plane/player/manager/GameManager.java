package online.geimu.plane.player.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import online.geimu.plane.handler.Operator;
import online.geimu.plane.handler.OperatorHandler;
import online.geimu.plane.player.pojo.*;
import online.geimu.plane.player.pojo.Obj;
import online.geimu.plane.player.pojo.bullet.TestBullet;
import online.geimu.plane.player.pojo.enemy.AbstractEnemyPlane;
import online.geimu.plane.player.pojo.enemy.TestEnemyPlane;
import online.geimu.plane.protocol.Head;
import online.geimu.plane.protocol.ResBody;
import online.geimu.plane.protocol.WCResponse;
import org.apache.log4j.Logger;


import java.util.*;
import java.util.concurrent.*;

/**
 * Created by ltlxy on 2016/11/28.
 * 游戏管理
 */
public class GameManager {


    private static final Logger log = Logger.getLogger(GameManager.class);

    /**
     * 延迟测量
     */
    private final ScheduledExecutorService delay = Executors.newScheduledThreadPool(1);

    /**
     * 屏幕画面发送
     */
    private final ScheduledExecutorService frame = Executors.newScheduledThreadPool(1);

    /**
     * 子弹线程
     */
    private final ScheduledExecutorService bframe = Executors.newScheduledThreadPool(1);

    //本局飞机数组
    private final List<Plane> plist = new ArrayList();

    /**
     * id 玩家映射,没有使用{@link ConcurrentHashMap},{@link ConcurrentHashMap}在存储数据较少的情况下性能感觉不比同步的Map高.
     * 在内部对map操作的时候采用同步,暂时不涉及到频繁的修改操作
     */
    @SuppressWarnings("unchecked")
    private final Map<String, Plane> idps = new HashMap();

    /**
     * 子弹数组
     */
    private final List<IndependentObj> eb = new ArrayList();

    private final List<IndependentObj> ep = new ArrayList<>();

    private volatile String content;

    private SocketChannel sc;

    public GameManager(SocketChannel sc) {
        this.sc = sc;
    }

    public GameManager() {
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
        bframe.scheduleAtFixedRate( new Runnable() {
            @Override
            public void run() {
                Iterator<? extends IndependentObj> it = eb.iterator();
                while (it.hasNext()){
                    IndependentObj next = it.next();
                    boolean b = next.move();
                    if (!b){
                        it.remove();
                    }
                }
            }
        }, 0l, OperatorHandler.INTERVAL, TimeUnit.MILLISECONDS);
        frame.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                castMsg(Operator.FRAME_INFO.code());
            }
        }, 0l, OperatorHandler.INTERVAL, TimeUnit.MILLISECONDS);
        ep.add(TestEnemyPlane.newInstance(1,100,100,plist.get(0).getMap()));

    }

    /**
     * 停止本局游戏
     */
    public void stop() {
        bframe.shutdownNow();
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
        body.setEb(eb);
        body.setPlanes(plist);
        body.setEp(ep);
        response.setHead(head);
        response.setBody(body);
        for (Plane p : plist) {
            head.setId(p.getId());
            String jstr = JSON.toJSONString(response, SerializerFeature.DisableCircularReferenceDetect);
            log.debug(OperatorHandler.SEND + jstr);
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

    public void move(String id, String forward) {
        Plane p = idps.get(id);
        p.startForward(forward);
    }


    public void stopMove(String id, String forword) {
        Plane p = idps.get(id);
        p.stopForward(forword);
    }

    public List<String> getIds() {
        List<String> list = new ArrayList();
        for (Plane p : plist) {
            list.add(p.getId());
        }
        return list;
    }


    /**
     * 开枪
     * @param id
     */
    public void shoot(String id){
        Plane plane = idps.get(id);
        final int px = plane.getPx();
        final int py = plane.getPy();
        final int width = plane.getWidth();
        final int speed = plane.getSpeed();
        IndependentObj o = new TestBullet(px+width/2,py,2*speed);
        eb.add(o);
    }


}
