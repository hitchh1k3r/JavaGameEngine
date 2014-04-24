package com.hitchh1k3rsguide.gameEngine.entities;

public abstract class AbstractEntity
{

    public final long index;

    public AbstractEntity(long index)
    {
        this.index = index;
    }

    public void dispose()
    {
        // TODO - trigger entity removal request
    }

}
