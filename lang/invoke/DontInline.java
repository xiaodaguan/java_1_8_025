package java.lang.invoke;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@interface DontInline {}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/invoke/DontInline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */