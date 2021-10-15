package monapplication;

import magasin.*;
import mesproduits.*;

import java.util.List;
import java.util.Scanner;


public class MonApplication {

    public static void main(String[] args){

        /*Création de la base de donnée de produit*/
        DatabaseProduits databaseProduits = new DatabaseProduits();

        databaseProduits.addProduit(new Bracelet("Bracelet_Metalica",12.50));
        databaseProduits.addProduit(new Bracelet("Bracelet_ACDC", 10.25));
        databaseProduits.addProduit(new Bracelet("Bracelet_Pink Floyd", 8.25));
        databaseProduits.addProduit(new Bracelet("Bracelet_Powerwolf", 13.0));
        databaseProduits.addProduit(new Bracelet("Bracelet_SOAD", 12.75));

        databaseProduits.addProduit(new Tasse("Tasse_BestDad",3.50));
        databaseProduits.addProduit(new Tasse("Tasse_Cafe",4.75));
        databaseProduits.addProduit(new Tasse("Tasse_FreddyM",6.52));
        databaseProduits.addProduit(new Tasse("Tasse_Bière",7.69));
        databaseProduits.addProduit(new Tasse("Tasse_BestTeach",3.23));

        databaseProduits.addProduit(new Vinyle("Vinyle_MotorHead",27.35));
        databaseProduits.addProduit(new Vinyle("Vinyle_ACDC",32.55));
        databaseProduits.addProduit(new Vinyle("Vinyle_Powerwolf",16.70));
        databaseProduits.addProduit(new Vinyle("Vinyle_Metalica",18.25));
        databaseProduits.addProduit(new Vinyle("Vinyle_Foofighters",19.95));

        databaseProduits.addProduit(new Tshirt("Tshirt_Bleu", 15.20));
        databaseProduits.addProduit(new Tshirt("Tshirt_Noir", 15.33));
        databaseProduits.addProduit(new Tshirt("Tshirt_Blanc", 19.65));
        databaseProduits.addProduit(new Tshirt("Tshirt_BestTeach", 17.89));
        databaseProduits.addProduit(new Tshirt("Tshirt_LifeSucks", 17.35));
        databaseProduits.addProduit(new Tshirt("Tshirt_AyaNakamura", 15.00));
        databaseProduits.addProduit(new Tshirt("Tshirt_CoffeeTime", 15.00));
        databaseProduits.addProduit(new Tshirt("Tshirt_GoodBoy", 16.00));

        /*Création du magasin*/
        Magasin magasin = new Magasin();

        /*Remplissage du magasin*/
        for(iArticle a : databaseProduits.getListProduits()){
            int qtt = (int)(Math.random() * 60 + 10);
            try{
                magasin.referencerAuStock(a, qtt);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Les commande disponibles sont les suivantes :");
        System.out.print("client : se connecter en tant que client\n" +
                "gerant : se connecter en tant que gerant du magasin\n" +
                "quit : quitter l'application\n");
        System.out.print("Veuillez écrire ce que vous souhaitez faire : ");
        String clientOuGerant = sc.next();

        boolean quit = false;
        while(!quit) {
            switch (clientOuGerant) {
                case "client" -> {
                    System.out.println("Les commande disponibles en tant que client sont les suivantes :");
                    System.out.print("enregistrer : s'enregistrer en tant que client\n"+
                            "ajoutPanier : ajouter un article au panier\n" +
                            "suppPanier : supprimer un article du panier\n" +
                            "consultPanier : consulter son panier\n" +
                            "consultMontant : consulter le montant du panier\n" +
                            "viderPanier : vide le panier en cours\n" +
                            "termCommande : termine la commande et valide le panier\n" +
                            "help : donne l'ensemble des commandes disponibles\n" +
                            "quit : quitter l'application\n");
                    System.out.println("/N'OUBLIEZ PAS DE VOUS ENREGISTRER AVANT TOUTE COMMANDE\\");
                    System.out.print("Veuillez écrire ce que vous souhaitez faire : ");
                    String cmd = sc.next();
                    boolean quitClient = false;
                    Client client = new Client();
                    while (!quitClient) {
                        switch (cmd) {
                            case "enregistrer" :
                                    try{
                                        magasin.enregistrerNouveauClient(client);
                                        System.out.println("Vous vous êtes bien enregistré...");
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                break;
                            case "ajoutPanier":
                                System.out.print("Quel est le nom du produit que vous souhaitez ajouter ? : ");
                                String nomProduit = sc.next();
                                System.out.print("Quel quantité souhaitez vous ajouter ? : ");
                                int nbProduit = sc.nextInt();
                                try {
                                    for (Produit p : databaseProduits.getListProduits()) {
                                        if (p.nom().equals(nomProduit)) {
                                            magasin.ajouterAuPanier(client, p, nbProduit);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "suppPanier":
                                System.out.print("Quel est le nom du produit que vous souhaitez supprimer ? : ");
                                String nomProduitsupp = sc.next();
                                System.out.print("Quel quantité souhaitez vous supprimer ? : ");
                                int nbProduitsupp = sc.nextInt();
                                try {
                                    for (Produit p : databaseProduits.getListProduits()) {
                                        if (p.nom().equals(nomProduitsupp)) {
                                            magasin.supprimerDuPanier(client, nbProduitsupp, p);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "consultPanier":
                                System.out.println("PANIER :");
                                try {
                                    Commande commande = magasin.consulterPanier(client);
                                    for (iArticle a : commande.listerArticlesParNom()) {
                                        System.out.println("" + a.nom() + " : " + a.prix() + "€ | x" + commande.quantite(a));
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case "consultMontant":
                                try {
                                    System.out.print("Montant panier : " + magasin.consulterMontantPanier(client) + "\n");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "viderPanier":
                                System.out.println("Vidage du panier...");
                                try {
                                    magasin.viderPanier(client);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Panier vidé !");
                                break;
                            case "termCommande":
                                System.out.println("Terminaison de la commande...");
                                try {
                                    magasin.terminerLaCommande(client);
                                    System.out.println("Le total des commandes fera : " + magasin.consulterMontantTotalCommandes(client) + "€");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Commande terminée !");
                                break;
                            case "help":
                                System.out.println("Les commande disponibles en tant que client sont les suivantes :");
                                System.out.print("ajoutPanier : ajouter un article au panier\n" +
                                        "suppPanier : supprimer un article du panier\n" +
                                        "consultPanier : consulter son panier\n" +
                                        "consultMontant : consulter le montant du panier\n" +
                                        "viderPanier : vide le panier en cours\n" +
                                        "termCommande : termine la commande et valide le panier\n" +
                                        "help : donne l'ensemble des commandes disponibles\n" +
                                        "quit : quitter l'application\n");

                                break;
                            case "quit":
                                quitClient = true;
                                System.out.println("Vous quitter le menu client...");
                                break;
                            default:
                                System.out.println("Veuillez entrer une commande valide");
                                break;
                        }
                        if (!quitClient) {
                            System.out.print("Veuillez écrire ce que vous souhaitez faire : ");
                            cmd = sc.next();
                        }
                    }
                }
                case "gerant" -> {
                    System.out.println("Les commande disponibles en tant que gerant sont les suivantes :");
                    System.out.print("stock : consulte les stock du magasin\n" +
                            "refStock : reference au stock un nouvel article\n" +
                            "reapproStock : reapprovisionne un article\n" +
                            "consultQuantite : consulte la quantité d'un article en stock\n" +
                            "retirerStock : retire un article du stock\n" +
                            "help : donne l'ensemble des commandes disponibles\n" +
                            "quit : quitter l'application\n");
                    System.out.print("Veuillez écrire ce que vous souhaitez faire : ");
                    String cmd2 = sc.next();
                    boolean quitGerant = false;
                    while (!quitGerant) {
                        switch (cmd2) {
                            case "stock" -> {
                                List<iArticle> arrayList = magasin.listerArticlesEnStockParNom();
                                for (iArticle article : arrayList) {
                                    try {
                                        System.out.println("Nom : " + article.nom() + " | Prix : " + article.prix() + " | Quantité : " + magasin.consulterQuantiteEnStock(article));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            case "refStock" -> {
                                System.out.print("Veuillez renseigner le nom de l'article (sans mettre d'espace) : ");
                                String nomProduit = sc.next();
                                System.out.print("Veuillez renseigner le prix de l'article : ");
                                Double prixProduit = Double.parseDouble(sc.next());
                                System.out.print("Veuillez renseigner la quantité à ajouter au stock : ");
                                int qttProduit = sc.nextInt();
                                try {
                                    Produit p = new Produit(nomProduit, prixProduit);
                                    magasin.referencerAuStock(p, qttProduit);
                                    databaseProduits.addProduit(p);
                                    System.out.print("Est ajouté au stock : ");
                                    System.out.println("Nom : " + p.nom() + " | Prix : " + p.prix() + " | Quantité : " + qttProduit);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            case "reapproStock" -> {
                                System.out.print("Veuillez renseigner le nom de l'article (sans mettre d'espace) : ");
                                String nomProduitReappro = sc.next();
                                System.out.print("Veuillez renseigner la quantité à ajouter au stock : ");
                                int qttProduitReappro = sc.nextInt();
                                try {
                                    for (Produit p : databaseProduits.getListProduits()) {
                                        if (p.nom().equals(nomProduitReappro)) {
                                            magasin.reapprovisionnerStock(p, qttProduitReappro);
                                            System.out.print("Est réapprovisonné au stock : ");
                                            System.out.println("Nom : " + p.nom() + " | Prix : " + p.prix() + " | Quantité : " + qttProduitReappro);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            case "consultQuantite" -> {
                                System.out.print("Veuillez renseigner le nom de l'article (sans mettre d'espace) : ");
                                String nomProduitQtt = sc.next();
                                try {
                                    for (Produit p : databaseProduits.getListProduits()) {
                                        if (p.nom().equals(nomProduitQtt)) {
                                            int qttEnStock = magasin.consulterQuantiteEnStock(p);
                                            System.out.println("Nom : " + p.nom() + " | Prix : " + p.prix() + " | Quantité : " + qttEnStock);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            case "retirerStock" -> {
                                System.out.print("Veuillez renseigner le nom de l'article (sans mettre d'espace) : ");
                                String nomProduitRetirer = sc.next();
                                System.out.print("Veuillez renseigner la quantité à retirer du stock : ");
                                int qttProduitRetirer = sc.nextInt();
                                try {
                                    for (Produit p : databaseProduits.getListProduits()) {
                                        if (p.nom().equals(nomProduitRetirer)) {
                                            magasin.retirerDuStock(qttProduitRetirer, p);
                                            System.out.print("Est retiré au stock : ");
                                            System.out.println("Nom : " + p.nom() + " | Prix : " + p.prix() + " | Quantité : " + qttProduitRetirer);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            case "help" -> {
                                System.out.println("Les commande disponibles en tant que gerant sont les suivantes :");
                                System.out.print("stock : consulte les stock du magasin\n" +
                                        "refStock : reference au stock un nouvel article\n" +
                                        "reapproStock : reapprovisionne un article\n" +
                                        "consultQuantite : consulte la quantité d'un article en stock\n" +
                                        "retirerStock : retire un article du stock\n" +
                                        "help : donne l'ensemble des commandes disponibles\n" +
                                        "quit : quitter l'application\n");
                            }
                            case "quit" -> {
                                quitGerant = true;
                                System.out.println("Vous quittez le menu Gerant...");
                            }
                            default -> System.out.println("Veuillez entrer une commande valide");
                        }
                        if (!quitGerant) {
                            System.out.print("Veuillez écrire ce que vous souhaitez faire : ");
                            cmd2 = sc.next();
                        }
                    }
                }
                case "help" -> {
                    System.out.println("Les commande disponibles sont les suivantes :");
                    System.out.println("client : se connecter en tant que client\n" +
                            "gerant : se connecter en tant que gerant du magasin\n" +
                            "quit : quitter l'application\n");
                }
                case "quit" -> {
                    quit = true;
                    System.out.println("Vous quittez l'application...");
                }
                default -> System.out.println("Veuillez entrer une commande valide");
            }
            if(!quit){
                System.out.print("Veuillez écrire ce que vous souhaitez faire : ");
                clientOuGerant = sc.next();
            }
        }
    }
}
