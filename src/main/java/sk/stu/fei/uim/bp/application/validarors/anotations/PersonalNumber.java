package sk.stu.fei.uim.bp.application.validarors.anotations;

import sk.stu.fei.uim.bp.application.validarors.PersonalNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PersonalNumberValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonalNumber {
    String message() default "Neplatné rodné číslo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
