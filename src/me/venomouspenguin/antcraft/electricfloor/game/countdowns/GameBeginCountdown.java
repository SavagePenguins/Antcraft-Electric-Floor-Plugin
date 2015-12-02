package me.venomouspenguin.antcraft.electricfloor.game.countdowns;

import me.venomouspenguin.antcraft.electricfloor.game.GameManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameBeginCountdown extends BukkitRunnable {

	GameManager gm ;
	private int i;
	
	
	public GameBeginCountdown(GameManager gm)
	{
		this.i = 20;
		this.gm = gm;
	}
	
	public void run() 
	{
		if(i == 0)
		{
			Bukkit.getServer().getScheduler().cancelTask(gm.getTaskID1());
			gm.inGameStart();
		}
		else if(i == 3 || i == 2)
		{
			gm.broadcastMessage("Game starting in: " + ChatColor.GREEN + i + " Seconds");
		}
		else if(i == 1)
		{
			gm.broadcastMessage("Game starting in: " + ChatColor.GREEN + i + " Second");
		}
		i--;
	}

}
