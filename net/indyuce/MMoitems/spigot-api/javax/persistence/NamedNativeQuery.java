// 
// Decompiled by Procyon v0.5.36
// 

package javax.persistence;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedNativeQuery {
    String name() default "";
    
    String query();
    
    QueryHint[] hints() default {};
    
    Class resultClass() default void.class;
    
    String resultSetMapping() default "";
}
