package java.beans;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transient
{
  boolean value() default true;
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/Transient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */