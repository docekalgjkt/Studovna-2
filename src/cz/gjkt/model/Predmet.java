package cz.gjkt.model;

public class Predmet {

    private static int id;
    private String nazev;
    private String popis;
    private String sylabus;
    private String zkratka;

    public String toString(){return "Predmet: " + id + ", " + nazev + ", " + popis + ", " + sylabus + ", " + zkratka;};

    public int getId(){return id;}
    public String getNazev(){return nazev;}
    public String getPopis(){return popis;}
    public String getSylabus(){return sylabus;}
    public String getZkratka() {return zkratka;}

    public void setId(int id){this.id = id;}
    public void setId(String id){this.id = Integer.parseInt(id);}
    public void setNazev(String nazev){this.nazev = nazev;}
    public void setPopis(String popis) {this.popis = popis;}
    public void setSylabus(String sylabus){this.sylabus = sylabus;}
    public void setZkratka(String zkratka){this.zkratka  = zkratka;}
}
