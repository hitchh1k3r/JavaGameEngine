package com.hitchh1k3rsguide.game.entities;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import com.hitchh1k3rsguide.game.Game;
import com.hitchh1k3rsguide.game.utilities.graphs.GraphicsNode;
import com.hitchh1k3rsguide.game.utilities.graphs.NodeTraversal;
import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentArrowKeys;
import com.hitchh1k3rsguide.gameEngine.components.ComponentCollision;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMovable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentRenderable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentUpkeep;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.utilities.graphics.LayeredImage;
import com.hitchh1k3rsguide.gameEngine.utilities.graphics.Sprite;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.AbstractBoundingVolume;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.AxisAlignedBoundingBox;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.Vec2d;

public class EntityRagDoll extends AbstractEntity implements ComponentRenderable, ComponentMovable,
        ComponentCollision, ComponentArrowKeys, ComponentUpkeep
{

    int[] wasd;
    Vec2d position;
    Vec2d velocity;
    AxisAlignedBoundingBox bounds;
    private static Sprite[] parts = null;
    GraphicsNode dagRoot, dagNeck, dagHead, dagLegRU, dagLegRL, dagFootR, dagLegLU, dagLegLL,
            dagFootL, dagArmRU, dagArmRL, dagHandR, dagArmLU, dagArmLL, dagHandL;
    int age;

    public EntityRagDoll(long index)
    {
        super(index);
        wasd = new int[] { KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D };
        position = new Vec2d(300, 300);
        velocity = new Vec2d(0, 0);
        bounds = new AxisAlignedBoundingBox(0, 0, 65, 150);
        bounds.setCenter(position);
        if (parts == null)
        {
            parts = new Sprite[] { new Sprite("guy/body.png", 16, 59),
                    new Sprite("guy/neck.png", 9, 21), new Sprite("guy/head.png", 28, 43),
                    new Sprite("guy/leg.r.u.png", 5, 13), new Sprite("guy/leg.r.l.png", 9, 12),
                    new Sprite("guy/foot.r.png", 9, 5), new Sprite("guy/leg.l.u.png", 5, 12),
                    new Sprite("guy/leg.l.l.png", 9, 12), new Sprite("guy/foot.l.png", 8, 5),
                    new Sprite("guy/arm.r.u.png", 6, 8), new Sprite("guy/arm.r.l.png", 7, 7),
                    new Sprite("guy/hand.r.png", 6, 9), new Sprite("guy/arm.l.u.png", 4, 6),
                    new Sprite("guy/arm.l.l.png", 7, 6), new Sprite("guy/hand.l.png", 5, 9) };
        }
        dagRoot = new GraphicsNode(parts[0], null, null, 31);
        dagNeck = new GraphicsNode(parts[1], null, null, 41);
        dagNeck.origin.set(5, -55);
        dagHead = new GraphicsNode(parts[2], null, null, 42);
        dagHead.origin.set(4, -16);
        dagLegRU = new GraphicsNode(parts[3], null, null, 51);
        dagLegRU.origin.set(-5, 3);
        dagLegRU.rotation = Math.PI / 2;
        dagLegRL = new GraphicsNode(parts[4], null, null, 52);
        dagLegRL.origin.set(37, 0);
        dagFootR = new GraphicsNode(parts[5], null, null, 53);
        dagFootR.origin.set(34, 2);
        dagFootR.rotation = -Math.PI / 2;
        dagLegLU = new GraphicsNode(parts[6], null, null, 21);
        dagLegLU.origin.set(13, 4);
        dagLegLU.rotation = Math.PI / 2;
        dagLegLL = new GraphicsNode(parts[7], null, null, 22);
        dagLegLL.origin.set(34, -1);
        dagFootL = new GraphicsNode(parts[8], null, null, 23);
        dagFootL.origin.set(31, 1);
        dagFootL.rotation = -Math.PI / 2;
        dagArmRU = new GraphicsNode(parts[9], null, null, 61);
        dagArmRU.origin.set(-5, -51);
        dagArmRU.rotation = Math.PI;
        dagArmRL = new GraphicsNode(parts[10], null, null, 62);
        dagArmRL.origin.set(34, 0);
        dagHandR = new GraphicsNode(parts[11], null, null, 63);
        dagHandR.origin.set(20, 0);
        dagArmLU = new GraphicsNode(parts[12], null, null, 11);
        dagArmLU.origin.set(14, -44);
        dagArmLL = new GraphicsNode(parts[13], null, null, 12);
        dagArmLL.origin.set(33, 1);
        dagHandL = new GraphicsNode(parts[14], null, null, 13);
        dagHandL.origin.set(18, 0);
        dagRoot.getChildren().add(dagNeck);
        dagRoot.getChildren().add(dagArmLU);
        dagRoot.getChildren().add(dagArmRU);
        dagRoot.getChildren().add(dagLegLU);
        dagRoot.getChildren().add(dagLegRU);
        dagNeck.getChildren().add(dagHead);
        dagArmLU.getChildren().add(dagArmLL);
        dagArmRU.getChildren().add(dagArmRL);
        dagArmLL.getChildren().add(dagHandL);
        dagArmRL.getChildren().add(dagHandR);
        dagLegLU.getChildren().add(dagLegLL);
        dagLegRU.getChildren().add(dagLegRL);
        dagLegLL.getChildren().add(dagFootL);
        dagLegRL.getChildren().add(dagFootR);
    }

    @Override
    public int[] getKeyAliases()
    {
        return wasd;
    }

    @Override
    public void pressArrow(Direction arrow)
    {
        double orthSpeed = 3;
        double diagSpeed = 2.12;
        double orthAccel = 0.1;
        double diagAccel = 0.0707;
        switch (arrow)
        {
            case DOWN:
                if (velocity.y < orthSpeed)
                    velocity.y += orthAccel;
                break;
            case DOWN_LEFT:
                if (velocity.y < diagSpeed)
                    velocity.y += diagAccel;
                if (velocity.x > -diagSpeed)
                    velocity.x -= diagAccel;
                break;
            case DOWN_RIGHT:
                if (velocity.y < diagSpeed)
                    velocity.y += diagAccel;
                if (velocity.x < diagSpeed)
                    velocity.x += diagAccel;
                break;
            case LEFT:
                if (velocity.x > -orthSpeed)
                    velocity.x -= orthAccel;
                break;
            case RIGHT:
                if (velocity.x < orthSpeed)
                    velocity.x += orthAccel;
                break;
            case UP:
                if (velocity.y > -orthSpeed)
                    velocity.y -= orthAccel;
                break;
            case UP_LEFT:
                if (velocity.y > -diagSpeed)
                    velocity.y -= diagAccel;
                if (velocity.x > -diagSpeed)
                    velocity.x -= diagAccel;
                break;
            case UP_RIGHT:
                if (velocity.y > -diagSpeed)
                    velocity.y -= diagAccel;
                if (velocity.x < diagSpeed)
                    velocity.x += diagAccel;
        }
    }

    public double getFallingSpeed()
    {
        return velocity.y;
    }

    public double getMaxFallSpeed(double gravity)
    {
        return 5 * gravity;
    }

    public double getGravityMultiplier()
    {
        return 1;
    }

    public void setFallingSpeed(double speed)
    {
        velocity.y = speed;
    }

    @Override
    public AbstractBoundingVolume getBounds()
    {
        return bounds;
    }

    @Override
    public void collidesWith(ComponentCollision other)
    {
        System.out.println("RAGDOLL COLLIDES WITH = " + other);
    }

    @Override
    public Vec2d getPosition()
    {
        return position;
    }

    @Override
    public Vec2d getVelocity()
    {
        return velocity;
    }

    @Override
    public void setPosition(double x, double y)
    {
        position.x = x;
        position.y = y;
    }

    @Override
    public void move()
    {
        velocity.x *= 0.965;
        velocity.y *= 0.965;
    }

    @Override
    public void draw(Graphics2D g)
    {
        AffineTransform af = g.getTransform();
        g.translate(position.x, position.y);
        LayeredImage zBuffer = new LayeredImage(Game.GAME_WIDTH, Game.GAME_HEIGHT);
        NodeTraversal.drawGraph(g, dagRoot, zBuffer);
        zBuffer.draw(g);
        g.setTransform(af);
    }

    @Override
    public int getZIndex()
    {
        return 0;
    }

    @Override
    public void primaryUpdate(GameEngine ecs)
    {
        update(ecs);
    }

    @Override
    public void update(GameEngine ecs)
    {
        ++age;
        double sin = Math.sin(age / 10.0);
        dagArmLU.rotation = (sin / 1.5) + 1.5;
        dagArmRU.rotation = ((-sin) / 1.5) + 1.5;
        dagArmLL.rotation = (sin / 2.5) - 1.5;
        dagArmRL.rotation = ((-sin) / 2.5) - 1.5;
        dagLegRU.rotation = (sin / 1.5) + 1.5;
        dagLegLU.rotation = ((-sin) / 1.5) + 1.5;
        dagLegRL.rotation = (sin / 2.5) + 0.25;
        dagLegLL.rotation = ((-sin) / 2.5) + 0.25;
        dagFootR.rotation = Math.abs(sin / 1.5) - 1.8;
        dagFootL.rotation = Math.abs(-sin / 1.5) - 1.8;
    }

}
