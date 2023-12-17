package dev.skyphi.Models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import dev.skyphi.KillGoal;

public class PlayerData implements Serializable {
    
    private static HashMap<UUID, PlayerData> ALL_DATA = new HashMap<>();

    private UUID uuid;
    private int killCount;

    public PlayerData(Player player) {
        this.uuid = player.getUniqueId();

        ALL_DATA.put(player.getUniqueId(), this);
    }

    // GETTERS & SETTERS //
    public UUID getUniqueId() { return uuid; }

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
        player.sendMessage("+1... Total: " + get(player).getKillCount());
        KillGoal.addToKills();
        save();
    }

    public static void save() {
        String path = KillGoal.INSTANCE.getDataFolder()+"/players.data";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            List<PlayerData> valuesList = new ArrayList<>(ALL_DATA.values());
            oos.writeObject(valuesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        String path = KillGoal.INSTANCE.getDataFolder()+"/players.data";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            @SuppressWarnings("unchecked")
            Collection<PlayerData> loadedData = (Collection<PlayerData>) ois.readObject();
            
            for(PlayerData pd : loadedData) {
                ALL_DATA.put(pd.getUniqueId(), pd);
            }
        } catch (FileNotFoundException e) {
            // file doesn't exist yet - do nothing
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
