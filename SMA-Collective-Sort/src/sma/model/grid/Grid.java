package sma.model.grid;

import sma.model.CarriableObject;

public class Grid
{
    public static Builder create()
    {
        return new Builder();
    }
    public static class Builder
    {
        
        public Grid build()
        {
            return new Grid(5, 5);
        }
    }
    
    public Grid(int width, int height)
    {
        this.height = height;
        this.width = width;
        
        this.objects = new Case[width][height];
    }
    
    private final Case[][] objects;
    private final int height;
    private final int width;
    
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    
    public boolean isValidCoordinate(int x, int y)
    {
        return x < 0 || y < 0 || x >= width || y >= height;
    }
    
    public Case getCase(int x, int y)
    {
        if(!isValidCoordinate(x, y))
            return null;
        
        return objects[x][y];
    }
    
    public CarriableObject catchObject(int x, int y)
    {
        if(!isValidCoordinate(x, y))
            return null;
        
        Case c = objects[x][y];
        CarriableObject obj;
        synchronized(c)
        {
            obj = c.getObject();
            c.setObject(null);
        }
        return obj;
    }
}
