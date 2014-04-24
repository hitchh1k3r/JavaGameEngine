package com.hitchh1k3rsguide.gameEngine.utilities.physics;

public class PhysicsHelper
{

    public static boolean collisionTest(AbstractBoundingVolume a, AbstractBoundingVolume b)
    {
        AxisAlignedBoundingBox aSweep = a.getSweepBounds();
        AxisAlignedBoundingBox bSweep = b.getSweepBounds();
        if (aSweep.isColliding(bSweep))
        {
            if (a instanceof AxisAlignedBoundingBox)
            {
                if (b instanceof AxisAlignedBoundingBox)
                {
                    return AABBvAABB((AxisAlignedBoundingBox) a, (AxisAlignedBoundingBox) b);
                }
                else if (b instanceof OrientedBoundingBox)
                {
                    return OBBvOBB(((AxisAlignedBoundingBox) a).toOBB(), (OrientedBoundingBox) b);
                }
                else if (b instanceof CircularBounding)
                {
                    return OBBvCircle(((AxisAlignedBoundingBox) a).toOBB(), (CircularBounding) b);
                }
            }
            else if (a instanceof OrientedBoundingBox)
            {
                if (b instanceof AxisAlignedBoundingBox)
                {
                    return OBBvOBB((OrientedBoundingBox) a, ((AxisAlignedBoundingBox) b).toOBB());
                }
                else if (b instanceof OrientedBoundingBox)
                {
                    return OBBvOBB((OrientedBoundingBox) a, (OrientedBoundingBox) b);
                }
                else if (b instanceof CircularBounding)
                {
                    return OBBvCircle((OrientedBoundingBox) a, (CircularBounding) b);
                }
            }
            else if (a instanceof CircularBounding)
            {
                if (b instanceof AxisAlignedBoundingBox)
                {
                    return OBBvCircle(((AxisAlignedBoundingBox) b).toOBB(), (CircularBounding) a);
                }
                else if (b instanceof OrientedBoundingBox)
                {
                    return OBBvCircle((OrientedBoundingBox) b, (CircularBounding) a);
                }
                else if (b instanceof CircularBounding)
                {
                    return CirclevCircle((CircularBounding) a, (CircularBounding) b);
                }
            }
        }
        return false;
    }

    private static boolean CirclevCircle(CircularBounding a, CircularBounding b)
    {
        return (a.origin.sqDist(b.origin) < (a.radius + b.radius) * (a.radius + b.radius));
    }

    private static boolean OBBvCircle(OrientedBoundingBox a, CircularBounding b)
    {
        AxisAlignedBoundingBox deltaA = new AxisAlignedBoundingBox(a.center.x, a.center.y,
                a.radius.x * 2, a.radius.y * 2);
        Vec2d deltaO = b.origin.copy();
        if (deltaA.isInside(deltaO))
            return true;
        deltaO.rotateAround(a.getRotation(), a.center);
        Vec2d A = deltaA.getCorner(0);
        Vec2d B = deltaA.getCorner(1);
        Vec2d C = deltaA.getCorner(2);
        Vec2d D = deltaA.getCorner(3);
        double sqRadius = b.radius * b.radius;
        Vec2d p = nearestLinePoint(A, B, deltaO, true);
        if (p.sqDist(deltaO) < sqRadius)
            return true;
        p = nearestLinePoint(B, C, deltaO, true);
        if (p.sqDist(deltaO) < sqRadius)
            return true;
        p = nearestLinePoint(C, D, deltaO, true);
        if (p.sqDist(deltaO) < sqRadius)
            return true;
        p = nearestLinePoint(D, A, deltaO, true);
        if (p.sqDist(deltaO) < sqRadius)
            return true;
        return false;
    }

    private static boolean OBBvOBB(OrientedBoundingBox a, OrientedBoundingBox b)
    {
        OrientedBoundingBox aA = a.copy();
        OrientedBoundingBox aB = b.copy();
        OrientedBoundingBox bA = a.copy();
        OrientedBoundingBox bB = b.copy();
        aA.rotate(-a.getRotation());
        aB.rotate(-a.getRotation());
        bA.rotate(-b.getRotation());
        bB.rotate(-b.getRotation());
        return (aA.getSweepBounds().isColliding(aB.getSweepBounds()) && bA.getSweepBounds()
                .isColliding(bB.getSweepBounds()));
    }

    private static boolean AABBvAABB(AxisAlignedBoundingBox a, AxisAlignedBoundingBox b)
    {
        return true; // if it's passed the sweep then they collide
    }

    public static Vec2d nearestLinePoint(Vec2d A, Vec2d B, Vec2d P, boolean clamp)
    {
        double apx = P.x - A.x;
        double apy = P.y - A.y;
        double abx = B.x - A.x;
        double aby = B.y - A.y;

        double ab2 = abx * abx + aby * aby;
        double ap_ab = apx * abx + apy * aby;
        double t = ap_ab / ab2;
        if (clamp)
        {
            t = Math.min(Math.max(t, 0), 1);
        }

        return new Vec2d(A.x + abx * t, A.y + aby * t);
    }

    public boolean SegmentIntersectRectangle(double a_rectangleMinX, double a_rectangleMinY,
            double a_rectangleMaxX, double a_rectangleMaxY, double a_p1x, double a_p1y,
            double a_p2x, double a_p2y)
    {
        double minX = a_p1x;
        double maxX = a_p2x;

        if (a_p1x > a_p2x)
        {
            minX = a_p2x;
            maxX = a_p1x;
        }

        if (maxX > a_rectangleMaxX)
        {
            maxX = a_rectangleMaxX;
        }

        if (minX < a_rectangleMinX)
        {
            minX = a_rectangleMinX;
        }

        if (minX > maxX)
        {
            return false;
        }

        double minY = a_p1y;
        double maxY = a_p2y;

        double dx = a_p2x - a_p1x;

        if (Math.abs(dx) > 0.0000001)
        {
            double a = (a_p2y - a_p1y) / dx;
            double b = a_p1y - a * a_p1x;
            minY = a * minX + b;
            maxY = a * maxX + b;
        }

        if (minY > maxY)
        {
            double tmp = maxY;
            maxY = minY;
            minY = tmp;
        }

        if (maxY > a_rectangleMaxY)
        {
            maxY = a_rectangleMaxY;
        }

        if (minY < a_rectangleMinY)
        {
            minY = a_rectangleMinY;
        }

        if (minY > maxY)
        {
            return false;
        }

        return true;
    }
}
