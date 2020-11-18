package employees;

import works.Work;
public class Employee {
    private Work work;

    public Employee(Work work) {
        this.work = work;
    }

    public void startWork(){
        work.start();
    }

}
