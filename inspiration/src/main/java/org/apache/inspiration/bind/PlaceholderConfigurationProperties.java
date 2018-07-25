package org.apache.inspiration.bind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PlaceholderConfigurationProperties {

    /**
     * The name prefix of the properties that are valid to bind to this object. Synonym for {@link #prefix()}.
     * 
     * @return the name prefix of the properties to bind
     */
    @AliasFor("prefix")
    String value() default "";

    /**
     * The name prefix of the properties that are valid to bind to this object. Synonym for {@link #value()}.
     * 
     * @return the name prefix of the properties to bind
     */
    @AliasFor("value")
    String prefix() default "";

    /**
     * Flag to indicate that when binding to this object invalid fields should be ignored. Invalid means invalid according
     * to the binder that is used, and usually this means fields of the wrong type (or that cannot be coerced into the
     * correct type).
     * 
     * @return the flag value (default false)
     */
    boolean ignoreInvalidFields() default false;

    /**
     * Flag to indicate that when binding to this object unknown fields should be ignored. An unknown field could be a sign
     * of a mistake in the Properties.
     * 
     * @return the flag value (default true)
     */
    boolean ignoreUnknownFields() default true;

}
