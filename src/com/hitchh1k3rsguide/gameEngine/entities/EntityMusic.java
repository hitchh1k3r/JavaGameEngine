package com.hitchh1k3rsguide.gameEngine.entities;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

import com.hitchh1k3rsguide.gameEngine.components.ComponentPlaysSound;

public class EntityMusic extends AbstractEntity implements ComponentPlaysSound
{

    boolean hasPlayed;
    String songName;
    String songID;
    float songLength;
    SoundSystem ssRef;

    public EntityMusic(long index, String songName, float length)
    {
        super(index);
        this.songName = songName;
        songID = songName + "_" + index;
        hasPlayed = false;
        songLength = length;
    }

    @Override
    public void playSounds(SoundSystem soundSystem)
    {
        if (!hasPlayed)
        {
            hasPlayed = true;
            ssRef = soundSystem;
            soundSystem.newStreamingSource(true, songID,
                    getClass().getResource("/assets/snd/" + songName), songName, false, 0, 0, 0,
                    SoundSystemConfig.ATTENUATION_NONE, 0);
            // soundSystem.setVolume(songID, 0.05f);
            soundSystem.play(songID);
        }
        else if (soundSystem.millisecondsPlayed(songID) >= songLength)
        {
            soundSystem.stop(songID);
            soundSystem.play(songID);
        }
    }

    @Override
    public void dispose()
    {
        super.dispose();
        ssRef.stop(songID);
        ssRef.removeSource(songID);
    }

}
