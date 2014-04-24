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

    public Vec2d copy()
    {
        return new Vec2d(x, y);
    }

    public void rotate(double angle)
    {
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        double newX = x * c + y * s;
        y = -x * s + y * c;
        x = newX;
    }

    public double sqDist(Vec2d other)
    {
        return ((x - other.x) * (x - other.x)) + ((y - other.y) * (y - other.y));
    }

    public void rotateAround(double angle, Vec2d center)
    {
        x -= center.x;
        y -= center.y;
        rotate(angle);
        x += center.x;
        y += center.y;
    }

    public void set(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

}
