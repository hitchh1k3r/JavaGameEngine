package com.hitchh1k3rsguide.gameEngine.components;

public interface ComponentArrowKeys extends IComponent
{

    public enum Direction
    {
        UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
    }

    /** Key code array :: [UP, DOWN, LEFT, RIGHT] **/
    public int[] getKeyAliases();

    public void pressArrow(Direction arrow);

}
