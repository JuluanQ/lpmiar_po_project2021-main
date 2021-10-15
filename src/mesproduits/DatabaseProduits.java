package mesproduits;

import java.util.ArrayList;
import java.util.List;

public class DatabaseProduits {

    private List<Produit> listProduits;

    public DatabaseProduits() {
        this.listProduits = new ArrayList<>();
    }

    public void addProduit(Produit p){
        listProduits.add(p);
    }

    public List<Produit> getListProduits() {
        return listProduits;
    }
}
