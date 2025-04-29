package org.edu.pet.constant;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WebRoutes {

    private final String REDIRECT = "redirect:";

    public final String SIGN_UP = "/signup";
    public final String SIGN_IN = "/signin";
    public final String SIGN_OUT = "/signout";
    public final String MAIN = "/";

    public String redirectTo(@NonNull String route) {
        return REDIRECT + route;
    }
}