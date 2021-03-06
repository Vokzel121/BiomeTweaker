package me.superckl.api.biometweaker.script.pack;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import me.superckl.api.superscript.util.CollectionHelper;
import net.minecraft.world.biome.Biome;

public class IntersectBiomesPackage implements IBiomePackage{

	List<IBiomePackage> packs = Lists.newArrayList();

	public IntersectBiomesPackage(final IBiomePackage ... packs) {
		Collections.addAll(this.packs, packs);
	}

	@Override
	public Iterator<Biome> getIterator() {
		if(this.packs.size() == 0)
			return Iterators.emptyIterator();
		final List<Biome>[] lists = new List[this.packs.size()];
		for(int i = 0; i < this.packs.size(); i++){
			final List<Biome> list = Lists.newArrayList();
			Iterators.addAll(list, this.packs.get(i).getIterator());
			lists[i] = list;
		}
		final List<Biome> intersect = Lists.newArrayList(lists[0]);
		final Iterator<Biome> it = intersect.iterator();
		while(it.hasNext())
			if(!CollectionHelper.allContains(it.next(), lists))
				it.remove();
		return intersect.iterator();
	}

	@Override
	public boolean supportsEarlyRawIds() {
		for(final IBiomePackage pack:this.packs)
			if(!pack.supportsEarlyRawIds())
				return false;
		return true;
	}

	@Override
	public List<Integer> getRawIds() {
		if(this.packs.size() == 0)
			return Collections.EMPTY_LIST;
		final List<Integer>[] ints = new List[this.packs.size()];
		for(int i = 0; i < this.packs.size(); i++)
			ints[i] = this.packs.get(i).getRawIds();
		final List<Integer> intersect = Lists.newArrayList(ints[0]);
		final Iterator<Integer> it = intersect.iterator();
		while(it.hasNext())
			if(!CollectionHelper.allContains(it.next(), ints))
				it.remove();
		return intersect;
	}

}
