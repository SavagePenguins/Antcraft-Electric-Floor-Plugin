package me.venomouspenguin.antcraft.electricfloor.core;

import me.venomouspenguin.antcraft.electricfloor.commands.AC_ElectricCommand;
import me.venomouspenguin.antcraft.electricfloor.listeners.PlayerMove;
import me.venomouspenguin.antcraft.electricfloor.listeners.Playerleave;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	public String LOGO = ChatColor.DARK_GRAY + "(" + ChatColor.AQUA + "Antcraft" + ChatColor.DARK_GRAY + ")" + ChatColor.RESET + " ";

	static Main plugin = null;
	
	public void onEnable()
	{
		this.plugin = this;
		saveDefaultConfig();
		
		getCommand("electricfloor").setExecutor(new AC_ElectricCommand());
		Bukkit.getServer().getPluginManager().registerEvents(new Playerleave(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerMove(), this);
	}
	
	public static Main getInstance()
	{
		return plugin;
	}
}
