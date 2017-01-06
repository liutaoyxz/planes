package online.geimu.plane.protocol;

import java.util.List;

/**
 * @author liutao
 * @description : 公用的body部分
 * @create 2016-12-08 11:14
 */
public abstract class AbstractCommonBody {


    private List<Player> players;

    private ChatData chatData;

    private Delay delay;

    public Delay getDelay() {
        return delay;
    }

    public void setDelay(Delay delay) {
        this.delay = delay;
    }

    public ChatData getChatData() {
        return chatData;
    }

    public void setChatData(ChatData chatData) {
        this.chatData = chatData;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "AbstractCommonBody{" +
                "players=" + players +
                ", chatData=" + chatData +
                ", delay=" + delay +
                '}';
    }
}
