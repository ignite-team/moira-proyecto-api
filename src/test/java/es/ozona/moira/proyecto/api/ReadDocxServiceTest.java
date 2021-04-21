package es.ozona.moira.proyecto.api;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.File;
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
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.ozona.moira.proyecto.api.services.ReadDocxService;

@SpringBootTest
public class ReadDocxServiceTest {
	
	@Autowired
	ReadDocxService docxService;
	
	File resources;
	File data;
	String encodedString;
	
	@BeforeEach
	public void init() throws IOException {
		String pathResources = "src\\test\\resources";
		resources = new File(pathResources);

		data = new File(pathResources, "data.docx");
		
		byte[] inputByteArray = Files.readAllBytes(Paths.get(data.getPath()));
        encodedString = Base64.getEncoder().encodeToString(inputByteArray);
	}
	
	@Test
	public void readTable1() throws ParseException, IOException, org.json.simple.parser.ParseException {
		JSONObject json = docxService.readEncodedDocx(encodedString);
		
		JSONParser parser = new JSONParser();
		JSONObject testData = (JSONObject) parser.parse(new FileReader("src\\test\\resources\\table1.json"));
		
		Map<String,String> jsonMap = new ObjectMapper().readValue(json.toJSONString(), HashMap.class);
		Map<String,String> testDataMap = new ObjectMapper().readValue(testData.toJSONString(), HashMap.class);
		
		jsonMap.forEach((k,v) -> System.out.println("jsonMap: key-" + k + ", value-" + v));
		testDataMap.forEach((k,v) -> System.out.println("jsonMap: key-" + k + ", value-" + v));
		
		assertTrue(containsAll(jsonMap, testDataMap));
	}
	
	private boolean containsAll(Map<String,String> jsonMap, Map<String,String> testDataMap) {
		for (Entry<String, String> entry : testDataMap.entrySet()) {
		    if(!jsonMap.get(entry.getKey()).equals(entry.getValue())) {
		    	return false;
		    }
		}
		return true;
	}
}
