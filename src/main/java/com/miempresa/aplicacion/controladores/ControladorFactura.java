package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.dtos.FacturaDto;
import com.miempresa.aplicacion.dtos.ProductoDto;
import com.miempresa.aplicacion.modelos.RepositorioFactura;
import com.miempresa.aplicacion.modelos.Factura;
import com.miempresa.aplicacion.modelos.Producto;
import com.miempresa.aplicacion.modelos.RepositorioProducto;
import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
import java.sql.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControladorFactura {
    
    private final RepositorioFactura repositorioFactura;
    private final RepositorioProducto repositorioProducto;
    private final RepositorioVendedor repositorioVendedor;
    
    @GetMapping("/facturas") //path del controlador
    public String getTodasLasFacturas(Model model){
        Iterable<Factura> facturas = repositorioFactura.findAll();
        model.addAttribute("facturas",facturas);
        return "vistaFactura";
    }    
    
    @GetMapping("/facturas/{numeroFactura}") //path del controlador
    public String getFacturaByNumero(@PathVariable String numeroFactura, Model model){
        Factura factura = repositorioFactura.findByNumeroFactura(numeroFactura);
        model.addAttribute("facturas",factura);
        return "vistaFactura";
    }
    
    @GetMapping("/crear/factura") //path del controlador
    public String crearFactura(Model model){
        
        model.addAttribute("factura",new FacturaDto());
        return "vistaCrearFactura";
    }   
      

    @PostMapping("/crear/factura")
    public RedirectView procesarProducto(@ModelAttribute FacturaDto facturaDto){
       
       Vendedor vendedor = repositorioVendedor.findByCodVendedor(facturaDto.getCodigoVendedor());
       Producto producto = repositorioProducto.findByCodProducto(facturaDto.getCodigoProducto());
       Factura factura = new Factura();
       factura.setNumeroFactura(facturaDto.getNumeroFactura());
       factura.setProducto(producto);
       factura.setVendedor(vendedor);
       factura.setFechaVenta(facturaDto.getFechaVenta());
       factura.setValorFactura(facturaDto.getValorFactura());
       
       Factura facturaGuardada = repositorioFactura.save(factura);
       if (facturaGuardada == null){
           return new RedirectView("/crear/factura/",true);
       }
       return new RedirectView("/facturas/"+facturaGuardada.getNumeroFactura(),true);
       
    }
 //Actualizar
     @GetMapping("/actualizar/factura")//path del controlador
     public String actualizarFactura(Model model) {

        model.addAttribute("factura", new FacturaDto());
        return "vistaActualizarFactura";
    }
     @PostMapping("/actualizar/factura")
    public RedirectView procesarFactua (@ModelAttribute FacturaDto facturaDto) {
        Vendedor vendedor = repositorioVendedor.findByCodVendedor(facturaDto.getCodigoVendedor());
        Producto producto = repositorioProducto.findByCodProducto(facturaDto.getCodigoProducto());
      
        Factura factura = new Factura();
        //boolean existfactura = repositorioFactura.findByNumeroFactura(FacturaDto.getNumeroFactura());

        //if (existfactura == true) {
            //seteo de los datos capturados en la vista
            factura.setNumeroFactura(facturaDto.getNumeroFactura());
            factura.setFechaVenta(facturaDto.getFechaVenta());
            factura.setProducto(producto);
            factura.setVendedor(vendedor);
            factura.setValorFactura(facturaDto.getValorFactura());

            Factura facturaActualizada = repositorioFactura.save(factura);
            if (facturaActualizada == null) {
                return new RedirectView("/actualizar/factura/", true);
            }

            return new RedirectView("/facturas/" + facturaActualizada.getNumeroFactura(), true);

        } // else {
            //return new RedirectView("/actualizar/factura/", true);
       // }
    }

