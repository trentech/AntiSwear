package com.gmail.trentech.antiswear;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.gmail.trentech.antiswear.commands.CommandManager;
import com.google.inject.Inject;

import me.flibio.updatifier.Updatifier;

@Updatifier(repoName = Resource.ID, repoOwner = "TrenTech", version = Resource.VERSION)
@Plugin(id = Resource.ID, name = Resource.NAME, version = Resource.VERSION, authors = Resource.AUTHOR, url = Resource.URL, description = Resource.DESCRIPTION, dependencies = {@Dependency(id = "Updatifier", optional = true)})
public class Main {

	@Inject
	private static Logger log;	
    private static Game game;
	private static PluginContainer plugin;

	@Listener
    public void onPreInitialization(GamePreInitializationEvent event) {
		game = Sponge.getGame();
		plugin = getGame().getPluginManager().getPlugin(Resource.ID).get();
    }

    @Listener
    public void onInitialization(GameInitializationEvent event) {
    	getGame().getEventManager().registerListeners(this, new EventHandler());
    	
    	getGame().getCommandManager().register(this, new CommandManager().cmdAntiSwear, "antiswear", "as");
    	
    	new ConfigManager();
    }

    @Listener
    public void onPostInitialization(GamePostInitializationEvent event) {

    }

    @Listener
    public void onStartedServer(GameStartedServerEvent event) {

    }

    @Listener
	public void onStoppingServer(GameStoppingServerEvent event) {
    	
	}
    
    @Listener
    public void onStoppedServer(GameStoppedServerEvent event) {

    }

    public static Logger getLog() {
        return log;
    }
    
	public static Game getGame() {
		return game;
	}

	public static PluginContainer getPlugin() {
		return plugin;
	}
}