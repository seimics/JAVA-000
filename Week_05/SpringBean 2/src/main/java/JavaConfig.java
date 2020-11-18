import employees.DaGongRen;
import employees.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import works.Work;
import works.WorkOvertime;

@Configuration
public class JavaConfig {
    @Bean("employEE")
    public Employee employee(){
        return new DaGongRen(work());
    }

    @Bean
    public Work work(){
        return new WorkOvertime(System.out);
    }

}
