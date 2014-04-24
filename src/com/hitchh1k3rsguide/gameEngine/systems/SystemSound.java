package com.hitchh1k3rsguide.gameEngine.systems;

import java.util.Collection;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryJavaSound;

import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentPlaysSound;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;
import com.hitchh1k3rsguide.gameEngine.messages.MessageCleanup;
import com.hitchh1k3rsguide.gameEngine.messages.MessageInitialize;

public class SystemSound implements ISystem
{

    SoundSystem soundSystem;

    @Override
    public void update(GameEngine ecs)
    {
    }

    @Override
    public void getMessage(GameEngine ecs, IMessage message)
    {
        if (message instanceof MessageInitialize)
        {
            try
            {
                SoundSystemConfig.addLibrary(LibraryJavaSound.class);
                SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
            }
            catch (SoundSystemException e)
            {
                e.printStackTrace();
            }
            soundSystem = new SoundSystem();
            soundSystem.setListenerPosition(100, 0, 0);
            soundSystem.setListenerOrientation(0, 0, -1, 0, 1, 0);
            soundSystem.setListenerVelocity(0, 0, 0);
            Collection<AbstractEntity> entities = ecs.getAll(ComponentPlaysSound.class).values();
        }
        else if (message instanceof MessageCleanup)
        {
            soundSystem.cleanup();
        }
    }

    @Override
    public void primaryUpdate(GameEngine ecs)
    {
        Collection<AbstractEntity> entities = ecs.getAll(ComponentPlaysSound.class).values();
        for (AbstractEntity entity : entities)
        {
            ((ComponentPlaysSound) entity).playSounds(soundSystem);
        }
    }

}
