package com.gmail.trentech.antiswear.commands;

import java.util.List;
import java.util.stream.Collectors;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.antiswear.ConfigManager;

import ninja.leaping.configurate.ConfigurationNode;

public class CMDRemove implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(!args.hasAny("word")) {
			src.sendMessage(Texts.of(TextColors.GOLD, "/antiswear add <word>"));
			return CommandResult.empty();
		}
		String word = args.<String>getOne("word").get();
		
		ConfigManager configManager = new ConfigManager();		
		ConfigurationNode config = configManager.getConfig();
		
		List<String> list = config.getNode("Words").getChildrenList().stream().map(ConfigurationNode::getString).collect(Collectors.toList());
		
		if(list.contains(word)){
			list.remove(word);
			config.getNode("Words").setValue(list);
			configManager.save();
		}
		
		src.sendMessage(Texts.of(TextColors.DARK_GREEN, "Removed ", word, " to censor list"));
		
		return CommandResult.success();
	}

}
