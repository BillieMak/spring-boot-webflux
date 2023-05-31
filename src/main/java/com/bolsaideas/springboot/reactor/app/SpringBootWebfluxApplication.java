package com.bolsaideas.springboot.reactor.app;

import com.bolsaideas.springboot.reactor.app.models.dao.ProductoDao;
import com.bolsaideas.springboot.reactor.app.models.documents.Producto;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Log4j2
public class SpringBootWebfluxApplication implements CommandLineRunner {

	@Autowired
	private ProductoDao dao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.just(
				new Producto("TV Panasonic Pantalla LCD", 456.89),
				new Producto("Sony Camara HD Digital", 177.89),
				new Producto("Apple iPod", 46.89),
				new Producto("Sony Notebook", 846.89),
				new Producto("Hewlett Packard Multifuncional", 200.89),
				new Producto("Bianchi Bicicleta", 70.89),
				new Producto("HP Notebook Omen 17", 2500.89),
				new Producto("Mica Cómoda 5 Cajones", 150.89),
				new Producto("TV Sony Bravia OLED 4K Ultra HD", 2255.89)
				)
				.flatMap(producto ->
					dao.save(producto)
				).subscribe(
						p -> log.info(p.toString())
				);
	}
}
