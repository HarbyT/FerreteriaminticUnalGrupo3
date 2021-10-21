package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.dtos.VendedorDto1;
import com.miempresa.aplicacion.modelos.RepositorioVendedor;
import com.miempresa.aplicacion.modelos.Vendedor;
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
public class ControladorVendedor {
    
    private final RepositorioVendedor repositorioVendedor;
    
    @GetMapping("/vendedores") //path del controlador
    public String getTodosLosVendedores(Model model){
        Iterable<Vendedor> vendedores = repositorioVendedor.findAll();
        model.addAttribute("vendedores",vendedores);
        return "vistaVendedor";
    }    
    
    @GetMapping("/vendedores/{codigoVendedor}") //path del controlador
    public String getVendedorById(@PathVariable String codigoVendedor, Model model){
        Vendedor vendedores = repositorioVendedor.findByCodVendedor(codigoVendedor);
        model.addAttribute("vendedores",vendedores);
        return "vistaVendedor";
    }
    
    @GetMapping("/crear/vendedor") //path del controlador
    public String crearVendedor(Model model){
        
        model.addAttribute("vendedor",new Vendedor());
        return "vistaCrearVendedor";
    }   
    
    @PostMapping("/crear/vendedor")
    public RedirectView procesarVendedor(@ModelAttribute Vendedor vendedor){
       Vendedor vendedorGuardado = repositorioVendedor.save(vendedor);
       if (vendedorGuardado == null){
           return new RedirectView("/crear/vendedor/",true);
       }
       return new RedirectView("/vendedores/"+vendedorGuardado.getCodVendedor(),true);
    
       
       
    }
    
    //Actualizar
    
    @GetMapping("/actualizar/vendedor/{codigoVendedor}") //path del controlador
    public String actualizarVendedor(
            @PathVariable String codigoVendedor,
            Model model) {
        
        Vendedor vendedor = repositorioVendedor.findByCodVendedor(codigoVendedor);
        VendedorDto1 vendedorDto1 = new VendedorDto1();
        vendedorDto1.setCodigoVendedor(vendedor.getCodVendedor());
        vendedorDto1.setNombreVendedor(vendedor.getNombreVendedor());

        model.addAttribute("vendedor", vendedorDto1);
        return "vistaActualizarVendedor";
    }

    @PostMapping("/actualizar/vendedor")
    public RedirectView vendedores(@ModelAttribute VendedorDto1 vendedorDto1) {
        Vendedor vendedor = new Vendedor();
        boolean existvendedor = repositorioVendedor.existsById(vendedorDto1.getCodigoVendedor());

        if (existvendedor == true) {
            //seteo de los datos capturados en la vista
            vendedor.setCodVendedor(vendedorDto1.getCodigoVendedor());
            vendedor.setNombreVendedor(vendedorDto1.getNombreVendedor());

            Vendedor vendedorActualizado = repositorioVendedor.save(vendedor);
            if (vendedorActualizado == null) {
                return new RedirectView("/actualizar/vendedor/", true);
            }

            return new RedirectView("/vendedores/" + vendedorActualizado.getCodVendedor(), true);

        } else {
            return new RedirectView("/vendedores", true);
        }
    }
    @GetMapping("/eliminar/vendedor/{codigoVendedor}") //path del controlador
    public RedirectView eliminarVendedor(@PathVariable String codigoVendedor) {
        Vendedor t = repositorioVendedor.findByCodVendedor(codigoVendedor);
        repositorioVendedor.delete(t);  
        
        return new RedirectView("/vendedores", true);
    
        }
}