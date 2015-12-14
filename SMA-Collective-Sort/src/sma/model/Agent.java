package sma.model;

import java.util.LinkedList;

public class Agent implements Runnable {
    private CarriableObject carriableObject;
    private LinkedList<CarriableObject> history;

    public Agent() {
        history = new LinkedList<>();
    }

    public CarriableObject getCarriableObject() {
        return carriableObject;
    }

    public boolean setCarriableObject(CarriableObject carriableObject) {
        if (this.carriableObject == null) {
            this.carriableObject = carriableObject;
            return true;
        }
        return false;
    }

    public void removeCarriableObject() {
        this.carriableObject = null;
    }

    @Override
    public void run() {

    }
}
