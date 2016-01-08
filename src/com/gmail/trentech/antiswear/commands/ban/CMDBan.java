package com.gmail.trentech.antiswear.commands.ban;

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
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.antiswear.Main;

public class CMDBan implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		PaginationBuilder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();
		
		pages.title(Text.builder().color(TextColors.DARK_PURPLE).append(Text.of(TextColors.GOLD, "Command List")).build());
		
		List<Text> list = new ArrayList<>();

		list.add(Text.of(TextColors.GOLD, "/antiswear ban enable <value>"));
		list.add(Text.of(TextColors.GOLD, "/antiswear ban message <message>"));
		list.add(Text.of(TextColors.GOLD, "/antiswear ban strikes <value>"));
		list.add(Text.of(TextColors.GOLD, "/antiswear ban temp <value> [time]"));

		pages.contents(list);
		
		pages.sendTo(src);

		return CommandResult.success();
	}

}
