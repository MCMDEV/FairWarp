package de.mcmdev.fairwarp.command;

import de.mcmdev.fairwarp.FairWarp;
import de.mcmdev.fairwarp.warp.PadEntity;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetWarp implements CommandExecutor {

    private final FairWarp plugin;

    public CommandSetWarp(FairWarp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))    {
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("fairwarp.setwarp"))   {
            return true;
        }

        if(args.length != 1)    {
            return true;
        }

        String name = args[0];
        plugin.getWarpManager().setWarp(name, player.getLocation());
        player.sendMessage("§aWarp created!");

        return true;
    }
}
