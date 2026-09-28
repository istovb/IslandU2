package com.javarush.island.istovb.config;

import java.util.HashMap;
import java.util.Map;

public class EatTable {

    public static final Map<String, Map<String, Integer>> PROBABILITIES = new HashMap<>();

    static {

        add("Wolf", "Horse", 10);       add("Wolf", "Лошадь", 10);
        add("Wolf", "Deer", 15);        add("Wolf", "Олень", 15);
        add("Wolf", "Rabbit", 60);      add("Wolf", "Кролик", 60);
        add("Wolf", "Mouse", 80);       add("Wolf", "Мышь", 80);
        add("Wolf", "Goat", 60);        add("Wolf", "Коза", 60);
        add("Wolf", "Sheep", 70);       add("Wolf", "Овца", 70);
        add("Wolf", "Boar", 15);        add("Wolf", "Кабан", 15);
        add("Wolf", "Buffalo", 10);     add("Wolf", "Буйвол", 10);
        add("Wolf", "Duck", 40);        add("Wolf", "Утка", 40);


        add("Boa", "Fox", 15);          add("Boa", "Лиса", 15);
        add("Boa", "Rabbit", 20);       add("Boa", "Кролик", 20);
        add("Boa", "Mouse", 40);        add("Boa", "Мышь", 40);
        add("Boa", "Duck", 10);         add("Boa", "Утка", 10);


        add("Fox", "Rabbit", 70);       add("Fox", "Кролик", 70);
        add("Fox", "Mouse", 90);        add("Fox", "Мышь", 90);
        add("Fox", "Duck", 60);         add("Fox", "Утка", 60);
        add("Fox", "Caterpillar", 40);  add("Fox", "Гусеница", 40);


        add("Bear", "Boa", 80);         add("Bear", "Удав", 80); // Исправлено с Snake
        add("Bear", "Horse", 40);       add("Bear", "Лошадь", 40);
        add("Bear", "Deer", 80);        add("Bear", "Олень", 80);
        add("Bear", "Rabbit", 80);      add("Bear", "Кролик", 80);
        add("Bear", "Mouse", 90);       add("Bear", "Мышь", 90);
        add("Bear", "Goat", 70);        add("Bear", "Коза", 70);
        add("Bear", "Sheep", 70);       add("Bear", "Овца", 70);
        add("Bear", "Boar", 50);        add("Bear", "Кабан", 50);
        add("Bear", "Buffalo", 20);     add("Bear", "Буйвол", 20);
        add("Bear", "Duck", 10);        add("Bear", "Утка", 10);
        add("Bear", "Caterpillar", 30); add("Bear", "Гусеница", 30);


        add("Eagle", "Fox", 10);        add("Eagle", "Лиса", 10);
        add("Eagle", "Rabbit", 90);     add("Eagle", "Кролик", 90);
        add("Eagle", "Mouse", 90);      add("Eagle", "Мышь", 90);
        add("Eagle", "Duck", 80);       add("Eagle", "Утка", 80);
    }

    private static void add(String predator, String prey, int prob) {
        if (!PROBABILITIES.containsKey(predator)) {
            PROBABILITIES.put(predator, new HashMap<>());
        }
        PROBABILITIES.get(predator).put(prey, prob);
    }
}
