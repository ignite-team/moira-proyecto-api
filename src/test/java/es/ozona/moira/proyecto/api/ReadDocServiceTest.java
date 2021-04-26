package es.ozona.moira.proyecto.api;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.ozona.moira.proyecto.api.services.ReadDocService;


@SpringBootTest
public class ReadDocServiceTest {
	
	@Autowired
	ReadDocService docService;
	
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
	public void readEncodedString() throws ParseException, IOException, org.json.simple.parser.ParseException {
		Map<String, Object> fieldValues = docService.readPdfForm(encodedString);
		utilities.assertMaps(fieldValues);
	}
	
	@Test
	public void readStream() throws IOException, org.json.simple.parser.ParseException {
		FileInputStream stream = new FileInputStream(data);
		Map<String, Object> fields = docService.readPdfForm(stream);
		utilities.assertMaps(fields);
	}
	
	
}
