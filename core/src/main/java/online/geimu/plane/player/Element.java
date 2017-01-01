package online.geimu.plane.player;

/**
 * @author liutao
 * @description : 拟定为所有元素的模型接口,规定一些基础的行为.
 * @create 2016-12-28 11:30
 */
public interface Element {

    /**
     * 发送消息给本局游戏
     */
    void castMsgToBureauPlayers(String msg);


}
