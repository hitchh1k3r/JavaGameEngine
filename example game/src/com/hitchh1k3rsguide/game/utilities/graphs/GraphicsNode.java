package com.hitchh1k3rsguide.game.utilities.graphs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.hitchh1k3rsguide.game.utilities.graphs.components.NodeComponentGraphics;
import com.hitchh1k3rsguide.gameEngine.utilities.graphics.Sprite;

public class GraphicsNode extends AbstractDAGNode implements NodeComponentGraphics
{

    AffineTransform matrix;
    Sprite graphic;

    public GraphicsNode(Sprite graphic, AbstractDAGNode parent, ArrayList<AbstractDAGNode> children)
    {
        super(parent, children);
        matrix = new AffineTransform(); // Identity Matrix
        this.graphic = graphic;
    }

    @Override
    public AffineTransform getTransform()
    {
        return matrix;
    }

    @Override
    public void draw(Graphics2D g)
    {
        graphic.draw(g);
    }

}
