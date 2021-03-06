package me.superckl.biometweaker.script.command;

import java.lang.reflect.Field;
import java.util.Iterator;

import lombok.RequiredArgsConstructor;
import me.superckl.api.biometweaker.script.pack.IBiomePackage;
import me.superckl.api.superscript.command.IScriptCommand;
import me.superckl.biometweaker.util.ArrayHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;

@RequiredArgsConstructor
public class ScriptCommandAddActualFillerBlock implements IScriptCommand{

	private static Field actualFillerBlocks;

	private final IBiomePackage pack;
	private final String block;
	private final int meta;

	public ScriptCommandAddActualFillerBlock(final IBiomePackage pack, final String block) {
		this(pack, block, -1);
	}

	@Override
	public void perform() throws Exception {
		if(ScriptCommandAddActualFillerBlock.actualFillerBlocks == null)
			ScriptCommandAddActualFillerBlock.actualFillerBlocks = Biome.class.getDeclaredField("actualFillerBlocks");
		final Block toAdd = Block.getBlockFromName(this.block);
		if(toAdd == null)
			throw new IllegalArgumentException("Failed to find block "+this.block+"! Tweak will not be applied.");
		final Iterator<Biome> it = this.pack.getIterator();
		while(it.hasNext()){
			final Biome biome = it.next();
			IBlockState[] blocks = (IBlockState[]) ScriptCommandAddActualFillerBlock.actualFillerBlocks.get(biome);
			blocks = ArrayHelper.append(blocks, this.meta == -1 ? toAdd.getDefaultState():toAdd.getStateFromMeta(this.meta));
			ScriptCommandAddActualFillerBlock.actualFillerBlocks.set(biome, blocks);
		}
	}

}
