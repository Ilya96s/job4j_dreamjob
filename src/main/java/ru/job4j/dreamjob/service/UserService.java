package ru.job4j.dreamjob.service;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDBStore;

import java.util.List;
import java.util.Optional;

/**
 * UserService - класс, описывающий бизнес логику приложения
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
@Service
public class UserService {
    @GuardedBy("this")
    private final UserDBStore userStore;

    public UserService(UserDBStore userStore) {
        this.userStore = userStore;
    }

    public Optional<User> add(User user) {
        return (userStore.add(user));
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userStore.findUserByEmailAndPassword(email, password);
    }

}
