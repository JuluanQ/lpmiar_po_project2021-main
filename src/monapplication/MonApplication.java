package monapplication;

import magasin.*;
import magasin.exceptions.ArticleDejaEnStockException;
import magasin.exceptions.QuantiteNegativeException;
import mesproduits.Client;
import mesproduits.DatabaseProduits;
import mesproduits.Produit;

import java.util.Scanner;
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

        Scanner sc = new Scanner(System.in);

        System.out.println("Les commande disponibles sont les suivantes :");
        System.out.println("client : devenir un client\n" +
                "stock : consulte les stock du magasin\n" +
                "ajoutPanier : ajouter un article au panier\n" +
                "consultPanier : consulter son panier\n" +
                "help : donne l'ensemble des commandes disponibles\n" +
                "quit : quitter l'application");
        System.out.printf("Veuillez écrire ce que vous souhaitez faire : ");
        String cmd = sc.next();
        Client client = new Client();

        boolean quit = false;

        while(!quit){
            switch (cmd){
                case "client":
                    try{
                        magasin.enregistrerNouveauClient(client);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "stock":
                    System.out.println(magasin.listerArticlesEnStockParNom());
                    break;
                case "ajoutPanier":
                    System.out.printf("Quel est le nom du produit que vous souhaitez ajouter ? : ");
                    String nomProduit = sc.next();
                    System.out.printf("Quel quantité souhaitez vous ajouter ? : ");
                    int nbProduit = sc.nextInt();
                    Produit produit = new Produit(nomProduit);
                    try{
                        for(Produit p : databaseProduits.getListProduits()){
                            if(p.nom().equals(nomProduit)){
                                magasin.ajouterAuPanier(client,p,nbProduit);
                            }
                        }
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "consultPanier":
                    System.out.println("PANIER :");
                    try{
                        System.out.println(magasin.consulterPanier(client).listerArticlesParNom());
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "help":
                    System.out.println("client : devenir un client\n" +
                            "stock : consulte les stock du magasin\n" +
                            "ajoutPanier : ajouter un article au panier\n" +
                            "consultPanier : consulter son panier\n" +
                            "help : donne l'ensemble des commandes disponibles\n" +
                            "quit : quitter l'application");
                    break;
                case "quit":
                    quit = true;
                    break;
                default:
                    System.out.println(":(");
            }
            if(!quit){
                System.out.printf("Veuillez écrire ce que vous souhaitez faire : ");
                cmd = sc.next();
            }
        }
    }
}
