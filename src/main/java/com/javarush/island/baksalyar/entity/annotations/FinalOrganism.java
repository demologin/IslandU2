package com.javarush.island.baksalyar.entity.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Marker annotation to identify <strong>final (leaf)</strong> classes in the organism hierarchy.
 * <p>
 * This annotation is applied to concrete organism implementations that should have no subclasses,
 * such as {@code Wolf}, {@code Rabbit}, or {@code Grass}. These classes represent the actual
 * game entities that can be instantiated and used in the simulation.
 * </p>
 * <p>
 * The primary purpose is to facilitate automatic discovery of all available organism types
 * via reflection (e.g., classpath scanning or package loading). This allows the game to
 * dynamically create instances of organisms, configure prototypes, and manage them without
 * hard-coding the list of types.
 * </p>
 * <p>
 * The annotation is retained at runtime ({@link RetentionPolicy#RUNTIME}) to be accessible
 * through the reflection API.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FinalOrganism {
}
