package com.aluracursos.desafio.Repository;

import com.aluracursos.desafio.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {//recibe un tipo de dato generico y un id


 //  List<Libro> findAllByNombreOrderByNombre();

    //@Query("select l from Libro l ")
    @Query("select distinct nombre from Libro order by nombre")
    List<String> nombresDistintos();

    //@Query("select s from Serie s where s.totalTemporadas<=:totalTempoaradas AND s.evaluacion>=:evaluacion")

    @Query("select l from Libro l where l.fechaDeDefuncion<=:a")
    List<Libro> autoresVivos(Integer a);

    @Query("select distinct idiomas from Libro order by idiomas")
    List<String> idiomas();

    @Query("select l from Libro l where l.idiomas=:lpi")
    List<Libro> librosPorIdioma(String lpi);

    @Query("select l from Libro l where l.titulo=:titulo")
    List<Libro>libroYaIngresado(String titulo);





}
