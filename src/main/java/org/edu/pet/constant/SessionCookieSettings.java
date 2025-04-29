package org.edu.pet.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SessionCookieSettings {

    public final String COOKIE_NAME = "sessionId";
    public final int LIFETIME_SECONDS = 60 * 60 * 24 * 14;
}