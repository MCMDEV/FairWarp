package de.mcmdev.fairwarp.warp;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PadEntity {

    private Player holder;

    public Vector source;
    public Vector target;

    public double launchHeight;
    public int maxHeight = 200;
    public int lockHeight = 3;

    //Unused: public int targetHeight = -1;
    // Difference
    public double deltaPathX;
    public double deltaPathY;
    public double deltaPathZ;
    // Flat Distance
    public double flatDistance;
    // The flight time in ticks
    public float missileFlightTime;
    // Acceleration
    public float acceleration;

    public double motionX;
    public double motionY;
    public double motionZ;

    public float rotationPitch;
    public float rotationYaw;

    public int ticksInAir;

    private boolean allowFlight;
    private boolean flying;

    public PadEntity(Player holder) {
        this.holder = holder;

        this.flying = holder.isFlying();
        this.allowFlight = holder.getAllowFlight();

        this.holder.setAllowFlight(true);
    }

    public void launch(Vector target, double launchHeight)    {

        this.source = holder.getLocation().toVector();
        this.target = target;
        this.launchHeight = launchHeight;

        recalculate();
        update();
    }

    public void recalculate()   {
        if(target != null)  {

            this.deltaPathX = this.target.getX() - this.source.getX();
            this.deltaPathY = this.target.getY() - this.source.getY();
            this.deltaPathZ = this.target.getZ() - this.source.getZ();


            //this.flatDistance = this.source.toVector2().distance(this.targetPos.toVector2());
            this.flatDistance = flatDistance(source, target);
            // Parabolic Height
            this.maxHeight = (int) (80 + (this.flatDistance * 3) * launchHeight);
            // Flight time
            this.missileFlightTime = (float) Math.max(100, 2 * this.flatDistance) - this.ticksInAir;
            // Acceleration
            this.acceleration = (float) this.maxHeight * 2 / (this.missileFlightTime * this.missileFlightTime);
        }
    }

    public void update()    {
        this.holder.setFlying(true);
        //Start motion
        ticksInAir++;

        if (this.lockHeight > 0)
        {
            this.motionY = 1 * this.ticksInAir * (this.ticksInAir / 2f);
            this.motionX = 0;
            this.motionZ = 0;
            this.lockHeight -= this.motionY;
            if (this.lockHeight <= 0)
            {
                this.motionY = this.acceleration * (this.missileFlightTime / 2);
                this.motionX = this.deltaPathX / missileFlightTime;
                this.motionZ = this.deltaPathZ / missileFlightTime;
            }
        }
        else
        {
            this.motionY -= this.acceleration;
        }

        Location location = holder.getLocation();
        location.add(motionX, motionY, motionZ);

        Vector direction = location.toVector().subtract(holder.getLocation().toVector());
        location.setDirection(direction);

        holder.teleport(location);
    }

    public void sendEstimatedTimeLeft()    {
        long rawtime = (long) (missileFlightTime - ticksInAir);

        int etl = (int) (rawtime / 20);

        int hours =(etl/3600);
        int minAndSec = (etl%3600);
        int min = minAndSec/60;
        int sec = minAndSec%60;

        String cooldown = (hours > 9 ?  hours : "0" + hours) + ":" + (min > 9 ?  min : "0" + min) + ":" + (sec > 9 ?  sec : "0" + sec);

        holder.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§eEstimated Time left: §c" + cooldown));
    }

    public void sendArrival()   {
        holder.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§aYou have arrived!"));
    }

    public double flatDistance(Vector first, Vector second)  {

        double firstX = first.getX();
        double firstZ = first.getZ();

        double secondX = second.getX();
        double secondZ = second.getZ();

        firstX = firstX - secondX;
        firstZ = firstZ - secondZ;

        return Math.sqrt(firstX * firstX + firstZ * firstZ);

    }

    public boolean arrived()    {
        if(holder.getEyeLocation().getBlock().getType() != Material.AIR || holder.getLocation().getBlock().getType() != Material.AIR)   {
            if(holder.getEyeLocation().getY() < 254)   {
                return true;
            }
        }
        if(holder.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR)     {
            if(holder.getEyeLocation().getY() < 254)   {
                return true;
            }
        }
        return ticksInAir >= missileFlightTime;
    }

    public void arrive()    {
        this.holder.setFlying(flying);
        this.holder.setAllowFlight(allowFlight);
        this.holder.setMaximumNoDamageTicks(100);
        this.holder.setNoDamageTicks(100);
    }

    public Location getEntityLocation() {
        return holder.getLocation();
    }

    private Rotation lookAt(double x, double y, double z)   {
        double dirx = holder.getLocation().getX() - x;
        double diry = holder.getLocation().getY() - y;
        double dirz = holder.getLocation().getZ() - z;

        double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        //to degree
        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;

        yaw += 90f;
        return new Rotation(pitch, yaw);
    }

    class Rotation  {
        private float pitch;
        private float yaw;

        public Rotation(double pitch, double yaw) {
            this.pitch = (float) pitch;
            this.yaw = (float) yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public float getYaw() {
            return yaw;
        }
    }

    public boolean isFlying()   {
        return holder.isFlying();
    }

    public void setFlying(boolean bool)    {
        holder.setFlying(bool);
    }
}
