package com.hitchh1k3rsguide.gameEngine.systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentRenderable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentWindow;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;

public class SystemRendering implements ISystem
{

    Comparator<AbstractEntity> zSort;

    public SystemRendering()
    {
        zSort = new Comparator<AbstractEntity>()
        {

            @Override
            public int compare(AbstractEntity a, AbstractEntity b)
            {
                return ((ComponentRenderable) a).getZIndex()
                        - ((ComponentRenderable) b).getZIndex();
            }

        };
    }

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
        Collection<AbstractEntity> windows = ecs.getAll(ComponentWindow.class).values();
        ArrayList<AbstractEntity> graphics = new ArrayList<AbstractEntity>(ecs.getAll(
                ComponentRenderable.class).values());
        Collections.sort(graphics, zSort);
        for (AbstractEntity window : windows)
        {
            for (AbstractEntity graphic : graphics)
            {
                ((ComponentRenderable) graphic).draw(((ComponentWindow) window).getBufferContext());
            }
        }
    }
}
