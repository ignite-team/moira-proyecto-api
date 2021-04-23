package es.ozona.moira.proyecto.api.controller;

import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.ozona.moira.proyecto.api.services.ReadDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/apiProyecto/v1")
@Api(value = "Template proyecto API.", tags = "Proyecto API.")
@SwaggerDefinition(tags = { @Tag(name = "Proyecto API.", description = "Commands to read and process form documents.") })
public class ReadDocController {
	
	@Autowired
	private ReadDocService readDocService;
	
	@GetMapping("/proyecto/readDoc/stringBase64")
	@ResponseStatus(code = HttpStatus.OK)
	@ResponseBody
	@ApiOperation(value= "Read form document using a encodedString")
	public ResponseEntity<Map<String, Object>> readDoc(@ApiParam(required=true, value="Document encoded on String base64") @RequestBody String encodedString){
		return ResponseEntity.ok(readDocService.readEncodedDoc(encodedString));
	}
	
	@GetMapping("/proyecto/readDoc/stream")
	@ResponseStatus(code = HttpStatus.OK)
	@ResponseBody
	@ApiOperation(value= "Read form document using a stream")
	public ResponseEntity<Map<String, Object>> readDoc(@ApiParam(required=true, value="Stream of the form") @RequestBody InputStream form){
		return ResponseEntity.ok(readDocService.readEncodedDoc(form));
	}
}
