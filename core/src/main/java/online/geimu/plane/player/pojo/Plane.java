package online.geimu.plane.player.pojo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.sun.org.apache.xpath.internal.SourceTree;
import io.netty.channel.socket.SocketChannel;
import online.geimu.plane.handler.OperatorHandler;
import online.geimu.plane.player.pojo.map.TestMap;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ltlxy on 2016/12/20.
 */
public class Plane {

    private static final Logger log = Logger.getLogger(Plane.class);

    private int imgIndex;  //图片编号

    private String color = ""; //颜色
    @JSONField(serialize = false)
    private TestMap map; //所处地图

    private String id; //玩家id
    @JSONField(serialize = false)
    private int speed; //速度
    @JSONField(serialize = false)
    private SocketChannel sc; //玩家频道

    private int width; //x 的大小

    private int height; //y的大小

    private int px;  //x轴位置

    private int py;  //y轴位置
    //上
    @JSONField(serialize = false)
    private volatile int w;
    //下
    @JSONField(serialize = false)
    private volatile int s;
    //左
    @JSONField(serialize = false)
    private volatile int a;
    //右
    @JSONField(serialize = false)
    private volatile int d;

    public Plane(String id,int imgIndex ,int speed, int px, int py) {
        this.id = id;
        this.speed = speed;
        this.px = px;
        this.py = py;
        this.imgIndex = imgIndex;
    }

    public Plane(){

    }

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    /**
     * 飞机自动移动
     */
    public void move(){
        final Plane plane = this;
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                final int cx = plane.getMx();
                final int cy = plane.getMy();
                if (cx > 0){
                    px = Math.min((px+ width+speed),map.getX())-width;
                }else if (cx < 0){
                    px = Math.max((px-speed),0);
                }

                if (cy > 0){
                    py = Math.min((py+ height+speed),map.getY())-height;
                }else if (cy < 0){
                    py = Math.max((py-speed),0);
                }
            }
        },0l, OperatorHandler.INTERVAL, TimeUnit.MILLISECONDS);
    }

    public void stopForward(String forward){
        if (forward.toLowerCase().equals("w") ){
            w = 0;
        }else if (forward.toLowerCase().equals("s") ){
            s = 0;
        }else if (forward.toLowerCase().equals("a") ){
            a = 0;
        }else if (forward.toLowerCase().equals("d") ){
            d = 0;
        }
    }

    public void startForward(String forward){
        if (forward.toLowerCase().equals("w")) {
            w = s+1;
        } else if (forward.toLowerCase().equals("s")) {
            s = w+1;
        } else if (forward.toLowerCase().equals("a")) {
            a = d+1;
        } else if (forward.toLowerCase().equals("d")) {
            d = a+1;
        }
    }

    public void stopMove(){
        scheduledExecutorService.shutdown();
    }


    public int getMx(){
        return d-a;
    }

    public int getMy(){
        return s-w;
    }



    public int getImgIndex() {
        return imgIndex;
    }

    public void setImgIndex(int imgIndex) {
        this.imgIndex = imgIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public SocketChannel getSc() {
        return sc;
    }

    public void setSc(SocketChannel sc) {
        this.sc = sc;
    }

    public TestMap getMap() {
        return map;
    }

    public void setMap(TestMap map) {
        this.map = map;
    }

    public static void main(String[] args) {
        List<Plane> ps = new ArrayList();

        Plane p = new Plane("xxx",1,4,0,0);
        JSONObject j = new JSONObject();
        ps.add(p);
        ps.add(p);
        j.put("ps",ps);

        System.out.println(j.toString());
    }


}
