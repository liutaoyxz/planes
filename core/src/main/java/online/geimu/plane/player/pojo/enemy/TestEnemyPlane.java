package online.geimu.plane.player.pojo.enemy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import online.geimu.plane.handler.OperatorHandler;
import online.geimu.plane.player.manager.ThreadPoolManager;
import online.geimu.plane.player.pojo.Obj;
import online.geimu.plane.player.pojo.Position;
import online.geimu.plane.player.pojo.map.TestMap;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liutao
 * @description :  测试敌机
 * @create 2017-01-09 17:15
 */
public class TestEnemyPlane extends AbstractEnemyPlane {

    private int width;

    private int height;
    @JSONField(serialize = false)
    private int speed;

    private int x;

    private int y;
    @JSONField(serialize = false)
    private final ScheduledExecutorService works = Executors.newScheduledThreadPool(2);
    @JSONField(serialize = false)
    private volatile boolean isDebut;
    @JSONField(serialize = false)
    private Position targetPosition;
    @JSONField(serialize = false)
    private TestMap map;

    private TestEnemyPlane(int speed, int width, int height, TestMap map) {
        Random r = new Random();
        this.map = map;
        this.width = width;
        this.height = height;
        x = Math.max(0, r.nextInt(map.getX() - width));
        y = -height;
        this.speed = speed;
    }

    /**
     * 静态工厂生产敌机
     *
     * @param speed
     * @param width
     * @param height
     * @param map
     * @return
     */
    public static TestEnemyPlane newInstance(int speed, int width, int height, TestMap map) {
        TestEnemyPlane instance = new TestEnemyPlane(speed, width, height, map);
        instance.map = map;
        instance.debut();
        return instance;
    }

    @Override
    public Obj checkCollision() {
        return null;
    }

    @Override
    public boolean move() {
        if (targetPosition == null)
            refreshTargetPosition();
        final int tx = targetPosition.getX();
        final int ty = targetPosition.getY();
        if (tx > x) {
            x = Math.min(x + (speed*((y/50)+1)), tx);
        } else if (tx < x) {
            x = Math.max(x - speed*((y/50)+1), tx);
        }

        y += speed*((y/50)+1);
        Position now = new Position(x, y);
        if (now.equals(targetPosition))
            this.refreshTargetPosition();
        if (y >= ty)
            return false;
        return true;
    }

    @Override
    protected void debut() {
        ThreadPoolManager.CACHE.execute(new Runnable() {
            @Override
            public void run() {
                while (y < 0) {
                    try {
                        Thread.sleep(OperatorHandler.INTERVAL);
                        y = y + speed;
                    } catch (InterruptedException e) {

                    }
                }
                isDebut = true;
            }
        });
    }

    /**
     * 刷新目标位置
     */
    private void refreshTargetPosition() {
        final int maxX = map.getX();
        Random random = new Random();
        int rx = Math.max(random.nextInt(maxX) + 1 - width, 0);
        int ry = map.getY();
        this.targetPosition = new Position(rx, ry);
    }

    @JSONField(serialize = false)
    @Override
    public Position getPosition() {
        return new Position(x, y);
    }


    @Override
    public boolean checkMoveable() {
        return isDebut;
    }


    public static void main(String[] args) throws InterruptedException {
        TestEnemyPlane plane = newInstance(1, 100, 100, new TestMap(200, 300));
        while (true) {
            Thread.sleep(1000);
            String s = JSON.toJSONString(plane);
            System.out.println(s);
        }
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
