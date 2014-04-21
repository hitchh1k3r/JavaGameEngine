package com.hitchh1k3rsguide.game.utilities.graphs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.hitchh1k3rsguide.game.utilities.graphs.components.NodeComponentGraphics;

public class NodeTraversal
{

    public static void drawGraph(Graphics2D g, AbstractDAGNode node)
    {
        AffineTransform af = g.getTransform();
        if (node instanceof NodeComponentGraphics)
        {
            NodeComponentGraphics drawNode = (NodeComponentGraphics) node;
            g.transform(drawNode.getTransform());
            drawNode.draw(g);
        }
        for (AbstractDAGNode child : node.children)
        {
            drawGraph(g, child);
        }
        g.setTransform(af);
    }

}
