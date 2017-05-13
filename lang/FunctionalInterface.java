package java.lang;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface FunctionalInterface {}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/FunctionalInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */