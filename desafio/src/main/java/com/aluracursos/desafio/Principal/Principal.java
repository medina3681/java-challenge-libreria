package com.aluracursos.desafio.Principal;

import com.aluracursos.desafio.Repository.LibroRepository;
import com.aluracursos.desafio.model.*;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI=new ConsumoAPI();
    private ConvierteDatos conversor =new ConvierteDatos();
    private Scanner teclado=new Scanner(System.in);
    private String json;
    private LibroRepository repositorio;
    private List<Libro> libros;




    public Principal(LibroRepository repository) {
        this.repositorio=repository;//un vez hecho esto ya se puede tener acceso a los metodos de la interfaz repositorio

    }



    public void muestraElMenu (){

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                1 - Búsqueda de libro por título
                2 - Lista de todos los libros
                3 - Lista de autores
                4 - Listar autores vivos en determinado año
                5 - Listar libros por idioma

                0 - Salir
                    """;

            System.out.println(menu);
            var opcionS=teclado.nextLine();

            while(opcionS.chars().anyMatch(c -> !Character.isDigit(c))){
                //si contiene un caracter que no es digito se pide de nuevo la entrada
                System.out.print("Solo puedes escribir numeros. \n Elige una opcion \n ");

                System.out.println(menu);
                opcionS = teclado.nextLine();
            }

            opcion = Integer.valueOf(opcionS);

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarTodosLosLibros();
                    break;
                case 3:
                    listaDeAutores();
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
                    System.out.println("Opcion no valida");
            }
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

//        json=consumoAPI.obtenerDatos(URL_BASE);
//
//        System.out.println(json);
//
//        var datos=conversor.obtenerDatos(json, Datos.class);
//
//        System.out.println(datos);

///////agregar el listado de libros mas descargads
//        System.out.println("Libros mas descargados");
//
//        datos.resultados().stream()
//                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
//                .limit(10)
//                .map(l ->l.titulo().toUpperCase())
//                .forEach(System.out::println);





        ///trabajndo con estadisticas
      //  DoubleSummaryStatistics est=datosBusqueda.resultados().stream()
            //    .filter(d -> d.numeroDeDescargas() >0)
            //    .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));

      //  System.out.println("Cantidad de descargas "+ est.getAverage());
       // System.out.println("MAximo de descargas "+ est.getMax());
      //  System.out.println("Minimo de descargas "+est.getMin());
    //    System.out.println("Cantidad de registros evaluados "+ est.getCount());

/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void listarLibrosPorIdioma() {


        System.out.println("Selecciona el idioma ");
        List<String> idiomas=repositorio.idiomas();
        System.out.println(idiomas);
        var tituloLibro=teclado.nextLine();

        List<Libro> l=repositorio.librosPorIdioma(tituloLibro);

        l.forEach(a->
                System.out.printf("Titulo: %s - Autor:  %s - Idioma: %s Numero de Descargas: %s \n ",
                        a.getTitulo(), a.getNombre(), a.getIdiomas(), a.getNumeroDeDescargas()));
    }

    private void listarAutoresVivos()   {
        System.out.println("Indica el año ");

        String titulo=teclado.nextLine();

        while(titulo.chars().anyMatch(c -> !Character.isDigit(c))){
            //si contiene un caracter que no es digito se pide de nuevo la entrada
            System.out.print("Solo puedes escribir numeros ");
            titulo = teclado.nextLine();
        }

        Integer tituloLibro=Integer.valueOf(titulo);

        List<Libro> l=repositorio.autoresVivos(tituloLibro)
                .stream()
                .distinct()
                .collect(Collectors.toList());

        List<String> l2=new ArrayList<>();

        l.stream().forEach(a-> l2.add(
                "Autor "+a.getNombre()+
                " Año de Nacimiento "+ a.getFechaDeNacimiento() +
                " Año de defuncion "+ a.getFechaDeDefuncion()));

        System.out.println("AUTORES VIVOS ANTES DE  "+tituloLibro);

        l2.stream().distinct().forEach(a-> System.out.println(a));

    }

    private void buscarLibroPorTitulo() {
        //busqueda de libro por nombre
        System.out.println("Ingresa el titulo del libro buscado");
        var tituloLibro=teclado.nextLine();

        json=consumoAPI.obtenerDatos(URL_BASE+"?search="+tituloLibro.replace(" ","%20"));
        var datosBusqueda=conversor.obtenerDatos(json, Datos.class);
        System.out.println("JSON " + json);



        System.out.println("Datos libro "+datosBusqueda);

        Optional<DatosLibros> libroBuscado=datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

//        for(int i=1;i<=100; i++){
//            DatosLibros dl=datosBusqueda.resultados().get(i);
//
//            Optional<DatosAutor> da=dl.autores().stream().findFirst();
//
//            Libro l2=new Libro(dl, da.get());
//            repositorio.save(l2);
//
//        }

        if (libroBuscado.isPresent()){
            var libroEncontrado=libroBuscado.get();
            System.out.println("Libro encontrado "+libroEncontrado);
            Optional<DatosAutor> autor=libroEncontrado.autores().stream().findFirst();
            Autor a=new Autor(autor.get());
            Libro libro=new Libro(libroEncontrado, autor.get());
            //a.setLibros(todosLosLibros);

            System.out.println("Autor "+a);
            System.out.println("Libro "+libro);

            List<Libro>buscaLibroEnBD=repositorio.libroYaIngresado(libro.getTitulo());

            if (buscaLibroEnBD.isEmpty()){
                repositorio.save(libro);
            }else{
                buscaLibroEnBD.forEach(l->
                        System.out.printf("Titulo: %s - Autor:  %s - Idioma: %s - Numero de Descargas: %s \n ",
                                l.getTitulo(), l.getNombre(), l.getIdiomas(), l.getNumeroDeDescargas()));;
            }

        }else{
            System.out.println("Libro no encontrado");
        }
    }

    private void listarTodosLosLibros(){
        libros=repositorio.findAll();

        libros.forEach(System.out::println);

        libros.forEach(l->
                System.out.printf("Titulo: %s - Autor:  %s - Idioma: %s - Numero de Descargas: %s \n ",
                        l.getTitulo(), l.getNombre(), l.getIdiomas(), l.getNumeroDeDescargas()));



    }

    private void listaDeAutores(){


        List<String> s=new ArrayList<>();

        s=repositorio.nombresDistintos();

        System.out.println(s);

        System.out.println("Autores en BD \n");
        s.forEach(l-> System.out.printf("Autor: %s \n", l));


    }


}
