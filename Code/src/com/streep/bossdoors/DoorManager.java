package com.streep.bossdoors;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import com.streep.bossdoors.objects.Door;
import com.streep.bossdoors.objects.DoorBlock;

import net.md_5.bungee.api.ChatColor;

public class DoorManager {

	public static HashMap<Integer, Door> doors = new HashMap<Integer, Door>();
	public static Main m = null;
	
	public static void setMain(Main ma) {
		m = ma;
	}
	
	public static boolean open(int id) {
		if(doors.containsKey(id)) {
			if(!(doors.get(id).isOpen())) {
				doors.get(id).open();
			} else {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean close(int id) {
		if(doors.containsKey(id)) {
			if(doors.get(id).isOpen()) {
				doors.get(id).close();
			} else {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean openDoorPlayer(int id,Player p, PlayerToggleSneakEvent e) {
		if(doors.containsKey(id)) {
			doors.get(id).openAsPlayer(p, e);
			return true;
		} else {
			return false;
		}
	}
	
	public static void createDoor(int id, Player p) {
		if(!doors.containsKey(id)) {
			try {
				Location locone = LocationManager.getLocationOne(p);
				Location loctwo = LocationManager.getLocationTwo(p);
				if(!(locone.getWorld() == null || loctwo.getWorld() == null)) {
					int centerX = (locone.getBlockX() + loctwo.getBlockX()) / 2;
					int centerY = (locone.getBlockY() + loctwo.getBlockY()) / 2;
					int centerZ = (locone.getBlockZ() + loctwo.getBlockZ()) / 2;
					Vector max = Vector.getMaximum(locone.toVector(), loctwo.toVector());
					Vector min = Vector.getMinimum(locone.toVector(), loctwo.toVector());
					Door d = new Door(id,m);
					for (int i = min.getBlockX(); i <= max.getBlockX();i++) {
					  for (int j = min.getBlockY(); j <= max.getBlockY(); j++) {
					    for (int k = min.getBlockZ(); k <= max.getBlockZ();k++) {
					      Block block = p.getWorld().getBlockAt(i,j,k);
					      d.addBlock(block);
					    }
					  }
					}
					d.setLocation(centerX, centerY, centerZ, p.getWorld());
					doors.put(id, d);
					saveDoor(id, d);
					p.sendMessage(ChatColor.GREEN + "Door " + ChatColor.AQUA + id + ChatColor.AQUA + " created successful!");
				} else {
					p.sendMessage(ChatColor.RED + "Select the door first!");
				}
			} catch(Exception e) {
				p.sendMessage(ChatColor.RED + "Select the door first!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "ID already Taken!");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void saveDoor(int id, Door d) {
		int blockid = 0;
		m.getCustomConfig().set("doors." + id + ".id", id);
		m.getCustomConfig().set("doors." + id + ".x", d.getLocation().getX());
		m.getCustomConfig().set("doors." + id + ".y", d.getLocation().getY());
		m.getCustomConfig().set("doors." + id + ".z", d.getLocation().getZ());
		for(DoorBlock b : d.getBlocks()) {
			m.getCustomConfig().set("doors." + id + ".blocks." + blockid + ".type", b.getType().toString());
			m.getCustomConfig().set("doors." + id + ".blocks." + blockid + ".data", b.getBlock().getData());
			m.getCustomConfig().set("doors." + id + ".blocks." + blockid + ".x", b.getLocation().getBlockX());
			m.getCustomConfig().set("doors." + id + ".blocks." + blockid + ".y", b.getLocation().getBlockY());
			m.getCustomConfig().set("doors." + id + ".blocks." + blockid + ".z", b.getLocation().getBlockZ());
			m.getCustomConfig().set("doors." + id + ".blocks." + blockid + ".world", b.getLocation().getWorld().getName());
			blockid++;
		}
		saveDoors();
	}
	
	public static void deleteDoor(int id) {
		if(doors.containsKey(id)) {
			doors.remove(id);
			m.getCustomConfig().set("doors." + id, null);
		}
		saveDoors();
	}
	
	public static void saveDoors() {
		m.saveFile();
	}
	
	@SuppressWarnings("deprecation")
	public static void loadDoors() {
		ConfigurationSection dsec = m.getCustomConfig().getConfigurationSection("doors");
		doors.clear();
		if(dsec != null) {
			for (String key : dsec.getKeys(false))
			{
			    int id = m.getCustomConfig().getInt("doors." + key + ".id");
			    int x = m.getCustomConfig().getInt("doors." + key + ".x");
			    int y = m.getCustomConfig().getInt("doors." + key + ".y");
			    int z = m.getCustomConfig().getInt("doors." + key + ".z");
			    String Wname = m.getCustomConfig().getString("doors." + key + ".blocks." + 0 + ".world");
		    	World wR = m.getServer().getWorld(Wname);
			    Door d = new Door(id,m);
			    d.setLocation(x, y, z, wR);
			    ConfigurationSection bsec = m.getCustomConfig().getConfigurationSection("doors." + key + ".blocks");
			    for (String keyB : bsec.getKeys(false))
				{
			    	double Bx = m.getCustomConfig().getDouble("doors." + key + ".blocks." + keyB + ".x");
			    	double By = m.getCustomConfig().getDouble("doors." + key + ".blocks." + keyB + ".y");
			    	double Bz = m.getCustomConfig().getDouble("doors." + key + ".blocks." + keyB + ".z");
			    	String name = m.getCustomConfig().getString("doors." + key + ".blocks." + keyB + ".world");
			    	World w = m.getServer().getWorld(name);
			    	Block b = w.getBlockAt((int) Math.round(Bx),(int)  Math.round(By),(int)  Math.round(Bz));
			    	b.setType(Material.getMaterial(m.getCustomConfig().getString("doors." + key + ".blocks." + keyB + ".type")));
			    	//b.setBlockData(m.getServer().getUnsafe().fromLegacy(b.getType(), (byte) m.getCustomConfig().getInt("doors." + key + ".blocks." + keyB + ".data"))); //getUnsafe().fromLegacy(, true
			    	d.addBlock(b);
				}
			    doors.put(id, d);
			    d.open();
			    d.close();
			}
		}
	}

	public static Set<Integer> getDoorIds() {
		return doors.keySet();
	}

	public static boolean isOpen(int id) {
		if(doors.containsKey(id)) {
			return doors.get(id).isOpen();
		} else {
			return false;
		}
	}
	
}
