package online.geimu.plane.player.pojo.arms;


import online.geimu.plane.player.pojo.Obj;
import online.geimu.plane.player.pojo.bullet.Bullet;

/**
 * @author liutao
 * @description :  枪
 * @create 2017-01-06 11:11
 */
public interface Gun extends Obj{

    Bullet shoot();

}
