package com.hitchh1k3rsguide.gameEngine.systems;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;

public interface ISystem
{

    public void primaryUpdate(GameEngine ecs);

    public void update(GameEngine ecs);

    public void getMessage(GameEngine ecs, IMessage message);

}
