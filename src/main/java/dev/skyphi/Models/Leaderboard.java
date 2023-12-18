package dev.skyphi.Models;

import java.io.Serializable;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unldenis.hologram.Hologram;
import com.github.unldenis.hologram.HologramBuilder;
import com.github.unldenis.hologram.HologramPool;

import dev.skyphi.KillGoal;

public class Leaderboard implements Serializable {
    
    public static final int UPDATE_FREQUENCY = 20*10; // 1 hour in ticks
    public static final int VISIBLE_RANGE = 200;

    private int numPlayers;
    private Location location;
    
    private transient HologramPool hologramPool;
    private transient boolean running;

    private final transient BukkitRunnable UPDATE_RUNNABLE = new BukkitRunnable() {
            @Override
            public void run() {
                clearHolograms();
                PlayerData[] topPlayers = PlayerData.getTopPlayers(numPlayers).toArray(PlayerData[]::new);
                HologramBuilder builder = Hologram.builder(KillGoal.INSTANCE, location);    
                for(int i = 0; i < topPlayers.length; i++) {
                    builder.addLine(makePlayerLine(topPlayers[i], i));
                }
                builder.loadAndBuild(hologramPool);
            }
        };

    public Leaderboard(int numPlayers, Location location) {
        this.numPlayers = numPlayers;
        this.location = location;
    }

    public void destroy() {
        if(!running) return;
        UPDATE_RUNNABLE.cancel();
        clearHolograms();
    }

    public void initLeaderboard() {
        if(hologramPool != null) hologramPool.getHolograms().clear();
        hologramPool = new HologramPool(KillGoal.INSTANCE, VISIBLE_RANGE);

        UPDATE_RUNNABLE.runTaskTimer(KillGoal.INSTANCE, 0, UPDATE_FREQUENCY);
        running = true;
    }

    private void clearHolograms() {
        hologramPool.getHolograms().forEach(h -> {
            hologramPool.remove(h);
        });
    }

    private String makePlayerLine(PlayerData playerData, int number) {
        number++;
        String colour = null;
        switch(number) {
            case 1:
                colour = ChatColor.YELLOW+""+ChatColor.BOLD;
                break;
            case 2:
                colour = ChatColor.GRAY+""+ChatColor.BOLD;
                break;
            case 3:
                colour = ChatColor.GOLD+""+ChatColor.BOLD;
                break;
            default:
                colour = ChatColor.GREEN+""+ChatColor.BOLD;
                break;
        }

        return colour + number + ". " + playerData.getName() + " - " 
            + ChatColor.RED+""+ChatColor.BOLD + playerData.getKillCount();
    }

}
