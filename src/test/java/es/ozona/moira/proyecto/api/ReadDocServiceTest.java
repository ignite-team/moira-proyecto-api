package es.ozona.moira.proyecto.api;

import static org.junit.Assert.assertTrue;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.ozona.moira.proyecto.api.services.ReadDocService;


@SpringBootTest
public class ReadDocServiceTest {
	
	@Autowired
	ReadDocService docxService;
	
	File resources;
	File data;
	String encodedString;
	
	@BeforeEach
	public void init() throws IOException {
		String pathResources = "src\\test\\resources";
		resources = new File(pathResources);

		data = new File(pathResources, "Data.pdf");
		
		byte[] inputByteArray = Files.readAllBytes(Paths.get(data.getPath()));
        encodedString = Base64.getEncoder().encodeToString(inputByteArray);
	}
	
	@Test
	public void readTable1() throws ParseException, IOException, org.json.simple.parser.ParseException {
		Map<String, Object> fieldValues = docxService.readEncodedDoc(encodedString);
		
		JSONParser parser = new JSONParser();
		JSONObject testData = (JSONObject) parser.parse(new FileReader("src\\test\\resources\\table1.json"));
		Map<String,Object> testDataMap = new ObjectMapper().readValue(testData.toJSONString(), HashMap.class);
		
		fieldValues.forEach((k,v) -> System.out.println("key:"+ k + ", value:" + v));
		assertTrue(containsAll(fieldValues, testDataMap));
	}
	
	private boolean containsAll(Map<String,Object> jsonMap, Map<String,Object> testDataMap) {
		for (Entry<String, Object> entry : testDataMap.entrySet()) {
		    if(!jsonMap.get(entry.getKey()).toString().equals(entry.getValue().toString())) {
		    	return false;
		    }
		}
		return true;
	}
}
