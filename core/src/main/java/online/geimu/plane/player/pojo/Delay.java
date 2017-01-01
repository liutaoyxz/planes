package online.geimu.plane.player.pojo;

/**
 * @author liutao
 * @description :  延迟
 * @create 2016-12-19 11:46
 */
public class Delay {

    private long delayId; //id

    private long start;  // 开始时间

    private long end;  //结束时间

    private long delayNum;  //延迟数

    public long getDelayId() {
        return delayId;
    }

    public void setDelayId(long delayId) {
        this.delayId = delayId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getDelayNum() {
        return delayNum;
    }

    public void setDelayNum(long delayNum) {
        this.delayNum = delayNum;
    }

    @Override
    public String toString() {
        return "Delay{" +
                "delayId=" + delayId +
                ", start=" + start +
                ", end=" + end +
                ", delayNum=" + delayNum +
                '}';
    }
}
