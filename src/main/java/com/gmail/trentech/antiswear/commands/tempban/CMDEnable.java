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

public class CMDEnable implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (!args.hasAny("value")) {
			src.sendMessage(Text.of(TextColors.GOLD, "/antiswear tempban enable <value>"));
			return CommandResult.empty();
		}
		String value = args.<String> getOne("value").get();

		if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
			src.sendMessage(Text.of(TextColors.DARK_RED, value, " is not a valid value"));
			return CommandResult.empty();
		}

		ConfigManager configManager = new ConfigManager();
		ConfigurationNode config = configManager.getConfig();

		config.getNode("Options", "Ban", "Temporary", "Enable").setValue(Boolean.parseBoolean(value));

		if (value.equalsIgnoreCase("true") && !config.getNode("Options", "Ban", "Enable").getBoolean()) {
			config.getNode("Options", "Ban", "Enable").setValue(Boolean.parseBoolean(value));
		}

		configManager.save();

		src.sendMessage(Text.of(TextColors.DARK_GREEN, "Temp ban set to ", value));

		return CommandResult.success();
	}

}
