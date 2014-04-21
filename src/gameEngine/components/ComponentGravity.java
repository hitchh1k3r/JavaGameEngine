package com.hitchh1k3rsguide.gameEngine.components;

public interface ComponentGravity extends IComponent
{

    public double getFallingSpeed();

    public double getMaxFallSpeed(double gravity);

    public double getGravityMultiplier();

    public void setFallingSpeed(double speed);

}
