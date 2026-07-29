package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;

public final class MoveHelper {

    public static Location getTargetLocation(

            Island island,
            Location current,
            Direction direction,
            int distance) {

        int x = current.getX();

        int y = current.getY();

        switch (direction) {

            case UP:
                y -= distance;
                break;

            case DOWN:
                y += distance;
                break;

            case LEFT:
                x -= distance;
                break;

            case RIGHT:
                x += distance;
                break;

        }

        if (x < 0 || x >= island.getWidth()) {
            return current;
        }

        if (y < 0 || y >= island.getHeight()) {
            return current;
        }

        return island.get(x, y);

    }
}
