package com.hitchh1k3rsguide.gameEngine.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hitchh1k3rsguide.gameEngine.components.ComponentWindow;

public class EntityGameWindow extends AbstractEntity implements ComponentWindow, ComponentListener,
        WindowListener, MouseListener, KeyListener, MouseMotionListener, MouseWheelListener
{

    Frame window;
    public Graphics2D windowGraphics;
    public Image buffer;
    public Graphics2D bufferedGraphics;
    public final int width, height;
    private boolean closeRequested, closed;
    private int frameXClip, frameYClip, windowWidth, windowHeight;

    private final Set<Integer> keysDown, mouseButtonsDown;
    private final Map<Integer, Integer> keyStates, mouseStates;
    private Point mousePosition;
    private int mouseWheelInput, mouseWheelState;
    private boolean keyReleased, mouseReleased;

    private final int REPEAT_DELAY = 30;
    private final int REPEAT_INTERVAL = 7;

    public EntityGameWindow(long index, String title, int width, int height, boolean resizable)
    {
        super(index);
        this.width = width;
        this.height = height;
        window = new Frame();
        window.setResizable(resizable);
        window.setSize(0, 0);
        window.setBackground(Color.BLACK);
        window.setName(title);
        window.setTitle(title);
        window.setVisible(true);
        if (!System.getProperty("os.name").contains("Mac OS X")
                && !System.getProperty("os.name").contains("Windows"))
        { // Tests showed Linux (specifically Fedora 20) reports the incorect window inset
          // initially, so we wait one second for the window manager to fix it.
            try
            {
                Thread.sleep(1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        Insets inset = window.getInsets();
        window.setResizable(true);
        window.setSize(width + inset.left + inset.right, height + inset.top + inset.bottom);
        window.setMinimumSize(new Dimension(width + inset.left + inset.right, height + inset.top
                + inset.bottom));
        window.setResizable(resizable);
        window.setLocationRelativeTo(null);
        window.addWindowListener(this);
        window.addComponentListener(this);
        window.addKeyListener(this);
        window.addMouseListener(this);
        window.addMouseMotionListener(this);
        window.addMouseWheelListener(this);
        windowGraphics = (Graphics2D) window.getGraphics();
        buffer = window.createImage(width, height);
        bufferedGraphics = (Graphics2D) buffer.getGraphics();
        closeRequested = false;
        closed = false;
        frameXClip = 0;
        frameYClip = 0;
        windowWidth = window.getWidth() - inset.left - inset.right;
        windowHeight = window.getHeight() - inset.top - inset.bottom;
        keysDown = new HashSet<Integer>();
        mouseButtonsDown = new HashSet<Integer>();
        keyStates = new HashMap<Integer, Integer>();
        mouseStates = new HashMap<Integer, Integer>();
        mousePosition = new Point();
        mouseWheelInput = 0;
        mouseWheelState = 0;
        keyReleased = false;
        mouseReleased = false;
    }

    @Override
    public boolean isCloseRequested()
    {
        return closeRequested;
    }

    @Override
    public boolean isClosed()
    {
        return closed;
    }

    @Override
    public void windowOpened(WindowEvent e)
    {
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        closeRequested = true;
        window.dispose(); // TODO - Maybe Add "Are You Sure?" Dialogue
                          // closeRequested Will Help With That
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
        closed = true;
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        keysDown.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keysDown.remove(e.getKeyCode());
        keyReleased = true;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseButtonsDown.add(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        mouseButtonsDown.remove(e.getButton());
        mouseReleased = true;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        mousePosition = getScreenPos(e.getPoint());
    }

    private Point getScreenPos(Point point)
    {
        double scale = 1;
        double windowAspect = (double) windowWidth / (double) windowHeight;
        double imgAspect = (double) width / (double) height;
        if (windowAspect > imgAspect)
        {
            scale = (double) height / (double) windowHeight;
        }
        else
        {
            scale = (double) width / (double) windowWidth;
        }
        return new Point((int) (scale * (point.x - frameXClip - window.getInsets().left)),
                (int) (scale * (point.y - frameYClip - window.getInsets().top)));
    }

    @Override
    public void swapBuffer()
    {
        windowGraphics.drawImage(buffer, frameXClip + window.getInsets().left,
                frameYClip + window.getInsets().top, windowWidth - frameXClip - frameXClip,
                windowHeight - frameYClip - frameYClip, null);
        bufferedGraphics.setBackground(Color.BLUE);
        bufferedGraphics.clearRect(0, 0, width, height);
    }

    @Override
    public Graphics2D getBufferContext()
    {
        return bufferedGraphics;
    }

    @Override
    public void componentResized(ComponentEvent e)
    {
        windowGraphics = (Graphics2D) window.getGraphics();
        windowGraphics.setBackground(Color.BLACK);
        windowGraphics.clearRect(0, 0, window.getWidth(), window.getHeight());
        windowWidth = window.getWidth() - window.getInsets().left - window.getInsets().right;
        windowHeight = window.getHeight() - window.getInsets().top - window.getInsets().bottom;
        double windowAspect = (double) windowWidth / (double) windowHeight;
        double imgAspect = (double) width / (double) height;
        frameXClip = 0;
        frameYClip = 0;
        if (windowAspect > imgAspect)
        {
            frameXClip = (int) ((windowAspect - imgAspect) * windowHeight / 2);
        }
        else if (windowAspect < imgAspect)
        {
            frameYClip = (int) ((1 / windowAspect - 1 / imgAspect) * windowWidth / 2);
        }
    }

    @Override
    public void componentMoved(ComponentEvent e)
    {
    }

    @Override
    public void componentShown(ComponentEvent e)
    {
    }

    @Override
    public void componentHidden(ComponentEvent e)
    {
    }

    @Override
    public Frame getWindow()
    {
        return window;
    }

    @Override
    public boolean isKeyPressed(int key)
    {
        return keyStates.get(key) != null && keyStates.get(key) == 1;
    }

    @Override
    public boolean isKeyRepeat(int key)
    {
        return keyStates.get(key) != null && keyStates.get(key) == REPEAT_DELAY;
    }

    @Override
    public boolean isKeyDown(int key)
    {
        return keyStates.get(key) != null && keyStates.get(key) > 0;
    }

    @Override
    public boolean isMousePressed(int button)
    {
        return mouseStates.get(button) != null && mouseStates.get(button) == 1;
    }

    @Override
    public boolean isMouseRepeat(int button)
    {
        return mouseStates.get(button) != null && mouseStates.get(button) == REPEAT_DELAY;
    }

    @Override
    public boolean isMouseDown(int button)
    {
        return mouseStates.get(button) != null && mouseStates.get(button) > 0;
    }

    @Override
    public Point getMousePos()
    {
        return mousePosition;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        mouseWheelInput += e.getWheelRotation();
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mousePosition = getScreenPos(e.getPoint());
    }

    @Override
    public void updatePolling()
    {
        mouseWheelState = mouseWheelInput;
        mouseWheelInput = 0;
        for (int key : keysDown)
        {
            if (keyStates.containsKey(key))
            {
                int state = keyStates.get(key) + 1;
                if (state > REPEAT_DELAY)
                {
                    state -= REPEAT_INTERVAL;
                }
                keyStates.put(key, state);
            }
            else
            {
                keyStates.put(key, 1);
            }
        }
        for (int button : mouseButtonsDown)
        {
            if (mouseStates.containsKey(button))
            {
                int state = mouseStates.get(button) + 1;
                if (state > REPEAT_DELAY)
                {
                    state -= REPEAT_INTERVAL;
                }
                mouseStates.put(button, state);
            }
            else
            {
                mouseStates.put(button, 1);
            }
        }
        if (keyReleased)
        {
            keyReleased = false;
            for (int key : keyStates.keySet())
            {
                if (!keysDown.contains(key) && keyStates.get(key) > 0)
                {
                    keyStates.put(key, 0);
                }
            }
        }
        if (mouseReleased)
        {
            mouseReleased = false;
            for (int button : mouseStates.keySet())
            {
                if (!mouseButtonsDown.contains(button) && mouseStates.get(button) > 0)
                {
                    mouseStates.put(button, 0);
                }
            }
        }
    }

    @Override
    public int getMouseWheel()
    {
        return mouseWheelState;
    }

}
