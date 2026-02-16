package com.aluracursos.literalura;

import com.aluracursos.literalura.principal.Principal; // Este es el nuevo import clave
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository repository;

    @Autowired // Agrega esto
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Ahora le pasamos AMBOS repositorios a la clase Principal
        Principal principal = new Principal(repository, autorRepository);
        principal.muestraElMenu();
    }
}