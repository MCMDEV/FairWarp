package de.mcmdev.fairwarp;

import de.mcmdev.fairwarp.command.CommandSetWarp;
import de.mcmdev.fairwarp.command.CommandWarp;
import de.mcmdev.fairwarp.data.StorageHandler;
import de.mcmdev.fairwarp.manager.WarpManager;
import de.mcmdev.fairwarp.warp.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FairWarp extends JavaPlugin {

    private ConfigManager configManager;
    private WarpManager warpManager;
    private StorageHandler storageHandler;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.configManager = new ConfigManager();
        this.configManager.load(this);
        this.warpManager = new WarpManager();
        this.storageHandler = new StorageHandler(this);

        getCommand("warp").setExecutor(new CommandWarp(this));
        getCommand("setwarp").setExecutor(new CommandSetWarp(this));

        storageHandler.loadWarps();
    }

    @Override
    public void onDisable() {
        storageHandler.saveWarps();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }
}
