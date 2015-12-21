package com.gmail.trentech.antiswear;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.MessageSinkEvent;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.Ban.Builder;
import org.spongepowered.api.util.ban.BanTypes;

import ninja.leaping.configurate.ConfigurationNode;

public class EventHandler {

	@Listener
	public void onMessageEvent(MessageSinkEvent.Chat event){
		if(!event.getCause().first(Player.class).isPresent()){
			return;
		}
		Player player = event.getCause().first(Player.class).get();

		if(player.hasPermission("AntiSwear.ignore")){
			return;
		}
		
		Text text = event.getMessage();
		String msg = Texts.toPlain(text);

		boolean swear = false;
		
		ConfigurationNode config = new ConfigManager().getConfig();
		List<String> list = config.getNode("Words").getChildrenList().stream().map(ConfigurationNode::getString).collect(Collectors.toList());
		
		for(String item : list){
			if(!StringUtils.containsIgnoreCase(msg, item)){
				continue;
			}
			
			if(config.getNode("Options", "Replace-Message", "Enable").getBoolean()){
				msg = "<" + player.getName() + "> " + config.getNode("Options", "Replace-Message", "Message").getString();
				swear = true;
				break;
			}
			
			char[] word = item.toCharArray();
			for (int i = 1; i < word.length - 1; i++){
			    word[i] = '*';
			}

			msg = msg.replaceAll("(?i)" + item, String.copyValueOf(word));
			
			swear = true;
		}

		if(!swear){
			return;
		}
		
		int value = 1;
		
		ConfigManager playerLoader = new ConfigManager("players.conf"); 
		ConfigurationNode playerConfig = playerLoader.getConfig();
		
		if(playerConfig.getNode(player.getUniqueId().toString(), "Strikes").getString() != null){
			value = playerConfig.getNode(player.getUniqueId().toString(), "Strikes").getInt() + 1;
		}
		
		playerConfig.getNode(player.getUniqueId().toString(), "Strikes").setValue(value);		
		playerLoader.save();
		
		if(config.getNode("Options", "Ban", "Enable").getBoolean()){
			if(config.getNode("Options", "Ban", "Strikes").getInt() <= value){
				Text reason = Texts.of(config.getNode("Options", "Ban", "Message").getString());
				
				UserStorageService userStorage = Main.getGame().getServiceManager().provide(UserStorageService.class).get();
				BanService banService = Main.getGame().getServiceManager().provide(BanService.class).get();
				
				User user = userStorage.get(player.getUniqueId()).get();

				Builder builder = Ban.builder();
				builder.type(BanTypes.PROFILE).reason(reason).profile(user.getProfile());
				
				if(config.getNode("Options", "Ban", "Temporary", "Enable").getBoolean()){
					builder.expirationDate(new Date(new Date().getTime() + getTimeInMilliSeconds(config.getNode("Options", "Ban", "Temporary", "Time").getString())));
				}

				banService.addBan(builder.build());

				player.kick(reason);
				
				playerConfig.getNode(player.getUniqueId().toString(), "Strikes").setValue(0);
				playerLoader.save();
				
				event.setCancelled(true);
				return;
			}
			player.sendMessage(Texts.of(TextColors.YELLOW, "Strikes: ", value));
		}

		if(config.getNode("Options", "Kick", "Enable").getBoolean()){
			player.kick(Texts.of(config.getNode("Options", "Kick", "Message").getString()));
			event.setCancelled(true);
			return;
		}

		event.setMessage(Texts.of(msg));
	}
	
	public long getTimeInMilliSeconds(String time) {
		String[] times = time.split(" ");
		long milliSeconds = 0;
		for(String t : times) {
			if(t.matches("(\\d+)[s]$")) {
				milliSeconds = TimeUnit.SECONDS.toMillis(Integer.parseInt(t.replace("s", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[m]$")) {
				milliSeconds = TimeUnit.MINUTES.toMillis(Integer.parseInt(t.replace("m", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[h]$")) {
				milliSeconds = TimeUnit.HOURS.toMillis(Integer.parseInt(t.replace("h", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[d]$")) {
				milliSeconds = TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("d", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[w]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("d", ""))) * 7) + milliSeconds;
			}else if(t.matches("(\\d+)[mo]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("mo", ""))) * 30) + milliSeconds;
			}else if(t.matches("(\\d+)[y]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("y", ""))) * 365) + milliSeconds;
			}
		}
		return milliSeconds;
	}
}
