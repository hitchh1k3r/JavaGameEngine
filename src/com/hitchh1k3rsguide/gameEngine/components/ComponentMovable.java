package com.hitchh1k3rsguide.gameEngine.components;

import com.hitchh1k3rsguide.gameEngine.utilities.physics.Vec2d;

public interface ComponentMovable extends IComponent
{

    public Vec2d getPosition();

    public Vec2d getVelocity();

    public void setPosition(double x, double y);

    /** Entity specific extra move code. */
    public void move();

}
