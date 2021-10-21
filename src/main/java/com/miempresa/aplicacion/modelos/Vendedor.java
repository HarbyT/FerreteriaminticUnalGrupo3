package com.miempresa.aplicacion.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity 
@Table(name="t_vendedores")
public class Vendedor {
    @Getter
    @Setter @Id @Column(name = "cod_vendedor") 
    private String codVendedor;
    @Getter 
    @Setter @Column(name = "nombre_vendedor")  
    private String nombreVendedor;
}
