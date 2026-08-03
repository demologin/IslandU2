package com.javarush.island.baksalyar.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island.baksalyar.entity.annotations.FinalOrganism;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
/**
 * Utility class for discovering and instantiating organism prototypes
 * using reflection and JSON configuration files.
 * <p>
 * Scans the classpath for classes annotated with {@link FinalOrganism},
 * then loads their configuration from JSON resources. The configuration
 * path is determined by the class name (lowercase) and can fall back
 * to a default file if the specific one is not found.
 * </p>
 * <p>
 * This class is typically used during application startup to populate
 * {@link Setting#PROTOTYPES} with the available organism types.
 * </p>
 */
public class OrganismScanner {
    private OrganismScanner(){}
    /**
     * Scans for all classes annotated with {@link FinalOrganism} and
     * creates an array of organism prototypes from their JSON configurations.
     *
     * @return an array of organism instances, each configured from its JSON file
     * @throws RuntimeException if any organism fails to load
     */
    public static Organism[] createPrototypes(){
        Reflections reflections = new Reflections(Setting.PATH_TO_ORGANISMS);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(FinalOrganism.class);
        Organism[] organisms =  new Organism[classes.size()];
        int i = 0;
        for (Class<?> clazz : classes) {
            organisms[i++] = createPrototypes(clazz);
        }
        return organisms;
    }
    /**
     * Creates a single organism prototype from the specified class.
     * <p>
     * Loads the JSON configuration from {@code /organism/[classname].json}
     * (lowercase). If the file does not exist, falls back to the default
     * configuration {@code /organism/default.json}. The organism's type
     * is set to the class's simple name after deserialization.
     * </p>
     */
    public static Organism createPrototypes(Class type){
        ObjectMapper mapper = new ObjectMapper();
        String resourcePath = String.format(Setting.SETTING_JSON, type.getSimpleName().toLowerCase());
        if (OrganismScanner.class.getResource(resourcePath) == null) {resourcePath = Setting.SETTING_JSON_DEFAULT;}
        try (InputStream is = OrganismScanner.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Ресурс не найден: " + resourcePath);
            }
            Organism organism = (Organism) mapper.readValue(is, type);
            organism.setType(type.getSimpleName());
            return organism;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
