package com.hitchh1k3rsguide.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

import com.hitchh1k3rsguide.game.Game;
import com.hitchh1k3rsguide.gameEngine.components.ComponentPlaysSound;
import com.hitchh1k3rsguide.gameEngine.components.ComponentRenderable;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;

public class EntityWater extends AbstractEntity implements ComponentRenderable, ComponentPlaysSound
{

    Color waterColor;
    String soundID;

    public EntityWater(long index)
    {
        super(index);
        waterColor = new Color(0, 100, 255, 200);
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
    public void initializeSounds(SoundSystem soundSystem)
    {
    }

    @Override
    public void playSounds(SoundSystem soundSystem)
    {
        if (Math.random() > 0.99)
        {
            System.out.println("playing...");
            soundSystem.quickPlay(false, getClass().getResource("/assets/snd/Coin.ogg"),
                    "Coin.ogg", false, 0 - (Game.GAME_WIDTH / 2),
                    (int) (Math.random() * Game.GAME_HEIGHT) - (Game.GAME_HEIGHT / 2), -1,
                    SoundSystemConfig.ATTENUATION_NONE, 0);
        }
    }

}
