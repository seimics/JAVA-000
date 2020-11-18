package employees;

import org.springframework.context.support.*;

public class EmployeeMain {
    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("employees.xml");
        Employee employee = context.getBean(Employee.class);
        employee.startWork();
        context.close();
    }
}
