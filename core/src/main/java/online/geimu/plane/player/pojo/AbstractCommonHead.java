package online.geimu.plane.player.pojo;

/**
 * @author liutao
 * @description :  head 公用部分
 * @create 2016-12-08 11:13
 */
public abstract class AbstractCommonHead {

    private int type;

    private String playid;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPlayid() {
        return playid;
    }

    public void setPlayid(String playid) {
        this.playid = playid;
    }

    @Override
    public String toString() {
        return "AbstractCommonHead{" +
                "type=" + type +
                ", playid='" + playid + '\'' +
                '}';
    }
}
