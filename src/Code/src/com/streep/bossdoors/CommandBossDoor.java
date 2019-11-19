package com.streep.bossdoors;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class CommandBossDoor implements CommandExecutor {
	@SuppressWarnings("unused")
	private Main m = null;
	
	public CommandBossDoor(Main m) {
		this.m = m;
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("bossdoors.door")) {
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("wand")) {
						ItemStack item = new ItemStack(Material.STICK, 1);
						item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(ChatColor.AQUA + "BossDoorWand");
						meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						List<String> lore = meta.getLore();
						if(lore == null) {
							lore = new ArrayList<String>();
						}
						lore.add(ChatColor.GREEN + "Used to create BossDoors");
						meta.setLore(lore);
						item.setItemMeta(meta);
						if(sender instanceof Player) {
							Player p = (Player) sender;
							p.getInventory().addItem(item);
						} else {
							sender.sendMessage(ChatColor.RED + "Only players can use this command!");
						}
				} else {
					if(args[0].equalsIgnoreCase("create")) {
						if(args.length > 1) {
							try {
								int id = 0;
								id = Integer.parseInt(args[1]);
								if(sender instanceof Player) {
									Player p = (Player) sender;
									DoorManager.createDoor(id, p);
								}
							} catch(Exception e) {
								sender.sendMessage(ChatColor.RED + "Id could not be parsed please try again!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Invalid syntax, please do '/bossdoor info'");
						}
					} else {
						if(args[0].equalsIgnoreCase("info")) {
							sender.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Bossdoor Syntax");
							sender.sendMessage(ChatColor.WHITE + "/bossdoor wand");
							sender.sendMessage(ChatColor.YELLOW + "Or");
							sender.sendMessage(ChatColor.WHITE + "/bossdoor create <id>");
							sender.sendMessage(ChatColor.YELLOW + "Or");
							sender.sendMessage(ChatColor.WHITE + "/bossdoor delete <id>");
							sender.sendMessage(ChatColor.YELLOW + "Or");
							sender.sendMessage(ChatColor.WHITE + "/bossdoor open/close <id>");
							sender.sendMessage(ChatColor.YELLOW + "Or");
							sender.sendMessage(ChatColor.WHITE + "/bossdoor list");
						} else { 
							if(args[0].equalsIgnoreCase("delete")) {
								if(args.length > 1) {
									try {
										int id = 0;
										id = Integer.parseInt(args[1]);
										DoorManager.deleteDoor(id);
										sender.sendMessage(ChatColor.RED + "Deleted door!");
									} catch(Exception e) {
										sender.sendMessage(ChatColor.RED + "Id could not be parsed please try again!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "Invalid syntax, please do '/bossdoor info'");
								}
							} else {
								if(args[0].equalsIgnoreCase("list")) {
									sender.sendMessage(ChatColor.AQUA + "Doors:");
									for(int i : DoorManager.getDoorIds()) {
										sender.sendMessage("- " + ChatColor.YELLOW + "" + i + "");
									}
								} else {
									if(args[0].equalsIgnoreCase("open")) {
										if(args.length > 1) {
											try {
												int id = 0;
												id = Integer.parseInt(args[1]);
												if(DoorManager.open(id)) {
													sender.sendMessage(ChatColor.GREEN + "Opened door!");
												} else {
													sender.sendMessage(ChatColor.RED + "Failed to open door!");
												}
											} catch(Exception e) {
												sender.sendMessage(ChatColor.RED + "Id could not be parsed please try again!");
											}
										} else {
											sender.sendMessage(ChatColor.RED + "Invalid syntax, please do '/bossdoor info'");
										}
									} else { 
										if(args[0].equalsIgnoreCase("close")) {
											if(args.length > 1) {
												try {
													int id = 0;
													id = Integer.parseInt(args[1]);
													if(DoorManager.close(id)) {
														sender.sendMessage(ChatColor.GREEN + "Closed door!");
													} else {
														sender.sendMessage(ChatColor.RED + "Failed to close!");
													}
												} catch(Exception e) {
													sender.sendMessage(ChatColor.RED + "Id could not be parsed please try again!");
												}
											} else {
												sender.sendMessage(ChatColor.RED + "Invalid syntax, please do '/bossdoor info'");
											}
										} else {
										sender.sendMessage(ChatColor.RED + "Invalid syntax, please do '/bossdoor info' for help!");
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You require the permission 'bossdoor.door' to do this!");
		}
		return true;
	}
}
