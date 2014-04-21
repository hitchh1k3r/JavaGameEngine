package com.hitchh1k3rsguide.gameEngine.messages;

public class MessageRequest implements IMessage
{

    private static int counter;

    public final int index;
    public final String requestName;

    public MessageRequest(String requestName)
    {
        this.index = counter++;
        this.requestName = requestName;
    }

}
