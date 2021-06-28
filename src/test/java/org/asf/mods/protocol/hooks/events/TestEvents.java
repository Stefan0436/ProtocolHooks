package org.asf.mods.protocol.hooks.events;

import org.asf.cyan.mods.events.IEventListenerContainer;
import org.asf.cyan.mods.events.SimpleEvent;

import modkit.enhanced.events.chat.ChatEvent;
import modkit.enhanced.events.objects.chat.ChatEventObject;
import modkit.enhanced.events.objects.player.PlayerJoinEventObject;
import modkit.enhanced.events.objects.server.ServerEventObject;
import modkit.enhanced.events.player.PlayerJoinEvent;
import modkit.enhanced.events.server.ServerStartupEvent;
import modkit.enhanced.player.titles.MinecraftTitle;
import modkit.enhanced.player.titles.TitleIntervalConfiguration;
import modkit.enhanced.player.titles.TitleType;
import modkit.enhanced.util.SimpleChatFormatter;
import modkit.util.Colors;
import net.minecraft.network.chat.TextComponent;

public class TestEvents implements IEventListenerContainer {

	@SimpleEvent(ChatEvent.class)
	public void chatMessage(ChatEventObject event) {
		event.setFormatter(new SimpleChatFormatter("$0 says " + Colors.LIGHT_BLUE + "$2"));
		event.getPlayer().setCustomDisplayName("Test");
		event.getPlayer().setCustomTabListDisplayName("Test");
		MinecraftTitle title = MinecraftTitle.create();
		title.addComponent(TitleType.TITLE, Colors.LIGHT_GREEN + "Test title message");
		title.addComponent(TitleType.SUBTITLE, Colors.LIGHT_BLUE + "Subtitle test");
		title.setIntervalConfiguration(TitleIntervalConfiguration.create(40, 140, 40));
		event.getPlayer().showTitle(title);
	}
	
	@SimpleEvent(ServerStartupEvent.class)
	public void startup(ServerEventObject event) {
		event = event;
	}

	@SimpleEvent(PlayerJoinEvent.class)
	public void login(PlayerJoinEventObject event) {
		event.setFormatter(player -> {
			return new TextComponent(
					Colors.DARK_GREY + player.getDisplayName() + Colors.DARK_GREY + " has joined the server");
		});
	}

}
