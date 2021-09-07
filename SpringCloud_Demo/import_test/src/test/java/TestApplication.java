import cn.itcast.bean.EnableUserBean;
import cn.itcast.bean.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



@EnableUserBean
public class TestApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext  annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TestApplication.class);
        User user=annotationConfigApplicationContext.getBean(User.class);
        System.out.println(user);

    }
}
