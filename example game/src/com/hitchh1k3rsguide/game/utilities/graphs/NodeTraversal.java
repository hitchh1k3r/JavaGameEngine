package com.hitchh1k3rsguide.game.utilities.graphs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.hitchh1k3rsguide.game.utilities.graphs.components.NodeComponentGraphics;
import com.hitchh1k3rsguide.gameEngine.utilities.graphics.LayeredImage;

public class NodeTraversal
{

    public static void drawGraph(Graphics2D g, AbstractDAGNode node, LayeredImage zBuffer)
    {
        AffineTransform af = g.getTransform();
        if (node instanceof NodeComponentGraphics)
        {
            NodeComponentGraphics drawNode = (NodeComponentGraphics) node;
            g.transform(drawNode.getTransform());
            drawNode.draw(g, zBuffer);
        }
        for (AbstractDAGNode child : node.children)
        {
            drawGraph(g, child, zBuffer);
        }
        g.setTransform(af);
    }

}
