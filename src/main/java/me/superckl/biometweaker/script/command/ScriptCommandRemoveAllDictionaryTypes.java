package me.superckl.biometweaker.script.command;

import java.util.Iterator;

import lombok.RequiredArgsConstructor;
import me.superckl.api.biometweaker.event.BiomeTweakEvent;
import me.superckl.api.biometweaker.script.pack.IBiomePackage;
import me.superckl.api.superscript.command.IScriptCommand;
import me.superckl.biometweaker.config.Config;
import me.superckl.biometweaker.util.BiomeHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;

@RequiredArgsConstructor
public class ScriptCommandRemoveAllDictionaryTypes implements IScriptCommand{

	private final IBiomePackage pack;

	@Override
	public void perform() throws Exception {
		final Iterator<Biome > it = this.pack.getIterator();
		while(it.hasNext()){
			final Biome gen = it.next();
			if(MinecraftForge.EVENT_BUS.post(new BiomeTweakEvent.RemoveAllDictionaryTypes(this, gen)))
				continue;
			BiomeHelper.removeAllBiomeDicType(gen);
			Config.INSTANCE.onTweak(Biome.getIdForBiome(gen));
		}
	}

}
