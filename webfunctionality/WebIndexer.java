package webfunctionality;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import repo.Frequency;
import repo.Info;
import repo.Rank;
import repo.Path;
import textprocessing.TST;

public class WebIndexer {
	private static final Logger logger = Logger.getLogger(WebIndexer.class.getName());

	private TST<List<Frequency>> indexedTerms = new TST<List<Frequency>>();
	private HashMap<Integer, Info> documentIdNameMap = new HashMap<Integer, Info>();
	String stemmedWord = "";
	int id = 0;

	private static WebIndexer indexer;

	private WebIndexer() {
	}

	public static WebIndexer getInstance() {
		if (indexer == null) {
			indexer = new WebIndexer();
		}
		return indexer;
	}

	public void start_Indexer() throws IOException {
		logger.log(Level.INFO, "Crawling Started");
//		new Crawler().crawl();
		logger.log(Level.INFO, "Crawling Completed");

		logger.log(Level.INFO, "Indexing Started");

		List<String> tokenWords;
		int count = 0;
		for (File file : WebParser.getWebPageFilesList()) {
			System.out.println(
					"Indexing (" + (++count) + "/" + WebParser.getWebPageFilesList().size() + ") " + file.getName());
			String title = file.getName().substring(0, file.getName().length() - 4);
			String parentDir = file.getAbsolutePath().replaceAll(Path.txtDirectoryName, "");
			String link = parentDir.substring(0, parentDir.length() - 4) + ".html";
			String data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

			double documentLength = data.split("[^a-zA-Z0-9'-]").length;
			documentIdNameMap.put(id, new Info(title, link));
			tokenWords = WebParser.parse(data);

			tokenWords.stream().filter(word1 -> word1.trim().length() > 1 || word1.length() > 1).forEach(word -> {
				stemmedWord = word;
				if (null == indexedTerms.get(stemmedWord)) {
					indexedTerms.put(stemmedWord, new ArrayList<Frequency>());
					indexedTerms.get(stemmedWord).add(new Frequency(id, 1, documentLength));
				} else {
					List<Frequency> docList = indexedTerms.get(stemmedWord);
					if (docList.contains(new Frequency(id))) {
						Frequency docFreqObj = docList.get(docList.indexOf((new Frequency(id, documentLength))));
						docFreqObj.add_Occurrence();
					} else {
						Frequency newDoc = new Frequency(id, documentLength);
						newDoc.add_Occurrence();
						docList.add(newDoc);
					}
				}
			});
			id++;
		}
		logger.log(Level.INFO, "Indexing Completed");
	}

	public List<Rank> tfIdf(String term) {
		List<Rank> docRankList = new ArrayList<Rank>();
		int totalDocuments = WebParser.getWebPageFilesList().size();
		if (indexedTerms.get(term) != null) {
			double docListLength = indexedTerms.get(term).size();
			for (Frequency doc : indexedTerms.get(term)) {
				docRankList
						.add(new Rank(doc.get_Document_Id(), documentIdNameMap.get(doc.get_Document_Id()).get_Doc_Title(),
								documentIdNameMap.get(doc.get_Document_Id()).get_Doc_Link(),
								doc.get_Term_Frequency() * Math.log10(totalDocuments / docListLength)));
			}
		}
		return docRankList;
	}

	public List<Rank> getFilteredDocuments(String query) {
		String[] queryTokens = query.split(" ");

		List<Rank> filteredDocumentsList = tfIdf(queryTokens[0]);
		for (int i = 1; i < queryTokens.length; i++) {
			for (Rank doc : tfIdf(queryTokens[i])) {
				if (filteredDocumentsList.contains(doc)) {
					filteredDocumentsList.get(filteredDocumentsList.indexOf(doc)).add_TfIdf(doc.get_TfIdf());
				}
			}
		}
		filteredDocumentsList.sort((c1, c2) -> Double.compare(c2.get_TfIdf(), c1.get_TfIdf()));
		return filteredDocumentsList;
	}

	public TST<List<Frequency>> getIndexedTerms() {
		return indexedTerms;
	}
}
