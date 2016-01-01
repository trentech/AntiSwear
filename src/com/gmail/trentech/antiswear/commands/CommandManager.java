package com.gmail.trentech.antiswear.commands;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandManager {

	public CommandSpec cmdAdd = CommandSpec.builder()
		    .description(Text.of("Add words to censor list"))
		    .permission("antiswear.cmd.antiswear.add")
		    .arguments(GenericArguments.optional(GenericArguments.string(Text.of("word"))))
		    .executor(new CMDAdd())
		    .build();

	public CommandSpec cmdRemove = CommandSpec.builder()
		    .description(Text.of("Remove words from censor list"))
		    .permission("antiswear.cmd.antiswear.remove")
		    .arguments(GenericArguments.optional(GenericArguments.string(Text.of("word"))))
		    .executor(new CMDRemove())
		    .build();
	
	public CommandSpec cmdAntiSwear = CommandSpec.builder()
			.description(Text.of("Base command"))
			.permission("antiswear.cmd.antiswear")
			.child(cmdAdd, "add", "a")
			.child(cmdRemove, "remove", "r")
			.executor(new CMDAntiSwear())
			.build();
}
