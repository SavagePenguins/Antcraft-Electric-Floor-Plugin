package me.venomouspenguin.antcraft.electricfloor.commands;

import me.venomouspenguin.antcraft.electricfloor.core.Main;
import me.venomouspenguin.antcraft.electricfloor.game.GameManager;
import me.venomouspenguin.antcraft.electricfloor.util.CommandMethods;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AC_ElectricCommand extends CommandMethods implements CommandExecutor {

	Main m = Main.getInstance();
	
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		
		if(!(cs instanceof Player))
		{
			return true;
		}
		
		Player p = (Player) cs;
		
		if(args.length == 0)
		{
			p.sendMessage(ChatColor.YELLOW + "-=-= " + ChatColor.RED + "Electric" + ChatColor.AQUA + "Floor " + ChatColor.YELLOW + "=-=-");
			p.sendMessage(m.LOGO + ChatColor.YELLOW + "To join the game - " + ChatColor.GREEN + "/ecf join");
			p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "To leave the game - " + ChatColor.GREEN + "/ecf leave");
			p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Admin commands - " + ChatColor.GREEN + "/ecf admin");
			return true;
		}
		
		if(args[0].equals("join"))
		{
			GameManager.getManager().addPlayer(p);
			return true;
		}
		if(args[0].equals("leave"))
		{
			GameManager.getManager().removePlayer(p);
			return true;
		}
		if(args[0].equals("admin") && hasPermission(p, "electricfloor.admin"))
		{
			if(args.length == 1)
			{
				p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "To setup spawnpoints - " + ChatColor.GREEN + "/ecf admin setspawn <1 - 8>");
				p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "To setup lobby spawn - " + ChatColor.GREEN + "/ecf admin setlobby");
				p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "To setup floor cords - " + ChatColor.GREEN + "/ecf admin setlocations");
				p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "To disable use of join - " + ChatColor.GREEN + "/ecf admin disable");
				return true;
			}
			if(args[1].equalsIgnoreCase("setspawn"))
			{
				if(args.length == 2)
				{
					p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Incorrect Arguments");
					return true;
				}
				int slots;
				
				try
				{
				   slots = Integer.parseInt(args[2]);
				}catch(NumberFormatException e)
				{
				   p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Set spawn must be an integer");
				   return true;
				}
				
				GameManager.getManager().setSpawnLocation(p, slots);
				return true;
			}
			if(args[1].equalsIgnoreCase("setlobby"))
			{
				GameManager.getManager().setLobbySpawn(p);
				return true;
			}
			if(args[1].equalsIgnoreCase("setlocations"))
			{
				if(args.length == 2)
				{
					p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Set Xmin cord - " + ChatColor.GREEN + "/ecf admin setlocations xmin");
					p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Set Xmax cord - " + ChatColor.GREEN + "/ecf admin setlocations xmax");
					p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Set Zmin cord - " + ChatColor.GREEN + "/ecf admin setlocations zmin");
					p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Set Zmax cord - " + ChatColor.GREEN + "/ecf admin setlocations zmax");
					p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Set Y cord - " + ChatColor.GREEN + "/ecf admin setlocations y");
					return true;
				}
				if(args[2].equalsIgnoreCase("xmin"))
				{
					GameManager.getManager().setXMin(p);
					return true;
				}
				if(args[2].equalsIgnoreCase("xmax"))
				{
					GameManager.getManager().setXMax(p);
					return true;
				}
				if(args[2].equalsIgnoreCase("zmin"))
				{
					GameManager.getManager().setZMin(p);
					return true;
				}
				if(args[2].equalsIgnoreCase("zmax"))
				{
					GameManager.getManager().setZMax(p);
					return true;
				}
				if(args[2].equalsIgnoreCase("y"))
				{
					GameManager.getManager().setY(p);
					return true;
				}
			}
			if(args[1].equalsIgnoreCase("disable"))
			{
				GameManager.getManager().setAbleToJoin(false);
				return true;
			}
			if(args[1].equalsIgnoreCase("test"))
			{
				GameManager.getManager().resetFloor();
				return true;
			}
		}
			
		return false;
	}

}
