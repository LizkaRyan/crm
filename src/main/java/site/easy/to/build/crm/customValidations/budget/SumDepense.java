package site.easy.to.build.crm.customValidations.budget;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SumDepenseValidator.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SumDepense {
    String message() default "There's no more funds on the budget";

    Class<?>[] groups() default {}; // Obligatoire pour la conformité

    Class<? extends Payload>[] payload() default {}; // Permet d'ajouter des métadonnées si nécessaire
}
