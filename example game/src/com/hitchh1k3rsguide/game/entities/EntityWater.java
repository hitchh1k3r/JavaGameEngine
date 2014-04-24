package com.hitchh1k3rsguide.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

import com.hitchh1k3rsguide.game.Game;
import com.hitchh1k3rsguide.gameEngine.components.ComponentCollision;
import com.hitchh1k3rsguide.gameEngine.components.ComponentPlaysSound;
import com.hitchh1k3rsguide.gameEngine.components.ComponentRenderable;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.AbstractBoundingVolume;
import com.hitchh1k3rsguide.gameEngine.utilities.physics.AxisAlignedBoundingBox;

public class EntityWater extends AbstractEntity implements ComponentRenderable,
        ComponentPlaysSound, ComponentCollision
{

    Color waterColor;
    String soundID;
    AxisAlignedBoundingBox bounds;
    boolean playSplash;

    public EntityWater(long index)
    {
        super(index);
        waterColor = new Color(0, 100, 255, 200);
        bounds = new AxisAlignedBoundingBox(Game.GAME_WIDTH / 2, Game.GAME_HEIGHT - 87.5,
                Game.GAME_WIDTH, 175);
        playSplash = false;
    }

    @Override
    public void draw(Graphics2D g)
    {
        g.setColor(waterColor);
        g.fillRect(0, Game.GAME_HEIGHT - 175, Game.GAME_WIDTH, 175);
    }

    @Override
    public int getZIndex()
    {
        return 1;
    }

    @Override
    public void playSounds(SoundSystem soundSystem)
    {
        if (playSplash)
        {
            playSplash = false;
            soundSystem.setVolume(soundSystem.quickPlay(false,
                    getClass().getResource("/assets/snd/Splash.ogg"), "Splash.ogg", false,
                    0 - (Game.GAME_WIDTH / 2), (int) (Math.random() * Game.GAME_HEIGHT)
                            - (Game.GAME_HEIGHT / 2), -1, SoundSystemConfig.ATTENUATION_NONE, 0),
                    1.0f);
        }
    }

    @Override
    public void collidesWith(ComponentCollision other)
    {
    }

    @Override
    public AbstractBoundingVolume getBounds()
    {
        return bounds;
    }

}
