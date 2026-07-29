package com.javarush.island.zimina.animalcrossing.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Базовый класс для любого живого организма на острове —
 * как животных, так и растений.
 * Содержит общие свойства: положение на острове, вес и состояние "жив/мёртв".
 */
@Getter
public abstract class Organism {

    /** Координата организма по горизонтали (столбец клетки). */
    @Setter(AccessLevel.PROTECTED)
    private int x;

    /** Координата организма по вертикали (строка клетки). */
    @Setter(AccessLevel.PROTECTED)
    private int y;

    /** Вес организма в килограммах. */
    protected double weight;

    /** Признак того, жив ли организм в данный момент. */
    private boolean alive;

    public Organism(int x, int y, double weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.alive = true;
    }

    /**
     * Отмечает организм как погибший.
     * Вызывается при смерти от голода, поедании другим животным и т.д.
     */
    public void die() {
        this.alive = false;
    }
}