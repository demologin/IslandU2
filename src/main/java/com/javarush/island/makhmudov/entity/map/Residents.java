package com.javarush.island.makhmudov.entity.map;

import com.javarush.island.makhmudov.util.Rnd;
import com.javarush.island.makhmudov.entity.organism.Organisms;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Residents extends ConcurrentHashMap<String, Organisms> {
    private static final int PERCENT_RANDOM_ROTATE = 1;

    private void checkNull(Object key) {
        this.putIfAbsent(key.toString(), null);
    }

    @Override
    public Organisms get(Object key) {
        if (key == null) {
            return null;
        }
        // Если ключа нет, автоматически создастся новый new Organisms() и положится в карту
        return this.computeIfAbsent(key.toString(), k -> new Organisms());
    }

    @Override
    public Organisms put(String key, Organisms value) {
        checkNull(key);
        return super.put(key, value);
    }

    public void randomRotateResidents() {
        if (size() > 1 && Rnd.get(PERCENT_RANDOM_ROTATE)) {
            synchronized (this) {
                Set<Entry<String, Organisms>> entrySet = entrySet();
                var iterator = entrySet.iterator();
                if (iterator.hasNext()) {
                    var organisms = iterator.next();
                    iterator.remove();
                    put(organisms.getKey(), organisms.getValue());
                }
            }
        }
    }
}
