package online.geimu.plane.player.pojo.enemy;

import com.alibaba.fastjson.annotation.JSONField;
import online.geimu.plane.player.pojo.Obj;
import online.geimu.plane.player.pojo.Position;
import online.geimu.plane.player.pojo.map.TestMap;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author liutao
 * @description :  测试敌机
 * @create 2017-01-09 17:15
 */
public class TestEnemyPlane extends AbstractEnemyPlane {


    private int width;

    private int height;

    private int speed;

    private int x;

    private int y;

    private final ScheduledExecutorService works = Executors.newScheduledThreadPool(2);

    private volatile boolean isDebut;

    private Position targetPosition;

    private TestMap map;

    private TestEnemyPlane(int x,int y,int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public static TestEnemyPlane newInstance(int x,int y,int speed){
        TestEnemyPlane instance = new TestEnemyPlane(x,y,speed);
        instance.initAutoMove();
        return instance;
    }

    @Override
    public Obj checkCollision() {
        return null;
    }

    @Override
    public boolean move() {
        final int tx = targetPosition.getX();
        final int ty = targetPosition.getY();
        if (tx > x){
            x = Math.min(x+speed,tx);
        }else if(tx < x) {
            x = Math.max(x-speed,tx);
        }

        if (ty > y){
            y = Math.min(y+speed,ty);
        }else if(ty < y) {
            y = Math.max(y-speed,ty);
        }
        Position now = new Position(x,y);
        if (now.equals(targetPosition))
            this.refreshTargetPosition();
        return false;
    }

    @Override
    protected void debut() {
        isDebut = true;
    }

    /**
     * 刷新目标位置
     */
    private void refreshTargetPosition(){
        final int maxX = map.getX();
        final int maxY = map.getY()/2;
        Random random = new Random();
        int rx = Math.max(random.nextInt(maxX)+1-width,0);
        int ry = random.nextInt(maxY)+1;
        this.targetPosition = new Position(rx,ry);
    }

    @JSONField(serialize = false)
    @Override
    public Position getPosition() {
        return new Position(x,y);
    }

    /**
     * 初始化并且自动移动
     */
    private void initAutoMove(){


    }


    public static void main(String[] args) {

    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
