package sma.controller;

import java.io.IOException;
import sma.model.agent.AgentParameterSet;

public class Main
{

    public static void main(String[] arg) throws IOException, InterruptedException
    {
        boolean multiThreading = true;
        int nbAgents = 20;
        int nbObjects = 200;
        int w = 50;
        int h = 50;
        
        final AgentParameterSet aps = AgentParameterSet.create()
                .setMaxHistory(10)
                .setKMinus(0.3)
                .setKPlus(0.1)
                .setNbMove(1)
                .setError(0)
                .build();
        
        new Runtime(aps, multiThreading, nbObjects, nbAgents, w, h)
                .run();
    }
}
