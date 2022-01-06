import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import webfunctionality.WebIndexer;
import webfunctionality.WebSpellChecker;
import repo.Rank;
import repo.Searches;

public class WebSearchEngineApp {
	static WebIndexer indexer = WebIndexer.getInstance();
	static WebSpellChecker spellChecker = new WebSpellChecker();

	/** Initialize crawler and indexer.
	 * @throws IOException Signals that an I/O exception has occurred. */
	private static void initializeCrawlerAndWebIndexer() throws IOException {
		indexer.start_Indexer();
	}

	/** Gets the search results.
	 * @param query the query
	 * @return the search results
	 * @throws IOException */
	private static Searches getSearches(String query) throws IOException {
		String finalQuery = spellChecker.spell_Check(query);
		Searches searchResults = new Searches();
		searchResults.set_Query(finalQuery.trim());
		double startTime = System.nanoTime();
		searchResults.set_Results(indexer.getFilteredDocuments(finalQuery));
		searchResults.set_Time_Taken((System.nanoTime() - startTime) / 1000000.0);
		return searchResults;
	}

	/** Gets the suggestions.
	 * @param query the query
	 * @return the suggestions */
	private static List<String> get_Suggestions(String query) {

		String[] tokens = query.split(" ");
		String suggestedQueryPrefix = "";
		if (tokens.length > 1)
			suggestedQueryPrefix = tokens[0];

		ArrayList<String> array = new ArrayList<String>();
		for (String key : indexer.getIndexedTerms().keys()) {
			if (key.startsWith(tokens[tokens.length - 1])) {
				array.add(suggestedQueryPrefix + " " + key);
			}
		}
		return array;
	}

	/** The main method.
	 * @param args the arguments
	 * @throws Exception */
	public static void main(String[] args) throws Exception {
		initializeCrawlerAndWebIndexer();
		Scanner s = new Scanner(System.in);
		String choice = "Yes";
		do {
			System.out.print("Search Something: ");
			String query = s.nextLine();
			System.out.println();
			Searches result = getSearches(query);
			if (!query.equalsIgnoreCase(result.get_Query())) {
				System.out.println("Sorry we can't find your search... " + result.get_Query() + "' We found something else for you'" + query + "'");
			}
			List<String> suggestions = get_Suggestions(result.get_Query());
			suggestions.remove(0);
			suggestions.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));
			System.out.print("Suggestions: ");

			for (String word : suggestions) {
				System.out.print(word + ", ");
			}
			System.out.println("\nAbout " + result.get_Results().size() + " results ("
					+ String.format("%.5f", result.get_Time_Taken() / 1000.0) + " seconds)");
			if (result.get_Results().size() > 10)
				System.out.println("\nTop 10 results...");
			int count = 0;
			for (Rank res : result.get_Results()) {
				if (++count > 10)
					break;
				System.out.println(count + "\t" + res.get_Doc_Title());
				System.out.println("\t" + res.get_Doc_Link());
				System.out.println("\ttf-idf: " + res.get_TfIdf());
				System.out.println();
			}
			System.out.print("Do you want to search?(Yes/No): ");
			choice = s.nextLine();
		} while (!choice.equalsIgnoreCase("No"));
		System.out.println("Search Completed");
		s.close();
	}
}
