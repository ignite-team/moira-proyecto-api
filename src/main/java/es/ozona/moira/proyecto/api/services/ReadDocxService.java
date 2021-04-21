package es.ozona.moira.proyecto.api.services;

import java.io.IOException;

import org.json.simple.JSONObject;

public interface ReadDocxService {
	public JSONObject readEncodedDocx(String encodedFile) throws IOException;
}
