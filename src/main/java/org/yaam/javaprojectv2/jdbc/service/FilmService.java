package org.yaam.javaprojectv2.jdbc.service;
;
import org.yaam.javaprojectv2.jdbc.dao.FilmDao;
import org.yaam.javaprojectv2.jdbc.dao.impl.FilmDaoImp;
import org.yaam.javaprojectv2.jdbc.entities.Film;

import java.util.List;

public class FilmService {

    FilmDao filmDao = new FilmDaoImp();


    public void save (Film film){
        filmDao.insert(film);
    }
    public Film findById(Integer id){
       return filmDao.findById(id);
    }
    public void  Delete(Integer id){
            filmDao.deleteById(id);
    }
          public List<Film> findAll(){
            return filmDao.findAll();
        }
        public void modifier(Film film){
            filmDao.update(film);
        }

}
