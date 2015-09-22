package me.superckl.biometweaker.script.command;

import java.util.Iterator;

import lombok.RequiredArgsConstructor;
import me.superckl.api.biometweaker.script.pack.IBiomePackage;
import me.superckl.api.superscript.command.IScriptCommand;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

@RequiredArgsConstructor
public class ScriptCommandAddToGeneration implements IScriptCommand{

	private final IBiomePackage pack;
	private final String type;
	private final int weight;

	@Override
	public void perform() throws Exception {
		final BiomeManager.BiomeType type = BiomeManager.BiomeType.valueOf(this.type);
		final Iterator<BiomeGenBase> it = this.pack.getIterator();
		while(it.hasNext())
			BiomeManager.addBiome(type, new BiomeEntry(it.next(), this.weight));
	}

}
