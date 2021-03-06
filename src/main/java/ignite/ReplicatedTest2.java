package ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

public class ReplicatedTest2 {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		
		System.out.println("Starting Apache Ignite...");
		CacheConfiguration cfg = new CacheConfiguration("names");
		cfg.setCacheMode(CacheMode.REPLICATED);
		IgniteConfiguration ic = new IgniteConfiguration();
		ic.setClientMode(false);
		Ignite ignite = Ignition.start(ic);
		IgniteCache<String, String> cache = ignite.getOrCreateCache(cfg);
		System.out.println("Apache Ignite started");
		System.out.println();
		
		java.util.Scanner input = new java.util.Scanner(System.in);
		String customerId = "1";
		String currentName = "Mark";
		if (cache.get("1") != null) {
			currentName = cache.get(customerId);
		}
		System.out.print("Hello " + currentName + ". Enter new name: ");
		String newName = input.nextLine();
		System.out.println();
		input.close();

		System.out.println("Name in cache for customerId " + customerId + ": " + currentName);
		if (currentName == null) { currentName = getNameFromDatabase(customerId); cache.put(customerId, currentName); Thread.sleep(2000); }
		System.out.println("Updating customerId " + customerId + " from " + currentName + " to " + newName);
		cache.put(customerId, newName);
		
		currentName = cache.get(customerId);
		System.out.println("Name in cache for customerId " + customerId + ": " + currentName);
		ignite.close();
	}
	
	public static String getNameFromDatabase(String customerId) throws Exception {
		String name = "Mark";
		System.out.println("Retrieving current name from database: " + name);
		Thread.sleep(1000);
		System.out.println("Name in cache for customerId " + customerId + ": " + name);
		System.out.println();
		Thread.sleep(1000);
		return name;
	}
}
