package com.aluracursos.literalura.model;

import com.aluracursos.literalura.model.DatosAutor;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();

        // Manejo de fecha de nacimiento
        if (datosAutor.fechaDeNacimiento() != null) {
            this.fechaDeNacimiento = Integer.valueOf(datosAutor.fechaDeNacimiento());
        } else {
            this.fechaDeNacimiento = 0;
        }

        // Manejo de fecha de fallecimiento
        if (datosAutor.fechaDeFallecimiento() != null) {
            this.fechaDeFallecimiento = Integer.valueOf(datosAutor.fechaDeFallecimiento());
        } else {
            this.fechaDeFallecimiento = 0;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
      
        String librosNombres = (libros != null) ?
                libros.stream().map(Libro::getTitulo).collect(Collectors.joining(", ")) : "Ninguno";

        return "---------- AUTOR ----------\n" +
                "Nombre: " + nombre + "\n" +
                "Fecha de Nacimiento: " + fechaDeNacimiento + "\n" +
                "Fecha de Fallecimiento: " + fechaDeFallecimiento + "\n" +
                "Libros: [" + librosNombres + "]\n" +
                "---------------------------";
    }
}
