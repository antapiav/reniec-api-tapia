package com.example.demo.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class GenericResponseHeader implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer pagina;
	private Long total;
	private BigDecimal importePagina;
	private BigDecimal importeTotal;
	private Long totalCorrectos;
	private Long totalAnulados;
	private String numeroLiquidacion;
    
}