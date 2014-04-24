package com.hitchh1k3rsguide.gameEngine.components;

import com.hitchh1k3rsguide.gameEngine.utilities.physics.AbstractBoundingVolume;

public interface ComponentCollision extends IComponent
{

    public AbstractBoundingVolume getBounds();

    public void collidesWith(ComponentCollision other);

}
