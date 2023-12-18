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
    
    public static final int UPDATE_FREQUENCY = 20*60; // 1 minute (in ticks)
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
                builder.addLine(makeProgressLine());
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

    public void initialize() {
        if(hologramPool != null) hologramPool.getHolograms().clear();
        hologramPool = new HologramPool(KillGoal.INSTANCE, VISIBLE_RANGE);

        UPDATE_RUNNABLE.runTaskTimer(KillGoal.INSTANCE, 0, UPDATE_FREQUENCY);
        running = true;
    }

    public void save() {
        KillGoal.INSTANCE.getConfig().set("leaderboard.numPlayers", numPlayers);
        KillGoal.INSTANCE.getConfig().set("leaderboard.location", location);
        KillGoal.INSTANCE.saveConfig();
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
                colour = ChatColor.WHITE+""+ChatColor.BOLD;
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

    private String makeProgressLine() {
        return ChatColor.AQUA+""+ChatColor.BOLD + "GOAL PROGRESS: "
            + (KillGoal.TOTAL_KILLS < KillGoal.GOAL ? ChatColor.RED : ChatColor.GREEN)+""+ChatColor.BOLD + KillGoal.TOTAL_KILLS
            + ChatColor.AQUA+""+ChatColor.BOLD + "/"
            + ChatColor.GREEN+""+ChatColor.BOLD + KillGoal.GOAL;
    }

    // STATIC METHOD(S) //
    public static Leaderboard load() {
        int numPlayers = KillGoal.INSTANCE.getConfig().getInt("leaderboard.numPlayers");
        Location location = KillGoal.INSTANCE.getConfig().getLocation("leaderboard.location");
        if(location == null) return null;
        return new Leaderboard(numPlayers, location);
    }

}
