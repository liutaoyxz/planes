package online.geimu.plane.player.pojo;

/**
 * Created by ltlxy on 2016/12/20.
 * 定义飞机大战游戏中的基础规范
 *
 *
 * @author liutao
 */
public interface Obj {



    /**
     * 检测碰撞
     * @return
     */
    Obj checkCollision();


    /**
     * 获得这个物体的位置
     * @return
     */
    Position getPosition();


}
