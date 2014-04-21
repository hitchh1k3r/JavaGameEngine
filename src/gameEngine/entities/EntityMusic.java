package com.hitchh1k3rsguide.gameEngine.entities;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

import com.hitchh1k3rsguide.gameEngine.components.ComponentPlaysSound;

public class EntityMusic extends AbstractEntity implements ComponentPlaysSound
{

    boolean hasPlayed;
    String songName;
    String songID;

    public EntityMusic(long index, String songName)
    {
        super(index);
        this.songName = songName;
        this.hasPlayed = false;
    }

    @Override
    public void playSounds(SoundSystem soundSystem)
    {
        if (!hasPlayed)
        {
            hasPlayed = true;
            soundSystem.play(songID);
        }
    }

    @Override
    public void initializeSounds(SoundSystem soundSystem)
    {
        songID = songName + "_" + index;
        soundSystem.newStreamingSource(true, songID,
                getClass().getResource("/assets/snd/" + songName), songName, true, 0, 0, 0,
                SoundSystemConfig.ATTENUATION_NONE, 0);
        soundSystem.setPitch(songID, 2.0f);
        soundSystem.setVolume(songID, 1.0f);
    }

}
