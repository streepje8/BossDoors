package com.streep.bossdoors;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import net.md_5.bungee.api.ChatColor;

public class RightClickListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		if(e.getPlayer() != null) {
			if(e.getPlayer().getItemInHand() != null) {
				if(e.getPlayer().getItemInHand().getItemMeta() != null) {
					if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null) {
						if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().startsWith(ChatColor.AQUA + "Bosskey")) {
							if(e.getPlayer().getItemInHand().getItemMeta().getLore() != null) {
								List<String> lore = e.getPlayer().getItemInHand().getItemMeta().getLore();
								if(lore.size() > 1) {
									String idS = lore.get(2);
									try {
										int id = Integer.parseInt(idS);
										DoorManager.openDoorPlayer(id, e.getPlayer(), e);
									} catch(Exception ex) {}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(e.getAction() != null && e.getPlayer() != null) {
			if(e.getPlayer().getItemInHand() != null) {
				if(e.getPlayer().getItemInHand().getItemMeta() != null) {
					if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null) {
						if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "BossDoorWand")) {
							if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
								LocationManager.setLocationTwo(e.getPlayer(), e.getClickedBlock().getLocation());
								e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Location two set to " + e.getClickedBlock().getLocation().getBlockX() + ";" + e.getClickedBlock().getLocation().getBlockY() + ";" + e.getClickedBlock().getLocation().getBlockZ());
								e.setCancelled(true);
							}
							if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
								LocationManager.setLocationOne(e.getPlayer(), e.getClickedBlock().getLocation());
								e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Location one set to " + e.getClickedBlock().getLocation().getBlockX() + ";" + e.getClickedBlock().getLocation().getBlockY() + ";" + e.getClickedBlock().getLocation().getBlockZ());
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
	
}
