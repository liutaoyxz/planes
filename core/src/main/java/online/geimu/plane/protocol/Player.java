package online.geimu.plane.protocol;


import io.netty.channel.socket.SocketChannel;
import online.geimu.plane.player.pojo.Plane;

/**
 * Created by ltlxy on 2016/11/29.
 */
public class Player {

    private String id; //玩家id

    private Plane plane; //玩家操作的飞机

    private SocketChannel sc; //玩家频道

    public Player(String id, Plane plane, SocketChannel sc) {
        this.id = id;
        this.plane = plane;
        this.sc = sc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public SocketChannel getSc() {
        return sc;
    }

    public void setSc(SocketChannel sc) {
        this.sc = sc;
    }
}
