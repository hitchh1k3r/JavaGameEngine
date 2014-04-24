package com.hitchh1k3rsguide.gameEngine.utilities.physics;

public class AxisAlignedBoundingBox extends AbstractBoundingVolume
{

    Vec2d center, radius;

    public AxisAlignedBoundingBox(double x, double y, double width, double height)
    {
        center = new Vec2d(x, y);
        radius = new Vec2d(width / 2, height / 2);
    }

    @Override
    public AxisAlignedBoundingBox getSweepBounds()
    {
        return this;
    }

    public double getMinX()
    {
        return center.x - radius.x;
    }

    public double getMaxX()
    {
        return center.x + radius.x;
    }

    public double getMinY()
    {
        return center.y - radius.y;
    }

    public double getMaxY()
    {
        return center.y + radius.y;
    }

    public boolean isColliding(AxisAlignedBoundingBox other)
    {
        return (Math.abs(center.x - other.center.x) < radius.x + other.radius.x)
                && (Math.abs(center.y - other.center.y) < radius.y + other.radius.y);
    }

    public boolean isInside(Vec2d point)
    {
        return (Math.abs(center.x - point.x) < radius.x)
                && (Math.abs(center.y - point.y) < radius.y);
    }

    public OrientedBoundingBox toOBB()
    {
        return new OrientedBoundingBox(center.x, center.y, radius.x * 2, radius.y * 2, 0);
    }

    public Vec2d getCorner(int i) // 0 TL, 1 TR, 2 BR, 3 BL
    {
        switch (i)
        {
            case 0:
                return new Vec2d(center.x - radius.x, center.y - radius.y);
            case 1:
                return new Vec2d(center.x + radius.x, center.y - radius.y);
            case 2:
                return new Vec2d(center.x + radius.x, center.y + radius.y);
            case 3:
                return new Vec2d(center.x - radius.x, center.y + radius.y);
        }
        return null;
    }

    public void setCenter(double x, double y)
    {
        center.x = x;
        center.y = y;
    }

    public void setCenter(Vec2d center)
    {
        this.center = center;
    }

}
