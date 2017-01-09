package online.geimu.plane.player.pojo;



/**
 * @author liutao
 * @description :  位置信息
 * @create 2017-01-06 13:41
 */
public class Position {

    private int x;

    private int y;


    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position))
            return false;
        Position p2 = (Position)obj;
        if (p2.getX() == x && p2.getY() == y)
            return true;
        return false;
    }
}
