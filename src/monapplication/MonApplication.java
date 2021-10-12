package monapplication;

import magasin.*;
import magasin.exceptions.ArticleDejaEnStockException;
import magasin.exceptions.QuantiteNegativeException;
import mesproduits.DatabaseProduits;
//import mesproduits.*;

public class MonApplication {

    public static void main(String[] args){

        /*Création de la base de donnée de produit*/
        DatabaseProduits databaseProduits = new DatabaseProduits();
        databaseProduits.makeDataBase(100);

        /*Création du magasin*/
        Magasin magasin = new Magasin();

        /*Remplissage du magasin*/
        for(iArticle a : databaseProduits.getListProduits()){
            int qtt = (int)(Math.random() * 50 + 1);
            try{
                magasin.referencerAuStock(a, qtt);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println(databaseProduits.getListProduits());


    }
}
