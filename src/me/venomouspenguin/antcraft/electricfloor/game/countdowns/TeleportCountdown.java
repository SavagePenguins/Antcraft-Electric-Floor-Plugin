package me.venomouspenguin.antcraft.electricfloor.game.countdowns;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;

import me.venomouspenguin.antcraft.electricfloor.game.GameManager;

public class TeleportCountdown implements Runnable {

	private GameManager gm;
	private int i;
	
	
	public TeleportCountdown(GameManager gm)
	{
		this.gm = gm;
		this.i = 10;
	}
	
	@Override
	public void run() 
	{
		
		if(i == 0)
		{
			Bukkit.getServer().getScheduler().cancelTask(gm.getTaskID());
			gm.setHasDoneCountdownTeleport(true);
			gm.teleportPlayersToArena();
		}
		else if(i == 3 || i == 2)
		{
			gm.broadcastMessage("Teleporting in: " + ChatColor.GREEN + i + " Seconds");
		}
		else if(i == 1)
		{
			gm.broadcastMessage("Teleporting in: " + ChatColor.GREEN + i + " Second");
		} 
		
		i--;
		
	}

}
