package com.hitchh1k3rsguide.game.systems;

import java.awt.event.MouseEvent;
import java.util.Collection;

import com.hitchh1k3rsguide.game.components.ComponentDivable;
import com.hitchh1k3rsguide.game.components.ComponentFlappable;
import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentWindow;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;
import com.hitchh1k3rsguide.gameEngine.systems.ISystem;

public class SystemFlapping implements ISystem
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
        Collection<AbstractEntity> windows = ecs.getAll(ComponentWindow.class).values();
        Collection<AbstractEntity> flappableEntities = ecs.getAll(ComponentFlappable.class)
                .values();
        Collection<AbstractEntity> divableEntities = ecs.getAll(ComponentDivable.class).values();
        for (AbstractEntity window : windows)
        {
            for (AbstractEntity flapper : flappableEntities)
            {
                if (((ComponentWindow) window).isMousePressed(MouseEvent.BUTTON1)
                        || ((ComponentWindow) window).isMouseRepeat(MouseEvent.BUTTON1))
                {
                    ((ComponentFlappable) flapper).flap();
                }
            }
            for (AbstractEntity diver : divableEntities)
            {
                if (((ComponentWindow) window).isMousePressed(MouseEvent.BUTTON3)
                        || ((ComponentWindow) window).isMouseRepeat(MouseEvent.BUTTON3))
                {
                    ((ComponentDivable) diver).dive();
                }
            }
        }
    }

}
