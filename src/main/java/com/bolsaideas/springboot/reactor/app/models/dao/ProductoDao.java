package com.bolsaideas.springboot.reactor.app.models.dao;

import com.bolsaideas.springboot.reactor.app.models.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {

}
