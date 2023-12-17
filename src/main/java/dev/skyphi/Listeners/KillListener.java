package dev.skyphi.Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import dev.skyphi.Models.PlayerData;

public class KillListener implements Listener {
    
    @EventHandler
    public void on(EntityDamageByEntityEvent event) {
        if(event.getDamager().getType() != EntityType.PLAYER) return;   // damager is not a player
        if(!(event.getEntity() instanceof LivingEntity)) return;        // damaged is not a mob
        LivingEntity entity = (LivingEntity)event.getEntity();
        if(entity.getHealth() - event.getFinalDamage() > 0) return;     // not a fatal hit

        Player killer = (Player)event.getDamager();
        PlayerData.addKill(killer);                                     // add kill to player's data
    }

    @EventHandler
    public void on(EntityDeathEvent event) {
        if(event.getEntityType() == EntityType.SNOWMAN) event.getDrops().clear();
    }

}
