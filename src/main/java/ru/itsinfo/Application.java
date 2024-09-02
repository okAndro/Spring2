package ru.itsinfo;

import jakarta.el.BeanNameResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsinfo.service.AppServiceImpl;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        // Вызов метода обновления пароля
//        AppServiceImpl appService = context.getBean(AppServiceImpl.class);
//        appService.updatePasswordForAdmin();
    }
}
