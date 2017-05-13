package java.beans;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({java.lang.annotation.ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstructorProperties
{
  String[] value();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/ConstructorProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */