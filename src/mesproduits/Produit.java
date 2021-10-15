package mesproduits;
import magasin.*;


abstract public class Produit implements iArticle{

    private static int nbReference = 1;
    private int reference;
    private String nom;
    private double prix;
    private String description;

    public Produit(String nom, double prix){
        this.reference = nbReference++;
        this.nom = nom;
        this.prix = prix;
    }

    public Produit(String nom, String desc, double prix) {
        this(nom, prix);
        this.description = desc;
    }

    public Produit(String nom) {
        this(nom, 0.0);
    }

    @Override
    public int reference() {
        return reference;
    }
    @Override
    public String nom() {
        return nom;
    }

    @Override
    public double prix() {
        return prix;
    }

    @Override
    public String toString() {
        return "| "+nom+" : "+prix+"€ |\n"+
                "---------------------\n";
    }
}
