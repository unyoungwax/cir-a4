import java.util.ArrayList;
import java.util.List;

public class Paper {

	private String id;
	private String title;
	private String venue;
	private Integer year;
	private List<Author> authors = new ArrayList<>();
	private List<Paper> inCitations = new ArrayList<>();
	private List<Paper> outCitations = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void addAuthor(Author author) {
		authors.add(author);
	}

	public List<Paper> getInCitations() {
		return inCitations;
	}

	public void addInCitation(Paper inCitation) {
		inCitations.add(inCitation);
	}

	public List<Paper> getOutCitations() {
		return outCitations;
	}

	public void addOutCitations(Paper outCitation) {
		outCitations.add(outCitation);
	}
}
