package com.miempresa.aplicacion.controladores;

import com.miempresa.aplicacion.dtos.ProductoDto;
import com.miempresa.aplicacion.modelos.Producto;
import com.miempresa.aplicacion.modelos.RepositorioProducto;
import java.util.ArrayList;
import java.util.List;


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
public class ControladorProducto {
    
    private final RepositorioProducto repositorioProducto;
    
    @GetMapping("/productos") //path del controlador
    public String getTodosLosProductos(Model model){
        Iterable<Producto> productos = repositorioProducto.findAll();
        model.addAttribute("productos",productos);
        return "vistaProducto";
    }    
    
    @GetMapping("/productos/{codigoProducto}") //path del controlador
    public String getProductoById(@PathVariable String codigoProducto, Model model){
        List<String> listaProducto = new ArrayList<>();
        listaProducto.add(codigoProducto);
        Iterable<Producto> productos = repositorioProducto.findAllById(listaProducto);
        model.addAttribute("productos",productos);
        return "vistaProducto";
    }
    
    // Crear
 
    @GetMapping("/crear/producto") //path del controlador
    public String crearProducto(Model model){
        
        model.addAttribute("producto",new ProductoDto());
        return "vistaCrearProducto";
    }   
    
    @PostMapping("/crear/producto")
    public RedirectView procesarProducto(@ModelAttribute ProductoDto productoDto){
        Producto producto = new Producto();
        boolean existproducto = repositorioProducto.existsById(productoDto.getCodigoProducto());
        
        if (existproducto == false){
            //seteo de los datos capturados en la vista
            producto.setCodProducto(productoDto.getCodigoProducto());
            producto.setDescripcionProducto(productoDto.getDescripcionProducto());
            producto.setNombreProducto(productoDto.getNombreProducto());
            producto.setPrecioProducto(productoDto.getPrecioProducto()); 

            Producto productoGuardado = repositorioProducto.save(producto);
            if (productoGuardado == null){
                 return new RedirectView("/crear/producto/",true);
             }

            return new RedirectView("/productos/"+productoGuardado.getCodProducto(),true);
           
       }else{
        return new RedirectView("/crear/producto/",true);
        }       
    }    
    
    //Actualizar
    
    @GetMapping("/actualizar/producto/{codigoProducto}") //path del controlador
     public String actualizarProducto(
            @PathVariable String codigoProducto,
            Model model){
           
        
        Producto producto = repositorioProducto.findByCodProducto(codigoProducto);
        ProductoDto productoDto = new ProductoDto();
        productoDto.setCodigoProducto(producto.getCodProducto());
        productoDto.setNombreProducto(producto.getNombreProducto());
        productoDto.setDescripcionProducto(producto.getDescripcionProducto());
        productoDto.setPrecioProducto(producto.getPrecioProducto());

        model.addAttribute("producto", productoDto);
        return "vistaActualizarProducto";
    }

    @PostMapping("/actualizar/producto")
    public RedirectView aprocesarProducto(@ModelAttribute ProductoDto productoDto) {
        Producto producto = new Producto();
        boolean existproducto = repositorioProducto.existsById(productoDto.getCodigoProducto());

        if (existproducto == true) {
            //seteo de los datos capturados en la vista
            producto.setCodProducto(productoDto.getCodigoProducto());
            producto.setDescripcionProducto(productoDto.getDescripcionProducto());
            producto.setNombreProducto(productoDto.getNombreProducto());
            producto.setPrecioProducto(productoDto.getPrecioProducto());

            Producto productoActualizado = repositorioProducto.save(producto);
            if (productoActualizado == null) {
                return new RedirectView("/actualizar/producto/", true);
            }

            return new RedirectView("/productos/" + productoActualizado.getCodProducto(), true);

        } else {
            return new RedirectView("/actualizar/producto/", true);
        }
    }
    @GetMapping("/eliminar/producto/{codigoProducto}") //path del controlador
    public RedirectView eliminarProducto(@PathVariable String codigoProducto) {
        Producto t = repositorioProducto.findByCodProducto(codigoProducto);
        repositorioProducto.delete(t);
        
        return new RedirectView("/productos", true);
    }
}
