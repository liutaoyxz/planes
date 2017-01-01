package online.geimu.plane.player.pojo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.netty.channel.socket.SocketChannel;
import online.geimu.plane.handler.OperatorHandler;
import online.geimu.plane.player.pojo.map.TestMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ltlxy on 2016/12/20.
 */
public class Plane {

    private int imgIndex;  //图片编号

    private String color = ""; //颜色
    @JSONField(serialize = false)
    private TestMap map; //所处地图

    private String id; //玩家id
    @JSONField(serialize = false)
    private int speed; //速度

    private SocketChannel sc; //玩家频道

    private int width; //x 的大小

    private int height; //y的大小

    private int px;  //x轴位置

    private int py;  //y轴位置
    @JSONField(serialize = false)
    private volatile int mx; //x 轴移动标识
    @JSONField(serialize = false)
    private volatile int my;  //y 轴移动标识

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
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (mx > 0){
                    px = Math.min((px+ width+speed),map.getX());
                }else if (mx < 0){
                    px = Math.max((px-speed),0);
                }

                if (my > 0){
                    py = Math.min((py+ height+speed),map.getY());
                }else if (my < 0){
                    py = Math.max((py-speed),0);
                }
            }
        },0l, OperatorHandler.INTERVAL, TimeUnit.MILLISECONDS);
    }

    public void stopMove(){
        scheduledExecutorService.shutdown();
    }

    public void setMx(int x){
        this.mx = x;
    }

    public void setMy(int y){
        this.my = y;
    }

    public void setMXY(int x, int y){
        this.mx = x;
        this.my = y;
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
