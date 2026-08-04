package com.javarush.island.baksalyar.entity.map;

import com.javarush.island.baksalyar.entity.organizm.Organisms;

import javax.annotation.Nonnull;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A thread-safe map that associates a string key (representing an organism type)
 * with an {@link Organisms} collection, ensuring that a non-null {@link Organisms}
 * instance always exists for any requested key.
 * <p>
 * This class extends {@link ConcurrentHashMap} and overrides {@link #get(Object)}
 * and {@link #put(String, Organisms)} to automatically create a new {@link Organisms}
 * object if the key does not already exist in the map. This simplifies the code
 * by eliminating the need for explicit null checks or conditional initialization
 * when accessing the collection of organisms for a given type.
 * </p>
 * <p>
 * The map is typically used to store residents of a {@link Cell}, where each
 * organism type (e.g., "Wolf", "Rabbit", "Grass") maps to an {@link Organisms}
 * collection containing all instances of that type in the cell.
 * </p>
 */
public class Residents extends ConcurrentHashMap<String, Organisms> {

    /**
     * Ensures that the map contains a non-null {@link Organisms} instance
     * for the given key. If the key is absent, it atomically inserts a new
     * empty {@link Organisms} object.
     *
     * @param key the key to check and initialize if absent
     */
    private void checkNull(Object key) {
        this.putIfAbsent(key.toString(), new Organisms());
    }

    /**
     * Returns the {@link Organisms} instance associated with the specified key.
     * If the key is not present in the map, a new empty {@link Organisms} is
     * created, inserted, and returned.
     *
     * @param key the key whose associated {@link Organisms} is to be returned
     * @return the (possibly newly created) {@link Organisms} for the given key
     */
    @Override
    public Organisms get(Object key) {
        checkNull(key);
        return super.get(key);
    }

    /**
     * Associates the specified value with the specified key in the map.
     * If the key is not already present, a new empty {@link Organisms} is
     * first inserted, and then the existing value is replaced with the provided one.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the {@link Organisms} to associate with the key
     * @return the previous value associated with the key, or {@code null} if
     *         there was no previous mapping
     */
    @Override
    public Organisms put(@Nonnull String key, @Nonnull Organisms value) {
        checkNull(key);
        return super.put(key, value);
    }
}
