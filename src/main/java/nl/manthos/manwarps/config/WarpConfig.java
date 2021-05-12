package nl.manthos.manwarps.config;

import nl.manthos.manwarps.ManWarps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class WarpConfig {

    private String fileName = "locations.yml";

    private ManWarps main;
    private File file;
    private YamlConfiguration config;

    public WarpConfig(ManWarps main) {

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

    public Location getWarpLocation(String key) {
        String header = "warps." + key;
        if (this.config.contains(header)) {
            UUID world = UUID.fromString(this.config.getString(header + ".world"));
            int x = this.config.getInt(header + ".x");
            int y = this.config.getInt(header + ".y");
            int z = this.config.getInt(header + ".z");
            float pitch = (float) this.config.getDouble(header + ".pitch");
            float yaw = (float) this.config.getDouble(header + ".yaw");
            return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        }
        return null;
    }

    public void setNewWarp(String key, Location location) {
        String header = "warps." + key;
        this.config.set(header + ".world", location.getWorld().getUID().toString());
        this.config.set(header + ".x", location.getX());
        this.config.set(header + ".y", location.getY());
        this.config.set(header + ".z", location.getZ());
        this.config.set(header + ".pitch", location.getPitch());
        this.config.set(header + ".yaw", location.getYaw());
        save();
    }

    public boolean exists(String warpName) {
        String header = "warps.";
        return this.config.contains(header + warpName);
    }

    public Location getWarp(String warpName){
        return this.getWarpLocation(warpName);
    }

    public FileConfiguration getWarpConfig() {
        return this.config;
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
