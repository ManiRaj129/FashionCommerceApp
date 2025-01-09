package src.Manufacturing.src;

import src.Manufacturing.src.interfaces.MachineOperations;

public class Machines implements MachineOperations {


    private boolean running;


    @Override
    public void startMachine() {
        this.running = true;
        System.out.println("Starting Machine");
    }
    
    @Override
    public boolean isRunning() {
        return running;
    }
}
