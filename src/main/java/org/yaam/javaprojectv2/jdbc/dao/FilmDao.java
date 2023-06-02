package org.yaam.javaprojectv2.jdbc.dao;


import org.yaam.javaprojectv2.jdbc.entities.Film;

import java.util.List;

public interface FilmDao {
    void insert(Film film);

    void update(Film film);

    void deleteById(Integer id);

    Film findById(Integer id);

    List<Film> findAll();
}
