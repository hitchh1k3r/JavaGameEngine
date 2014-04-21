package com.hitchh1k3rsguide.gameEngine.systems;

import java.util.Collection;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentGravity;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMovable;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;
import com.hitchh1k3rsguide.gameEngine.messages.MessageRequest;
import com.hitchh1k3rsguide.gameEngine.messages.MessageSetGravity;
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
        for (int t = 0; t < ecs.tickFrames; ++t)
        {
            doGravity(fallingEntities);
            moveEntities(movingEntities);
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
                speed += (maxSpeed - speed) / 10;
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

}
