package eu.ydp.gwtcreatejs.client.loader;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.Ordering.natural;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimaps;


public class ScriptFileSortingUtil {
	// TODO zapewnic, zeby liby byly zawsze ladowane przed skryptami! potem dopiero customowa klolejnosc
	
	public ImmutableList<ScriptFile> sort(List<ScriptFile> scriptsList) {
		Collections.sort(scriptsList, comparator);		
		ImmutableMultimap<Integer, ScriptFile> result = indexOnOrderNumber(scriptsList);		
		return result.values().asList();
	}	
	
	private Comparator<ScriptFile> comparator = new Comparator<ScriptFile>() {
		@Override
		public int compare(ScriptFile script1, ScriptFile script2) {
			return script1.getPath().compareToIgnoreCase(script2.getPath());
		}
	};	
	
	private Function<ScriptFile, Integer> scriptToOrd = new Function<ScriptFile, Integer>() {
		public Integer apply(ScriptFile script) {
			return script.getOrder();
		};	
	};
	
	private ImmutableMultimap<Integer, ScriptFile> indexOnOrderNumber(Iterable<ScriptFile> i) {
		List<ScriptFile> sorted = natural().onResultOf(scriptToOrd).sortedCopy(i);
		return Multimaps.index(sorted, scriptToOrd);
	}	
}
