package com.hitchh1k3rsguide.game.systems;

import java.awt.event.KeyEvent;
import java.util.Collection;

import com.hitchh1k3rsguide.game.components.ComponentSlideable;
import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentWindow;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;
import com.hitchh1k3rsguide.gameEngine.systems.ISystem;

public class SystemSliding implements ISystem
{

    @Override
    public void update(GameEngine ecs)
    {
        Collection<AbstractEntity> windows = ecs.getAll(ComponentWindow.class).values();
        Collection<AbstractEntity> slideableEntities = ecs.getAll(ComponentSlideable.class)
                .values();
        for (AbstractEntity window : windows)
        {
            for (AbstractEntity slider : slideableEntities)
            {
                if (((ComponentWindow) window).isKeyDown(KeyEvent.VK_RIGHT))
                {
                    ((ComponentSlideable) slider).slideRight();
                }
                if (((ComponentWindow) window).isKeyDown(KeyEvent.VK_LEFT))
                {
                    ((ComponentSlideable) slider).slideLeft();
                }
            }
        }
    }

    @Override
    public void getMessage(GameEngine ecs, IMessage message)
    {
    }

}
