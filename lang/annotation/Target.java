package java.lang.annotation;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface Target
{
  ElementType[] value();
}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/annotation/Target.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */