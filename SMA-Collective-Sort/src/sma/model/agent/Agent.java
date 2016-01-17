package sma.model.agent;

import sma.model.grid.Case;
import sma.model.grid.Grid;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import sma.model.object.CarriableObject;

public class Agent implements Runnable
{
    public Agent(Grid grid, AgentParameterSet parameters)
    {
        this.history = new LinkedList<>();
        this.parameters = parameters;
        this.grid = grid;
    }
    
    private static final Random rnd = new Random();

    private final LinkedList<CarriableObject> history;
    private final AgentParameterSet parameters;
    private final Grid grid;
    
    private CarriableObject carriableObject;
    

    public CarriableObject getCarriableObject()
    {
        return carriableObject;
    }

    public void setCarriableObject(CarriableObject carriableObject)
    {
        this.carriableObject = carriableObject;
    }
    
    public boolean isCarrying()
    {
        return this.carriableObject != null;
    }

    public void removeCarriableObject()
    {
        this.carriableObject = null;
    }

    public void updateHistory(CarriableObject c)
    {
        history.addLast(c);
        
        while(history.size() > parameters.MAX_HISTORY)
            history.removeFirst();
    }
    
    public double getPTake(CarriableObject c)
    {
        double value = parameters.K_PLUS / (parameters.K_PLUS + computeFp(c));
        return value * value;
    }
    public double getPDrop(CarriableObject c)
    {
        double value = computeFd(c);
        value = value / (parameters.K_MINUS + value);
        return value * value;
    }

    public double computeFp(CarriableObject c)
    {/*
        return history.stream()
                .map(c::equals)
                .mapToInt(b -> b ? 1 : 0)
                .average()
                .orElse(0);*/
        double error = 0;
        if(parameters.ERROR != 0)
        {
            CarriableObject nc = c == CarriableObject.A ? CarriableObject.B : CarriableObject.A;
            error = history.stream()
                    .filter(c::equals)
                    .count() * parameters.ERROR;
        }
        
        return (history.stream()
                .map(c::equals)
                .mapToInt(b -> b ? 1 : 0)
                .sum() + error) / history.size();
    }

    public double computeFd(CarriableObject c)
    {
        Collection<Case> collection = this.grid.getLocalCases(this);
        return collection.stream()
                .map(Case::getObject)
                .map(c::equals)
                .mapToInt(b -> b ? 1 : 0)
                .average()
                .orElse(0);
    }
    
    
    
    public void iterate()
    {
        for(int i = 0; i < parameters.NB_MOVE; i++)
        {
            Case[] cs = grid.getLocalCasesArray(this);/*
            Case nextCase = grid.getLocalCases(this)
                    .stream()
                    .sorted((c1,c2) -> Integer.compare(rnd.nextInt(), rnd.nextInt()))
                    .findFirst()
                    .get();*/
            //grid.move(this, nextCase);
            Case nextCase = null;
            while(nextCase == null)
                nextCase = cs[rnd.nextInt(cs.length)];
            grid.move(this, nextCase);
        }
/*
        if(grid.move(this, nextCase))
        {*/
            Case currentCase = grid.getCase(this);
            
            this.updateHistory(currentCase.getObject());

            if(!this.isCarrying() && currentCase.containsObject() && rnd.nextDouble() <= getPTake(currentCase.getObject()))
            { // take object
                grid.takeObject(this);
            }
            else if(this.isCarrying() && !currentCase.containsObject() && rnd.nextDouble() <= getPDrop(this.getCarriableObject()))
            { // drop object
                grid.dropObject(this);
            }
        //}
    }

    @Override
    public void run()
    {
        while(true)
        {
            iterate();
        }
    }
    
    public Thread toThread()
    {
        return new Thread(this);
    }
}
