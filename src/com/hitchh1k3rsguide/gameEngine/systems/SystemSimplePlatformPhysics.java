package com.hitchh1k3rsguide.gameEngine.systems;

import java.util.Collection;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentCollision;
import com.hitchh1k3rsguide.gameEngine.components.ComponentGravity;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMovable;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;
import com.hitchh1k3rsguide.gameEngine.messages.MessageRequest;
import com.hitchh1k3rsguide.gameEngine.messages.MessageSetGravity;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.PhysicsHelper;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.Vec2d;

public class SystemSimplePlatformPhysics implements ISystem
{

    public static final String REQUEST_getGravity = "SimplePlatformPhysics_getGravity";
    private double gravity;

    public SystemSimplePlatformPhysics(double gravity)
    {
        this.gravity = gravity;
    }

    @Override
    public void update(GameEngine ecs)
    {
        Collection<AbstractEntity> fallingEntities = ecs.getAll(ComponentGravity.class).values();
        Collection<AbstractEntity> movingEntities = ecs.getAll(ComponentMovable.class).values();
        Collection<AbstractEntity> collidingEntities = ecs.getAll(ComponentCollision.class)
                .values();
        doGravity(fallingEntities);
        moveEntities(movingEntities);
        collisionDetection(collidingEntities.toArray());
    }

    private void collisionDetection(Object[] collidingEntities)
    {
        for (int i0 = 0; i0 < collidingEntities.length; ++i0)
        {
            ComponentCollision entityA = (ComponentCollision) collidingEntities[i0];
            for (int i1 = i0 + 1; i1 < collidingEntities.length; ++i1)
            {
                ComponentCollision entityB = (ComponentCollision) collidingEntities[i1];
                if (PhysicsHelper.collisionTest(entityA.getBounds(), entityB.getBounds()))
                {
                    entityA.collidesWith(entityB);
                    entityB.collidesWith(entityA);
                }
            }
        }
    }

    private void moveEntities(Collection<AbstractEntity> movingEntities)
    {
        for (AbstractEntity entity : movingEntities)
        {
            ComponentMovable movingEntity = (ComponentMovable) entity;
            Vec2d pos = movingEntity.getPosition();
            pos.add(movingEntity.getVelocity());
            movingEntity.setPosition(pos.x, pos.y);
            movingEntity.move();
        }

    }

    private void doGravity(Collection<AbstractEntity> fallingEntities)
    {
        for (AbstractEntity entity : fallingEntities)
        {
            ComponentGravity fallingBody = (ComponentGravity) entity;
            double activeGravity = gravity * fallingBody.getGravityMultiplier();
            double maxSpeed = fallingBody.getMaxFallSpeed(gravity);
            double speed = fallingBody.getFallingSpeed();
            if (speed + activeGravity < maxSpeed)
            {
                speed += activeGravity;
            }
            else
            {
                speed += (maxSpeed - speed) / 5;
            }
            fallingBody.setFallingSpeed(speed);
        }
    }

    @Override
    public void getMessage(GameEngine ecs, IMessage message)
    {
        if (message instanceof MessageSetGravity)
        {
            gravity = ((MessageSetGravity) message).newGravity;
        }
        if (message instanceof MessageRequest)
        {
            if (((MessageRequest) message).requestName.equals(REQUEST_getGravity))
            {
                ecs.requestRespond((MessageRequest) message, gravity);
            }
        }
    }

    @Override
    public void primaryUpdate(GameEngine ecs)
    {
        update(ecs);
    }

}
