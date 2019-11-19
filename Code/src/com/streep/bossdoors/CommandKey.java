package com.streep.bossdoors;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class CommandKey implements CommandExecutor{
	
	private Main m = null;
	
	public CommandKey(Main m) {
		this.m = m;
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("bossdoors.key")) {
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("drop")) {
					if(args.length > 4) {
						int id = 0;
						int x = 0;
						int y = 0;
						int z = 0;
						List<World> worlds = sender.getServer().getWorlds();
						List<World> toDrop = new ArrayList<World>();
						for(World w : worlds) {
							if(w.getWorldType() == WorldType.NORMAL || w.getWorldType() == WorldType.FLAT) {
								toDrop.add(w);
							}
						}
						try {
							id = Integer.parseInt(args[1]);
						} catch(Exception e) {
							sender.sendMessage(ChatColor.RED + "Id could not be parsed so just gave 0.");
						}
						try {
							x = Integer.parseInt(args[2]);
						} catch(Exception e) {
							sender.sendMessage(ChatColor.RED + "x could not be parsed so just gave 0.");
						}
						try {
							y = Integer.parseInt(args[3]);
						} catch(Exception e) {
							sender.sendMessage(ChatColor.RED + "y could not be parsed so just gave 0.");
						}
						try {
							z = Integer.parseInt(args[4]);
						} catch(Exception e) {
							sender.sendMessage(ChatColor.RED + "z could not be parsed so just gave 0.");
						}
						ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK, 1);
						item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(ChatColor.AQUA + "Bosskey" + ChatColor.GRAY + "[" + ChatColor.YELLOW + "#" + id + ChatColor.GRAY + "]");
						meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						List<String> lore = meta.getLore();
						if(lore == null) {
							lore = new ArrayList<String>();
						}
						lore.add(ChatColor.GREEN + "Used to open BossDoors");
						lore.add(ChatColor.GRAY + "This key opens door id: ");
						lore.add("" + id);
						meta.setLore(lore);
						item.setItemMeta(meta);
						for(World w : toDrop) {
							Location l = new Location(w, x, y, z);
							w.dropItem(l, item);
						}
					}
				} else {
					if(args[0].equalsIgnoreCase("give")) {
						if(args.length > 2) {
							int amount = 1;
							if(args.length > 3) {
								try {
									amount = Integer.parseInt(args[3]);
								} catch(Exception e) {
									sender.sendMessage(ChatColor.RED + "Amount could not be parsed so just gave 1.");
								}
							}
							int id = 0;
							try {
								id = Integer.parseInt(args[2]);
							} catch(Exception e) {
								sender.sendMessage(ChatColor.RED + "Id could not be parsed so just gave 0.");
							}
							String player = args[1];
							boolean foundplayer = false;
							Player theplayer = null;
							for(Player p : this.m.getServer().getOnlinePlayers()) {
								if(p.getName().equals(player)) {
									foundplayer = true;
									theplayer = p;
								}
							}
							if(foundplayer) {
								ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK, 1);
								item.setAmount(amount);
								item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName(ChatColor.AQUA + "Bosskey" + ChatColor.GRAY + "[" + ChatColor.YELLOW + "#" + id + ChatColor.GRAY + "]");
								meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								List<String> lore = meta.getLore();
								if(lore == null) {
									lore = new ArrayList<String>();
								}
								lore.add(ChatColor.GREEN + "Used to open BossDoors");
								lore.add(ChatColor.GRAY + "This key opens door id: ");
								lore.add("" + id);
								meta.setLore(lore);
								item.setItemMeta(meta);
								theplayer.getInventory().addItem(item);
								sender.sendMessage(ChatColor.GREEN + "Gave bosskey to " + ChatColor.AQUA + theplayer.getName());
							} else {
								sender.sendMessage(ChatColor.RED + "Player not found!");
							}
						}
					} else {
						if(args[0].equalsIgnoreCase("info")) {
							sender.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Bosskey Syntax");
							sender.sendMessage(ChatColor.WHITE + "/bosskey give <player> <id> <amount>");
							sender.sendMessage(ChatColor.YELLOW + "Or");
							sender.sendMessage(ChatColor.WHITE + "/bosskey drop <id> <x> <y> <z>");
						} else { 
							sender.sendMessage(ChatColor.RED + "Invalid syntax, please do '/bosskey info' for help!");
						}
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Invalid syntax, please do '/bosskey info' for help!");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You require the permission 'bossdoor.key' to do this!");
		}
		return true;
    }
}
