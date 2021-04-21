package es.ozona.moira.proyecto.api.services;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ReadDocxServiceImpl implements ReadDocxService{
	
	public JSONObject readEncodedDocx(String encodedFile) throws IOException {
		InputStream docx = decodeFileToInputStream(encodedFile);
		
		XWPFDocument doc = new XWPFDocument(docx);
		List<XWPFTable> tables = doc.getTables();
		Map<String, String> data = new HashMap<>();
		
		readTable1(tables.get(0), data);
		//readTable2(tables.get(1), data);
		
		return new JSONObject(data);
	}
	
	private InputStream decodeFileToInputStream(String  encodedFile) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedFile);
		InputStream stream =  new ByteArrayInputStream(decodedBytes);
		return stream;
	}
	
	private void readTable1(XWPFTable table, Map<String, String> data) {
		data.put("fechaRegistro", table.getRow(2).getCell(1).getText().trim());
		data.put("registrante", table.getRow(3).getCell(1).getText().trim());
		data.put("registranteEmail", table.getRow(4).getCell(1).getText().trim());
	}
	
	private void readTable2(XWPFTable table, Map<String, String> data) {
		
	}
	
}
