package de.mcmdev.fairwarp.command;

import de.mcmdev.fairwarp.FairWarp;
import de.mcmdev.fairwarp.warp.PadEntity;
import de.mcmdev.fairwarp.warp.PadTimer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWarp implements CommandExecutor {

    private final FairWarp plugin;

    public CommandWarp(FairWarp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))    {
            return true;
        }

        Player player = (Player) sender;

        if(args.length != 1)    {
            return true;
        }

        String name = args[0];
        final Location warp = plugin.getWarpManager().getWarp(name);

        if(warp.getWorld() != player.getWorld())    {
            player.sendMessage("§cYou can only warp to locations in the same world!");
            return true;
        }

        final PadEntity padEntity = new PadEntity(player);
        padEntity.launch(warp.toVector(), 1.0);
        new PadTimer(plugin, padEntity).runTaskTimer(plugin, 0, 1);

        return true;
    }
}
