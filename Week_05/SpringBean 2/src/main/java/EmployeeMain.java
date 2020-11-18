import employees.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EmployeeMain {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        Employee employee = (Employee) ctx.getBean("employEE");
        employee.startWork();
    }
}
