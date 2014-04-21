package com.hitchh1k3rsguide.gameEngine.utilities.physics;

public class Vec2d
{

    public double x, y;

    public Vec2d(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void add(Vec2d other)
    {
        this.x += other.x;
        this.y += other.y;
    }

}
