package me.venomouspenguin.antcraft.electricfloor.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import me.venomouspenguin.antcraft.electricfloor.core.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameManager
{
	static GameManager gm = new GameManager();
	
	Main plugin = Main.getInstance();
	private GameStates state;
	private boolean inUse;
	private boolean ableToJoin;
	private boolean listenersToggle;
	private World arenaWorld;
	private World lobbyWorld;
	private ArrayList<Player> players = new ArrayList<Player>();
	private HashMap<UUID, Location> playerOriginalLocation = new HashMap<UUID, Location>();
	private HashMap<UUID, ItemStack[]> playerInv = new HashMap<UUID, ItemStack[]>();
	private HashMap<UUID, ItemStack[]> playerArmorInv = new HashMap<UUID, ItemStack[]>();
	
	public GameManager()
	{
		this.state = GameStates.LOBBY;
		this.inUse = false;
		this.arenaWorld = Bukkit.getServer().getWorld(plugin.getConfig().getString("antcraft.electricfloor.arena.game.world"));
		this.lobbyWorld = Bukkit.getServer().getWorld(plugin.getConfig().getString("antcraft.electricfloor.arena.lobby.world"));
		this.ableToJoin = true;
		this.listenersToggle = false;
	}
	
	public static GameManager getManager()
	{
		return gm;
	}
	
	public ArrayList<Player> getPlayers()
	{
		return this.players;
	}
	
	public void setLobbySpawn(Player p)
	{
		Bukkit.broadcastMessage("Test");
		Location loc = p.getLocation();
		
		if(!plugin.getConfig().isSet("antcraft.electricfloor.arena.world"))
		{
			plugin.getConfig().set("antcraft.electricfloor.arena.lobby.world", null);
			plugin.saveConfig();
		}
		if(!plugin.getConfig().isSet("antcraft.electricfloor.arena.lobby.x"))
		{
			plugin.getConfig().set("antcraft.electricfloor.arena.lobby.x", null);
			plugin.saveConfig();
		}
		if(!plugin.getConfig().isSet("antcraft.electricfloor.arena.lobby.y"))
		{
			plugin.getConfig().set("antcraft.electricfloor.arena.lobby.y", null);
			plugin.saveConfig();
		}
		if(!plugin.getConfig().isSet("antcraft.electricfloor.arena.lobby.z"))
		{
			plugin.getConfig().set("antcraft.electricfloor.arena.lobby.z", null);
			plugin.saveConfig();
		}
		if(!plugin.getConfig().isSet("antcraft.electricfloor.arena.lobby.pitch"))
		{
			plugin.getConfig().set("antcraft.electricfloor.arena.lobby.pitch", null);
			plugin.saveConfig();
		}
		if(!plugin.getConfig().isSet("antcraft.electricfloor.arena.lobby.yaw"))
		{
			plugin.getConfig().set("antcraft.electricfloor.arena.lobby.yaw", null);
			plugin.saveConfig();
		}
		plugin.getConfig().set("antcraft.electricfloor.arena.lobby.world", loc.getWorld().getName());
		plugin.getConfig().set("antcraft.electricfloor.arena.lobby.x", loc.getX());
		plugin.getConfig().set("antcraft.electricfloor.arena.lobby.y", loc.getY());
		plugin.getConfig().set("antcraft.electricfloor.arena.lobby.z", loc.getZ());
		plugin.getConfig().set("antcraft.electricfloor.arena.lobby.pitch", loc.getPitch());
		plugin.getConfig().set("antcraft.electricfloor.arena.lobby.yaw", loc.getYaw());
		
		plugin.saveConfig();
		
		p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "You have setup the lobby spawn");
		p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "You can re-set the spawn point to somewhere else by doing the command again");
	}
	
	public void setSpawnLocation(Player p, int i)
	{
		Location loc = p.getLocation();
		
		i--;
		
		if(i > 7)
		{
			p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "It must be a number between or equal to 1 & 8");
			return;
		}
		
		plugin.getConfig().set("antcraft.electricfloor.arena.game.world", loc.getWorld().getName());
		plugin.getConfig().set("antcraft.electricfloor.arena.game." + i + ".x", loc.getX());
		plugin.getConfig().set("antcraft.electricfloor.arena.game." + i + ".y", loc.getY());
		plugin.getConfig().set("antcraft.electricfloor.arena.game." + i + ".z", loc.getZ());
		plugin.getConfig().set("antcraft.electricfloor.arena.game." + i + ".pitch", loc.getPitch());
		plugin.getConfig().set("antcraft.electricfloor.arena.game." + i + ".yaw", loc.getYaw());
		plugin.saveConfig();
		
		i = i + 1;
		p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "You have set up spawn location: " + ChatColor.GREEN + i);
	}
	
	@SuppressWarnings("deprecation")
	public void addPlayer(Player p)
	{
		if(this.ableToJoin == true)
		{
			if(this.inUse == false)
			{
				players.add(p);
				playerInv.put(p.getUniqueId(), p.getInventory().getContents());
				playerArmorInv.put(p.getUniqueId(), p.getInventory().getArmorContents());
				playerOriginalLocation.put(p.getUniqueId(), p.getLocation());
				
				teleportPlayerToLobby(p);
				broadcastMessage("Player, " + ChatColor.AQUA + p.getName() + ChatColor.YELLOW + 
						" has joined the game " + ChatColor.GRAY + "[" + ChatColor.GREEN + players.size() + ChatColor.GRAY + "/" + 
				ChatColor.GREEN + "8" + ChatColor.GRAY + "]");
				
				p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "You have joined the " + ChatColor.RED + "Electric" + ChatColor.AQUA + "Floor" + ChatColor.YELLOW + " lobby");
				p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "Please wait...");
				
				if(players.size() == 2 && state == GameStates.LOBBY)
				{
					state = GameStates.COUNTDOWN;
					ableToJoin = false;
					Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Countdown(this), 0, 20);
				}
				return;
			}
			else
			{
				p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "Game in progress");
				return;
			}
		}
		else
		{
			p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "This game is not joinable");
			p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "Please come back later");
			return;
		}
	}
	
	public void removePlayer(Player p)
	{
		if(!players.contains(p))
		{
			p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "You are not in the game");
			return;
		}
		
		players.remove(p);
		
		Location loc = playerOriginalLocation.get(p.getUniqueId());
		p.teleport(loc);
		playerOriginalLocation.remove(p.getUniqueId());
		
		p.getInventory().clear();
		p.getInventory().setContents(playerInv.get(p.getUniqueId()));
		p.getInventory().setArmorContents(playerArmorInv.get(p.getUniqueId()));
		
		playerInv.remove(p.getUniqueId());
		playerArmorInv.remove(p.getUniqueId());
		
		p.sendMessage(plugin.LOGO + ChatColor.YELLOW + "You have left the game");
		broadcastMessage("Player, " + ChatColor.AQUA + p.getName() + ChatColor.YELLOW +  " has left the game");
	}
	
	public void teleportPlayerToLobby(Player p)
	{
		double x = plugin.getConfig().getDouble("antcraft.electricfloor.arena.lobby.x");
		double y = plugin.getConfig().getDouble("antcraft.electricfloor.arena.lobby.y");
		double z = plugin.getConfig().getDouble("antcraft.electricfloor.arena.lobby.z");
		double pitch = plugin.getConfig().getDouble("antcraft.electricfloor.arena.lobby.pitch");
		double yaw = plugin.getConfig().getDouble("antcraft.electricfloor.arena.lobby.yaw");

		Location loc = new Location(lobbyWorld, x, y , z, (float) yaw, (float) pitch);
		
		p.teleport(loc);
	}
	
	public void start()
	{
		ableToJoin = false;
		inUse = true;
		state = GameStates.INGAME;
		
		broadcastMessage("The waiting is over");
		broadcastMessage("You will be teleported to the map in: " + ChatColor.GREEN + "10 Seconds");
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			public void run() 
			{
				teleportPlayersToArena();
			}
			
		}, 20*10);
		
		broadcastMessage("Players have been teleported");
		broadcastMessage("Game begins in: " + ChatColor.GREEN + "10 Seconds");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() 
			{
				listenersToggle = true;
				broadcastMessage("The game has begun");
			}
		}, 20*10);
	}
	
	public void teleportPlayersToArena()
	{
		for(Player p : players)
		{
			if(players.contains(p))
			{
				int entryNum = players.indexOf(p);
				
				double x = plugin.getConfig().getDouble("antcraft.electricfloor.arena.game." + entryNum + ".x");
				double y = plugin.getConfig().getDouble("antcraft.electricfloor.arena.game." + entryNum + ".y");
				double z = plugin.getConfig().getDouble("antcraft.electricfloor.arena.game." + entryNum + ".z");
				double yaw = plugin.getConfig().getDouble("antcraft.electricfloor.arena.game." + entryNum + ".yaw");
				double pitch = plugin.getConfig().getDouble("antcraft.electricfloor.arena.game." + entryNum + ".pitch");
				
				Location loc = new Location(arenaWorld, x,y,z, (float) yaw, (float) pitch);
				p.teleport(loc);
			}
		}
	}
	
	public void broadcastMessage(String message)
	{
		for(Player ps : this.players)
		{
			if(this.players.contains(ps))
			{
				ps.sendMessage(plugin.LOGO + ChatColor.YELLOW + message);
			}
		}
	}
	
	public GameStates getState()
	{
		return state;
	}
	
	public void setState(GameStates state)
	{
		this.state = state;
	}
	
	public boolean getListenerToggle()
	{
		return listenersToggle;
	}
	
	public void setListenerToggle(boolean value)
	{
		this.listenersToggle = value;
	}
	
	public HashMap<UUID, ItemStack[]> getInventoryContents()
	{
		return this.playerInv;
	}

	public HashMap<UUID, ItemStack[]> getInventoryArmorContents()
	{
		return this.playerArmorInv;
	}
	
	public void setAbleToJoin(boolean value)
	{
		this.ableToJoin = value;
	}
	
}
