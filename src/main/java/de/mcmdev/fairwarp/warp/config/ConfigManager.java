package de.mcmdev.fairwarp.warp.config;

import de.mcmdev.fairwarp.FairWarp;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    private YamlConfiguration config;

    private SoundOption[] launchSounds;
    private SoundOption[] flightSounds;
    private SoundOption[] arrivalSounds;

    private ParticleOption[] launchParticles;
    private ParticleOption[] flightParticles;
    private ParticleOption[] arrivalParticles;

    public void load(FairWarp plugin)  {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            plugin.saveDefaultConfig();
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        config.options().copyDefaults(true);

        loadLaunchSounds();
        loadFlightSounds();
        loadArrivalSounds();

        loadLaunchParticles();
        loadFlightParticles();
        loadArrivalParticles();
    }

    private void loadLaunchSounds()   {
        String string = config.getString("launchSounds");
        String[] parts = string.split(";");

        launchSounds = new SoundOption[parts.length];

        for (int i = 0; i < parts.length; i++) {
            launchSounds[i] = new SoundOption(parts[i]);
        }
    }

    private void loadFlightSounds()   {
        String string = config.getString("flightSounds");
        String[] parts = string.split(";");

        flightSounds = new SoundOption[parts.length];

        for (int i = 0; i < parts.length; i++) {
            flightSounds[i] = new SoundOption(parts[i]);
        }
    }

    private void loadArrivalSounds()   {
        String string = config.getString("arrivalSounds");
        String[] parts = string.split(";");

        arrivalSounds = new SoundOption[parts.length];

        for (int i = 0; i < parts.length; i++) {
            arrivalSounds[i] = new SoundOption(parts[i]);
        }
    }

    private void loadLaunchParticles()   {
        String string = config.getString("launchParticles");
        String[] parts = string.split(";");

        launchParticles = new ParticleOption[parts.length];

        for (int i = 0; i < parts.length; i++) {
            launchParticles[i] = new ParticleOption(parts[i]);
        }
    }

    private void loadFlightParticles()   {
        String string = config.getString("flightParticles");
        String[] parts = string.split(";");

        flightParticles = new ParticleOption[parts.length];

        for (int i = 0; i < parts.length; i++) {
            flightParticles[i] = new ParticleOption(parts[i]);
        }
    }

    private void loadArrivalParticles()   {
        String string = config.getString("arrivalParticles");
        String[] parts = string.split(";");

        arrivalParticles = new ParticleOption[parts.length];

        for (int i = 0; i < parts.length; i++) {
            arrivalParticles[i] = new ParticleOption(parts[i]);
        }
    }

    public SoundOption[] getLaunchSounds() {
        return launchSounds;
    }

    public SoundOption[] getFlightSounds() {
        return flightSounds;
    }

    public SoundOption[] getArrivalSounds() {
        return arrivalSounds;
    }

    public ParticleOption[] getLaunchParticles() {
        return launchParticles;
    }

    public ParticleOption[] getFlightParticles() {
        return flightParticles;
    }

    public ParticleOption[] getArrivalParticles() {
        return arrivalParticles;
    }
}
