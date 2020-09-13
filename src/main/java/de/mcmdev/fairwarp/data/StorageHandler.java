package de.mcmdev.fairwarp.data;

import de.mcmdev.fairwarp.FairWarp;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class StorageHandler {

    private FairWarp plugin;
    private File warpsFile;
    private YamlConfiguration warps;

    public StorageHandler(FairWarp plugin) {
        this.plugin = plugin;

        this.warpsFile = new File(plugin.getDataFolder(), "warps.yml");
        try {
            this.warpsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.warps = YamlConfiguration.loadConfiguration(warpsFile);
    }

    public void loadWarps() {
        final ConfigurationSection section;
        if(warps.isConfigurationSection("warps"))   {
            section = this.warps.getConfigurationSection("warps");
        }   else    {
            section = this.warps.createSection("warps");
        }

        for (String key : section.getKeys(false)) {
            final Location location = section.getLocation(key);
            plugin.getWarpManager().setWarp(key, location);
        }
    }

    public void saveWarps() {
        final ConfigurationSection section;
        if(warps.isConfigurationSection("warps"))   {
            section = this.warps.getConfigurationSection("warps");
        }   else    {
            section = this.warps.createSection("warps");
        }

        for (Map.Entry<String, Location> entry : plugin.getWarpManager().getWarpMap().entrySet()) {
            section.set(entry.getKey(), entry.getValue());
        }

        try {
            warps.save(warpsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
