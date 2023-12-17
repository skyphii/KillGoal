package dev.skyphi.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import dev.skyphi.KillGoal;

public class PlayerData implements Serializable {
    
    private static HashMap<UUID, PlayerData> ALL_DATA = new HashMap<>();

    private Player player;
    private int killCount;

    public PlayerData(Player player) {
        this.player = player;

        ALL_DATA.put(player.getUniqueId(), this);
    }

    // GETTERS & SETTERS //
    public Player getPlayer() { return player; }

    public int getKillCount() { return killCount; }
    public void addKill() { killCount++; }

    // STATIC METHODS //
    public static PlayerData get(Player player) {
        PlayerData pd = ALL_DATA.get(player.getUniqueId());
        if(pd != null) return pd;
        return new PlayerData(player);
    }

    public static void addKill(Player player) {
        if(KillGoal.TOTAL_KILLS < KillGoal.GOAL) get(player).addKill();
        KillGoal.addToKills();
        save();
    }

    public static void save() {
        // TO-DO
        // save ALL_DATA hashmap to file
    }

    public static void load() {
        // TO-DO
        // load ALL_DATA hashmap from file
    }

}
