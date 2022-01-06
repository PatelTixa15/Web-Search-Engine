package repo;

public class Info {
	
	// The doc title. 
	private String docTitle;

	// The doc link. 
	private String docLink;

	/** Instantiates a new doc info.
	 * @param title   the title
	 * @param docLink the doc link */
	
	public Info(String title, String docLink) {
		this.docTitle = title;
		this.docLink = docLink;
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
}
