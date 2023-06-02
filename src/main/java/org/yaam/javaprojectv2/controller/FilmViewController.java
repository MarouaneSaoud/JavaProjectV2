package org.yaam.javaprojectv2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.json.JSONArray;
import org.json.JSONObject;
import org.yaam.javaprojectv2.App;
import org.yaam.javaprojectv2.jdbc.entities.Film;

import org.yaam.javaprojectv2.jdbc.service.FilmService;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class FilmViewController {
    FilmService filmService = new FilmService();

    @FXML
    private Label idLabel;
    @FXML
    private TextField idTextField ;

    @FXML
    private Button Add , actualiser , clear , chercher , modifier , importerExcel , exporterExcel , exporterJson ,importerJson , exporterTxt,importerTxt, wordFile ;
    @FXML
    private TextField titre,realisateur  , duree;
    @FXML
    private DatePicker anneeSortie;
    @FXML
    private ComboBox<String> genre ;
    @FXML
    private TextArea synopsis;
    @FXML
    private TableView<Film> FilmTable;
    @FXML
    private TableColumn<Film, Integer> id;
    @FXML
    private TableColumn<Film, String> titreCol;
    @FXML
    private TableColumn<Film, String> realisateurCol;
    @FXML
    private TableColumn<Film, Integer> anneeSortieCol;
    @FXML
    private TableColumn<Film, Integer> dureeCol;
    @FXML
    private TableColumn<Film, String> genreCol;
    @FXML
    private TableColumn<Film, String> synopsisCol;
    @FXML
    private TextField inputChercher;

    private List<Film> ListFilm= new ArrayList<>(getFilmList());



    private List<String> ListGenre=new ArrayList<>();
    TableColumn<Film, Void> colSupprimer = new TableColumn<>("Supprimer");

    @FXML
    public void initialize() {
            disableId();
            exporterTxt.setOnMouseClicked(arg->{
                exporterFilmsTxt();
            });
            importerTxt.setOnMouseClicked(arg->{
                SupprimerTout();
                importerFilmsTxt();
            });
            wordFile.setOnMouseClicked(arg->{
                generateWordFile(getFilmList());
            });
            exporterJson.setOnMouseClicked(arg->{
                exporterFilmsJson();
            });
            importerJson.setOnMouseClicked(arg->{
                SupprimerTout();
                importerFilmsJson();
            });
            importerExcel.setOnMouseClicked(arg->{
                SupprimerTout();
                importerFilmsExcel();

            });
            exporterExcel.setOnMouseClicked(arg->{
                exporterFilmsExcel();
            });
            modifier.setOnMouseClicked(arg->{
                    modifier(construirePourModifier());
                    vider();
                    actualiserTableView();
            });

            FilmTable.setOnContextMenuRequested(event -> {
                Film film = FilmTable.getSelectionModel().getSelectedItem();
                if (film != null) {
                    titre.setText(film.getTitre());
                    realisateur.setText(film.getRealisateur());
                    LocalDate date = LocalDate.of(film.getAnneeSortie(), 1, 1);
                    anneeSortie.setValue(date);
                    duree.setText(String.valueOf(film.getDuree()));
                    genre.setValue(film.getGenre());
                    synopsis.setText(film.getSynopsis());
                    idTextField.setText(String.valueOf(film.getId()));
                    enableId();
                }
            });
            actualiser.setOnMouseClicked(arg -> {
                actualiserTableView();
            });
            chercher.setOnMouseClicked(arg -> {
                FilmTable.getItems().clear();
                System.out.println(chercherParFilm(inputChercher.getText()));
                FilmTable.getItems().addAll(chercherParFilm(inputChercher.getText()));

            });


            FilmTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !FilmTable.getSelectionModel().isEmpty()) {
                    Film selectedFilm = FilmTable.getSelectionModel().getSelectedItem();
                    openModificationView(selectedFilm);
                }
            });


            clear.setOnMouseClicked(arg -> {
                vider();
            });

            loadTable(ListFilm);
            colSupprimer.setCellFactory(param -> new TableCell<Film, Void>() {
                private Button supprimerButton = new Button("Supprimer");

                {
                    supprimerButton.setOnAction(event -> {
                        Film film = getTableView().getItems().get(getIndex());
                        supprimerFilm(film.getId());

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(supprimerButton);
                    }
                }
            });

            InitialiseCombobox();
            Add.setOnMouseClicked(arg -> {
                try {
                    save(constructionFilm());
                    vider();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Enregistrement du film");
                    alert.setHeaderText(null);
                    alert.setContentText("Le film a été enregistré avec succès.");
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez saisir les informations Correctement");
                    alert.showAndWait();
                }

            });
        }


    public void save(Film film){
        filmService.save(film);
        actualiserTableView();
    }
    public void InitialiseCombobox(){
        ListGenre.add("Action");
        ListGenre.add("Aventure");
        ListGenre.add("Animation");
        ListGenre.add("Comédie");
        ListGenre.add("Drame");
        ListGenre.add("Horreur");
        ListGenre.add("Science-fiction");
        ListGenre.add("Romance");
        ListGenre.add("Thriller");
        ListGenre.add("Documentaire");
        ListGenre.add("Fantaisie");
        ListGenre.add("Mystère");
        ListGenre.add("Crime");
        ListGenre.add("Guerre");
        ListGenre.add("Western");
        ListGenre.add("Familial");
        ListGenre.add("Musical");
        ListGenre.add("Historique");
        ListGenre.add("Biographie");
        ListGenre.add("Sport");
        genre.getItems().addAll(ListGenre);
    }
    public Film constructionFilm(){
        String genreSelected = genre.getValue();
        Film film ;
        if (titre.getText().isEmpty() ||realisateur.getText().isEmpty() ||genreSelected.toString()==null ||anneeSortie.getValue()==null||duree.getText().isEmpty() ||synopsis.getText().isEmpty() )
            return null ;
        else{

            film = new Film();
            film.setTitre(titre.getText());
            film.setRealisateur(realisateur.getText());
            film.setGenre(genreSelected.toString());
            film.setAnneeSortie(Integer.parseInt(String.valueOf(anneeSortie.getValue().getYear())));
            film.setDuree(Integer.parseInt(duree.getText()));
            film.setSynopsis(synopsis.getText());

        }
        return film;
    }
    public void vider(){
        titre.setText("");
        realisateur.setText("");
        genre.setValue("");
        anneeSortie.setValue(null);
        duree.setText("");
        synopsis.setText("");
        disableId();
    }
    public List<Film> getFilmList() {
        return filmService.findAll();
    }
    private void loadTable(List<Film> films){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        realisateurCol.setCellValueFactory(new PropertyValueFactory<>("realisateur"));
        anneeSortieCol.setCellValueFactory(new PropertyValueFactory<>("anneeSortie"));
        dureeCol.setCellValueFactory(new PropertyValueFactory<>("duree"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        synopsisCol.setCellValueFactory(new PropertyValueFactory<>("synopsis"));
        FilmTable.getColumns().add(colSupprimer);

        FilmTable.getItems().addAll(films);
        FilmTable.setEditable(true);
    }
    private void actualiserTableView() {
        FilmTable.getItems().clear(); // Effacer les données actuelles de la TableView
        FilmTable.getItems().addAll(getFilmList()); // Récupérer et ajouter la nouvelle liste de films
    }
    public void supprimerFilm(int id){
        filmService.Delete(id);
        actualiserTableView();

    }



    private List<Film> chercherParFilm(String titre){
        return ListFilm.stream()
                .filter(film -> film.getTitre().equals(titre))
                .toList();
    }
    private void openModificationView(Film film) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("film-modification.fxml"));
            Parent modificationView = loader.load();

            ModificationController modificationController = loader.getController();
            modificationController.setFilm(film);

            Stage modificationStage = new Stage();
            modificationStage.setTitle("Modification du film");
            modificationStage.setScene(new Scene(modificationView));
            modificationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void disableId(){
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        modifier.setVisible(false);
    }
    private void enableId(){
        idLabel.setVisible(true);
        idTextField.setVisible(true);
        idTextField.setDisable(true);
        modifier.setVisible(true);
    }
    private Film construirePourModifier(){
        String genreSelected = genre.getValue();
        Film film ;
        if (titre.getText().isEmpty() ||realisateur.getText().isEmpty() ||genreSelected.toString()==null ||anneeSortie.getValue()==null||duree.getText().isEmpty() ||synopsis.getText().isEmpty() )
            return null ;
        else{

            film = new Film();
            film.setTitre(titre.getText());
            film.setRealisateur(realisateur.getText());
            film.setGenre(genreSelected.toString());
            film.setAnneeSortie(Integer.parseInt(String.valueOf(anneeSortie.getValue().getYear())));
            film.setDuree(Integer.parseInt(duree.getText()));
            film.setSynopsis(synopsis.getText());
            film.setId(Integer.parseInt(idTextField.getText()));
            System.out.println(film);

        }
        return film;
    }
    public void modifier(Film film){
        if (film!=null)
             filmService.modifier(film);
    }
    public void exporterFilmsExcel(){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Mes Films");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Id");
        headerRow.createCell(1).setCellValue("Titre");
        headerRow.createCell(2).setCellValue("Réalisateur");
        headerRow.createCell(3).setCellValue("Annee de Sortie");
        headerRow.createCell(4).setCellValue("Duree");
        headerRow.createCell(5).setCellValue("Genre");
        headerRow.createCell(6).setCellValue("Synopsis");

        int rowNum = 1;
        for (Film film : getFilmList()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(film.getId());
            row.createCell(1).setCellValue(film.getTitre());
            row.createCell(2).setCellValue(film.getRealisateur());
            row.createCell(3).setCellValue(film.getAnneeSortie());
            row.createCell(4).setCellValue(film.getDuree());
            row.createCell(5).setCellValue(film.getGenre());
            row.createCell(6).setCellValue(film.getSynopsis());


        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("films.xlsx");
        File file = fileChooser.showSaveDialog(exporterExcel.getScene().getWindow());

        if (file != null) {

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Fichier Excel exporté avec succès.");
                alert.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        try {
            workbook.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void importerFilmsExcel() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Excel", "*.xlsx", "*.xls"));
        File file = fileChooser.showOpenDialog(importerExcel.getScene().getWindow());

        if (file != null) {
            try (FileInputStream fileIn = new FileInputStream(file);
                 Workbook workbook = new XSSFWorkbook(fileIn)) {

                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);

                    int id = (int) row.getCell(0).getNumericCellValue();
                    String titre = row.getCell(1).getStringCellValue();
                    String realisateur = row.getCell(2).getStringCellValue();
                    int anneeSortie = (int) row.getCell(3).getNumericCellValue();
                    int duree = (int) row.getCell(4).getNumericCellValue();
                    String genre = row.getCell(5).getStringCellValue();
                    String synopsis = row.getCell(6).getStringCellValue();

                    Film film = new Film();
                    film.setId(id);
                    film.setTitre(titre);
                    film.setRealisateur(realisateur);
                    film.setAnneeSortie(anneeSortie);
                    film.setDuree(duree);
                    film.setGenre(genre);
                    film.setSynopsis(synopsis);
                    save(film);
                }
                actualiserTableView();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Importation des films à partir du fichier Excel réussie. ");
                alert.showAndWait();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void exporterFilmsJson(){
        List<Film> films = getFilmList();

        JSONArray jsonArray = new JSONArray();

        for (Film film : films) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", film.getId());
            jsonObject.put("titre", film.getTitre());
            jsonObject.put("realisateur", film.getRealisateur());
            jsonObject.put("anneeSortie", film.getAnneeSortie());
            jsonObject.put("duree", film.getDuree());
            jsonObject.put("genre", film.getGenre());
            jsonObject.put("synopsis", film.getSynopsis());

            jsonArray.put(jsonObject);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier JSON", "*.json"));
        File file = fileChooser.showSaveDialog(exporterJson.getScene().getWindow());

        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(jsonArray.toString());
                fileWriter.flush();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Exportation des données des films en JSON réussie.");
                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void importerFilmsJson(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier JSON", "*.json"));
        File file = fileChooser.showOpenDialog(importerJson.getScene().getWindow());

        if (file != null) {
            try (FileReader fileReader = new FileReader(file)) {
                StringBuilder jsonContent = new StringBuilder();
                int character;
                while ((character = fileReader.read()) != -1) {
                    jsonContent.append((char) character);
                }

                JSONArray jsonArray = new JSONArray(jsonContent.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Film film = new Film();
                    film.setId(jsonObject.getInt("id"));
                    film.setTitre(jsonObject.getString("titre"));
                    film.setRealisateur(jsonObject.getString("realisateur"));
                    film.setAnneeSortie(jsonObject.getInt("anneeSortie"));
                    film.setDuree(jsonObject.getInt("duree"));
                    film.setGenre(jsonObject.getString("genre"));
                    film.setSynopsis(jsonObject.getString("synopsis"));

                    save(film);
                }
                actualiserTableView();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Importation des données des films depuis le fichier JSON réussie.");
                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void generateWordFile(List<Film> films) {
        XWPFDocument document = new XWPFDocument();

        // Créer un paragraphe pour le titre du document
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setFontSize(18);
        titleRun.setColor("FF0000");
        titleRun.setBold(true);
        titleRun.setText("Liste des films");

        for (Film film : films) {
            XWPFParagraph filmParagraph = document.createParagraph();
            XWPFRun filmRun = filmParagraph.createRun();
            filmRun.setText("Titre : " + film.getTitre());
            filmRun.addBreak();
            filmRun.setText("Realisateur : " + film.getRealisateur());
            filmRun.addBreak();
            filmRun.setText("Anneé de sortie : " + film.getAnneeSortie());
            filmRun.addBreak();
            filmRun.setText("Genre : " + film.getGenre());
            filmRun.addBreak();
            filmRun.setText("Dureé : " + film.getDuree());
            filmRun.addBreak();
            filmRun.setText("Synopsis : " + film.getSynopsis());
            filmRun.addBreak();
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Word");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Word", "*.docx"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                document.write(outputStream);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Fichier Word généré avec succès !");


                alert.showAndWait();
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void exporterFilmsTxt(){


        // Créer un objet FileChooser pour permettre à l'utilisateur de choisir l'emplacement du fichier à exporter
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les données des films");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));

        // Afficher la boîte de dialogue de sélection de fichier
        Stage stage = (Stage) exporterTxt.getScene().getWindow();
        java.io.File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                // Créer un BufferedWriter pour écrire dans le fichier sélectionné
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                // Parcourir la liste des films et écrire les données de chaque film dans le fichier
                for (Film film : ListFilm) {
                    writer.write(film.getId() + "\t" +film.getTitre() + "\t" + film.getRealisateur() + "\t" + film.getAnneeSortie() + "\t"
                            + film.getDuree() + "\t" + film.getGenre() + "\t" + film.getSynopsis());
                    writer.newLine();
                }

                // Fermer le BufferedWriter pour libérer les ressources
                writer.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export terminé");
                alert.setHeaderText(null);
                alert.setContentText("Les données des films ont été exportées avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                // Afficher une boîte de dialogue en cas d'erreur lors de l'exportation du fichier
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'exportation du fichier.");
                alert.showAndWait();
            }
        }

    }
    public void importerFilmsTxt(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir le fichier à importer");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));

        Stage stage = (Stage) importerTxt.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {

                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] attributs = line.split("\t");

                    Film film = new Film();
                    film.setId(Integer.parseInt(attributs[0]));
                    film.setTitre(attributs[1]);
                    film.setRealisateur(attributs[2]);
                    film.setAnneeSortie(Integer.parseInt(attributs[3]));
                    film.setDuree(Integer.parseInt(attributs[4]));
                    film.setGenre(attributs[5]);
                    film.setSynopsis(attributs[6]);
                    System.out.println(film);
                    save(film);
                }
                actualiserTableView();
                bufferedReader.close();
                reader.close();
                actualiserTableView();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Données importées");
                alert.setHeaderText(null);
                alert.setContentText("Les données ont été importées avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'importation des données.");
                alert.showAndWait();
            }
        }

    }
    public void SupprimerTout(){
        for(Film film : getFilmList())
            supprimerFilm(film.getId());
    }

}

