package dev.skyphi.Listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import dev.skyphi.KillGoal;
import dev.skyphi.Models.AngrySnowman;

public class SpawnerListener implements Listener {
    
    public static final int MIN_DELAY = 20, MAX_DELAY = 200; // in ticks

    private int activeSnowmen;

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if(event.getItem() == null || event.getItem().getType() != Material.SNOW_GOLEM_SPAWN_EGG) return;
        if(event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.SPAWNER) return;
        CreatureSpawner spawner = (CreatureSpawner)event.getClickedBlock().getState();
        spawner.setMinSpawnDelay(MIN_DELAY);
        spawner.setMaxSpawnDelay(MAX_DELAY);
        spawner.setRequiredPlayerRange(20);
        spawner.setSpawnRange(10);
        spawner.update();
        event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, SoundCategory.RECORDS, 1.0f, 1.0f);
    }

    @EventHandler
    public void on(SpawnerSpawnEvent event) {
        if(event.getEntityType() != EntityType.SNOWMAN) return;
        if(activeSnowmen >= KillGoal.getMaxSnowmen()) {
            event.setCancelled(true);
            return;
        }

        new AngrySnowman((Snowman)event.getEntity());
        activeSnowmen++;
    }

    @EventHandler
    public void on(EntityDeathEvent event) {
        if(event.getEntityType() != EntityType.SNOWMAN) return;
        if(!event.getEntity().getPersistentDataContainer().has(AngrySnowman.SNOWMAN_KEY, PersistentDataType.BOOLEAN)) return;
        
        activeSnowmen--;
    }

}
