package com.resolvix.dataflow.api.annotation;

import com.resolvix.dataflow.api.Module;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ModuleDependencies.class)
public @interface ModuleDependency {

       /**
        * Identifies one or more modules upon which the
        *
        * @return the
        */
       Class<? extends Module<?, ?, ?>>[] modules() default {};
}
