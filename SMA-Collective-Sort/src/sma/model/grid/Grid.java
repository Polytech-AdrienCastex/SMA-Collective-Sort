package sma.model.grid;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import sma.model.agent.Agent;
import sma.model.object.CarriableObject;

public class Grid
{
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
        return x >= 0 && y >= 0 && x < width && y < height;
    }
    
    public Case[] getLocalCasesArray(Agent agent)
    {
        Case c = getCase(agent);
        
        return new Case[]
        {
            getCase(c.getX() + 1, c.getY()),
            getCase(c.getX(), c.getY() + 1),
            getCase(c.getX() - 1, c.getY()),
            getCase(c.getX(), c.getY() - 1)
        };
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
    
    public Stream<Case> getAll()
    {
        return Stream.of(objects)
                .flatMap(Stream::of);
    }
    public Stream<Case> getAllRandomlySorted()
    {
        Random rnd = new Random();
        
        Map<Case, Integer> is = Stream.of(objects)
                .flatMap(Stream::of)
                .collect(Collectors.toMap(c -> c, c -> rnd.nextInt()));
        
        return Stream.of(objects)
                .flatMap(Stream::of)
                .sorted((c1,c2) -> Integer.compare(is.get(c1), is.get(c2)));
    }
    
    private Map<Agent, Case> agentToCase = new HashMap<>();
    
    public Case getCase(Agent agent)
    {
        return agentToCase.getOrDefault(agent, null);/*
        return getAll()
                .filter(c -> agent.equals(c.getAgent()))
                .findFirst()
                .orElse(null);*/
    }
    
    public Case getCase(int x, int y)
    {
        if(!isValidCoordinate(x, y))
            return null;
        
        return objects[x][y];
    }
    
    private static final Random rnd = new Random();
    
    public void initAgent(Case c, Agent a)
    {
        c.setAgent(a);
        agentToCase.put(a, c);
    }
    
    public boolean move(Agent agent, Case to)
    {
        if(to.containsAgent())
            return false;
        
        getCase(agent).setAgent(null);
        to.setAgent(agent);
        agentToCase.put(agent, to);
        
        return true;
    }
    
    public boolean dropObject(Agent agent)
    {
        CarriableObject obj = agent.getCarriableObject();
        if(obj == null)
            return false;
        
        getCase(agent).setObject(obj);
        agent.setCarriableObject(null);
        
        return true;
    }
    public boolean takeObject(Agent agent)
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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" /");
        for(int i = 0; i < width + 2; i++)
            sb.append("-");
        sb.append("\\");
        sb.append("\n");
        
        for(int y = 0; y < height; y++)
        {
            sb.append(" | ");
            for(int x = 0; x < width; x++)
            {
                Case c = getCase(x, y);
                if(c.isEmpty())
                    sb.append(" ");
                else if(c.containsAgent())
                {
                    CarriableObject o = c.getAgent().getCarriableObject();
                    if(o == CarriableObject.A)
                        sb.append("a");
                    else if(o == CarriableObject.B)
                        sb.append("b");
                    else
                        sb.append("*");
                }
                else if(c.containsObject())
                    sb.append(c.getObject().equals(CarriableObject.A) ? "A" : "B");
            }
            sb.append(" |");
            sb.append("\n");
        }
        
        sb.append(" \\");
        for(int i = 0; i < width + 2; i++)
            sb.append("-");
        sb.append("/");
        sb.append("\n");
        
        return sb.toString();
    }
}
