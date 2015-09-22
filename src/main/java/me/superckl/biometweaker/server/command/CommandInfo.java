package me.superckl.biometweaker.server.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cpw.mods.fml.common.registry.LanguageRegistry;
import me.superckl.biometweaker.util.BiomeHelper;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class CommandInfo implements ICommand{

	private final List<String> aliases = Arrays.asList("btinfo", "biometweakerinfo", "bti", "biometweakeri");

	@Override
	public int compareTo(final Object o) {
		if((o instanceof ICommand) == false)
			return 0;
		return this.getCommandName().compareTo(((ICommand)o).getCommandName());
	}

	@Override
	public String getCommandName() {
		return "BTInfo";
	}

	@Override
	public String getCommandUsage(final ICommandSender p_71518_1_) {
		return LanguageRegistry.instance().getStringLocalization("biometweaker.msg.info.usage.text");
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] p_71515_2_) {
		final ChunkCoordinates coord = sender.getPlayerCoordinates();
		final World world = sender.getEntityWorld();
		if((coord != null) && (world != null)){
			final JsonObject obj = BiomeHelper.fillJsonObject(world.getBiomeGenForCoords(coord.posX, coord.posZ), coord.posX, coord.posY, coord.posZ);
			sender.addChatMessage(new ChatComponentTranslation("biometweaker.msg.info.output.text").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA)));
			final ChatStyle gold = new ChatStyle().setColor(EnumChatFormatting.GOLD);
			final Gson gson = new GsonBuilder().setPrettyPrinting().create();
			for(final Entry<String, JsonElement> entry:obj.entrySet())
				if(entry.getValue().isJsonArray())
					sender.addChatMessage(new ChatComponentText(entry.getKey()+": Check the output files.").setChatStyle(gold)); //It looks hideous in MC chat.
				else
					sender.addChatMessage(new ChatComponentText(entry.getKey()+": "+gson.toJson(entry.getValue())).setChatStyle(gold));
		}else
			sender.addChatMessage(new ChatComponentTranslation("biometweaker.msg.info.invalsender.text").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
	}

	@Override
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return sender.canCommandSenderUseCommand(MinecraftServer.getServer().getOpPermissionLevel(), this.getCommandName());
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
		return false;
	}

}
