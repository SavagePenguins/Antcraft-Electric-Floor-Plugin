package me.venomouspenguin.antcraft.electricfloor.game.countdowns;

import me.venomouspenguin.antcraft.electricfloor.game.GameManager;
import me.venomouspenguin.antcraft.electricfloor.game.GameStates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

	private int i;
	private GameManager gm;
	
	
	public Countdown(GameManager gm)
	{
		this.gm = gm;
		this.i = 5;
	}
	
	@Override
	public void run() 
	{
		if(gm.getPlayers().size() == 2)
		{
			
			if(i == 0)
			{
				Bukkit.getServer().getScheduler().cancelTask(gm.getTaskID());
				gm.start();
			}
			else
			{
				if(i == 60 || i == 30 || i == 15 || i == 10)
				{
					gm.broadcastMessage("Game starting in: " + ChatColor.GREEN + i + " Seconds");
				}
				else if(i == 5 || i == 4 || i == 3 || i == 2)
				{
					gm.broadcastMessage("Game starting in: " + ChatColor.GREEN + i + " Seconds");
					for(Player p : gm.getPlayers())
					{
						if(gm.getPlayers().contains(p))
						{
							p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10, 15);
							break;
						}
					}
					
				}
				else if(i == 1)
				{
					gm.broadcastMessage("Game starting in: " + ChatColor.GREEN + i + " Second");
					
					for(Player p : gm.getPlayers())
					{
						if(gm.getPlayers().contains(p))
						{
							p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10, 15);
							break;
						}
					}
				}
				i--;
			}
		}
		else
		{
			Bukkit.getServer().getScheduler().cancelTask(gm.getTaskID());
			gm.broadcastMessage("Not enough players");
			gm.broadcastMessage("Countdown aborted: " + ChatColor.GRAY + "[" + ChatColor.GREEN + gm.getPlayers().size() + ChatColor.GRAY + "/" + ChatColor.GREEN + "8" + 
			ChatColor.GRAY + "]");
			gm.setState(GameStates.LOBBY);
			gm.setAbleToJoin(true);
		}
	}

}
