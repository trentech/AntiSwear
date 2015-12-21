package com.gmail.trentech.antiswear.commands;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Texts;

public class CommandManager {

	public CommandSpec cmdAdd = CommandSpec.builder()
		    .description(Texts.of("Add words to censor list"))
		    .permission("AntiSwear.cmd.antiswear.add")
		    .arguments(GenericArguments.optional(GenericArguments.string(Texts.of("word"))))
		    .executor(new CMDAdd())
		    .build();

	public CommandSpec cmdRemove = CommandSpec.builder()
		    .description(Texts.of("Remove words from censor list"))
		    .permission("AntiSwear.cmd.antiswear.remove")
		    .arguments(GenericArguments.optional(GenericArguments.string(Texts.of("word"))))
		    .executor(new CMDRemove())
		    .build();
	
	public CommandSpec cmdAntiSwear = CommandSpec.builder()
			.description(Texts.of("Base command"))
			.permission("AntiSwear.cmd.antiswear")
			.child(cmdAdd, "add", "a")
			.child(cmdRemove, "remove", "r")
			.executor(new CMDAntiSwear())
			.build();
}
