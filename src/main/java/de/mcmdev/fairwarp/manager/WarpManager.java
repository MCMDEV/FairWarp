package de.mcmdev.fairwarp.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WarpManager {

    private Map<String, Location> warpMap = new HashMap<>();
    private Map<UUID, Map<String, Location>> homeMap = new HashMap<>();

    public void setWarp(String name, Location location) {
        this.warpMap.put(name, location);
    }

    public Location getWarp(String name)    {
        return warpMap.get(name);
    }

    public void setWarpMap(Map<String, Location> warpMap) {
        this.warpMap = warpMap;
    }

    public Map<String, Location> getWarpMap() {
        return warpMap;
    }

    public Location getHome(Player player, String name)    {
        final UUID uuid = player.getUniqueId();
        if(!homeMap.containsKey(uuid))  {
            homeMap.put(uuid, new HashMap<>());
        }
        final Map<String, Location> locationMap = homeMap.get(uuid);
        return locationMap.get(name);
    }

    public void setHome(Player player, String name) {
        final UUID uuid = player.getUniqueId();
        if(!homeMap.containsKey(uuid))  {
            homeMap.put(uuid, new HashMap<>());
        }
        final Map<String, Location> locationMap = homeMap.get(uuid);
        locationMap.put(name, player.getLocation());
    }

    public int getHomeCount(Player player)  {
        final UUID uuid = player.getUniqueId();
        if(!homeMap.containsKey(uuid)) return 0;
        return homeMap.get(uuid).size();
    }

}
