package monapplication;

import magasin.*;
import magasin.exceptions.*;
import mesproduits.Client;
import mesproduits.DatabaseProduits;
import mesproduits.Produit;

import java.util.ArrayList;
import java.util.List;
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
                "suppPanier : supprimer un article du panier\n" +
                "consultPanier : consulter son panier\n" +
                "consultMontant : consulter le montant du panier\n" +
                "viderPanier : vide le panier en cours\n" +
                "termCommande : termine la commande et valide le panier\n" +
                "help : donne l'ensemble des commandes disponibles\n" +
                "quit : quitter l'application\n");
        System.out.printf("Veuillez écrire ce que vous souhaitez faire : ");
        String cmd = sc.next();
        Client client = new Client();

        boolean quit = false;

        while(!quit){
            switch (cmd){
                case "client":
                    try{
                        magasin.enregistrerNouveauClient(client);
                    }catch (ClientDejaEnregistreException e){
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
                    try{
                        for(Produit p : databaseProduits.getListProduits()){
                            if(p.nom().equals(nomProduit)){
                                magasin.ajouterAuPanier(client,p,nbProduit);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case "suppPanier" :
                    System.out.printf("Quel est le nom du produit que vous souhaitez supprimer ? : ");
                    String nomProduitsupp = sc.next();
                    System.out.printf("Quel quantité souhaitez vous supprimer ? : ");
                    int nbProduitsupp = sc.nextInt();
                    try{
                        for(Produit p : databaseProduits.getListProduits()){
                            if(p.nom().equals(nomProduitsupp)){
                                magasin.supprimerDuPanier(client,nbProduitsupp,p);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case "consultPanier":
                    System.out.println("PANIER :");
                    try{
                        Commande commande = magasin.consulterPanier(client);
                        for (iArticle a : commande.listerArticlesParNom()){
                            System.out.println(""+a.nom()+" : "+a.prix()+"€ | x"+commande.quantite(a));
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "consultMontant" :
                    try{
                        System.out.print("Montant panier :"+magasin.consulterMontantPanier(client)+"\n");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case "viderPanier" :
                    System.out.println("Vidage du panier...");
                    try{
                        magasin.viderPanier(client);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println("Panier vidé !");
                    break;
                case "termCommande":
                    System.out.println("Terminaison de la commande...");
                    try{
                        magasin.terminerLaCommande(client);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println("Commande terminée !");
                    break;

                case "help":
                    System.out.println("client : devenir un client\n" +
                            "stock : consulte les stock du magasin\n" +
                            "ajoutPanier : ajouter un article au panier\n" +
                            "suppPanier : supprimer un article du panier\n" +
                            "consultPanier : consulter son panier\n" +
                            "consultMontant : consulter le montant du panier\n" +
                            "viderPanier : vide le panier en cours\n" +
                            "termCommande : termine la commande et valide le panier\n" +
                            "help : donne l'ensemble des commandes disponibles\n" +
                            "quit : quitter l'application\n");
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
