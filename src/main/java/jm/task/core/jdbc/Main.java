package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Nekrasov", (byte) 21);
        userService.saveUser("Anna", "Petrova", (byte) 37);
        userService.saveUser("Oleg", "Ivanov", (byte) 12);
        userService.saveUser("Vladimir", "Schneider", (byte) 18);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
