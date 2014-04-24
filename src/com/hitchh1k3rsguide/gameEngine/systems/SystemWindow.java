package com.hitchh1k3rsguide.gameEngine.systems;

import java.awt.event.KeyEvent;
import java.util.Collection;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentArrowKeys;
import com.hitchh1k3rsguide.gameEngine.components.ComponentArrowKeys.Direction;
import com.hitchh1k3rsguide.gameEngine.components.ComponentWindow;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;

public class SystemWindow implements ISystem
{

    @Override
    public void update(GameEngine ecs)
    {
    }

    @Override
    public void getMessage(GameEngine ecs, IMessage message)
    {
    }

    @Override
    public void primaryUpdate(GameEngine ecs)
    {
        Collection<AbstractEntity> windowEntities = ecs.getAll(ComponentWindow.class).values();
        Collection<AbstractEntity> arrowEntities = ecs.getAll(ComponentArrowKeys.class).values();
        for (AbstractEntity window : windowEntities)
        {
            ((ComponentWindow) window).swapBuffer();
            ((ComponentWindow) window).updatePolling();
            if (((ComponentWindow) window).isClosed())
            {
                ecs.running = false;
            }
            int x = 0;
            int y = 0;
            if (((ComponentWindow) window).isKeyDown(KeyEvent.VK_UP))
            {
                y -= 1;
            }
            if (((ComponentWindow) window).isKeyDown(KeyEvent.VK_DOWN))
            {
                y += 1;
            }
            if (((ComponentWindow) window).isKeyDown(KeyEvent.VK_LEFT))
            {
                x -= 1;
            }
            if (((ComponentWindow) window).isKeyDown(KeyEvent.VK_RIGHT))
            {
                x += 1;
            }
            for (AbstractEntity arrows : arrowEntities)
            {
                int aX = x;
                int aY = y;
                int[] aliases = ((ComponentArrowKeys) arrows).getKeyAliases(); // [UP, DOWN, LEFT, RIGHT]
                if (aliases != null && aliases.length >= 4)
                {
                    if (((ComponentWindow) window).isKeyDown(aliases[0])) // UP
                    {
                        aY -= 1;
                    }
                    if (((ComponentWindow) window).isKeyDown(aliases[1])) // DOWN
                    {
                        aY += 1;
                    }
                    if (((ComponentWindow) window).isKeyDown(aliases[2])) // LEFT
                    {
                        aX -= 1;
                    }
                    if (((ComponentWindow) window).isKeyDown(aliases[3])) // RIGHT
                    {
                        aX += 1;
                    }
                }
                if (aX == 0)
                {
                    if (aY < 0)
                    {
                        ((ComponentArrowKeys) arrows).pressArrow(Direction.UP);
                    }
                    else if (aY > 0)
                    {
                        ((ComponentArrowKeys) arrows).pressArrow(Direction.DOWN);
                    }
                }
                else if (aX < 0)
                {
                    if (aY == 0)
                    {
                        ((ComponentArrowKeys) arrows).pressArrow(Direction.LEFT);
                    }
                    else if (aY > 0)
                    {
                        ((ComponentArrowKeys) arrows).pressArrow(Direction.DOWN_LEFT);
                    }
                    else
                    {
                        ((ComponentArrowKeys) arrows).pressArrow(Direction.UP_LEFT);
                    }
                }
                else
                {
                    if (aY == 0)
                    {
                        ((ComponentArrowKeys) arrows).pressArrow(Direction.RIGHT);
                    }
                    else if (aY > 0)
                    {
                        ((ComponentArrowKeys) arrows).pressArrow(Direction.DOWN_RIGHT);
                    }
                    else
                    {
                        ((ComponentArrowKeys) arrows).pressArrow(Direction.UP_RIGHT);
                    }
                }
            }
        }
    }

}
