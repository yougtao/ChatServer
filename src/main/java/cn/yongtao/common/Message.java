package cn.yongtao.common;

public class Message
{
    private short length;
    private short type;
    private Object body;


    public Message() {
    }

    public Message(int type, Object body) {
        this.type = (short) type;
        this.body = body;
    }

    public Message(int length, int type, Object body) {
        this.length = (short) length;
        this.type = (short) type;
        this.body = body;
    }


    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
