package online.geimu.plane.player.pojo.map;

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
    void move();

    /**
     * 检测x轴是否碰撞
     * @param x
     * @return
     */
    boolean checkX(int x);

    /**
     * 检测y轴碰撞
     * @param y
     * @return
     */
    boolean checkY(int y);

    /**
     * 刷新位置
     *
     */
    void refreshPosition();

}
