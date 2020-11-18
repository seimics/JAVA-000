package employees;

import works.Work;

public class DaGongRen extends Employee {

    private Work work;

    public DaGongRen(Work work) {
        super(work);
        this.work = work;
    }

    public void startWork() {
        work.start();
    }
}
