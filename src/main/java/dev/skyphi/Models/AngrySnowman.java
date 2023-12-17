package dev.skyphi.Models;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import dev.skyphi.KillGoal;

public class AngrySnowman {
    
    private static final int SNOWBALL_DELAY = 30; // in ticks
    private static final int MIN_HEALTH = 20, MAX_HEALTH = 40;

    private Snowman snowman;

    private final BukkitRunnable SNOWBALL_RUNNABLE = new BukkitRunnable() {
        @Override
        public void run() {
            if(snowman == null || snowman.isDead()) {
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
        
        if(Math.random() <= 0.1) {
            // 10% chance of gaining speed
            snowman.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, -1, 1));
        }

        SNOWBALL_RUNNABLE.runTaskTimer(KillGoal.INSTANCE, (int)(Math.random() * 21), SNOWBALL_DELAY);
    }

    private void throwSnowball() {
        List<Player> nearbyPlayers = snowman.getNearbyEntities(20, 20, 20).stream()
            .filter(entity -> entity instanceof Player)
            .map(entity -> (Player) entity)
            .filter(p -> p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
            .collect(Collectors.toList());
        if(nearbyPlayers.size() == 0) return;

        Player target = nearbyPlayers.get((int)(Math.random() * nearbyPlayers.size()));
        Vector velocity = target.getLocation().toVector().subtract(snowman.getLocation().toVector()).normalize().multiply(1.5);

        snowman.teleport(snowman.getLocation().setDirection(velocity));
        snowman.launchProjectile(Snowball.class, velocity);
    }

    private void die() {
        SNOWBALL_RUNNABLE.cancel();
    }

}
