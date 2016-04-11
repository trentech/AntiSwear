package com.gmail.trentech.antiswear.commands.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.antiswear.ConfigManager;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDEnable implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(!args.hasAny("value")) {
			src.sendMessage(Text.of(TextColors.GOLD, "/antiswear command enable <value>"));
			return CommandResult.empty();
		}
		String value = args.<String>getOne("value").get();
		
		if(!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")){
			src.sendMessage(Text.of(TextColors.DARK_RED, value, " is not a valid value"));
			return CommandResult.empty();
		}
		
		ConfigManager configManager = new ConfigManager();		
		ConfigurationNode config = configManager.getConfig();
		
		config.getNode("Options", "Command", "Enable").setValue(Boolean.parseBoolean(value));
		
		configManager.save();
		
		src.sendMessage(Text.of(TextColors.DARK_GREEN, "Command set to ", value));
		
		return CommandResult.success();
	}

}
