package me.venomouspenguin.antcraft.electricfloor.listeners;

import me.venomouspenguin.antcraft.electricfloor.core.Main;
import me.venomouspenguin.antcraft.electricfloor.game.GameManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Playerleave implements Listener{

	@SuppressWarnings("unused")
	private Main plugin;
	
	public Playerleave(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		if(!GameManager.getManager().getPlayers().contains(p)) return;
		
		GameManager.getManager().removePlayer(p);
	}
}
