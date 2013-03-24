package com.oresomecraft.BattleUtils;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BattleUtils extends JavaPlugin {

    public final Logger logger = Logger.getLogger("Minecraft");

    public void onDisable() {

	PluginDescriptionFile pdfFile = getDescription();
	this.logger.info(pdfFile.getName() + "is now disabled");
    }

    public void onEnable() {

	PluginDescriptionFile pdfFile = getDescription();
	this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + "is now enabled");
	registerCommands();
	
    }

    public void registerCommands() {

	getCommand("loadworld").setExecutor(new Commands(this));
	getCommand("unloadworld").setExecutor(new Commands(this));
	getCommand("worldtp").setExecutor(new Commands(this));
	getCommand("worldsetspawn").setExecutor(new Commands(this));
    }
}
