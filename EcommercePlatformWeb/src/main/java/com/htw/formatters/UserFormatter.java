package com.htw.formatters;

import com.htw.pojo.User;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Trung Hau
 */
public class UserFormatter implements Formatter<User> {

    @Override
    public String print(User user, Locale locale) {
        return String.valueOf(user.getId());
    }

    @Override
    public User parse(String userId, Locale locale) throws ParseException {
        User user = new User(Integer.valueOf(userId));

        return user;
    }

}
