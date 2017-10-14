public class PaperData {

	private String id;
	private String title;
	private String venue;
	private Integer year;
	private AuthorData[] authors;
	private String[] inCitations;
	private String[] outCitations;

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getVenue() {
		return venue;
	}

	public Integer getYear() {
		return year;
	}

	public AuthorData[] getAuthors() {
		return authors;
	}

	public String[] getInCitations() {
		return inCitations;
	}

	public String[] getOutCitations() {
		return outCitations;
	}
}
