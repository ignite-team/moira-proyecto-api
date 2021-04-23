package es.ozona.moira.proyecto.api.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import es.ozona.moira.proyecto.api.services.exceptions.ReadDocException;

@Service
public class ReadDocServiceImpl implements ReadDocService{
	
	public Map<String, Object> readEncodedDoc(String encodedFile) {
		InputStream form = decodeFileToInputStream(encodedFile);
        return readEncodedDoc(form);
	}
	
	public Map<String, Object> readEncodedDoc(InputStream form){
		Map<String, Object> fieldValues = new HashMap<>();
		
		PDDocument pdDoc = null;
		try {
			pdDoc = PDDocument.load(form, MemoryUsageSetting.setupMainMemoryOnly());
		} catch (IOException e1) {
			throw new ReadDocException("No se pudo cargar el documento", e1);
		}
		PDDocumentCatalog pdCatalog = pdDoc.getDocumentCatalog();
		PDAcroForm pdAcroForm = pdCatalog.getAcroForm();

		// get all fields in form
		List<PDField> fields = pdAcroForm.getFields();

		// inspect field values
		for (PDField field : fields) {
			// processField(field, "|--", field.getPartialName());
			fieldValues.put(field.getPartialName(), field.getValueAsString());
		}

		try {
			form.close();
		} catch (IOException e) {
			throw new ReadDocException("Fallo cerrando el inputStream", e);
		}
		
        return fieldValues;
	}
	
	private InputStream decodeFileToInputStream(String  encodedFile) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedFile);
		InputStream stream =  new ByteArrayInputStream(decodedBytes);
		return stream;
	}
	
}
