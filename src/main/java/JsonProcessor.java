import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProcessor {

	private static final int NUM_LINES = 200000;

	private ObjectMapper mapper = getObjectMapper();

	public static void main(String[] args) throws IOException {

		JsonProcessor processor = new JsonProcessor();

		List<PaperData> paperDataList = processor.read("");

		Map<String, Paper> paperMap = new HashMap<>(NUM_LINES);
		Map<String, Author> authorMap = new HashMap<>(NUM_LINES);

		for (PaperData paperData : paperDataList) {

			Paper paper = new Paper();

			paper.setId(paperData.getId());
			paper.setTitle(paperData.getTitle());
			paper.setYear(paperData.getYear());
			paper.setVenue(paperData.getVenue());

			for (AuthorData authorData : paperData.getAuthors()) {

				// Ignore authors without ID
				if (authorData.getIds().length <= 0) {
					continue;
				}

				Author author = null;

				for (String authorId : authorData.getIds()) {

					author = authorMap.get(authorId);

					if (author != null) {
						break;
					}
				}

				if (author == null) {

					author = new Author();

					author.setName(authorData.getName());
				}

				for (String authorId : authorData.getIds()) {
					authorMap.put(authorId, author);
				}
			}

			paperMap.put(paper.getId(), paper);
		}

		for (PaperData paperData : paperDataList) {

			Paper paper = paperMap.get(paperData.getId());

			for (AuthorData authorData : paperData.getAuthors()) {

				// Skip authors without ID
				if (authorData.getIds().length <= 0) {
					continue;
				}

				Author author = authorMap.get(authorData.getIds()[0]);

				paper.addAuthor(author);
			}

			for (String inCitationPaperId : paperData.getInCitations()) {

				Paper inCitationPaper = paperMap.get(inCitationPaperId);

				if (inCitationPaper != null) {
					paper.addInCitation(inCitationPaper);
				}
			}

			for (String outCitationPaperId : paperData.getOutCitations()) {

				Paper outCitationPaper = paperMap.get(outCitationPaperId);

				if (outCitationPaper != null) {
					paper.addOutCitations(outCitationPaper);
				}
			}
		}

		Collection<Author> authors = authorMap.values();
		Collection<Paper> papers = paperMap.values();
	}

	public List<PaperData> read(String path) throws JsonParseException, JsonMappingException, IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
		List<PaperData> paperDataList = new ArrayList<>(NUM_LINES);

		for (int i = 0; i < NUM_LINES; i++) {

			String line = reader.readLine();

			if (line == null) {
				break;
			}

			PaperData paperData = mapper.readValue(line, PaperData.class);

			paperDataList.add(paperData);
		}

		reader.close();

		return paperDataList;
	}

	private ObjectMapper getObjectMapper() {

		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper;
	}
}
