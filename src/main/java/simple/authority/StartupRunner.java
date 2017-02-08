package simple.authority;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE - 20)
public class StartupRunner implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {
//        ModelConverters.getInstance().addConverter(new AccessHiddenModelConverter());
    }
}
