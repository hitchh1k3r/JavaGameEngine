package com.hitchh1k3rsguide.game.entities;

import java.awt.Graphics2D;

import com.hitchh1k3rsguide.game.Game;
import com.hitchh1k3rsguide.game.components.ComponentDivable;
import com.hitchh1k3rsguide.game.components.ComponentFlappable;
import com.hitchh1k3rsguide.game.components.ComponentSlideable;
import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentGravity;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMessageable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMovable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentRenderable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentUpkeep;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;
import com.hitchh1k3rsguide.gameEngine.messages.MessageRequest;
import com.hitchh1k3rsguide.gameEngine.messages.MessageResponse;
import com.hitchh1k3rsguide.gameEngine.systems.SystemSimplePlatformPhysics;
import com.hitchh1k3rsguide.gameEngine.utilities.graphics.Sprite;
import com.hitchh1k3rsguide.gameEngine.utilities.graphics.Sprite.Property;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.Vec2d;

public class EntityFlappyBird extends AbstractEntity implements ComponentRenderable,
        ComponentFlappable, ComponentGravity, ComponentSlideable, ComponentDivable,
        ComponentMovable, ComponentUpkeep, ComponentMessageable
{

    private static Sprite birdGFX;
    int flapTime = -1;
    Vec2d position;
    Vec2d velocity;
    boolean inWater = false;
    boolean askForGravity = false;
    int request;

    public EntityFlappyBird(long index, int value)
    {
        super(index);
        if (birdGFX == null)
        {
            birdGFX = new Sprite("Flappy.png");
        }
        this.position = new Vec2d(100 + (value * 50), 100 + (value * 50));
        this.velocity = new Vec2d(0, 0);
        request = -1;
    }

    @Override
    public void draw(Graphics2D g)
    {
        birdGFX.draw(g, Property.Pos2i, (int) position.x, (int) position.y, Property.Scale2d, 0.5,
                0.5, Property.Rotate1d, velocity.y / 15.0, (inWater ? Property.FlipVert : null));
    }

    @Override
    public int getZIndex()
    {
        return 0;
    }

    @Override
    public void flap()
    {
        flapTime = 1;
    }

    @Override
    public double getFallingSpeed()
    {
        return velocity.y;
    }

    @Override
    public double getMaxFallSpeed(double gravity)
    {
        return 20 * gravity * getGravityMultiplier();
    }

    @Override
    public double getGravityMultiplier()
    {
        return (position.y < Game.GAME_HEIGHT - 200 ? 1 : 0.5);
    }

    @Override
    public void setFallingSpeed(double speed)
    {
        velocity.y = speed;
    }

    @Override
    public void slideRight()
    {
        velocity.x = 50.0 / 60.0; // 50 pixels/seconds
    }

    @Override
    public void slideLeft()
    {
        velocity.x = -50.0 / 60.0; // 50 pixels/seconds
    }

    @Override
    public void dive()
    {
        flapTime = -1;
        velocity.y += 10;
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
        if (position.y < 25)
        {
            position.y = 25;
            velocity.y = 0;
        }
        if (position.y > Game.GAME_HEIGHT - 25)
        {
            position.y = Game.GAME_HEIGHT - 25;
            velocity.y = 0;
        }
        velocity.x = 0;
        if (flapTime >= 0)
        {
            velocity.y -= 7 * getGravityMultiplier();
            if (velocity.y < -10 * getGravityMultiplier())
            {
                velocity.y = -10 * getGravityMultiplier();
            }
            flapTime += 1;
            if (flapTime > 3)
            {
                flapTime = -1;
            }
        }
    }

    @Override
    public void update(GameEngine ecs)
    {
        if (inWater && position.y < Game.GAME_HEIGHT - 175)
        {
            inWater = false;
        }
        else if (!inWater && position.y > Game.GAME_HEIGHT - 175)
        {
            if (!askForGravity)
            {
                askForGravity = true;
                MessageRequest requestMessage = new MessageRequest(
                        SystemSimplePlatformPhysics.REQUEST_getGravity);
                request = requestMessage.index;
                ecs.sendMessage(requestMessage);
            }
            inWater = true;
        }
    }

    @Override
    public void getMessage(GameEngine ecs, IMessage message)
    {
        if (message instanceof MessageResponse)
        {
            if (((MessageResponse) message).index == request)
            {
                System.out
                        .println("GOT GRAVITY BY REQUEST: " + ((MessageResponse) message).payload);
            }
        }
    }

}
