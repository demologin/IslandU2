package com.javarush.island.zimina.animalcrossing.model.animals;

import com.javarush.island.zimina.animalcrossing.util.ProbabilityUtils;

import java.util.Map;

/**
 * Умение животного пытаться съесть добычу-животное по вероятности.
 * Реализуется хищниками и всеядными.
 */
public interface Carnivorous {

    /**
     * Таблица вероятностей поедания: ключ — название вида жертвы,
     * значение — вероятность съедения в процентах (0-100).
     */
    Map<String, Integer> getFoodProbabilities();

    /**
     * Попытка съесть добычу указанного вида.
     *
     * @param preyType имя вида потенциальной жертвы
     * @return true, если добыча была съедена
     */
    default boolean tryToEat(String preyType) {
        Integer probability = getFoodProbabilities().get(preyType);
        if (probability == null) {
            return false;
        }
        return ProbabilityUtils.rollChance(probability);
    }
}
