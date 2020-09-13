package de.mcmdev.fairwarp.warp.config;

import org.bukkit.Sound;

public class SoundOption {

    private Sound sound;
    private float volume;
    private float pitch;

    public SoundOption(String string) {
        String[] parts = string.split(",");

        sound = Sound.valueOf(parts[0]);
        volume = Float.parseFloat(parts[1]);
        pitch = Float.parseFloat(parts[2]);
    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }
}
