package cz.gjkt.model;

public class Kurz {

    private static int id;
    private String nazev;
    private String skolniRok;
    private String predmet;


    public String toString(){return "Kurz: " + id + ", " + nazev + ", " + skolniRok + ", " + predmet;}

    public int getId(){return id;}
    public String getNazev(){return nazev;}
    public String getSkolniRok(){return skolniRok;}
    public String getPredmet(){return predmet;}

    public void setId(int id){this.id = id;}
    public void setId(String id){this.id = Integer.parseInt(id);}
    public void setNazev(String nazev){this.nazev = nazev;}
    //public void setSkolniRok(int skolniRok){this.skolniRok = skolniRok;}
    public void setSkolniRok(String skolniRok){this.skolniRok = skolniRok;}
    //public void setPredmet(int predmet){this.predmet = predmet;}
    public void setPredmet(String predmet){this.predmet = predmet;}




}


