package com.gmail.trentech.antiswear.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.antiswear.Main;

public class CMDAntiSwear implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Builder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();

		pages.title(Text.builder().color(TextColors.DARK_PURPLE).append(Text.of(TextColors.GOLD, "Command List")).build());

		List<Text> list = new ArrayList<>();

		if (src.hasPermission("antiswear.cmd.antiswear.add")) {
			list.add(Text.of(TextColors.GOLD, "/antiswear add <word>"));
		}
		if (src.hasPermission("antiswear.cmd.antiswear.remove")) {
			list.add(Text.of(TextColors.GOLD, "/antiswear remove <word>"));
		}
		if (src.hasPermission("antiswear.cmd.antiswear.ban")) {
			list.add(Text.of(TextColors.GOLD, "/antiswear ban"));
		}
		if (src.hasPermission("antiswear.cmd.antiswear.tempban")) {
			list.add(Text.of(TextColors.GOLD, "/antiswear tempban"));
		}
		if (src.hasPermission("antiswear.cmd.antiswear.kick")) {
			list.add(Text.of(TextColors.GOLD, "/antiswear kick"));
		}
		if (src.hasPermission("antiswear.cmd.antiswear.replace")) {
			list.add(Text.of(TextColors.GOLD, "/antiswear replace"));
		}
		if (src.hasPermission("antiswear.cmd.antiswear.command")) {
			list.add(Text.of(TextColors.GOLD, "/antiswear command"));
		}

		pages.contents(list);

		pages.sendTo(src);

		return CommandResult.success();
	}

}
