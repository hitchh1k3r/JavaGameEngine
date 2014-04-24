package com.hitchh1k3rsguide.gameEngine.utilities.physics;

public class OrientedBoundingBox extends AbstractBoundingVolume
{

    Vec2d center, radius;
    private double rotation;
    AxisAlignedBoundingBox MBR;

    public OrientedBoundingBox(double x, double y, double width, double height, double rotation)
    {
        center = new Vec2d(x, y);
        radius = new Vec2d(width / 2, height / 2);
        this.rotation = rotation;
        updateMBR();
    }

    @Override
    public AxisAlignedBoundingBox getSweepBounds()
    {
        return MBR;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
        updateMBR();
    }

    public double getRotation()
    {
        return rotation;
    }

    public void rotate(double angle)
    {
        rotation += angle;
        updateMBR();
    }

    private void updateMBR()
    {
        Vec2d left = new Vec2d(-radius.x, radius.y);
        Vec2d right = new Vec2d(radius.x, radius.y);
        left.rotate(rotation);
        right.rotate(rotation);
        double width = Math.max(Math.abs(left.x), Math.abs(right.x)) * 2;
        double height = Math.max(Math.abs(left.y), Math.abs(right.y)) * 2;
        MBR = new AxisAlignedBoundingBox(center.x, center.y, width, height);
    }

    public OrientedBoundingBox copy()
    {
        return new OrientedBoundingBox(center.x, center.y, radius.x * 2, radius.y * 2, rotation);
    }

}
