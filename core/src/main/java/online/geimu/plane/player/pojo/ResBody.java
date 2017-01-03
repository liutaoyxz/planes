package online.geimu.plane.player.pojo;

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
}
