package com.hitchh1k3rsguide.gameEngine.components;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;

public interface ComponentMessageable extends IComponent
{

    public void getMessage(GameEngine ecs, IMessage message);

}
