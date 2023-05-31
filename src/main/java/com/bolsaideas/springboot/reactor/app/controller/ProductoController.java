package com.bolsaideas.springboot.reactor.app.controller;

import com.bolsaideas.springboot.reactor.app.models.dao.ProductoDao;
import com.bolsaideas.springboot.reactor.app.models.documents.Producto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
