package com.example.demo.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class Reniec implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String dni;
    private String nombre;
    private String apPaterno;
    private String apMaterno;

}
