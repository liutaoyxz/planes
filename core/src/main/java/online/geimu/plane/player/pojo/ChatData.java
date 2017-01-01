package online.geimu.plane.player.pojo;

/**
 * @author liutao
 * @description :  聊天对象
 * @create 2016-12-16 13:28
 */
public class ChatData {

    private String name;

    private String message;

    private int chatType;

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatData{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", chatType=" + chatType +
                '}';
    }
}
