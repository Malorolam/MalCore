package mal.core.util;

import mal.core.version.VersionInfo;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class MalEvents {

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		//test
		event.player.addChatComponentMessage(new ChatComponentText(event.player.getDisplayName()));
		
		ModList.doVersionCheck(event);
	}
}
