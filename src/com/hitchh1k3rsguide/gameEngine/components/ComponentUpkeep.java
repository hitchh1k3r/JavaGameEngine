package com.hitchh1k3rsguide.gameEngine.components;

import com.hitchh1k3rsguide.gameEngine.GameEngine;

public interface ComponentUpkeep extends IComponent
{

    public void primaryUpdate(GameEngine ecs);

    public void update(GameEngine ecs);

}
