package com.hitchh1k3rsguide.gameEngine.messages;

public class MessageSetGravity implements IMessage
{

    public final double newGravity;

    public MessageSetGravity(double gravity)
    {
        this.newGravity = gravity;
    }

}
