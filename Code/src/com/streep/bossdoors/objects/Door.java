package com.streep.bossdoors.objects;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.streep.bossdoors.DoorManager;
import com.streep.bossdoors.Main;

public class Door {

	private int id = 0;
	private boolean opened = false;
	private ArrayList<DoorBlock> blocks = new ArrayList<DoorBlock>();
	private int centerX = 0;
	private int centerY = 0;
	private int centerZ = 0;
	public static int TEMPID = 0;
	private World world = null;
	private Main main = null;
	
	public Door(int id, Main m) {
		this.id = id;
		this.main = m;
	}
	
	public int getID() {
		return this.id;
	}
	
	public ArrayList<DoorBlock> getBlocks() {
		return this.blocks;
	}
	
	public void addBlock(Block b) {
		this.blocks.add(new DoorBlock(b));
	}
	
	public void setLocation(int x, int y, int z, World w) {
		this.centerX = x;
		this.centerY = y;
		this.centerZ = z;
		this.world = w;
	}
	
	@SuppressWarnings("deprecation")
	public void openAsPlayer(Player p, PlayerToggleSneakEvent e) {
		if(p.getWorld().getWorldType() == WorldType.NORMAL || p.getWorld().getWorldType() == WorldType.FLAT) {
			this.world = p.getWorld();
		}
		Location l = p.getLocation();
		float distance = (float) Math.sqrt(Math.pow(l.getX()-this.centerX,2) + Math.pow(l.getY()-this.centerY,2) + Math.pow(l.getZ()-this.centerZ,2));
		if(distance < 10) {
			for(DoorBlock b : blocks) {
				b.setAirP(p);
			}
			if(e.isSneaking() && (this.opened == false)) {
				e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
			}
			this.opened = true;
			Bukkit.getScheduler ().runTaskLater (this.main, () -> DoorManager.close(this.id), 30*20);
		}
	}
	
	public void open() {
		for(DoorBlock b : blocks) {
			b.setAir();
		}
		this.opened = true;
	}
	
	public void close() {
		World w = this.world;
		for(DoorBlock b : blocks) {
			b.setBlock(w);
		}
		this.opened = false;
	}
	
	public boolean isOpen() {
		return this.opened;
	}

	public Location getLocation() {
		return new Location(null,centerX,centerY,centerZ);
	}
	
	
	
}
