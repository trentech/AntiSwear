package com.gmail.trentech.antiswear.commands;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import com.gmail.trentech.antiswear.commands.ban.CMDBan;
import com.gmail.trentech.antiswear.commands.ban.CMDEnable;
import com.gmail.trentech.antiswear.commands.ban.CMDMessage;
import com.gmail.trentech.antiswear.commands.ban.CMDStrikes;
import com.gmail.trentech.antiswear.commands.command.CMDCommand;
import com.gmail.trentech.antiswear.commands.command.CMDRun;
import com.gmail.trentech.antiswear.commands.kick.CMDKick;
import com.gmail.trentech.antiswear.commands.tempban.CMDTempBan;
import com.gmail.trentech.antiswear.commands.tempban.CMDTime;

public class CommandManager {

	public CommandSpec cmdTempBanEnable = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.tempban")
			.arguments(GenericArguments.optional(GenericArguments.string(Text.of("value"))))
			.executor(new com.gmail.trentech.antiswear.commands.tempban.CMDEnable())
			.build();
	
	public CommandSpec cmdTempBanTime = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.tempban")
			.arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("value"))))
			.executor(new CMDTime())
			.build();
	
	public CommandSpec cmdTempBan = CommandSpec.builder()
		    .permission("antiswear.cmd.antiswear.tempban")
		    .child(cmdTempBanEnable, "enable", "e")
		    .child(cmdTempBanTime, "time", "t")
		    .executor(new CMDTempBan())
		    .build();
	
	public CommandSpec cmdReplaceEnable = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.replace")
			.arguments(GenericArguments.optional(GenericArguments.string(Text.of("value"))))
			.executor(new com.gmail.trentech.antiswear.commands.replace.CMDEnable())
			.build();
	
	public CommandSpec cmdReplaceMessage = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.replace")
			.arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("value"))))
			.executor(new com.gmail.trentech.antiswear.commands.replace.CMDMessage())
			.build();
	
	public CommandSpec cmdReplace = CommandSpec.builder()
		    .permission("antiswear.cmd.antiswear.replace")
		    .child(cmdReplaceEnable, "enable", "e")
		    .child(cmdReplaceMessage, "message", "m")
		    .executor(new CMDKick())
		    .build();
	
	public CommandSpec cmdKickEnable = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.kick")
			.arguments(GenericArguments.optional(GenericArguments.string(Text.of("value"))))
			.executor(new com.gmail.trentech.antiswear.commands.kick.CMDEnable())
			.build();
	
	public CommandSpec cmdKickMessage = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.kick")
			.arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("value"))))
			.executor(new com.gmail.trentech.antiswear.commands.kick.CMDMessage())
			.build();
	
	public CommandSpec cmdKick = CommandSpec.builder()
		    .permission("antiswear.cmd.antiswear.kick")
		    .child(cmdKickEnable, "enable", "e")
		    .child(cmdKickMessage, "message", "m")
		    .executor(new CMDKick())
		    .build();
	
	public CommandSpec cmdCommandEnable = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.command")
			.arguments(GenericArguments.optional(GenericArguments.string(Text.of("value"))))
			.executor(new com.gmail.trentech.antiswear.commands.command.CMDEnable())
			.build();
	
	public CommandSpec cmdRun = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.command")
			.arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("value"))))
			.executor(new CMDRun())
			.build();
	
	public CommandSpec cmdCommand = CommandSpec.builder()
		    .permission("antiswear.cmd.antiswear.command")
		    .child(cmdCommandEnable, "enable", "e")
		    .child(cmdRun, "run", "r")
		    .executor(new CMDCommand())
		    .build();
	
	public CommandSpec cmdBanEnable = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.ban")
			.arguments(GenericArguments.optional(GenericArguments.string(Text.of("value"))))
			.executor(new CMDEnable())
			.build();
	
	public CommandSpec cmdBanMessage = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.ban")
			.arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("value"))))
			.executor(new CMDMessage())
			.build();
	
	public CommandSpec cmdBanStrikes = CommandSpec.builder()
			.permission("antiswear.cmd.antiswear.ban")
			.arguments(GenericArguments.optional(GenericArguments.string(Text.of("value"))))
			.executor(new CMDStrikes())
			.build();
	
	public CommandSpec cmdBan = CommandSpec.builder()
		    .permission("antiswear.cmd.antiswear.ban")
		    .child(cmdBanEnable, "enable", "e")
		    .child(cmdBanMessage, "message", "m")
		    .child(cmdBanStrikes, "strikes", "s")
		    .executor(new CMDBan())
		    .build();
	
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
			.child(cmdRemove, "remove", "rm")
			.child(cmdBan, "ban", "b")
			.child(cmdTempBan, "tempban", "t")
			.child(cmdKick, "kick", "k")
			.child(cmdReplace, "replace", "r")
			.child(cmdCommand, "command", "c")
			.executor(new CMDAntiSwear())
			.build();
}
