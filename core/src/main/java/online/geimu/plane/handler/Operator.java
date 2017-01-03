package online.geimu.plane.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ltlxy on 2017/1/2.
 */
public enum Operator {
    NEW_PLAYER("新玩家进入,返回玩家id",0),
    READY_START("准备",1),
    MOVE_START("玩家开始移动",2),
    MOVE_STOP("取消移动",3),
    CAHT("聊天",10086),
    TIME_SEND("发送时间戳",5),
    TIME_RECEIVE("收到时间戳",6),
    GAME_STOP("游戏结束",7);

    private static final Map<Integer,Operator> intToEnum = new HashMap<Integer, Operator>();

    static {
        for (Operator op : Operator.values()){
            intToEnum.put(op.code(),op);
        }
    }

    private final int code;

    private final String des;

    Operator(String des,int code){
        this.code = code;
        this.des = des;
    }

    public  int code(){
        return code;
    }

    public String des(){
        return des;
    }

    public static Operator getOperator(int code){
        return intToEnum.get(code);
    }


}
