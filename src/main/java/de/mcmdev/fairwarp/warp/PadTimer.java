package de.mcmdev.fairwarp.warp;

import de.mcmdev.fairwarp.FairWarp;
import de.mcmdev.fairwarp.warp.config.ParticleOption;
import de.mcmdev.fairwarp.warp.config.SoundOption;
import org.bukkit.scheduler.BukkitRunnable;

public class PadTimer extends BukkitRunnable {

    private FairWarp plugin;
    private PadEntity entity;
    public PadTimer(FairWarp plugin, PadEntity entity) {
        this.plugin = plugin;
        this.entity = entity;
    }

    @Override
    public void run() {
        entity.update();
        flightEffects();

        if(entity.arrived())    {
            entity.arrive();
            arrivalEffects();
            cancel();
        }
    }

    private void flightEffects()    {
        for (SoundOption flightSound : plugin.getConfigManager().getFlightSounds()) {
            if(flightSound.getPitch() == -1)    {
                entity.getEntityLocation().getWorld().playSound(
                        entity.getEntityLocation(),
                        flightSound.getSound(),
                        flightSound.getVolume(),
                        (float) MathUtil.scale(entity.ticksInAir, 0, entity.missileFlightTime, 0.5, 2));
            }   else    {
                entity.getEntityLocation().getWorld().playSound(
                        entity.getEntityLocation(),
                        flightSound.getSound(),
                        flightSound.getVolume(),
                        flightSound.getPitch());
            }
        }

        for (ParticleOption flightParticle : plugin.getConfigManager().getFlightParticles()) {
            entity.getEntityLocation().getWorld().spawnParticle(flightParticle.getParticle(), entity.getEntityLocation(), flightParticle.getCount(), 0, 0, 0, flightParticle.getTime());
        }

        entity.sendEstimatedTimeLeft();
    }

    private void arrivalEffects()    {
        for (SoundOption arrivalSounds : plugin.getConfigManager().getArrivalSounds()) {
                entity.getEntityLocation().getWorld().playSound(
                        entity.getEntityLocation(),
                        arrivalSounds.getSound(),
                        arrivalSounds.getVolume(),
                        arrivalSounds.getPitch());
        }

        for (ParticleOption arrivalParticle : plugin.getConfigManager().getArrivalParticles()) {
            entity.getEntityLocation().getWorld().spawnParticle(arrivalParticle.getParticle(), entity.getEntityLocation(), arrivalParticle.getCount(), 0, 0, 0, arrivalParticle.getTime());
        }

        entity.sendArrival();
    }
}
