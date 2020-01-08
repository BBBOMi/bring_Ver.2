package com.wonder.bring.config.security.auth;

import java.lang.annotation.*;

@Target(ElementType.METHOD)

@Retention(RetentionPolicy.RUNTIME)

@Documented

@Inherited
public @interface Auth {
}


