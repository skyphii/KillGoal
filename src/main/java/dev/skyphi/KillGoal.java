package dev.skyphi;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.unldenis.hologram.HologramPool;

import dev.skyphi.Commands.LeaderboardCommand;
import dev.skyphi.Commands.SetGoalCommand;
import dev.skyphi.Listeners.KillListener;
import dev.skyphi.Listeners.SnowballListener;
import dev.skyphi.Listeners.SpawnerListener;
import dev.skyphi.Models.Leaderboard;
import dev.skyphi.Models.PlayerData;

public class KillGoal extends JavaPlugin {

    public static KillGoal INSTANCE;
    public static HologramPool HOLOGRAMS;

    public static int GOAL;
    public static int TOTAL_KILLS;
    public static Leaderboard LEADERBOARD;

    @Override
    public void onEnable() {
        KillGoal.INSTANCE = this;

        initConfig();
        PlayerData.load();
        LEADERBOARD = Leaderboard.load();
        if(LEADERBOARD != null) {
            LEADERBOARD.initialize();
        }

        getServer().getPluginManager().registerEvents(new SnowballListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
        getServer().getPluginManager().registerEvents(new KillListener(), this);

        this.getCommand("setgoal").setExecutor(new SetGoalCommand());
        this.getCommand("leaderboard").setExecutor(new LeaderboardCommand());
    }

    @Override
    public void onDisable() {
        PlayerData.save();
        if(LEADERBOARD != null) {
            LEADERBOARD.save();
            // LEADERBOARD.destroy();
        }
    }

    private void initConfig() {
        saveDefaultConfig();
        KillGoal.GOAL = getConfig().getInt("goal");
        KillGoal.TOTAL_KILLS = getConfig().getInt("progress");
    }

    public static int addToKills() {
        if(++TOTAL_KILLS == GOAL) onKillGoalReached();
        INSTANCE.getConfig().set("progress", TOTAL_KILLS);
        INSTANCE.saveConfig();
        return TOTAL_KILLS;
    }

    public static void onKillGoalReached() {
        INSTANCE.getServer().broadcastMessage(ChatColor.AQUA+""+ChatColor.BOLD
            + "THE KILL GOAL OF " + ChatColor.RED+""+ChatColor.BOLD + GOAL
            + ChatColor.AQUA+""+ChatColor.BOLD + " HAS BEEN REACHED!");
    }

}
