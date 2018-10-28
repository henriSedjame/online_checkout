package fr.projects.online_checkout.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 27/10/2018
 * @Class purposes : .......
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface EmptyIfNull {
}
