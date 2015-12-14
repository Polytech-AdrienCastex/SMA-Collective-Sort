package sma.model.grid;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import sma.model.Agent;
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
        
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                this.objects[x][y] = new Case(x, y);
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
    
    public Collection<Case> getLocalCases(Agent agent)
    {
        Case c = getCase(agent);
        
        return Stream.of(new Case[]
        {
            getCase(c.getX() + 1, c.getY()),
            getCase(c.getX(), c.getY() + 1),
            getCase(c.getX() - 1, c.getY()),
            getCase(c.getX(), c.getY() - 1)
        })
                .filter(cx -> cx != null)
                .collect(Collectors.toList());
    }
    
    public Case getCase(Agent agent)
    {
        return Stream.of(objects)
                .flatMap(Stream::of)
                .filter(c -> agent.equals(c.getAgent()))
                .findFirst()
                .orElse(null);
    }
    
    public Case getCase(int x, int y)
    {
        if(!isValidCoordinate(x, y))
            return null;
        
        return objects[x][y];
    }
    
    public synchronized boolean dropObject(Agent agent)
    {
        if(agent.getCarriableObject() == null)
            return false;
        
        CarriableObject obj = agent.getCarriableObject();
        getCase(agent).setObject(obj);
        agent.setCarriableObject(obj);
        
        return true;
    }
    public synchronized boolean takeObject(Agent agent)
    {
        if(agent.getCarriableObject() != null)
            return false;
        
        Case c = getCase(agent);
        CarriableObject obj;
        synchronized(c)
        {
            obj = c.getObject();
            c.setObject(null);
        }
        
        agent.setCarriableObject(obj);
        
        return true;
    }
}
