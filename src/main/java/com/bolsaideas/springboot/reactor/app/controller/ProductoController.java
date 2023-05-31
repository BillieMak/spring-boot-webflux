package com.bolsaideas.springboot.reactor.app.controller;

import com.bolsaideas.springboot.reactor.app.models.dao.ProductoDao;
import com.bolsaideas.springboot.reactor.app.models.documents.Producto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoDao dao;

    @GetMapping
    public Flux<Producto> index() {
        Flux<Producto> productos = dao.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        });
        return productos;
    }
    @GetMapping("/{id}")
    public Mono<Producto> show(@PathVariable String id){

        /* Mono<Producto> producto = dao.findById(id); */

        Flux<Producto> productos = dao.findAll();

        Mono<Producto> producto = productos
                .filter(p -> p.getId().equals(id))
                .next()
                .doOnNext(prod -> log.info(prod.getNombre()));

        return producto;
    }

    @PutMapping("/{id}")
    public Mono<Producto> update(@RequestBody Producto producto, @PathVariable String id){
        return dao.findById(id)
                .flatMap(p -> {
                    p.setNombre(producto.getNombre());
                    p.setPrecio(producto.getPrecio());
                    return dao.save(p);
                });
    }

    @PostMapping
    public Mono<Producto> save(@RequestBody Producto producto){
        return dao.save(producto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return dao.findById(id)
                .flatMap(p -> dao.delete(p));
    }
}
