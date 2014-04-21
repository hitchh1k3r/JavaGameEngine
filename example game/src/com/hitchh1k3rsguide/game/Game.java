package com.hitchh1k3rsguide.game;

import com.hitchh1k3rsguide.game.components.ComponentDivable;
import com.hitchh1k3rsguide.game.components.ComponentFlappable;
import com.hitchh1k3rsguide.game.components.ComponentSlideable;
import com.hitchh1k3rsguide.game.entities.EntityFlappyBird;
import com.hitchh1k3rsguide.game.entities.EntityWater;
import com.hitchh1k3rsguide.game.systems.SystemDebug;
import com.hitchh1k3rsguide.game.systems.SystemFlapping;
import com.hitchh1k3rsguide.game.systems.SystemSliding;
import com.hitchh1k3rsguide.gameEngine.GameEngine;
import com.hitchh1k3rsguide.gameEngine.components.ComponentGravity;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMessageable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentMovable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentPlaysSound;
import com.hitchh1k3rsguide.gameEngine.components.ComponentRenderable;
import com.hitchh1k3rsguide.gameEngine.components.ComponentUpkeep;
import com.hitchh1k3rsguide.gameEngine.components.ComponentWindow;
import com.hitchh1k3rsguide.gameEngine.entities.EntityGameWindow;
import com.hitchh1k3rsguide.gameEngine.entities.EntityMusic;
import com.hitchh1k3rsguide.gameEngine.messages.MessageCleanup;
import com.hitchh1k3rsguide.gameEngine.messages.MessageInitialize;
import com.hitchh1k3rsguide.gameEngine.systems.SystemRendering;
import com.hitchh1k3rsguide.gameEngine.systems.SystemSimplePlatformPhysics;
import com.hitchh1k3rsguide.gameEngine.systems.SystemSound;
import com.hitchh1k3rsguide.gameEngine.systems.SystemUpkeep;
import com.hitchh1k3rsguide.gameEngine.systems.SystemWindow;

public class Game
{

    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 800;

    public static void main(String[] args)
    {
        // Create Entity Component System:
        GameEngine ecs = new GameEngine();
        ecs.setFPS(60);

        // Add Components:
        ecs.addComponent(ComponentWindow.class);
        ecs.addComponent(ComponentRenderable.class);
        ecs.addComponent(ComponentFlappable.class);
        ecs.addComponent(ComponentSlideable.class);
        ecs.addComponent(ComponentGravity.class);
        ecs.addComponent(ComponentDivable.class);
        ecs.addComponent(ComponentMovable.class);
        ecs.addComponent(ComponentUpkeep.class);
        ecs.addComponent(ComponentMessageable.class);
        ecs.addComponent(ComponentPlaysSound.class);

        // Add Systems:
        ecs.systems.add(new SystemDebug());
        ecs.systems.add(new SystemWindow());
        ecs.systems.add(new SystemRendering());
        ecs.systems.add(new SystemFlapping());
        ecs.systems.add(new SystemSliding());
        ecs.systems.add(new SystemSimplePlatformPhysics(0.5));
        ecs.systems.add(new SystemUpkeep());
        ecs.systems.add(new SystemSound());

        // Add Entities:
        ecs.addEntity(new EntityGameWindow(ecs.getUniqueID(), "Flappy Entity Component System",
                GAME_WIDTH, GAME_HEIGHT, true));
        ecs.addEntity(new EntityFlappyBird(ecs.getUniqueID(), 0));
        ecs.addEntity(new EntityFlappyBird(ecs.getUniqueID(), 1));
        ecs.addEntity(new EntityFlappyBird(ecs.getUniqueID(), 2));
        ecs.addEntity(new EntityFlappyBird(ecs.getUniqueID(), 3));
        ecs.addEntity(new EntityWater(ecs.getUniqueID()));
        ecs.addEntity(new EntityMusic(ecs.getUniqueID(), "Song.ogg"));

        // Allow Systems and Entities to do late Initialization:
        ecs.sendMessage(new MessageInitialize());

        // Run ECS!
        ecs.run();

        // Allow Systems and Entities to do Cleanup:
        ecs.sendMessage(new MessageCleanup());

        System.exit(0);
    }

}
