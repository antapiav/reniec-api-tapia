package com.example.demo.controller;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.jsoup.Connection;

import com.example.demo.bean.GenericResponse;
import com.example.demo.bean.Reniec;

@CrossOrigin
@RestController
@RequestMapping(value = "/consulta")
public class ConsultaDocumentoController {
    private static final Logger log = LoggerFactory.getLogger(ConsultaDocumentoController.class);

    @GetMapping(value = "/reniec/{numDocument}")
    public GenericResponse<Reniec> getDocumnetReniec(@PathVariable("numDocument") String numDocument){
    	GenericResponse<Reniec> response = consultaDocumentoReniec(numDocument);
        response.setMessage("ok");
        return response;
    }
    
    public GenericResponse<Reniec> consultaDocumentoReniec(String numDocumento) {
		Connection pageHTML = null;
		Integer statusCode = null;
		String mensaje = null;
	    Reniec reniec = new Reniec();
	    StringBuilder urlReniec = new StringBuilder(); 
	    urlReniec.append("https://eldni.com/buscar-por-dni?dni=");
	    urlReniec.append(numDocumento);
	    try {
	    	pageHTML = Jsoup.connect(urlReniec.toString())
	    			.userAgent("Mozilla/5.0")
	    			.timeout(60000)
	    			.ignoreHttpErrors(true);
	    	statusCode = pageHTML.execute().statusCode();

	    	if(statusCode == 200) {
	    		String tblDatosReniec = pageHTML.get().getElementsByClass("table table-striped").outerHtml();
			    Document docHTMLFinal = Jsoup.parse(tblDatosReniec);
			    Elements elements = docHTMLFinal.getElementsByClass("text-left");
		    	if(elements.size() > 0) {
		    		reniec.setDni(numDocumento);
		    		reniec.setNombre((elements.get(0).text()!=null)?elements.get(0).text():"");
			    	reniec.setApPaterno((elements.get(1).text()!=null)?elements.get(1).text():"");
			    	reniec.setApMaterno((elements.get(2).text()!=null)?elements.get(2).text():"");
		    	}else {
		    		statusCode = 0;
		    	}
	    	}else {
	    		log.info("Pagina no existe");
	    	}

		} catch (IOException e) {
			log.info("Error en consulta RENEIC");
		}
	    
	    GenericResponse<Reniec> ress = new GenericResponse<>();
	    ress.setMessage(mensaje);
		ress.setCode(statusCode);
        ress.setBody(reniec);
		return ress;
	}

}
