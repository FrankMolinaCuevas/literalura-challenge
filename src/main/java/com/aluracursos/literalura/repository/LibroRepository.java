package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // <--- ¡No olvides esta línea!

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Esta es la "Derived Query". Spring construye el SQL solito:
    // SELECT * FROM libros WHERE idioma = ?
    List<Libro> findByIdioma(String idioma);
}