package mesproduits;

import java.util.ArrayList;
import java.util.List;

public class DatabaseProduits {

    private List<Produit> listProduits;

    public DatabaseProduits() {
        this.listProduits = new ArrayList<>();
    }

    /** Rempli la liste de produit à l'aide d'une API
        Wallah je cherche des API cool c cho
     */
    public void makeDataBase(int nbProduits) {
        for(int i = 0; i < nbProduits; i++){
            //Ajout dans la liste
            String nom ="pouet"+i, desc="pouetpouet"+i;
            double prix = (int)(Math.random() * i + 1);

            //Utilisation de l'API ici
            // TODO

            listProduits.add(new Produit(nom, desc, prix));
        }
    }

    public List<Produit> getListProduits() {
        return listProduits;
    }
}
