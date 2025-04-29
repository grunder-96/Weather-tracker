package org.edu.pet.constant;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WebRoutes {

    private final String REDIRECT = "redirect:";
    private final String ALL = "/**";

    public final String ADD = "/add";
    public final String DELETE = "/delete";

    public final String SIGN_UP = "/signup";
    public final String SIGN_IN = "/signin";
    public final String SIGN_OUT = "/signout";
    public final String MAIN = "/";
    public final String SEARCH = "/search";
    public final String LOCATION = "/location";
    public final String STATIC = "/static";

    public final String LOCATION_ALL = LOCATION + ALL;
    public final String STATIC_ALL = STATIC + ALL;

    public String redirectTo(@NonNull String route) {
        return REDIRECT + route;
    }
}