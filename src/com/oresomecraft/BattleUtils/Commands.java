package com.oresomecraft.BattleUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class Commands implements CommandExecutor {

    BattleUtils plugin;
    Utility utility;
    public Commands(BattleUtils pl) {
	plugin = pl;
	utility = new Utility(plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	if (cmd.getName().equalsIgnoreCase("loadworld")) {

	    if (args.length < 1) {

		sender.sendMessage(ChatColor.RED + "Correct usage: /loadworld <WorldName> [Flags]");
	    } else if (args.length < 2) {

		plugin.getServer().createWorld(new WorldCreator(args[0]));
		sender.sendMessage(ChatColor.AQUA + "Created world '" + args[0] + "'!");

	    } else if (args[1].contains("-n")) {
		plugin.getServer().createWorld(new WorldCreator(args[0]).generator(new NullChunkGenerator()));
		sender.sendMessage(ChatColor.AQUA + "Created/loaded world '" + args[0] + "' using the Nullterrain generator!");
		sender.sendMessage(ChatColor.AQUA + "Warning: Make sure each time you load this world you load it with the -n flag!");

	    }	else if (args[1].contains("-e")) {
		plugin.getServer().createWorld(new WorldCreator(args[0]).environment(Environment.THE_END));
		sender.sendMessage(ChatColor.AQUA + "Created/loaded world '" + args[0] + "'!");

	    } else if (args[1].contains("-") && args[1].contains("e") && args[1].contains("n")) {
		plugin.getServer().createWorld(new WorldCreator(args[0]).environment(Environment.THE_END).generator(new NullChunkGenerator()));
		sender.sendMessage(ChatColor.AQUA + "Created/loaded world '" + args[0] + "' using the Nullterrain generator!");
		sender.sendMessage(ChatColor.AQUA + "Warning: Make sure each time you load this world you load it with the -n flag!");


	    } 

	}

	if (cmd.getName().equalsIgnoreCase("unloadworld")) {
	    if (args.length < 1) {

		sender.sendMessage(ChatColor.RED + "Correct usage: /unloadworld <WorldName>");

	    } else if (args[0].equals("world")) {

		sender.sendMessage(ChatColor.RED + "You may not unlooad the main world!");

	    } else {

		World world = Bukkit.getWorld(args[0]);

		for (Player p : world.getPlayers()) {
		    p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
		}

		utility.forceUnloadWorld(world);
		sender.sendMessage(ChatColor.AQUA + "Unloaed world '" + args[0] + "'");
	    }
	}

	if (cmd.getName().equalsIgnoreCase("worldtp")) {
	    Player p = (Player) sender;
	    if (args.length < 1) {
		sender.sendMessage(ChatColor.RED + "Correct usage: /worldtp <WorldName>");
	    } else {
		p.teleport(Bukkit.getWorld(args[0]).getSpawnLocation());
	    }
	}

	if (cmd.getName().equalsIgnoreCase("worldsetspawn")) {
	    Player p = (Player) sender;
	    World world = Bukkit.getWorld(args[0]);
	    world.setSpawnLocation((int) p.getLocation().getX(), (int) p.getLocation().getY(), (int) p.getLocation().getZ());
	}

	return true;

    }
}
