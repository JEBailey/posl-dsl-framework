package posl.engine.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import posl.engine.core.Context;
import posl.engine.spi.PoslImpl;

/**

 */
public final class PoslProvider {
	
	@SuppressWarnings("serial")
	private static Map<String, PoslImpl> impls = new HashMap<String, PoslImpl>() {
		{
			Logger log = Logger.getLogger(PoslProvider.class.getName());
			
			try {
				for (PoslImpl p : ServiceLoader.load(PoslImpl.class)) {
					put(p.getName(), p);
				}
			} catch (ServiceConfigurationError sce) {
				//not enough to warrant killing the process
				log.warning(sce.getLocalizedMessage());
			}
		}
	};

	/**
	 * populates the graph with all of the possible dependencies
	 * 
	 * 
	 * @param graph
	 * @param implementationList
	 * @return
	 */
	public static Context getContext(String language) {
		PoslImpl start = impls.get(language);
		List<PoslImpl> list = new ArrayList<PoslImpl>();
		populateImpls(language, list);
		Graph<PoslImpl> graph = new Graph<PoslImpl>();
		for (PoslImpl source : list) {
			for (String implName : source.getRequires()) {
				PoslImpl target = impls.get(implName);
				graph.addEdge(target, source);
			}
		}

		Context context = new Context();

		for (PoslImpl impl : graph.getList(start)) {
			impl.visit(context);
		}

		return context;
	}

	/**
	 * parses through the requirements of the language implementation, ensures
	 * that the implementations exist and that all possible nodes are collected
	 * into a list
	 * 
	 * @param currentNode
	 * @param visited
	 */
	private static void populateImpls(String currentNode, List<PoslImpl> visited) {
		PoslImpl poslImpl = impls.get(currentNode);
		if (poslImpl == null) {
			throw new RuntimeException("missing language implementation "
					+ currentNode);
		}
		if (!visited.contains(poslImpl)) {
			visited.add(poslImpl);
			for (String nextNode : poslImpl.getRequires()) {
				populateImpls(nextNode, visited);
			}
		}
	}

}