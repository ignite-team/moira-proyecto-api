package es.ozona.moira.proyecto.api.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.json.simple.JSONObject;

public interface ReadDocService {
	public Map<String,Object> readEncodedDoc(String encodedFile);
	public Map<String,Object> readEncodedDoc(InputStream form);
}
