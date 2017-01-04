package online.geimu.plane.player.pojo;

import online.geimu.plane.player.pojo.map.Obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltlxy on 2016/12/12.
 */
public class ResBody extends AbstractCommonBody {

    private List<Plane> planes = new ArrayList();

    public List<Plane> getPlanes() {
        return planes;
    }

    public List<Obj> eb = new ArrayList();

    public Plane plane;

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setPlanes(List<Plane> planes) {
        this.planes = planes;
    }

    public List<Obj> getEb() {
        return eb;
    }

    public void setEb(List<Obj> eb) {
        this.eb = eb;
    }
}
