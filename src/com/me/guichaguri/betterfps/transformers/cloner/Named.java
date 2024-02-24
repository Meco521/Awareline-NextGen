package com.me.guichaguri.betterfps.transformers.cloner;

import com.me.guichaguri.betterfps.tweaker.Naming;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Named {
  Naming value();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\transformers\cloner\Named.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */