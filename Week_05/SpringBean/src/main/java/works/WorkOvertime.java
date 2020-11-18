package works;

import java.io.PrintStream;

public class WorkOvertime implements Work{
    private PrintStream stream;

    public WorkOvertime (PrintStream stream) {
        this.stream = stream;
    }

    public void start(){
        stream.println("Start working overtime!!!");
    }
}
