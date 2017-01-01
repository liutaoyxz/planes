package online.geimu.plane.player.pojo;

/**
 * Created by ltlxy on 2016/11/29.
 */
public class Head extends AbstractCommonHead{



    private int sump;

    public final int getSump() {
        return sump;
    }

    public final void setSump(int sump) {
        this.sump = sump;
    }

    @Override
    public String toString() {
        return "Head{" +
                "sump=" + sump +
                '}';
    }
}
