package sma.model.grid;

import sma.model.object.CarriableObject;
import sma.model.agent.Agent;

public class Case
{
    public Case(int x, int y)
    {
        this.object = null;
        this.agent = null;
        
        this.x = x;
        this.y = y;
    }
    
    private final int x;
    private final int y;
    
    private CarriableObject object;
    
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    
    public void setObject(CarriableObject object)
    {
        this.object = object;
    }
    public <T extends CarriableObject> T getObject()
    {
        return (T)object;
    }
    
    private Agent agent;
    protected void setAgent(Agent agent)
    {
        this.agent = agent;
    }
    public <T extends Agent> T getAgent()
    {
        return (T)agent;
    }
    
    public boolean containsAgent()
    {
        return agent != null;
    }
    public boolean containsObject()
    {
        return object != null;
    }
    
    public boolean contains(Class c)
    {
        if(isEmpty())
            return false;
        
        return c.isInstance(object) || c.isInstance(agent);
    }
    
    public boolean isEmpty()
    {
        return !containsAgent() && !containsObject();
    }
}
