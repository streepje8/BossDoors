package com.streep.bossdoors.objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class DoorBlock {

	private Location loc = null;
	private Block b = null;
	private Material m = null;
	
	public DoorBlock(Block b) {
		this.b = b;
		this.m = b.getType();
		this.loc = b.getLocation();
	}

	public void setAirP(Player p) {
		World w = p.getWorld();
		w.getBlockAt(this.loc).setType(Material.AIR);
	}
	
	public void setAir() {
		World w = this.b.getWorld();
		w.getBlockAt(this.loc).setType(Material.AIR);
	}

	public void setBlock(World w) {
		w.getBlockAt(this.loc).setType(this.m);
	}

	public Material getType() {
		return this.b.getType();
	}

	public Location getLocation() {
		return this.loc;
	}

	public Block getBlock() {
		return this.b;
	}

}
