package me.superckl.api.biometweaker.script.wrapper;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import me.superckl.api.biometweaker.script.pack.TypeBiomesPackage;
import me.superckl.api.superscript.ScriptHandler;
import me.superckl.api.superscript.util.ParameterTypes;
import me.superckl.api.superscript.util.ParameterWrapper;

public class TypesPackParameterWrapper extends ParameterWrapper{

	public TypesPackParameterWrapper() {
		super(BTParameterTypes.TYPE_BIOMES_PACKAGE, 1, 1, false);
	}

	@Override
	public Pair<Object[], String[]> parseArgs(final ScriptHandler handler, final String... args) throws Exception {
		final List<String> parsed = Lists.newArrayList();
		String[] toReturn = new String[0];
		for(int i = 0; i < args.length; i++){
			final String obj = (String) ParameterTypes.STRING.tryParse(args[i], handler);
			if(obj == null){
				toReturn = new String[args.length-i];
				System.arraycopy(args, i, toReturn, 0, toReturn.length);
				break;
			}
			parsed.add(obj);
		}
		return Pair.of(new Object[] {new TypeBiomesPackage(parsed.toArray(new String[parsed.size()]))}, toReturn);
	}

}
