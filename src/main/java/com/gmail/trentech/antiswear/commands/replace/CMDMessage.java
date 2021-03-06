package com.gmail.trentech.antiswear.commands.replace;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.antiswear.ConfigManager;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDMessage implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (!args.hasAny("value")) {
			src.sendMessage(Text.of(TextColors.GOLD, "/antiswear replace message <value>"));
			return CommandResult.empty();
		}
		String value = args.<String> getOne("value").get();

		ConfigManager configManager = new ConfigManager();
		ConfigurationNode config = configManager.getConfig();

		config.getNode("Options", "Replace-Message", "Message").setValue(value);

		configManager.save();

		src.sendMessage(Text.of(TextColors.DARK_GREEN, "Replace curse message set to ", value));

		return CommandResult.success();
	}

}
