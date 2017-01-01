package online.geimu.plane.player.pojo;

import online.geimu.plane.player.pojo.map.Obj;

/**
 * Created by ltlxy on 2017/1/1.
 * 子弹类
 */
public class Bullet implements Obj{
    @Override
    public void move() {

    }

    @Override
    public boolean checkX(int x) {
        return false;
    }

    @Override
    public boolean checkY(int y) {
        return false;
    }

    @Override
    public void refreshPosition() {

    }
}
