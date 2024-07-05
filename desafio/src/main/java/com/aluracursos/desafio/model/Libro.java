package com.aluracursos.desafio.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table (name="libros")

public class Libro {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)//autoincremetnable
   private Long Id;

   @Column(unique = true)///no permite nombres duplicados
   private String titulo;

  // private List <String> idiomas;
  private String idiomas;
   private Double numeroDeDescargas;

    private String nombre;

    private Integer fechaDeNacimiento;
    private Integer fechaDeDefuncion;


   //@Transient //existe la lista autores
  ////  @ManyToOne///genera la relacion entre las dos tablas, series y episodios
   // @JoinColumn(name="autor_id")
    //private DatosAutor autor;
    //private Autor autor;

    public Libro(){}


    public Libro(DatosLibros datosLibros, DatosAutor autor) {
        this.titulo= datosLibros.titulo();

        Optional<String> i=datosLibros.idiomas().stream().findFirst();

        if(i.isPresent()){
            this.idiomas=i.get();
        }else{
            this.idiomas="en";
        }



        this.numeroDeDescargas= datosLibros.numeroDeDescargas();
        this.nombre= autor.nombre();

        try{
            this.fechaDeNacimiento=Integer.valueOf(autor.fechaDeNacimiento());
        }catch(NumberFormatException e){
            this.fechaDeNacimiento=0;
        }
        try{
            this.fechaDeDefuncion=Integer.valueOf(autor.fechaDeDefuncion());
        }catch(NumberFormatException e){
            this.fechaDeDefuncion=0;
        }


        //this.autor= new Autor(datosLibros.autores());
    }

//    public Autor getAutor() {
//        return autor;
//    }
//
//    public void setAutor(Autor autor) {
//        this.autor = autor;
//    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
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

    public Integer getFechaDeDefuncion() {
        return fechaDeDefuncion;
    }

    public void setFechaDeDefuncion(Integer fechaDeDefuncion) {
        this.fechaDeDefuncion = fechaDeDefuncion;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", idiomas=" + idiomas +
                ", numeroDeDescargas=" + numeroDeDescargas +
                ", nombre='" + nombre + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", fechaDeDefuncion=" + fechaDeDefuncion +
                '}';
    }
}
