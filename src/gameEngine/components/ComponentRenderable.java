package com.hitchh1k3rsguide.gameEngine.components;

import java.awt.Graphics2D;

public interface ComponentRenderable extends IComponent
{

    public void draw(Graphics2D g);

    public int getZIndex();

}
