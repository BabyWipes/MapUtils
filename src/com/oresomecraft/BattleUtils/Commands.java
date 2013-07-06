package com.oresomecraft.BattleUtils;

import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.*;

public class Commands {

    MapUtils plugin;
    Utility utility;

    public Commands(MapUtils pl) {
        plugin = pl;
        utility = new Utility(plugin);
    }

    @Command(aliases = {"loadworld"},
            usage = "/loadworld <WorldName> [Flags]",
            desc = "Loads are creates a world.",
            flags = "en")
    @CommandPermissions({"battleutils.loadworld"})
    public void loadworld(CommandContext args, CommandSender sender) throws CommandException {

        if (args.argsLength() < 1) {

            sender.sendMessage(ChatColor.RED + "Correct usage: /loadworld <WorldName> [Flags]");

        } else {

            if (args.hasFlag('n')) {
                plugin.getServer().createWorld(new WorldCreator(args.getString(0)).generator(new NullChunkGenerator()));
                sender.sendMessage(ChatColor.AQUA + "Created/loaded world '" + args.getString(0) + "' using the Nullterrain generator!");
                sender.sendMessage(ChatColor.AQUA + "Warning: Make sure each time you load this world you lo�ad it with the -n flag!");

            } else if (args.hasFlag('e')) {
                plugin.getServer().createWorld(new WorldCreator(args.getString(0)).environment(Environment.THE_END));
                sender.sendMessage(ChatColor.AQUA + "Created/loaded world '" + args.getString(0) + "'!");

            } else if (args.hasFlag('e') && args.hasFlag('n')) {
                plugin.getServer().createWorld(new WorldCreator(args.getString(0)).environment(Environment.THE_END).generator(new NullChunkGenerator()));
                sender.sendMessage(ChatColor.AQUA + "Created/loaded world '" + args.getString(0) + "' using the Nullterrain generator!");
                sender.sendMessage(ChatColor.AQUA + "Warning: Make sure each time you load this world you load it with the -n flag!");

            } else {
                plugin.getServer().createWorld(new WorldCreator(args.getString(0)));
                sender.sendMessage(ChatColor.AQUA + "Created world '" + args.getString(0) + "'!");
            }
        }
    }

    @Command(aliases = {"unloadworld"},
            usage = "/unloadworld <WorldName>",
            desc = "Unloads are creates a world.")
    @CommandPermissions({"battleutils.unloadworld"})
    public void unloadworld(CommandContext args, CommandSender sender) throws CommandException {

        if (args.argsLength() < 1) {

            sender.sendMessage(ChatColor.RED + "Correct usage: /unloadworld <WorldName>");

        } else if (args.getString(0).equals("world")) {

            sender.sendMessage(ChatColor.RED + "You may not unlooad the main world!");

        } else {

            World world = Bukkit.getWorld(args.getString(0));

            for (Player p : world.getPlayers()) {
                p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            }

            for (Chunk c : world.getLoadedChunks()) {
                c.unload();
            }

            Bukkit.unloadWorld(world, true);

            sender.sendMessage(ChatColor.AQUA + "Attempted unloaded world '" + args.getString(0) + "'");
        }

    }

    @Command(aliases = {"worldtp"},
            usage = "/worldtp <WorldName>",
            desc = "Teleports you to a world.")
    @CommandPermissions({"battleutils.worldtp"})
    public void worldtp(CommandContext args, CommandSender sender) throws CommandException {
        Player p = (Player) sender;
        if (args.argsLength() < 1) {
            sender.sendMessage(ChatColor.RED + "Correct usage: /worldtp <WorldName>");
        } else {
            if (Bukkit.getWorld(args.getString(0)) != null) {
                p.teleport(Bukkit.getWorld(args.getString(0)).getSpawnLocation());
            }
        }
    }

    @Command(aliases = {"worldsetspawn"},
            usage = "/worldsetspawn",
            desc = "Sets spawn for a world.")
    @CommandPermissions({"battleutils.worldsetspawn"})
    public void worldsetspawn(CommandContext args, CommandSender sender) throws CommandException {
        Player p = (Player) sender;
        World world = p.getWorld();
        world.setSpawnLocation((int) p.getLocation().getX(), (int) p.getLocation().getY(), (int) p.getLocation().getZ());
        sender.sendMessage(ChatColor.AQUA + "Set spawn point for world '" + p.getWorld().getName() + "'");
    }

}
