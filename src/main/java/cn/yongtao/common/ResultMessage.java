package cn.yongtao.common;

public class ResultMessage
{
    short length;
    short type;
    String msgBody;

    public ResultMessage(int length, int type, String msgBody) {
        this.length = (short) length;
        this.type = (short) type;
        this.msgBody = msgBody;
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

    public String getBody() {
        return msgBody;
    }

    public void setBody(String msgBody) {
        this.msgBody = msgBody;
    }
}
