package com.fabridev.apppeliculas.Domains;

import java.io.Serializable;
import java.util.ArrayList;

public class Series implements Serializable {
    private String Title;
    private String Description;
    private String Poster;
    private String Time;
    private String Trailer;
    private int Year;
    private ArrayList<String> Genre;
    private ArrayList<Cast> Casts;




}
