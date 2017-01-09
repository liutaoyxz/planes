package online.geimu.plane.player.pojo.enemy;

import online.geimu.plane.player.pojo.Obj;
import online.geimu.plane.player.pojo.Position;
import online.geimu.plane.player.pojo.map.TestMap;

import java.util.Random;

/**
 * @author liutao
 * @description :  测试敌机
 * @create 2017-01-09 17:15
 */
public class TestEnemyPlane extends AbstractEnemyPlane {


    private int width;

    private int height;

    private int speed;

    private volatile boolean isDebut;

    private Position targetPosition;

    private TestMap map;

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

        return false;
    }

    @Override
    protected void debut() {
        isDebut = true;
    }

    /**
     * 刷新目标位置
     */
    private void refreshPosition(){
        final int maxX = map.getX();
        final int maxY = map.getY()/2;
        Random random = new Random();
        int rx = Math.max(random.nextInt(maxX)+1-width,0);
        int ry = random.nextInt(maxY)+1;
        this.targetPosition = new Position(rx,ry);
    }


}
