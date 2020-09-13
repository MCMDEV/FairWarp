package de.mcmdev.fairwarp.warp.config;

import org.bukkit.Particle;

public class ParticleOption {

    private Particle particle;
    private int count;
    private int time;

    public ParticleOption(String string) {
        String[] parts = string.split(",");

        particle = Particle.valueOf(parts[0]);
        count = Integer.parseInt(parts[1]);
        time = Integer.parseInt(parts[2]);
    }

    public Particle getParticle() {
        return particle;
    }

    public int getCount() {
        return count;
    }

    public int getTime() {
        return time;
    }
}
