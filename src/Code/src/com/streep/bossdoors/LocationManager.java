package com.streep.bossdoors;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationManager {

	private static HashMap<String, Location> locone = new HashMap<String, Location>();
	private static HashMap<String, Location> loctwo = new HashMap<String, Location>();
	
	public static Location getLocationOne(Player p) {
		if(locone.containsKey(p.getName())) {
			return locone.get(p.getName());
		} else {
			return new Location(null,0,0,0);
		}
	}
	
	public static Location getLocationTwo(Player p) {
		if(loctwo.containsKey(p.getName())) {
			return loctwo.get(p.getName());
		} else {
			return new Location(null,0,0,0);
		}
	}
	
	public static void setLocationOne(Player p, Location l) {
		if(locone.containsKey(p.getName())) {
			locone.remove(p.getName());
		}
		locone.put(p.getName(), l);
	}
	
	public static void setLocationTwo(Player p, Location l) {
		if(loctwo.containsKey(p.getName())) {
			loctwo.remove(p.getName());
		}
		loctwo.put(p.getName(), l);
	}
	
}
