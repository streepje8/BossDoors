package com.streep.bossdoors;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
    public void onEnable() {
		getLogger().info("Loading BossDoors...");
		DoorManager.setMain(this);
		createCustomConfig();
		this.getCommand("bosskey").setExecutor(new CommandKey(this));
		this.getCommand("bossdoor").setExecutor(new CommandBossDoor(this));
		this.getServer().getPluginManager().registerEvents(new RightClickListener(), this);
		DoorManager.loadDoors();
    }
	
    @Override
    public void onDisable() {
    	getLogger().info("Unloading BossDoors...");
    	DoorManager.saveDoors();
    }
    
    private File customConfigFile;
    private FileConfiguration customConfig;
    
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }
    
    public void saveFile() {
    	try {customConfig.save(customConfigFile);} catch(Exception e) { getLogger().info("File could not be saved!");}
    }
    
    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "doordata.yml");
        try {
	        if (!customConfigFile.exists()) {
	            customConfigFile.getParentFile().mkdirs();
	            if(!customConfigFile.createNewFile()) {
	            	getLogger().info("Error whils't creating save file!");
	            }
	         }
	
	        customConfig = new YamlConfiguration();
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
