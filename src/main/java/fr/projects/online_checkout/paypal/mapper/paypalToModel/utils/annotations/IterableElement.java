package fr.projects.online_checkout.paypal.mapper.paypalToModel.utils.annotations;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 25/10/2018
 * @Class purposes : .......
 */
@Qualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface IterableElement {
}
