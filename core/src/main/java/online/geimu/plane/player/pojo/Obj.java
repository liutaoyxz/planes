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
     * 移动
     *
     */
    boolean move();

    /**
     * 检测碰撞
     * @param x
     * @return
     */
    boolean checkCollision(int x);



    /**
     * 刷新位置
     *
     */
    void refreshPosition();

}
