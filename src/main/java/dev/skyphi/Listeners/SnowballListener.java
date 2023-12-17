package dev.skyphi.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SnowballListener implements Listener {

    private static final double SNOWBALL_DAMAGE = 1.0;

    @EventHandler
    public void on(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if(projectile.getType() != EntityType.SNOWBALL) return;

        Entity hitEntity = event.getHitEntity();
        if(hitEntity == null) return;
        if(hitEntity.getType() == EntityType.SNOWMAN) event.setCancelled(true); // cancel hits on snowmen
        if(hitEntity.getType() != EntityType.PLAYER) return;

        Player hitPlayer = (Player)hitEntity;
        hitPlayer.damage(SNOWBALL_DAMAGE, (Entity)projectile.getShooter());
    }

}
