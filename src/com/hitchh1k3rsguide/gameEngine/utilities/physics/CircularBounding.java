package com.hitchh1k3rsguide.gameEngine.utilities.physics;

public class CircularBounding extends AbstractBoundingVolume
{

    Vec2d origin;
    double radius;

    public CircularBounding(Vec2d origin, double radius)
    {
        this.origin = origin;
        this.radius = radius;
    }

    @Override
    public AxisAlignedBoundingBox getSweepBounds()
    {
        return new AxisAlignedBoundingBox(origin.x, origin.y, radius * 2, radius * 2);
    }

}
