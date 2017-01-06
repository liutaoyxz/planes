package online.geimu.plane.protocol;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author liutao
 * @description :  响应
 * @create 2016-12-08 10:54
 */
public class WCResponse {


    @JSONField(ordinal = 1)
    private Head head;
    @JSONField(ordinal = 2)
    private ResBody body;

    public WCResponse(Head head, ResBody body) {
        this.head = head;
        this.body = body;
    }

    public WCResponse() {
    }

    public final Head getHead() {
        return head;
    }

    public final void setHead(Head head) {
        this.head = head;
    }

    public final ResBody getBody() {
        return body;
    }

    public final void setBody(ResBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "WCResponse{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
