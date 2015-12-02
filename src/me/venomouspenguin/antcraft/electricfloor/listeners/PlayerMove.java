package me.venomouspenguin.antcraft.electricfloor.listeners;

import me.venomouspenguin.antcraft.electricfloor.core.Main;
import me.venomouspenguin.antcraft.electricfloor.game.GameManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener{

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		if(!GameManager.getManager().getInGamePlayers().contains(p)) return;
		if(GameManager.getManager().getListenerToggle() == true)
		{
			if (e.getTo().getBlockX() != e.getFrom().getBlockX() || e.getTo().getBlockY() != e.getFrom().getBlockY() || e.getTo().getBlockZ() != e.getFrom().getBlockZ())
			{
			  Block block = e.getFrom().getBlock().getRelative(BlockFace.DOWN);
			  
			  if (block.getType().equals(Material.STAINED_GLASS))
			  {
			    if (block.getData() == 0)
			    {
			      block.setData((byte) 4);
			    }
			    else if (block.getData() == 4)
			    {
			      block.setData((byte) 1);
			    }
			    else if (block.getData() == 1)
			    {
			    	
			      block.setData((byte) 14);
			    }
			  }
			}
			else return;
			if (e.getTo().getBlockX() != e.getFrom().getBlockX() || e.getTo().getBlockY() != e.getFrom().getBlockY() || e.getTo().getBlockZ() != e.getFrom().getBlockZ())
			{
				Block block = e.getTo().getBlock().getRelative(BlockFace.DOWN);
				
				if(block.getType().equals(Material.STAINED_GLASS))
				{
					if(block.getData() == 14)
					{
						if(GameManager.getManager().getInGamePlayers().size() > 2)
						{
							GameManager.getManager().getInGamePlayers().remove(p);
							GameManager.getManager().getSpectators().add(p);
							p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have lost");
							p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have joined the spectators");
							GameManager.getManager().broadcastMessage("Player, " + ChatColor.AQUA + p.getName() + ChatColor.YELLOW + " has lost");
						}
						else if(GameManager.getManager().getInGamePlayers().size() == 2)
						{
							GameManager.getManager().getInGamePlayers().remove(p);
							GameManager.getManager().getSpectators().add(p);
							p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have lost");
							p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have joined the spectators");
							GameManager.getManager().broadcastMessage("Player, " + ChatColor.AQUA + p.getName() + ChatColor.YELLOW + " has lost");
							GameManager.getManager().endGame();
						}
					}
				}
			}
		}
		
		
	}
}
