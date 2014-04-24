package com.hitchh1k3rsguide.gameEngine.systems;

import java.util.Collection;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMessageable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentUpkeep;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;

public class SystemUpkeep implements ISystem
{

    @Override
    public void update(GameEngine ecs)
    {
        Collection<AbstractEntity> entities = ecs.getAll(ComponentUpkeep.class).values();
        for (AbstractEntity entity : entities)
        {
            ((ComponentUpkeep) entity).update(ecs);
        }
    }

    @Override
    public void getMessage(GameEngine ecs, IMessage message)
    {
        Collection<AbstractEntity> entities = ecs.getAll(ComponentMessageable.class).values();
        for (AbstractEntity entity : entities)
        {
            ((ComponentMessageable) entity).getMessage(ecs, message);
        }
    }

    @Override
    public void primaryUpdate(GameEngine ecs)
    {
        Collection<AbstractEntity> entities = ecs.getAll(ComponentUpkeep.class).values();
        for (AbstractEntity entity : entities)
        {
            ((ComponentUpkeep) entity).primaryUpdate(ecs);
        }
    }

}
