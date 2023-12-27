package dev.skyphi.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinListener implements Listener {
    
    private static final PotionEffect JOIN_EFFECT = new PotionEffect(PotionEffectType.SPEED, -1, 1);    // speed 2

    @EventHandler
    public void on(PlayerJoinEvent event) {
        event.getPlayer().addPotionEffect(JOIN_EFFECT);
    }

}
