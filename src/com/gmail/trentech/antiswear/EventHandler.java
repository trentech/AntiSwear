package com.gmail.trentech.antiswear;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.Ban.Builder;
import org.spongepowered.api.util.ban.BanTypes;

import ninja.leaping.configurate.ConfigurationNode;

public class EventHandler {

	@Listener
	public void onMessageEvent(MessageEvent event, @First Player player){
		if(player.hasPermission("AntiSwear.ignore")){
			return;
		}

		if(!event.getMessage().isPresent()){
			return;
		}
		
		String msg = event.getMessage().get().toPlain();

		boolean swear = false;
		
		ConfigurationNode config = new ConfigManager().getConfig();
		List<String> list = config.getNode("Words").getChildrenList().stream().map(ConfigurationNode::getString).collect(Collectors.toList());
		
		for(String item : list){
			if(!msg.toLowerCase().matches(".*\\b" + item.toLowerCase() + "\\b.*")){
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
		
		ConfigManager playerConfigManager = new ConfigManager("players.conf"); 
		ConfigurationNode playerConfig = playerConfigManager.getConfig();
		
		if(playerConfig.getNode(player.getUniqueId().toString(), "Strikes").getString() != null){
			value = playerConfig.getNode(player.getUniqueId().toString(), "Strikes").getInt() + 1;
		}
		
		playerConfig.getNode(player.getUniqueId().toString(), "Strikes").setValue(value);		
		playerConfigManager.save();
		
		if(config.getNode("Options", "Ban", "Enable").getBoolean()){
			if(config.getNode("Options", "Ban", "Strikes").getInt() <= value){
				Text reason = Text.of(config.getNode("Options", "Ban", "Message").getString());
				
				UserStorageService userStorage = Main.getGame().getServiceManager().provide(UserStorageService.class).get();
				BanService banService = Main.getGame().getServiceManager().provide(BanService.class).get();

				User user = userStorage.get(player.getUniqueId()).get();

				Builder builder = Ban.builder();
				builder.type(BanTypes.PROFILE).reason(reason).profile(user.getProfile());
				
				if(config.getNode("Options", "Ban", "Temporary", "Enable").getBoolean()){				
					builder.expirationDate(Instant.now().plusMillis(getTimeInMilliSeconds(config.getNode("Options", "Ban", "Temporary", "Time").getString())));
				}

				banService.addBan(builder.build());

				player.kick(reason);
				
				playerConfig.getNode(player.getUniqueId().toString(), "Strikes").setValue(0);
				playerConfigManager.save();

				return;
			}
			player.sendMessage(Text.of(TextColors.YELLOW, "Strikes: ", value));
		}

		if(config.getNode("Options", "Kick", "Enable").getBoolean()){
			player.kick(Text.of(config.getNode("Options", "Kick", "Message").getString()));
			return;
		}

		event.setMessage(Text.of(msg));
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
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("w", ""))) * 7) + milliSeconds;
			}else if(t.matches("(\\d+)[mo]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("mo", ""))) * 30) + milliSeconds;
			}else if(t.matches("(\\d+)[y]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("y", ""))) * 365) + milliSeconds;
			}
		}
		return milliSeconds;
	}
}
