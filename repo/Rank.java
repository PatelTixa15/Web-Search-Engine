package repo;

public class Rank {
	// The document id. 
	private int documenId;

	// The doc title. 
	private String docTitle;

	// The doc link. 
	private String docLink;

	// The tf idf. 
	private double tfIdf;

	/** Instantiates a new doc rank.
	 * @param documentId the document id
	 * @param title - the title
	 * @param link  - the link
	 * @param tfIdf - the tf idf */
	public Rank(int documentId, String title, String link, double tfIdf) {
		this.documenId = documentId;
		this.tfIdf = tfIdf;
		this.docTitle = title;
		this.docLink = link;
	}

	/** Gets the document id.
	 * @return the document id */
	public int get_Document_Id() {
		return documenId;
	}

	/** Sets the document id.
	 * @param documenId the new document id */
	public void set_Document_Id(int documenId) {
		this.documenId = documenId;
	}

	/** Gets the tf idf.
	 * @return the tf idf */
	public double get_TfIdf() {
		return tfIdf;
	}

	/** Sets the tf idf.
	 * @param tfIdf the new tf idf */
	public void set_TfIdf(double tfIdf) {
		this.tfIdf = tfIdf;
	}

	/** Adds the tf idf.
	 * @param tfIdf the tf idf */
	public void add_TfIdf(double tfIdf) {

		this.tfIdf += tfIdf;
	}

	/** Gets the doc title.
	 * @return the doc title */
	public String get_Doc_Title() {
		return docTitle;
	}

	/** Gets the doc link.
	 * @return the doc link */
	public String get_Doc_Link() {
		return docLink;
	}

	/** Hash code.
	 * @return the int */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + documenId;
		return result;
	}

	/** Equals.
	 * @param obj the object
	 * @return true, if successful */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rank other = (Rank) obj;
		if (documenId != other.documenId)
			return false;
		return true;
	}
}
