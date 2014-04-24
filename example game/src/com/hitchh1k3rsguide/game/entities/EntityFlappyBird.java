package com.hitchh1k3rsguide.game.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.hitchh1k3rsguide.game.Game;
import com.hitchh1k3rsguide.game.components.ComponentDivable;
import com.hitchh1k3rsguide.game.components.ComponentFlappable;
import com.hitchh1k3rsguide.game.components.ComponentSlideable;
import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentCollision;
import com.hitchh1k3rsguide.gameEngine.components.ComponentGravity;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMessageable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMovable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentRenderable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentUpkeep;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;
import com.hitchh1k3rsguide.gameEngine.messages.MessageResponse;
import com.hitchh1k3rsguide.gameEngine.utilities.graphics.Sprite;
import com.hitchh1k3rsguide.gameEngine.utilities.graphics.Sprite.Property;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.AbstractBoundingVolume;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.CircularBounding;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.Vec2d;

public class EntityFlappyBird extends AbstractEntity implements ComponentRenderable,
        ComponentFlappable, ComponentGravity, ComponentSlideable, ComponentDivable,
        ComponentMovable, ComponentUpkeep, ComponentMessageable, ComponentCollision
{

    private enum Action
    {
        NONE, FLAP, DIVE
    }

    private static Sprite birdGFX;
    int flapTime = -1;
    Vec2d position;
    Vec2d velocity;
    boolean inWater = false;
    boolean inWaterLast = false;
    boolean askForGravity = false;
    int request;
    int actionIndex;
    Action lastAction;
    ArrayList<Action> actionQue;
    CircularBounding bounds;

    public EntityFlappyBird(long index, int value)
    {
        super(index);
        if (birdGFX == null)
        {
            birdGFX = new Sprite("Flappy.png");
        }
        this.position = new Vec2d(100 + (value * 50), 25 + (value * 100));
        this.velocity = new Vec2d(0, 10);
        request = -1;
        lastAction = Action.NONE;
        actionQue = new ArrayList<Action>();
        for (int i = 0; i <= 10 * 3; ++i)
        {
            actionQue.add(Action.NONE);
        }
        actionIndex = value * 10;
        bounds = new CircularBounding(position, 25);
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
        lastAction = Action.FLAP;
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
        lastAction = Action.DIVE;
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
        actionQue.add(lastAction);
        switch (actionQue.get(actionIndex))
        {
            case DIVE:
                flapTime = -1;
                velocity.y = 15;
                break;
            case FLAP:
                flapTime = 1;
                break;
            default:
                break;
        }
        actionQue.remove(0);
        lastAction = Action.NONE;
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

    @Override
    public void primaryUpdate(GameEngine ecs)
    {
        update(ecs);
        inWater = inWaterLast;
        inWaterLast = false;
    }

    @Override
    public AbstractBoundingVolume getBounds()
    {
        return bounds;
    }

    @Override
    public void collidesWith(ComponentCollision other)
    {
        if (other instanceof EntityWater)
        {
            if (!inWater)
            {
                ((EntityWater) other).playSplash = true;
            }
            inWater = true;
            inWaterLast = true;
        }
    }

}
