package online.geimu.plane.protocol;


/**
 * Created by ltlxy on 2016/11/29.
 *  接受到的请求
 */
public  class WCRequest {

    private Head head;

    private ReqBody body;

    public final Head getHead() {
        return head;
    }

    public final void setHead(Head head) {
        this.head = head;
    }

    public final ReqBody getReqBody() {
        return body;
    }

    public final void setReqBody(ReqBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "WCRequest{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }

    public static void main(String[] args) {

    }
}
