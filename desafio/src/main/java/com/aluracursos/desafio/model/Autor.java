package com.aluracursos.desafio.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

//@Entity
//@Table(name="autores")

public class Autor {
  //  @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)//autoincremetnable
//    private Long Id;
//
//    @Column(unique = true)

    private String nombre;

    private String fechaDeNacimiento;
    private String fechaDeDefuncion;



//    //@Transient
//    @OneToMany (mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)///genera la relacion entre las dos tablas, series y episodios
//    private List<Libro> libros;


    public Autor(){}


    public Autor( DatosAutor l) {
        this.nombre = l.nombre();
        this.fechaDeNacimiento =l.fechaDeNacimiento();
        this.fechaDeDefuncion = l.fechaDeDefuncion();
    }


//
//    public List<Libro> getLibros() {
//        return libros;
//    }
//
//    public void setLibros(List<Libro> libros) {
//        libros.forEach(l-> l.setAutor(this));
//        this.libros = libros;
//    }

//    public Long getId() {
//        return Id;
//    }
//
//    public void setId(Long id) {
//        Id = id;
//    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeDefuncion() {
        return fechaDeDefuncion;
    }

    public void setFechaDeDefuncion(String fechaDeDefuncion) {
        this.fechaDeDefuncion = fechaDeDefuncion;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", fechaDeDefuncion='" + fechaDeDefuncion + '\'' +
                '}';
    }


}
