package com.hitchh1k3rsguide.gameEngine.utilities.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Sprite
{

    public enum Property
    {
        Pos2i, Rotate1d, FlipHorz, FlipVert, Scale2d
    }

    private static Map<String, Image> images = new HashMap<String, Image>();
    private final String name;
    public final int width, height;
    private int originX, originY;

    public Sprite(String name)
    {
        this.name = name;
        int width = 0;
        int height = 0;

        if (!images.containsKey(name))
        {
            try
            {
                Image img = ImageIO.read(getClass().getResourceAsStream("/assets/img/" + name));
                images.put(name, img);
                width = img.getWidth(null);
                height = img.getHeight(null);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            width = images.get(name).getWidth(null);
            height = images.get(name).getHeight(null);
        }
        this.width = width;
        this.height = height;
        originX = width / 2;
        originY = height / 2;
    }

    public void setOrigin(int originX, int originY)
    {
        this.originX = originX;
        this.originY = originY;
    }

    public void draw(Graphics2D g, Object... params)
    {
        Image img = images.get(name);
        if (img != null)
        {
            AffineTransform af = g.getTransform();
            int rOriginX = originX;
            int rOriginY = originY;
            int rWidth = width;
            int rHeight = height;

            for (int i = 0; i < params.length; ++i)
            {
                if (params[i] == null)
                    continue;
                switch (((Property) params[i]))
                {
                    case FlipHorz:
                        rWidth *= -1;
                        rOriginX *= -1;
                        break;
                    case FlipVert:
                        rHeight *= -1;
                        rOriginY *= -1;
                        break;
                    case Pos2i:
                        g.translate((int) params[i + 1], (int) params[i + 2]);
                        i += 2;
                        break;
                    case Rotate1d:
                        g.rotate((double) params[i + 1]);
                        i += 1;
                        break;
                    case Scale2d:
                        g.scale((double) params[i + 1], (double) params[i + 2]);
                        i += 2;
                        break;
                }
            }

            g.drawImage(img, -rOriginX, -rOriginY, rWidth, rHeight, null);
            g.setTransform(af);
        }
    }
}
