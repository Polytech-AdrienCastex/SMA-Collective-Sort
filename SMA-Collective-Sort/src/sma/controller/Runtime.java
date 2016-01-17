package sma.controller;

import java.util.ArrayList;
import java.util.Random;
import sma.model.agent.Agent;
import sma.model.agent.AgentParameterSet;
import sma.model.grid.ConcurrentGrid;
import sma.model.grid.Grid;
import sma.model.object.CarriableObject;

public class Runtime implements Runnable
{
    public Runtime(
            AgentParameterSet aps,
            boolean multiThreading,
            int nbObjects,
            int nbAgents,
            int w,
            int h)
    {
        this.multiThreading = multiThreading;
        this.nbObjects = nbObjects;
        this.nbAgents = nbAgents;
        this.aps = aps;
        this.w = w;
        this.h = h;
    }
    
    private final AgentParameterSet aps;
    
    private final boolean multiThreading;
    private final int nbAgents;
    private final int nbObjects;
    private final int w;
    private final int h;

    @Override
    public void run()
    {
        try
        {
            Random rnd = new Random();

            Grid grid = new ConcurrentGrid(w, h);
            grid.getAllRandomlySorted()
                    .limit(Math.min(nbObjects, h * w))
                    .forEach(c -> c.setObject(rnd.nextBoolean() ? CarriableObject.A : CarriableObject.B));

            ArrayList<Agent> ags = new ArrayList<>(nbAgents);
            for(int i = 0; i < nbAgents; i++)
                ags.add(new Agent(grid, aps));

            ArrayList<Agent> agsClone = (ArrayList)ags.clone();

            grid.getAllRandomlySorted()
                    .limit(ags.size())
                    .forEach(c -> grid.initAgent(c, agsClone.remove(0)));

            System.out.println(grid);
            System.out.println("Press a key...");
            System.in.read();
            for(int i = 0; i < System.in.available(); i++)
                System.in.read();

            if(multiThreading)
                ags.stream()
                        .map(Agent::toThread)
                        .forEach(Thread::start);

            int nb = 1;
            int xnb = 0;
            while(true)
            {
                if(!multiThreading)
                {
                    xnb = nb * nb;
                    if(xnb < 100000)
                        nb++;
                    for(int j = 0; j < xnb; j++)
                        ags.forEach(Agent::iterate);
                }

                System.out.println(grid);

                System.out.print("Press a key for pause...");
                if(!multiThreading)
                    System.out.print(" [it_skip = " + xnb + "]");
                System.out.println();

                System.out.flush();

                if(multiThreading)
                    Thread.sleep(300);
                else
                    Thread.sleep(100);

                if(System.in.available() > 0)
                {
                    for(int i = 0; i < System.in.available(); i++)
                        System.in.read();
                    System.in.read();
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
