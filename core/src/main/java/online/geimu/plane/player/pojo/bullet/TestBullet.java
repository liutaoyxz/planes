package online.geimu.plane.player.pojo.bullet;

import com.alibaba.fastjson.annotation.JSONField;
import online.geimu.plane.player.pojo.IndependentObj;
import online.geimu.plane.player.pojo.Obj;
import online.geimu.plane.player.pojo.Position;

/**
 * Created by ltlxy on 2017/1/1.
 * 子弹类
 */
public class TestBullet implements IndependentObj {

    private int x;

    private int y;

    @JSONField(serialize = false)
    private int speed;
    @JSONField(serialize = false)
    private int type;

    public TestBullet(int x, int y, int speed){
        this.speed = speed;
        this.x = x;
        this.y = y;

    }

    @Override
    public boolean move() {
        y = y - speed;
        if (y <= 0){
            return false;
        }
        return true;
    }

    @Override
    public Obj checkCollision() {
        return null;
    }

    @Override
    public boolean checkMoveable() {
        return true;
    }

    @Override
    public Position getPosition() {
        return new Position(this.x,this.y);
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
