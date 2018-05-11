package com.tianyi.web;

import java.lang.annotation.*;

/**
 * Created by gaozhilai on 2018/3/28.
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminRequired {
}
