package sma.model;

import sma.model.grid.Case;
import sma.model.grid.Grid;

import java.util.Collection;
import java.util.LinkedList;

public class Agent implements Runnable {
    private final static int MAX_HISTORY = 10;
    private final static double K_PLUS = 0.1;
    private final static double K_MINUS = 0.3;

    private CarriableObject carriableObject;
    private LinkedList<CarriableObject> history;
    private int number_of_objects_history;
    private Grid grid;

    public Agent(Grid g) {
        history = new LinkedList<>();
        number_of_objects_history = 0;
        grid = g;
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

    public void updateHistory(CarriableObject c) {
        history.addLast(c);
        if (c != CarriableObject.EMPTY) {
            number_of_objects_history++;
        }
        while (history.size() > MAX_HISTORY) {
            if (history.removeFirst() != CarriableObject.EMPTY) {
                number_of_objects_history--;
            }
        }
    }

    public double computeFp() {
        return number_of_objects_history / (double) history.size();
    }

    public double computeFd(CarriableObject c) {
        Collection<Case> collection = this.grid.getLocalCases(this);
        return collection.stream().map(Case::getObject).mapToInt(o -> o == c ? 1 : 0).average().getAsDouble();
    }

    @Override
    public void run() {

    }
}
