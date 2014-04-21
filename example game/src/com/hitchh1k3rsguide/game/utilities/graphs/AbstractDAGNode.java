package com.hitchh1k3rsguide.game.utilities.graphs;

import java.util.ArrayList;

public abstract class AbstractDAGNode
{

    ArrayList<AbstractDAGNode> children;
    AbstractDAGNode parent;

    public AbstractDAGNode(AbstractDAGNode parent, ArrayList<AbstractDAGNode> children)
    {
        this.parent = parent;
        this.children = children;
    }

}
