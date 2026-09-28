package com.javarush.island.istovb.simulation;

import com.javarush.island.istovb.config.SimulationConfig;
import com.javarush.island.istovb.model.Island;
import com.javarush.island.istovb.model.Location;
import com.javarush.island.istovb.model.Terrain;
import com.javarush.island.istovb.model.animal.*;
import com.javarush.island.istovb.model.plant.Berry;
import com.javarush.island.istovb.model.plant.Grass;
import com.javarush.island.istovb.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class Simulation {

    private final Island island;
    private final Statistics statistics;
    private final IslandRenderer renderer;

    private final ScheduledExecutorService scheduler;
    private final ExecutorService workerPool;

    private final AtomicInteger tickCounter = new AtomicInteger(0);
    private volatile boolean stopped = false;

    public Simulation() {
        this.island = new Island(SimulationConfig.ROWS, SimulationConfig.COLS);
        this.statistics = new Statistics();
        this.renderer = new IslandRenderer(island);

        this.scheduler = Executors.newScheduledThreadPool(3);
        this.workerPool = Executors.newFixedThreadPool(SimulationConfig.ANIMAL_THREAD_POOL_SIZE);
    }

    public void start() {
        System.out.println("=== IslandU2: старт симуляции ===");
        System.out.printf("Остров: %d x %d, такт = %d мс, максимум тактов = %d%n",
                SimulationConfig.ROWS, SimulationConfig.COLS,
                SimulationConfig.TICK_MILLIS, SimulationConfig.MAX_TICKS);
        populateInitial();

        scheduler.scheduleAtFixedRate(this::growPlantsTask,
                SimulationConfig.TICK_MILLIS, SimulationConfig.TICK_MILLIS, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(this::animalLifeCycleTask,
                SimulationConfig.TICK_MILLIS, SimulationConfig.TICK_MILLIS, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(this::statisticsTask,
                SimulationConfig.TICK_MILLIS, SimulationConfig.TICK_MILLIS, TimeUnit.MILLISECONDS);

        scheduler.schedule(this::stop,
                SimulationConfig.TICK_MILLIS * SimulationConfig.MAX_TICKS, TimeUnit.MILLISECONDS);
    }

    private void stop() {
        if (stopped) return;
        stopped = true;
        System.out.println("=== Симуляция остановлена ===");
        scheduler.shutdown();
        workerPool.shutdown();
    }

    private void populateInitial() {
        List<Location> cells = island.allLocations();

        int grassPerCell = SimulationConfig.GRASS_MAX_PER_CELL
                * SimulationConfig.GRASS_INITIAL_FILL_PERCENT / 100;
        int berryPerCell = SimulationConfig.BERRY_MAX_PER_CELL
                * SimulationConfig.BERRY_INITIAL_FILL_PERCENT / 100;

        for (Location loc : cells) {
            if (loc.getTerrain() != Terrain.PLAIN) continue;

            for (int i = 0; i < grassPerCell; i++) {
                loc.getPlants().add(new Grass());
            }
            for (int i = 0; i < berryPerCell; i++) {
                loc.getPlants().add(new Berry());
            }
        }

        spawn(Wolf.class,        SimulationConfig.START_WOLF,        cells);
        spawn(Boa.class,         SimulationConfig.START_BOA,         cells);
        spawn(Fox.class,         SimulationConfig.START_FOX,         cells);
        spawn(Bear.class,        SimulationConfig.START_BEAR,        cells);
        spawn(Eagle.class,       SimulationConfig.START_EAGLE,       cells);
        spawn(Horse.class,       SimulationConfig.START_HORSE,       cells);
        spawn(Deer.class,        SimulationConfig.START_DEER,        cells);
        spawn(Rabbit.class,      SimulationConfig.START_RABBIT,      cells);
        spawn(Mouse.class,       SimulationConfig.START_MOUSE,       cells);
        spawn(Goat.class,        SimulationConfig.START_GOAT,        cells);
        spawn(Sheep.class,       SimulationConfig.START_SHEEP,       cells);
        spawn(Boar.class,        SimulationConfig.START_BOAR,        cells);
        spawn(Buffalo.class,     SimulationConfig.START_BUFFALO,     cells);
        spawn(Duck.class,        SimulationConfig.START_DUCK,        cells);
        spawn(Caterpillar.class, SimulationConfig.START_CATERPILLAR, cells);
        spawn(Squirrel.class,    SimulationConfig.START_SQUIRREL,    cells);
    }

    private Location randomLandCell(List<Location> cells) {
        Location loc;
        do {
            loc = cells.get(RandomUtil.nextInt(cells.size()));
        } while (loc.getTerrain() != Terrain.PLAIN);
        return loc;
    }

    private <T extends Animal> void spawn(Class<T> type, int count, List<Location> cells) {
        for (int i = 0; i < count; i++) {
            T animal = createAnimal(type);
            if (animal == null) continue;
            animal.applyInitialWeight();
            Location loc = randomLandCell(cells);
            if (loc.countAnimals(type) >= animal.getMaxPerCell()) continue;
            animal.setLocation(loc);
            loc.addAnimal(animal);
        }
    }

    private <T extends Animal> T createAnimal(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    private void growPlantsTask() {
        if (stopped) return;
        List<Location> cells = island.allLocations();
        int grown = 0;

        for (int i = 0; i < SimulationConfig.GRASS_GROW_PER_TICK; i++) {
            Location loc = randomLandCell(cells);
            if (loc.countPlants(Grass.class) < SimulationConfig.GRASS_MAX_PER_CELL) {
                loc.getPlants().add(new Grass());
                grown++;
            }
        }
        for (int i = 0; i < SimulationConfig.BERRY_GROW_PER_TICK; i++) {
            Location loc = randomLandCell(cells);
            if (loc.countPlants(Berry.class) < SimulationConfig.BERRY_MAX_PER_CELL) {
                loc.getPlants().add(new Berry());
                grown++;
            }
        }
        statistics.addGrownPlants(grown);
    }

    private void animalLifeCycleTask() {
        if (stopped) return;
        int tick = tickCounter.incrementAndGet();
        List<Location> cells = island.allLocations();

        List<Callable<Void>> tasks = new ArrayList<>();
        for (final Location loc : cells) {
            tasks.add(new Callable<Void>() {
                @Override
                public Void call() {
                    List<Animal> currentAnimals = new ArrayList<>(loc.getAnimals());

                    for (Animal animal : currentAnimals) {
                        if (animal.isAlive()) {
                            animal.tick(island);
                        }
                    }
                    loc.getAnimals().removeIf(new Predicate<Animal>() {
                        @Override
                        public boolean test(Animal a) {
                            return !a.isAlive();
                        }
                    });
                    return null;
                }
            });
        }
        try {
            workerPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        statistics.registerTick(tick, island);
        if (SimulationConfig.STOP_WHEN_NO_ANIMALS && statistics.getTotalAnimals() == 0) {
            stop();
        }
    }
    private void statisticsTask() {
        if (stopped) return;
        int tick = tickCounter.get();
        if (tick % SimulationConfig.STATS_PRINT_EVERY_N_TICKS != 0) return;
        renderer.render(tick);
        statistics.printSummary(tick);
    }
}
