package works;


import java.io.PrintStream;

public class WorkDaytime implements Work{
    private PrintStream stream;

    public WorkDaytime (PrintStream stream) {
        this.stream = stream;
    }

    public void start(){
        stream.println("Start working!!!");
    }
}