package sma.model.grid;

import sma.model.Agent;
import sma.model.CarriableObject;

public class Case
{
    public Case()
    {
        this.object = null;
        this.agent = null;
    }
    
    private CarriableObject object;
    private Agent agent;
    
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
