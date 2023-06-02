package org.yaam.javaprojectv2.jdbc.entities;

import javafx.beans.property.IntegerProperty;
import org.yaam.javaprojectv2.jdbc.service.FilmService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Film implements Serializable {
    private Integer id ;

    private String titre;
    private String realisateur;
    private int anneeSortie;
    private int duree;
    private String genre;
    private String synopsis;

    public Film() {
    }


    public Film(String titre, String realisateur, int anneeSortie, int duree, String genre, String synopsis) {
        this.titre = titre;
        this.realisateur = realisateur;
        this.anneeSortie = anneeSortie;
        this.duree = duree;
        this.genre = genre;
        this.synopsis = synopsis;
    }
    public Film(int id ,String titre, String realisateur, int anneeSortie, int duree, String genre, String synopsis) {
        this.id=id;
        this.titre = titre;
        this.realisateur = realisateur;
        this.anneeSortie = anneeSortie;
        this.duree = duree;
        this.genre = genre;
        this.synopsis = synopsis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }


    public int getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(int anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public static List<Film> getListeFilms() {
        FilmService filmService=new FilmService();
        return filmService.findAll();
    }
    public static Map<String, Film> getMapFilms() {
        FilmService filmService=new FilmService();
        Map<String, Film> filmsMap = new HashMap<>();
        for (Film film: filmService.findAll()){
            filmsMap.put(film.getTitre(), film);
            System.out.println(film);
        }
        return filmsMap;
    }



    @Override
    public String toString() {
        return "Film{" +
                "id='" + id + '\'' +
                "titre='" + titre + '\'' +
                ", realisateur='" + realisateur + '\'' +
                ", anneeSortie=" + anneeSortie +
                ", duree=" + duree +
                ", genre='" + genre + '\'' +
                ", synopsis='" + synopsis + '\'' +
                '}';
    }



}
