package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private LibroRepository repositorio;
    private AutorRepository autorRepository; // Unificado sin la 'o' final

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ********************************
                    1 - Buscar libro por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ********************************
                    """;
            System.out.println(menu);

            if (lectura.hasNextInt()) {
                opcion = lectura.nextInt();
                lectura.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroWeb();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresVivos();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } else {
                System.out.println("Formato inválido, ingresa un número.");
                lectura.nextLine();
            }
        }
    }

    private Datos getDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var nombreLibro = lectura.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        return conversor.obtenerDatos(json, Datos.class);
    }

    private void buscarLibroWeb() {
        Datos datosBusqueda = getDatosLibro();
        if (datosBusqueda != null && !datosBusqueda.resultados().isEmpty()) {
            DatosLibro datosLibro = datosBusqueda.resultados().get(0);

            DatosAutor datosAutor = datosLibro.autor().get(0);
            // Corregido a autorRepository
            Optional<Autor> autorOpcional = autorRepository.findByNombreIgnoreCase(datosAutor.nombre());

            Autor autor;
            if (autorOpcional.isPresent()) {
                autor = autorOpcional.get();
            } else {
                autor = new Autor(datosAutor);
                autorRepository.save(autor);
            }

            Libro libro = new Libro(datosLibro);
            libro.setAutor(autor);

            try {
                repositorio.save(libro);
                System.out.println(libro);
            } catch (Exception e) {
                System.out.println("Error: El libro ya está registrado.");
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = repositorio.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);
    }

    private void listarAutores() {
        System.out.println("--- AUTORES REGISTRADOS ---");
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año que desea consultar:");
        try {
            var anio = Integer.valueOf(lectura.nextLine());
            List<Autor> autoresVivos = autorRepository.buscarAutoresVivosEnAnio(anio);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio + " en la base de datos.");
            } else {
                System.out.println("--- AUTORES VIVOS EN " + anio + " ---");
                autoresVivos.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese un número de año válido.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
            Ingrese el idioma para buscar los libros:
            es - español
            en - inglés
            fr - francés
            pt - portugués
            """);
        var idioma = lectura.nextLine();
        List<Libro> libros = repositorio.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma en la base de datos.");
        } else {
            long cantidad = libros.stream().count();
            System.out.println("--- RESULTADOS ---");
            System.out.println("Cantidad de libros encontrados: " + cantidad);
            System.out.println("------------------");
            libros.forEach(System.out::println);
        }
    }
}