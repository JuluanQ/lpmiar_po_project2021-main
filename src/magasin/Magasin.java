package magasin;

import magasin.exceptions.*;

import java.util.*;


public class Magasin implements iStock, iClientele, iPanier {

    private Map<iArticle, Integer> stock;
    private List<iClient> client;
    private Map<iClient, Commande> panier;
    private Map<iClient, List<Commande>> commandes;

    public Magasin() {
        this.stock = new HashMap<>();
        this.client = new ArrayList<>();
        this.panier = new HashMap<>();
        this.commandes = new HashMap<>();
    }


    // iStock

    @Override
    public void referencerAuStock(iArticle nouvelArticle, int quantiteNouvelle)
            throws ArticleDejaEnStockException, QuantiteNegativeException {
        if(quantiteNouvelle < 0){
            throw new QuantiteNegativeException();
        }

        if(stock.containsKey(nouvelArticle)){
            throw new ArticleDejaEnStockException();
        }
        stock.put(nouvelArticle,quantiteNouvelle);
    }


    @Override
    public void reapprovisionnerStock(iArticle articleMaj, int quantiteAjoutee)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException {
        if(quantiteAjoutee <= 0) throw new QuantiteNegativeOuNulleException();
        Integer qtt = stock.get(articleMaj);
        if(qtt==null) throw new ArticleHorsStockException();
        qtt += quantiteAjoutee;
        stock.replace(articleMaj,qtt);
    }

    @Override
    public int consulterQuantiteEnStock(iArticle articleRecherche) throws ArticleHorsStockException {
        Integer qtt = stock.get(articleRecherche);
        if(qtt==null) throw new ArticleHorsStockException();
        return qtt;
    }

    @Override
    public void retirerDuStock(int quantiteRetiree, iArticle articleMaj)
            throws ArticleHorsStockException, QuantiteNegativeOuNulleException, QuantiteEnStockInsuffisanteException {
        if(quantiteRetiree<=0)throw new QuantiteNegativeOuNulleException();
        Integer qtt = stock.get(articleMaj);
        if(qtt==null)throw new ArticleHorsStockException();
        if(quantiteRetiree>qtt)throw new QuantiteEnStockInsuffisanteException();
        qtt -= quantiteRetiree;
        stock.replace(articleMaj, qtt);
    }


    @Override
    public List<iArticle> listerArticlesEnStockParNom() {
        List<iArticle> listeArticle = new ArrayList<>(stock.keySet());
        listeArticle.sort(iArticle.COMPARATEUR_NOM);
        return listeArticle;
    }

    @Override
    public List<iArticle> listerArticlesEnStockParReference() {
        List<iArticle> listeArticle = new ArrayList<>(stock.keySet());
        listeArticle.sort(iArticle.COMPARATEUR_REFERENCE);
        return listeArticle;
    }

    @Override
    public List<Map.Entry<iArticle, Integer>> listerStock() {
        List<Map.Entry<iArticle,Integer>> listeStock = new ArrayList<>();
        for(iArticle a : this.listerArticlesEnStockParNom()){
            Map.Entry<iArticle, Integer> entry = new AbstractMap.SimpleEntry<>(a, stock.get(a));
            listeStock.add(entry);
        }
        return listeStock;
    }

    // iClientele


    @Override
    public void enregistrerNouveauClient(iClient nouveauClient) throws ClientDejaEnregistreException {
        if(client.contains(nouveauClient))throw new ClientDejaEnregistreException();
        client.add(nouveauClient);
        panier.put(nouveauClient,new Commande());
        commandes.put(nouveauClient,new LinkedList<>());
    }

    @Override
    public List<iClient> listerLesClientsParId() {
        client.sort(iClient.COMPARATEUR_ID);
        return client;
    }


    // iPanier

    @Override
    public Commande consulterPanier(iClient client) throws ClientInconnuException {
        Commande commande = panier.get(client);
        if(commande==null)throw new ClientInconnuException();
        return commande;
    }

    @Override
    public void ajouterAuPanier(iClient client, iArticle article, int quantite)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            ArticleHorsStockException, QuantiteEnStockInsuffisanteException {
        if(!listerLesClientsParId().contains(client)) throw new ClientInconnuException();
        if(quantite<=0)throw new QuantiteNegativeOuNulleException();

        retirerDuStock(quantite,article);
        panier.get(client).ajout(quantite,article);

    }

    @Override
    public void supprimerDuPanier(iClient client, int quantite, iArticle article)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException,
            ArticleHorsStockException {
        if(!listerLesClientsParId().contains(client)) throw new ClientInconnuException();
        if(quantite<=0)throw new QuantiteNegativeOuNulleException();
        if(!panier.get(client).commande.containsKey(article))throw new ArticleHorsPanierException();

        Integer qttArticle = panier.get(client).quantite(article);
        if(quantite>qttArticle) throw new QuantiteSuppPanierException();

        panier.get(client).retirer(quantite,article);
        this.reapprovisionnerStock(article, quantite);

        if(panier.get(client).quantite(article)<=0){
            panier.get(client).commande.remove(article);
        }

    }

    @Override
    public double consulterMontantPanier(iClient client) throws ClientInconnuException {
        Commande cmd = panier.get(client);
        if(cmd ==null) throw new ClientInconnuException();
        return cmd.montant();
    }

    @Override
    public void viderPanier(iClient client) throws ClientInconnuException {
        Commande cmd = panier.get(client);
        if(cmd == null)throw new ClientInconnuException();

        for(iArticle a : cmd.listerArticlesParNom()){
            try {
                reapprovisionnerStock(a,cmd.quantite(a));
                cmd.commande.remove(a);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void terminerLaCommande(iClient client) throws ClientInconnuException {
        List<Commande> arrayCmd = commandes.get(client);
        if(arrayCmd == null) throw new ClientInconnuException();

        Commande commandeEnCours = panier.get(client);
        arrayCmd.add(commandeEnCours);
        commandes.replace(client,arrayCmd);

        panier.replace(client,new Commande());
    }

    @Override
    public List<Commande> listerCommandesTerminees(iClient client) throws ClientInconnuException {
        List<Commande> arrayCmd = commandes.get(client);
        if(arrayCmd == null)throw new ClientInconnuException();
        return arrayCmd;
    }

    @Override
    public double consulterMontantTotalCommandes(iClient client) throws ClientInconnuException {
        List<Commande> arrayCmd = commandes.get(client);
        if(arrayCmd==null)throw new ClientInconnuException();
        double montant = 0.0;
        for (Commande c: arrayCmd){
            montant += c.montant();
        }
        return montant;
    }


}