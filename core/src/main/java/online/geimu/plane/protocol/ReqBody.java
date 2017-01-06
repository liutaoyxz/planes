package online.geimu.plane.protocol;

/**
 * @author liutao
 * @description :  请求body
 * @create 2016-12-08 11:18
 */
public class ReqBody extends AbstractCommonBody {

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "ReqBody{" +
                "event=" + event +
                '}';
    }
}
