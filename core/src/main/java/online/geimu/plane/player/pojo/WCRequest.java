package online.geimu.plane.player.pojo;


import com.alibaba.fastjson.JSON;

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
        WCRequest req = new WCRequest();
        Head head = new Head();
        head.setPlayid("111");
        head.setType(1);
        ReqBody body = new ReqBody();
        body.setEvent(new Event());
        req.setHead(head);
        req.setReqBody(body);
        String sjson = JSON.toJSONString(req);
        System.out.println(sjson);

        WCRequest wcRequest = JSON.parseObject(sjson, WCRequest.class);
        System.out.println(wcRequest);

    }
}
