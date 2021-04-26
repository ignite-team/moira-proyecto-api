package es.ozona.moira.proyecto.api;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadDocTestUtilities {
	
	public void assertMaps(Map<String, Object> fields) throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {
		JSONParser parser = new JSONParser();
		JSONObject testData = (JSONObject) parser.parse(new FileReader("src\\test\\resources\\data.json"));
		Map<String,Object> testDataMap = new ObjectMapper().readValue(testData.toJSONString(), HashMap.class);
		assertTrue(containsAll(fields, testDataMap));
	}
	
	public boolean containsAll(Map<String,Object> jsonMap, Map<String,Object> testDataMap) {
		
		for (Entry<String, Object> entry : testDataMap.entrySet()) {
		    if(!jsonMap.get(entry.getKey()).toString().equals(entry.getValue().toString())) {
		    	return false;
		    }
		}
		return true;
	}
}
