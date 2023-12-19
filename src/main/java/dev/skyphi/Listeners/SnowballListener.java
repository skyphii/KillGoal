package dev.skyphi.Listeners;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dev.skyphi.Models.AngrySnowman;

public class SnowballListener implements Listener {

    private static final double SNOWBALL_DAMAGE = 0.5;

    @EventHandler
    public void on(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if(projectile.getType() != EntityType.SNOWBALL) return;

        if(projectile.getPersistentDataContainer().has(AngrySnowman.DEATHBALL_KEY, PersistentDataType.BOOLEAN)) {
            spawnDeathballEffect(projectile.getLocation());
        }

        Entity hitEntity = event.getHitEntity();
        if(hitEntity == null) return;
        if(hitEntity.getType() == EntityType.SNOWMAN) event.setCancelled(true); // cancel hits on snowmen
        if(hitEntity.getType() != EntityType.PLAYER) return;

        Player hitPlayer = (Player)hitEntity;
        hitPlayer.damage(SNOWBALL_DAMAGE, (Entity)projectile.getShooter());
    }

    private void spawnDeathballEffect(Location location) {
        AreaEffectCloud cloud = (AreaEffectCloud)location.getWorld().spawnEntity(location, EntityType.AREA_EFFECT_CLOUD);
        cloud.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 20*10, 1), true);
        cloud.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 20*10, 0), true);
        location.getWorld().playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE, 1.0f, 1.0f);
    }

}
