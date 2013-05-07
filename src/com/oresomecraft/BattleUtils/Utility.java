package com.oresomecraft.BattleUtils;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.server.v1_5_R3.MinecraftServer;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;

public class Utility {

    static BattleUtils plugin;
    public Utility(BattleUtils pl) {
	plugin = pl;
    }
    
    protected MinecraftServer getMinecraftServer() {
	CraftServer server = (CraftServer) plugin.getServer();
	return server.getServer();
    }
    
    public void forceUnloadWorld(World world) {

	CraftServer server = (CraftServer) plugin.getServer();
	CraftWorld craftWorld = (CraftWorld) world;

	try {
	    Field f = server.getClass().getDeclaredField("worlds");
	    f.setAccessible(true);
	    @SuppressWarnings("unchecked")
	    Map<String, World> worlds = (Map<String, World>) f.get(server);
	    worlds.remove(world.getName().toLowerCase());
	    f.setAccessible(false);
	} catch (IllegalAccessException ex) {
	} catch (NoSuchFieldException ex) {
	}

	MinecraftServer ms = getMinecraftServer();
	ms.worlds.remove(ms.worlds.indexOf(craftWorld.getHandle()));
    }
}
