package sma.model.grid;

import sma.model.Agent;
import sma.model.CarriableObject;

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
    private Agent agent;
    
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    
    protected void setObject(CarriableObject object)
    {
        this.object = object;
    }
    public CarriableObject getObject()
    {
        return object;
    }
    
    public Agent getAgent()
    {
        return agent;
    }
    protected void setAgent(Agent agent)
    {
        this.agent = agent;
    }
}
