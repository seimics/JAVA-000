package works;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
@Component
public class WorkOvertime implements Work{
    private PrintStream stream;

    public WorkOvertime (PrintStream stream) {
        this.stream = stream;
    }

    public void start(){
        stream.println("Start working overtime!!!");
    }
}
