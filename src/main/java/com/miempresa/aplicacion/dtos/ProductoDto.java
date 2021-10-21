package com.miempresa.aplicacion.dtos;


import java.sql.Date;
import lombok.Data;


@Data
public class ProductoDto {
    
    private String codigoProducto;
    private String nombreProducto;
    
    private String descripcionProducto;
    private Double precioProducto;
    
}
