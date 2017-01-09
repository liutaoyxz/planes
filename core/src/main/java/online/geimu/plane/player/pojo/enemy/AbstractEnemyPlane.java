package online.geimu.plane.player.pojo.enemy;

import online.geimu.plane.player.pojo.IndependentObj;
import online.geimu.plane.player.pojo.Position;

/**
 * @author liutao
 * @description :  敌人飞机抽象
 * @create 2017-01-06 11:06
 */
public abstract class AbstractEnemyPlane implements IndependentObj{


    protected int x;

    protected int y;



    /**
     * @return 飞机的位置对象
     */
    @Override
    public final Position getPosition() {
        return new Position(x,y);
    }

    protected abstract void debut();

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
