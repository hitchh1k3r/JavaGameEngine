package com.hitchh1k3rsguide.gameEngine.messages;

public class MessageResponse implements IMessage
{

    public final int index;
    public final String requestName;
    public final Object payload;

    public MessageResponse(int index, String requestName, Object payload)
    {
        this.index = index;
        this.requestName = requestName;
        this.payload = payload;
    }

}
