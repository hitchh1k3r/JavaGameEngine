package com.hitchh1k3rsguide.gameEngine.utilities.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class LayeredImage
{

    private final HashMap<Integer, BufferedImage> layers;
    public final int width, height;

    public LayeredImage(int width, int height)
    {
        this.width = width;
        this.height = height;
        layers = new HashMap<Integer, BufferedImage>();
    }

    public BufferedImage getLayer(int index)
    {
        if (!layers.containsKey(index))
        {
            layers.put(index, new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR));
        }
        return layers.get(index);
    }

    public void draw(Graphics2D g)
    {
        AffineTransform af = g.getTransform();
        g.setTransform(new AffineTransform());
        SortedSet<Integer> keys = new TreeSet<Integer>(layers.keySet());
        for (Integer i : keys)
        {
            g.drawImage(layers.get(i), 0, 0, null);
        }
        g.setTransform(af);
    }

}
