package com.javarush.island.istovb.config;

public interface SimulationConfig {

    int ROWS = 20;
    int COLS = 30;

    long TICK_MILLIS = 500;
    int MAX_TICKS = 100;
    boolean STOP_WHEN_NO_ANIMALS = true;

    // Растительность
    int GRASS_INITIAL_FILL_PERCENT = 10;
    int GRASS_GROW_PER_TICK = 60;
    int GRASS_MAX_PER_CELL = 200;

    int BERRY_INITIAL_FILL_PERCENT = 15;
    int BERRY_GROW_PER_TICK = 25; // Повышено, чтобы всеядным хватало ресурса ягод
    int BERRY_MAX_PER_CELL = 50;

    // Начальное количество особей
    int START_WOLF = 15;    int START_BOA = 4;    int START_FOX = 5;    int START_BEAR = 2;    int START_EAGLE = 4;
    int START_HORSE = 30;   int START_DEER = 40;   int START_RABBIT = 20;   int START_MOUSE = 20;
    int START_GOAT = 40;    int START_SHEEP = 40;   int START_BOAR = 20;    int START_BUFFALO = 10;
    int START_DUCK = 60;    int START_CATERPILLAR = 40;    int START_SQUIRREL = 30;

    // Количество детей в одном приплоде (Сбалансировано по биологическим видам)
    int OFFSPRING_WOLF = 2;
    int OFFSPRING_BOA = 1;
    int OFFSPRING_FOX = 3;
    int OFFSPRING_BEAR = 1;
    int OFFSPRING_EAGLE = 1;
    int OFFSPRING_HORSE = 1;
    int OFFSPRING_DEER = 1;
    int OFFSPRING_RABBIT = 4;
    int OFFSPRING_MOUSE = 5;
    int OFFSPRING_GOAT = 2;
    int OFFSPRING_SHEEP = 2;
    int OFFSPRING_BOAR = 3;
    int OFFSPRING_BUFFALO = 1;
    int OFFSPRING_DUCK = 3;
    int OFFSPRING_CATERPILLAR = 1;
    int OFFSPRING_SQUIRREL = 2;

    // Шанс на совершение хода в такте (%)
    int MOVE_CHANCE_WOLF = 70;    int MOVE_CHANCE_BOA = 20;    int MOVE_CHANCE_FOX = 70;    int MOVE_CHANCE_BEAR = 40;
    int MOVE_CHANCE_EAGLE = 80;   int MOVE_CHANCE_HORSE = 60;   int MOVE_CHANCE_DEER = 60;    int MOVE_CHANCE_RABBIT = 80;
    int MOVE_CHANCE_MOUSE = 90;   int MOVE_CHANCE_GOAT = 60;    int MOVE_CHANCE_SHEEP = 60;    int MOVE_CHANCE_BOAR = 50;
    int MOVE_CHANCE_BUFFALO = 50;  int MOVE_CHANCE_DUCK = 80;    int MOVE_CHANCE_CATERPILLAR = 0;    int MOVE_CHANCE_SQUIRREL = 85;

    int BREED_CHANCE_PERCENT = 15;


    int TICKS_WITHOUT_FOOD_TO_DIE = 4;

    int PREDATOR_TICKS_WITHOUT_FOOD_TO_DIE = 7;

    int INITIAL_WEIGHT_LOSS_PERCENT = 2;
    boolean RIVER_ENABLED = true;
    int RIVER_COLUMN_OFFSET = 5;
    int RIVER_WIDTH = 2;

    int ANIMAL_THREAD_POOL_SIZE = 8;

    int STATS_PRINT_EVERY_N_TICKS = 2;
}
