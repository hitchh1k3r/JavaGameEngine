package com.hitchh1k3rsguide.gameEngine.systems;

import java.util.Collection;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentWindow;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;

public class SystemWindow implements ISystem
{

    @Override
    public void update(GameEngine ecs)
    {
        Collection<AbstractEntity> entities = ecs.getAll(ComponentWindow.class).values();
        for (AbstractEntity entity : entities)
        {
            ((ComponentWindow) entity).swapBuffer();
            ((ComponentWindow) entity).updatePolling();
            if (((ComponentWindow) entity).isClosed())
            {
                ecs.running = false;
            }
        }
    }

    @Override
    public void getMessage(GameEngine ecs, IMessage message)
    {
    }

}
