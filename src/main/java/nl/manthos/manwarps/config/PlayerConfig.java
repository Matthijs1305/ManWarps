package nl.manthos.manwarps.config;

import nl.manthos.manwarps.ManWarps;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerConfig {

    private File file;
    private YamlConfiguration config;
    private String fileName = "playerdata.yml";

    public PlayerConfig(ManWarps main) {

        file = new File(main.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        save();
    }

    public void addPlayer(Player player) {
        String header = "players." + player.getUniqueId();
        this.config.set(header + ".standardwarp", "");
        save();
    }

    public void setStandardWarp(Player player, String warpName) {
        String header = "players." + player.getUniqueId();
        this.config.set(header + ".standardwarp", warpName);
        save();
    }

    public String getStandardWarp(Player player) {
        String header = "players." + player.getUniqueId();
        return (String) this.config.get(header + ".standardwarp");
    }

    public boolean contains(Player player) {
        String header = "players." + player.getUniqueId();
        return this.config.contains(header);
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
