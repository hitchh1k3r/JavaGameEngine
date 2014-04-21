package com.hitchh1k3rsguide.game.systems;

import java.awt.event.KeyEvent;
import java.util.Collection;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentWindow;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;
import com.hitchh1k3rsguide.gameEngine.messages.MessageSetGravity;
import com.hitchh1k3rsguide.gameEngine.systems.ISystem;

public class SystemDebug implements ISystem
{

    private boolean lowQoS = false;

    @Override
    public void update(GameEngine ecs)
    {
        Collection<AbstractEntity> windows = ecs.getAll(ComponentWindow.class).values();
        for (AbstractEntity window : windows)
        {
            if (((ComponentWindow) window).isKeyPressed(KeyEvent.VK_1))
            {
                ecs.sendMessage(new MessageSetGravity(0.25));
            }
            if (((ComponentWindow) window).isKeyPressed(KeyEvent.VK_2))
            {
                ecs.sendMessage(new MessageSetGravity(0.5));
            }
            if (((ComponentWindow) window).isKeyPressed(KeyEvent.VK_3))
            {
                ecs.sendMessage(new MessageSetGravity(1));
            }
            if (((ComponentWindow) window).isKeyPressed(KeyEvent.VK_Q))
            {
                lowQoS = !lowQoS;
            }
        }
        if (lowQoS)
        {
            try
            {
                Thread.sleep((long) (16 + (Math.random() * 128)));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getMessage(GameEngine ecs, IMessage message)
    {
    }

}
