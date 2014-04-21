package com.hitchh1k3rsguide.gameEngine.components;

import paulscode.sound.SoundSystem;

public interface ComponentPlaysSound extends IComponent
{

    public void initializeSounds(SoundSystem soundSystem);

    public void playSounds(SoundSystem soundSystem);

}
