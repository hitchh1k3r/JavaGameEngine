package com.hitchh1k3rsguide.gameEngine.components;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Point;

public interface ComponentWindow extends IComponent
{

    public boolean isCloseRequested();

    public boolean isClosed();

    public void swapBuffer();

    public Graphics2D getBufferContext();

    public Frame getWindow();

    public void updatePolling();

    public boolean isKeyPressed(int key);

    public boolean isKeyRepeat(int key);

    public boolean isKeyDown(int key);

    public boolean isMousePressed(int button);

    public boolean isMouseRepeat(int button);

    public boolean isMouseDown(int button);

    public int getMouseWheel();

    public Point getMousePos();

}
