package com.gmail.trentech.antiswear;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigManager {

	private File file;
	private CommentedConfigurationNode config;
	private ConfigurationLoader<CommentedConfigurationNode> loader;

	public ConfigManager(String configName) {
		String folder = "config" + File.separator + Resource.NAME.toLowerCase();
		if (!new File(folder).isDirectory()) {
			new File(folder).mkdirs();
		}
		file = new File(folder, configName);

		create();
		load();
	}

	public ConfigManager() {
		String folder = "config" + File.separator + Resource.NAME.toLowerCase();
		if (!new File(folder).isDirectory()) {
			new File(folder).mkdirs();
		}
		file = new File(folder, "config.conf");

		create();
		load();
	}

	public ConfigurationLoader<CommentedConfigurationNode> getLoader() {
		return loader;
	}

	public CommentedConfigurationNode getConfig() {
		return config;
	}

	public void save() {
		try {
			loader.save(config);
		} catch (IOException e) {
			Main.getLog().error("Config save FAIL:");
			e.printStackTrace();
		}
	}

	public void init() {
		if (file.getName().equalsIgnoreCase("config.conf")) {
			if (config.getNode("Words").getString() == null) {
				List<String> list = new ArrayList<>();

				list.add("fuck");
				list.add("bitch");
				list.add("shit");
				list.add("ass");

				config.getNode("Words").setValue(list);
			}
			if (config.getNode("Options", "Kick").isVirtual()) {
				config.getNode("Options", "Kick", "Enable").setValue(false);
				config.getNode("Options", "Kick", "Message").setValue("Kicked for cursing.");
			}
			if (config.getNode("Options", "Replace-Message").isVirtual()) {
				config.getNode("Options", "Replace-Message", "Enable").setValue(false);
				config.getNode("Options", "Replace-Message", "Message").setValue("I have a potty mouth.");
			}
			if (config.getNode("Options", "Ban").isVirtual()) {
				config.getNode("Options", "Ban", "Enable").setValue(false);
				config.getNode("Options", "Ban", "Temporary", "Enable").setValue(true);
				config.getNode("Options", "Ban", "Temporary", "Time").setValue("1h");
				config.getNode("Options", "Ban", "Strikes").setValue(3);
				config.getNode("Options", "Ban", "Message").setValue("Banned for cursing.");
			}
			if (config.getNode("Options", "Command").isVirtual()) {
				config.getNode("Options", "Command", "Enable").setValue(false);
				config.getNode("Options", "Command", "Run").setValue("give @p minecraft:rotten_flesh");
			}
			save();
		}
	}

	private void create() {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				Main.getLog().error("Config create FAIL:");
				e.printStackTrace();
			}
		}
	}

	private void load() {
		loader = HoconConfigurationLoader.builder().setFile(file).build();
		try {
			config = loader.load();
		} catch (IOException e) {
			Main.getLog().error("Config load FAIL:");
			e.printStackTrace();
		}
	}

}
