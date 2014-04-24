package com.hitchh1k3rsguide.gameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.hitchh1k3rsguide.gameEngine.components.IComponent;
import com.hitchh1k3rsguide.gameEngine.entities.AbstractEntity;
import com.hitchh1k3rsguide.gameEngine.messages.IMessage;
import com.hitchh1k3rsguide.gameEngine.messages.MessageCleanup;
import com.hitchh1k3rsguide.gameEngine.messages.MessageInitialize;
import com.hitchh1k3rsguide.gameEngine.messages.MessageRequest;
import com.hitchh1k3rsguide.gameEngine.messages.MessageResponse;
import com.hitchh1k3rsguide.gameEngine.systems.ISystem;

public class GameEngine implements Runnable
{

    public ArrayList<ISystem> systems;
    public Map<Long, AbstractEntity> entities;
    public HashMap<Class<? extends IComponent>, Map<Long, AbstractEntity>> entityComponentMap;
    public boolean running;
    private long entityIndex;
    private final ArrayList<Class<? extends IComponent>> components;
    private long frameDelay;
    private long lastFrame;
    private double partialTickFrames;

    public GameEngine()
    {
        systems = new ArrayList<ISystem>();
        entities = new HashMap<Long, AbstractEntity>();
        entityComponentMap = new HashMap<Class<? extends IComponent>, Map<Long, AbstractEntity>>();
        running = false;
        entityIndex = 0;
        components = new ArrayList<Class<? extends IComponent>>();
        frameDelay = 100000000L; // 100ms (10 FPS)
        lastFrame = 0;
        partialTickFrames = 0;
    }

    @Override
    public void run()
    {
        running = true;
        sendMessage(new MessageInitialize()); // TODO - MUSIC LOOP
        try
        {
            lastFrame = System.nanoTime();
            while (running)
            {
                long startTime = System.nanoTime();
                partialTickFrames += (double) (startTime - lastFrame) / (double) frameDelay;
                int tickFrames = (int) partialTickFrames;
                partialTickFrames -= tickFrames;
                lastFrame = System.nanoTime();
                updateProcessors(tickFrames);
                // TODO - delete all entities that have requested removal
                // TODO - allow entities to request removal
                long frameNanoDelay = Math.max(frameDelay - (System.nanoTime() - startTime), 0);
                Thread.sleep(frameNanoDelay / 1000000, (int) (frameNanoDelay % 1000000));
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        sendMessage(new MessageCleanup());
    }

    public void setFPS(int fps)
    {
        frameDelay = (int) (1000000000L / fps);
    }

    public long getUniqueID()
    {
        return ++entityIndex;
    }

    public void addEntity(AbstractEntity entity)
    {
        entities.put(entity.index, entity);
        for (Class<? extends IComponent> component : components)
        {
            if (component.isInstance(entity))
            {
                Map<Long, AbstractEntity> matchedEntities = entityComponentMap.get(component);
                if (matchedEntities == null)
                {
                    matchedEntities = new HashMap<Long, AbstractEntity>();
                    entityComponentMap.put(component, matchedEntities);
                }
                matchedEntities.put(entity.index, entity);
            }
        }
    }

    public void removeEntity(long index)
    {
        AbstractEntity entity = entities.remove(index);
        if (entity != null)
        {
            for (Class<? extends IComponent> component : components)
            {
                if (component.isInstance(entity))
                {
                    Map<Long, AbstractEntity> matchedEntities = entityComponentMap.get(component);
                    if (matchedEntities != null)
                    {
                        matchedEntities.remove(index);
                    }
                }
            }
        }
    }

    private void updateProcessors(int ticks)
    {
        for (int i = 0; i < ticks; ++i)
        {
            if (i == 0)
            {
                for (ISystem system : systems)
                {
                    system.primaryUpdate(this);
                }
            }
            else
            {
                for (ISystem system : systems)
                {
                    system.update(this);
                }
            }
        }
    }

    public Map<Long, AbstractEntity> getAll(Class<? extends IComponent> component)
    {
        Map<Long, AbstractEntity> ret = entityComponentMap.get(component);
        if (ret == null)
        {
            return new HashMap<Long, AbstractEntity>();
        }
        return ret;
    }

    public void addComponent(Class<? extends IComponent> component)
    {
        components.add(component);
    }

    public void sendMessage(IMessage message)
    {
        for (ISystem system : systems)
        {
            system.getMessage(this, message);
        }
    }

    public void requestRespond(MessageRequest request, Object response)
    {
        sendMessage(new MessageResponse(request.index, request.requestName, response));
    }

}
