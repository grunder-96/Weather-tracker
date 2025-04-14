package org.edu.pet.util;

import jakarta.servlet.http.Cookie;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.LocalDateTime;

@UtilityClass
public class CookieUtil {

    public Cookie delete(String cookieName) {

        return create(cookieName, null, 0);
    }

    public Cookie create(String cookieName, String cookieValue, @NonNull LocalDateTime expiresAt) {

        Duration between = Duration.between(LocalDateTime.now(), expiresAt);

        return between.isPositive() ? create(cookieName, cookieValue, (int) between.getSeconds()) :
                delete(cookieName);
    }

    private Cookie create(String cookieName, String cookieValue, int maxAge) {

        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
