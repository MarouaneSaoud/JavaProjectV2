package org.yaam.javaprojectv2.jdbc.dao.impl;

import org.yaam.javaprojectv2.jdbc.dao.FilmDao;
import org.yaam.javaprojectv2.jdbc.entities.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDaoImp implements FilmDao {
    private Connection conn= DB.getConnection();
    @Override
    public void insert(Film film) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "INSERT INTO film (titre , realisateur , anneeSortie , duree , genre,synopsis ) VALUES (?,?,?,?,?,?)"
                    , Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, film.getTitre());
            ps.setString(2, film.getRealisateur());
            ps.setInt(3, film.getAnneeSortie());
            ps.setInt(4, film.getDuree());
            ps.setString(5, film.getGenre());
            ps.setString(6, film.getSynopsis());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    film.setId(id);

                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'un film");;
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void update(Film film) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement
                    ("UPDATE `film` SET `titre`=?,`realisateur`=?,`anneeSortie`=?,`duree`=?,`genre`=?,`synopsis`=? WHERE Id = ?");

            ps.setString(1, film.getTitre());
            ps.setString(2, film.getRealisateur());
            ps.setInt(3, film.getAnneeSortie());
            ps.setInt(4, film.getDuree());
            ps.setString(5, film.getGenre());
            ps.setString(6, film.getSynopsis());
            ps.setInt(7, film.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'un film");;
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void deleteById(Integer id) {
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement("DELETE FROM film WHERE id = ?");

                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.err.println("problème de suppression d'un film");;
            } finally {
                DB.closeStatement(ps);
            }
    }

    @Override
    public Film findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement("SELECT * FROM `film` WHERE id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();



            if (rs.next()) {
                Film film= new Film();

                film.setId(id);
                film.setTitre(rs.getString("titre"));
                film.setRealisateur(rs.getString("realisateur"));
                film.setAnneeSortie(rs.getInt("anneeSortie"));
                film.setDuree(rs.getInt("duree"));
                film.setGenre(rs.getString("genre"));
                film.setSynopsis(rs.getString("synopsis"));

                return film;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un film");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Film> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM film");
            rs = ps.executeQuery();

            List<Film> listfilm = new ArrayList<>();

            while (rs.next()) {
                Film film = new Film();

                film.setId(rs.getInt("Id"));
                film.setTitre(rs.getString("titre"));
                film.setRealisateur(rs.getString("realisateur"));
                film.setAnneeSortie(rs.getInt("anneeSortie"));
                film.setDuree(rs.getInt("duree"));
                film.setGenre(rs.getString("genre"));
                film.setSynopsis(rs.getString("synopsis"));
                listfilm.add(film);
            }

            return listfilm;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner un département");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }
}
