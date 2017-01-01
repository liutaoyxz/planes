package online.geimu.plane.handler;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.socket.SocketChannel;
import online.geimu.plane.player.ContainerManager;

/**
 * @author liutao
 * @description :
 * @create 2016-12-28 11:09
 */
public class OperatorHandler {

    /**
     * 游戏每帧之间的延迟
     */
    public static final int INTERVAL = 30;


    private  OperatorHandler(){

    }

    /**
     * 采用单例模式
     */
    public static final OperatorHandler instance = new OperatorHandler();

    public static OperatorHandler getOperator(){
        return instance;
    }

    //请求
    //加入游戏
    public static final int REQ_TYPE_NEW_PLAYER = 0;
    //游戏开始  准备好了
    public static final int REQ_TYPE_GAME_START = 1;
    //玩家移动
    public static final int REQ_TYPE_PLAYER_MOVE = 2;
    //使用道具 or 产生事件
    public static final int REQ_TYPE_PLAYER_EVENT = 3;
    //取消按键
    public static final int REQ_TYPE_PLAYER_TURN_END = 4;
    //按下按键
    public static final int REQ_TYPE_PLAYER_TURN_START = 5;
    //玩家id
    public static final int REQ_TYPE_PLAYER_ID = 6;
    //
    public static final int REQ_TYPE_ROLL = 7;
    //游戏结束
    public static final int REQ_TYPE_STOP = 8;
    //聊天
    public static final int REQ_TYPE_CAHT = 10086;
    //延迟测量 发送
    public static final int REQ_TYPE_TIME_SEND = 9;
    //延迟测量 收取
    public static final int REQ_TYPE_TIME_RECEIVE = 10;


    public static final String SEND = "send  :";
    public static final String RECEIVE = "receive  :";

    /**
     * 处理请求
     * @param
     * @return
     */
    public boolean operator(String request, SocketChannel sc){
        boolean result = false;
        JSONObject json = JSONObject.parseObject(request);
        JSONObject head = json.getJSONObject("head");
        JSONObject body = json.getJSONObject("body");
        String pid = head.getString("id");
        String forward = null;
        int type = head.getInteger("type");
        ContainerManager cm = ContainerManager.getManager();
        final String id = sc.id().toString();
        switch (type){
            case REQ_TYPE_NEW_PLAYER:
                //新玩家进入
                cm.addPlayerAndStart(id,request,sc);
                break;
            case REQ_TYPE_GAME_START:
                //游戏开始  准备好了
                int mapx = body.getInteger("mapx");
                int mapy = body.getInteger("mapy");
                int width = body.getInteger("width");
                int height = body.getInteger("height");
                int speed = body.getInteger("speed");
                cm.ready(sc,mapx,mapy,width,height,speed);
                break;
            case REQ_TYPE_PLAYER_MOVE:
                //玩家移动
                break;
            case REQ_TYPE_PLAYER_TURN_END:
                //取消按键
                forward = body.getString("forward");
                cm.stopMove(pid,forward);
                break;
            case REQ_TYPE_PLAYER_TURN_START:
                //按下方向键
                forward = body.getString("forward");
                cm.move(pid,forward);
                break;
            case REQ_TYPE_PLAYER_ID:
                //请求玩家id
                break;
            case REQ_TYPE_ROLL:
                //roll
                break;
            case REQ_TYPE_PLAYER_EVENT:
                //玩家事件
                break;
            case REQ_TYPE_CAHT:
                //玩家事件
                break;
            case REQ_TYPE_TIME_SEND:
                //发送时间戳

                break;
            case REQ_TYPE_TIME_RECEIVE:
                //发送延迟
                break;
            default:
                break;
        }
        return result;
    }




}
