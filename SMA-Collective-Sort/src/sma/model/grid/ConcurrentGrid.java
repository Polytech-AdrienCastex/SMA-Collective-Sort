package sma.model.grid;

import sma.model.agent.Agent;

public class ConcurrentGrid extends Grid
{
    public ConcurrentGrid(int width, int height)
    {
        super(width, height);
    }
    
    @Override
    public boolean move(Agent agent, Case to)
    {
        synchronized(this)
        {
            return super.move(agent, to);
        }
    }
    @Override
    public boolean dropObject(Agent agent)
    {
        synchronized(this)
        {
            return super.dropObject(agent);
        }
    }
    @Override
    public boolean takeObject(Agent agent)
    {
        synchronized(this)
        {
            return super.takeObject(agent);
        }
    }
    @Override
    public String toString()
    {
        synchronized(this)
        {
            return super.toString();
        }
    }
}
