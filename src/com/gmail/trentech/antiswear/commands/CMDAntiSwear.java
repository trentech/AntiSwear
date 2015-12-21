package com.gmail.trentech.antiswear.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.antiswear.Main;

public class CMDAntiSwear implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		PaginationBuilder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();
		
		pages.title(Texts.builder().color(TextColors.DARK_PURPLE).append(Texts.of(TextColors.GOLD, "Command List")).build());
		
		List<Text> list = new ArrayList<>();
		
		if(src.hasPermission("AntiSwear.cmd.antiswear.add")) {
			list.add(Texts.of(TextColors.GOLD, "/antiswear add <word>"));
		}
		if(src.hasPermission("AntiSwear.cmd.antiswear.remove")) {
			list.add(Texts.of(TextColors.GOLD, "/antiswear remove <word>"));
		}

		pages.contents(list);
		
		pages.sendTo(src);

		return CommandResult.success();
	}

}