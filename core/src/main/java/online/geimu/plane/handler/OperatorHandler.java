package online.geimu.plane.handler;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.socket.SocketChannel;
import online.geimu.plane.player.manager.ContainerManager;

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

    /**
     * 刷新敌方飞机间隔
     */
    public static final int EP_INTERVAL = 330;


    private  OperatorHandler(){

    }

    /**
     * 采用单例模式
     */
    public static final OperatorHandler instance = new OperatorHandler();

    public static OperatorHandler getOperator(){
        return instance;
    }



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
        Operator op =Operator.getOperator(type);
        ContainerManager cm = ContainerManager.getManager();
        final String id = sc.id().toString();
        switch (op){
            case NEW_PLAYER:
                //新玩家进入
                cm.addPlayerAndStart(id,request,sc);
                break;
            case READY_START:
                //游戏开始  准备好了
                int mapx = body.getInteger("mapx");
                int mapy = body.getInteger("mapy");
                int width = body.getInteger("width");
                int height = body.getInteger("height");
                int imgIndex = body.getInteger("imgIndex");
                int speed = body.getInteger("speed");
                cm.ready(sc,imgIndex,mapx,mapy,width,height,speed);
                break;
            case MOVE_START:
                //按下方向键
                forward = body.getString("forward");
                cm.move(pid,forward);
                break;
            case MOVE_STOP:
            //取消按键
            forward = body.getString("forward");
            cm.stopMove(pid,forward);
            break;
            case CAHT:
                //玩家聊天
                break;
            case TIME_SEND:
                //发送时间戳

                break;
            case TIME_RECEIVE:
                //发送延迟
                break;
            case SHOOT:
                //开枪
                cm.shoot(pid);
                break;
            default:
                break;
        }
        return result;
    }




}
