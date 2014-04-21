package com.hitchh1k3rsguide.game.utilities.graphs.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public interface NodeComponentGraphics
{

    public AffineTransform getTransform();

    public void draw(Graphics2D g);

}
