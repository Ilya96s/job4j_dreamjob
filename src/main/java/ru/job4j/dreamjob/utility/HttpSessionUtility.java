package ru.job4j.dreamjob.utility;

import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

/**
 * HttpSessionUtility - утилитный класс
 *
 * @author Ilya Kaltygin
 */
public final class HttpSessionUtility {

    private HttpSessionUtility() {

    }

    public static User checkSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}
