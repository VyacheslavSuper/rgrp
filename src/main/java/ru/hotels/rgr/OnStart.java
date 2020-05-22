package ru.hotels.rgr;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hotels.rgr.dao.UserDao;
import ru.hotels.rgr.model.User;
import ru.hotels.rgr.model.types.UserType;

import java.time.LocalDate;

@Component
public class OnStart implements CommandLineRunner {
    @Autowired
    private UserDao userDao;

    @Value("${admin.login}")
    private String adminLogin;
    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        User user = userDao.findByLoginWithoutException(adminLogin);
        if (user == null) {
            System.out.println("Add admin");
            User admin = new User(adminLogin, adminPassword, "Administrator", "admin@yandex.ru", UserType.ADMINISTRATOR, LocalDate.now());
            userDao.saveUser(admin);
        }
    }
}
