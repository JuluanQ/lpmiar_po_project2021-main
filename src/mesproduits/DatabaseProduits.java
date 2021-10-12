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
    public List<Produit> makeDataBase(int nbProduits) {
        for(int i = 0; i < nbProduits; i++){
            //Ajout dans la liste
        }
        return null;
    }

    public List<Produit> getListProduits() {
        return listProduits;
    }
}
