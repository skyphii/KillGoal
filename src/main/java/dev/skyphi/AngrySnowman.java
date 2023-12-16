package dev.skyphi;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AngrySnowman {
    
    private static final int SNOWBALL_DELAY = 20; // in ticks
    private static final int MIN_HEALTH = 20, MAX_HEALTH = 100;

    private Snowman snowman;

    private final BukkitRunnable SNOWBALL_RUNNABLE = new BukkitRunnable() {
        @Override
        public void run() {
            if(snowman.isDead()) {
                die();
                return;
            }
            throwSnowball();
        }
    };


    public AngrySnowman(Snowman snowman) {
        this.snowman = snowman;
        snowman.getAttribute(Attribute.GENERIC_MAX_HEALTH)
            .setBaseValue(MIN_HEALTH + (int)(Math.random() * (MAX_HEALTH - MIN_HEALTH + 1)));
        snowman.setHealth(snowman.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        
        SNOWBALL_RUNNABLE.runTaskTimer(KillGoal.INSTANCE, 10, SNOWBALL_DELAY);
    }

    private void throwSnowball() {
        List<Player> nearbyPlayers = snowman.getNearbyEntities(20, 20, 20).stream()
            .filter(entity -> entity instanceof Player)
            .map(entity -> (Player) entity)
            .collect(Collectors.toList());
        if(nearbyPlayers.size() == 0) return;

        Player target = nearbyPlayers.get((int)(Math.random() * nearbyPlayers.size()));
        Vector velocity = target.getLocation().toVector().subtract(snowman.getLocation().toVector()).normalize();

        snowman.launchProjectile(Snowball.class, velocity);
    }

    private void die() {
        SNOWBALL_RUNNABLE.cancel();
    }

}
