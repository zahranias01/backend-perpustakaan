package com.example.perpustakaan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "filsafat") // Pastikan ini sesuai dengan nama tabel di database
public class Filsafat {
    @Id
    private int biblio_id;
    private String title;
    private String edition;
    private String isbn_issn;
    private String publish_year;
    private String collation;
    private String language_id;
    private String input_date;
    private String last_update;

    // Getter dan Setter
    public int getBiblio_id() {  // Sesuaikan nama dengan konvensi umum
        return biblio_id;
    }

    public void setBiblio_id(int biblio_id) {
        this.biblio_id = biblio_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }
    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbn_issn() {
        return isbn_issn;
    }
    public void setIsbn_issn(String isbn_issn) {
        this.isbn_issn = isbn_issn;
    }

    public String getPublish_year() {
        return publish_year;
    }
    public void setPublish_year(String publish_year) {
        this.publish_year = publish_year;
    }

    public String getCollation() {
        return collation;
    }
    public void setCollation(String collation) {
        this.collation = collation;
    }

    public String getLanguage_id() {
        return language_id;
    }
    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public String getInput_date() {
        return input_date;
    }
    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getLast_update() {
        return last_update;
    }
    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
