package dev.skyphi.Managers;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.scheduler.BukkitRunnable;

import dev.skyphi.AngrySnowman;
import dev.skyphi.KillGoal;

public class SpawnerManager {
    
    public static final int SPAWN_DELAY = 200;  // in ticks
    public static final int SPAWN_AMOUNT = 1;   // total spawns between all spawners each time

    private static final EntityType[] ENTITY_TYPES = {
        EntityType.SNOWMAN
    };
    
    private ArrayList<Location> spawners = new ArrayList<Location>();

    private final BukkitRunnable SPAWN_RUNNABLE = new BukkitRunnable() {
        @Override
        public void run() {
            spawnEntities(SPAWN_AMOUNT);
        }
    };


    public void addSpawner(Location location) {
        spawners.add(location);
    }

    public void start() {
        SPAWN_RUNNABLE.runTaskTimer(KillGoal.INSTANCE, 0, SPAWN_DELAY);
    }

    public void spawnEntities(int amount) {
        for(int i = 0; i < amount; i++) {
            // spawn each mob at random spawner
            int rand = (int)(Math.random() * spawners.size());
            Location loc = spawners.get(rand);
            
            long playersNearby = loc.getWorld().getNearbyEntities(loc, 10, 10, 10).stream()
                .filter(entity -> entity instanceof Player &&
                    (((Player)entity).getGameMode() == GameMode.SURVIVAL || ((Player)entity).getGameMode() == GameMode.ADVENTURE))
                .count();
            if(playersNearby == 0) continue;

            spawnRandomEntity(loc);
        }
    }

    public boolean hasSpawners() { return spawners.size() > 0; }


    private void spawnRandomEntity(Location location) {
        // choose random entity type -> spawn it
        EntityType type = ENTITY_TYPES[(int)(Math.random() * ENTITY_TYPES.length)];
        Entity entity = location.getWorld().spawnEntity(location, type);
        
        switch(type) {
            default:
                break;
            case SNOWMAN:
                new AngrySnowman((Snowman)entity);
                break;
        }
    }

}
