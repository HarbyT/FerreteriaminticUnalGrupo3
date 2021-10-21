package com.miempresa.aplicacion.modelos;

import org.springframework.data.repository.CrudRepository;

public interface RepositorioFactura extends CrudRepository<Factura,Long> {
    Factura findByNumeroFactura(String numeroFactura);
    
}
