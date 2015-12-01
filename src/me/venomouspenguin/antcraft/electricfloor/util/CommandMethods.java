package me.venomouspenguin.antcraft.electricfloor.util;

import org.bukkit.entity.Player;

public class CommandMethods 
{
	
	public boolean hasPermission(Player p, String perm)
	{
		return p.hasPermission("antcraft.user." + perm);
	}
	
}
