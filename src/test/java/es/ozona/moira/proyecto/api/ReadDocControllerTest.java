package es.ozona.moira.proyecto.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.ozona.moira.proyecto.api.controller.ReadDocController;

@SpringBootTest
public class ReadDocControllerTest {
	@Autowired
	ReadDocController readDocController;
	
	ReadDocTestUtilities utilities;
	
	File resources;
	File data;
	String encodedString;
	
	@BeforeEach
	public void init() throws IOException {
		utilities = new ReadDocTestUtilities();
		
		String pathResources = "src\\test\\resources";
		resources = new File(pathResources);

		data = new File(pathResources, "Data.pdf");
		
		byte[] inputByteArray = Files.readAllBytes(Paths.get(data.getPath()));
        encodedString = Base64.getEncoder().encodeToString(inputByteArray);
	}
	
	@Test
	public void readDocString() throws FileNotFoundException, IOException, ParseException {
		Map<String, Object> fields = readDocController.readDoc(encodedString).getBody();
		utilities.assertMaps(fields);
	}
	
	@Test
	public void readDocStream() throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(data);
		Map<String, Object> fields = readDocController.readDoc(stream).getBody();
	}
}
