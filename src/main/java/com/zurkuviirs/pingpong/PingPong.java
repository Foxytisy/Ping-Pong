package com.zurkuviirs.pingpong;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;

public class PingPong implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("pingpong");

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(ClientCommandManager.literal("pingpong")
					.executes(context -> {
						MinecraftClient.getInstance().player.sendMessage(Text.literal("Ping Pong! 🏓"), false);
						return 1;
					})
					.then(ClientCommandManager.argument("pongablemessage", StringArgumentType.greedyString())
							.executes(context -> {
								String message = StringArgumentType.getString(context, "pongablemessage");
								StringBuilder pongedMessageBuilder = new StringBuilder();
								int pongLength = message.length();
								for (int i = 0; i < pongLength / 2; i++) {
									char pongStart = message.charAt(i);
									pongedMessageBuilder.append(pongStart);
									char pongEnd = message.charAt((pongLength - 1) - i);
									pongedMessageBuilder.append(pongEnd);
								}
								if (pongLength % 2 != 0) {
									char middleChar = message.charAt(pongLength / 2);
									pongedMessageBuilder.append(middleChar);
								}

								String pongedMessage = pongedMessageBuilder.toString();

								//MinecraftClient client = MinecraftClient.getInstance();
								//if (client.player != null) {
								//	client.inGameHud.getChatHud().addMessage(Text.literal((pongedMessage + " :ping_pong:")));
								//}

								((ChatScreen)MinecraftClient.getInstance().currentScreen).sendMessage((pongedMessage + " :ping_pong:"),true);
								return 1;
							})
					)
			);
		});
}

}