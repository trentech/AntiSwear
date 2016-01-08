package com.gmail.trentech.antiswear.commands.tempban;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.antiswear.ConfigManager;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDTime implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(!args.hasAny("value")) {
			src.sendMessage(Text.of(TextColors.GOLD, "/antiswear tempban time <value>"));
			return CommandResult.empty();
		}
		String value = args.<String>getOne("value").get();

		if(!isValid(value)){
			src.sendMessage(Text.of(TextColors.DARK_RED, "Not a valid time"));
			return CommandResult.empty();
		}
		
		ConfigManager configManager = new ConfigManager();		
		ConfigurationNode config = configManager.getConfig();
		
		config.getNode("Options", "Ban", "Temporary", "Time").setValue(value);
		
		configManager.save();
		
		src.sendMessage(Text.of(TextColors.DARK_GREEN, "Temporary ban time set to ", value));
		
		return CommandResult.success();
	}
	
	public static boolean isValid(String time) {
		String[] times = time.split(" ");

		for(String t : times) {
			if(t.matches("(\\d+)[s]$")) {
				continue;
			}else if(t.matches("(\\d+)[m]$")) {
				continue;
			}else if(t.matches("(\\d+)[h]$")) {
				continue;
			}else if(t.matches("(\\d+)[d]$")) {
				continue;
			}else if(t.matches("(\\d+)[w]$")) {
				continue;
			}else if(t.matches("(\\d+)[mo]$")) {
				continue;
			}else if(t.matches("(\\d+)[y]$")) {
				continue;
			}else{
				return false;
			}
		}
		return true;
	}

}
