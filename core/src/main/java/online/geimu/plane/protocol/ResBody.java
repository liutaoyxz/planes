package online.geimu.plane.protocol;

import online.geimu.plane.player.pojo.IndependentObj;
import online.geimu.plane.player.pojo.Obj;
import online.geimu.plane.player.pojo.Plane;
import online.geimu.plane.player.pojo.enemy.AbstractEnemyPlane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltlxy on 2016/12/12.
 */
public class ResBody extends AbstractCommonBody {

    private List<Plane> planes = new ArrayList();

    private List<Plane> getPlanes() {
        return planes;
    }

    private List<IndependentObj> eb = new ArrayList();

    private List<IndependentObj> ep = new ArrayList<>();

    private Plane plane;

    public Plane getPlane() {
        return plane;
    }

    public List<IndependentObj> getEp() {
        return ep;
    }

    public void setEp(List<IndependentObj> ep) {
        this.ep = ep;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setPlanes(List<Plane> planes) {
        this.planes = planes;
    }

    public List<IndependentObj> getEb() {
        return eb;
    }

    public void setEb(List<IndependentObj> eb) {
        this.eb = eb;
    }
}
