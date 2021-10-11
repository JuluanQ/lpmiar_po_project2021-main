package magasin;

import magasin.exceptions.*;

import java.util.*;


public class Magasin implements iStock, iClientele, iPanier {

    private Map<iArticle, Integer> stock;


    public Magasin() {
        this.stock = new HashMap<>();
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
        //
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
        System.out.println(qtt);
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
        List<Map.Entry<iArticle, Integer>> listeStock = new ArrayList<>(stock.entrySet());
        return listeStock;
    }

    // iClientele


    @Override
    public void enregistrerNouveauClient(iClient nouveauClient) throws ClientDejaEnregistreException {

    }

    @Override
    public List<iClient> listerLesClientsParId() {
        // TODO
        return null;
    }


    // iPanier

    @Override
    public Commande consulterPanier(iClient client) throws ClientInconnuException {
        // TODO
        return null;
    }

    @Override
    public void ajouterAuPanier(iClient client, iArticle article, int quantite)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            ArticleHorsStockException, QuantiteEnStockInsuffisanteException {
        // TODO
    }

    @Override
    public void supprimerDuPanier(iClient client, int quantite, iArticle article)
            throws ClientInconnuException,
            QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException,
            ArticleHorsStockException {
        // TODO
    }

    @Override
    public double consulterMontantPanier(iClient client) throws ClientInconnuException {
        // TODO
        return -1.0;
    }

    @Override
    public void viderPanier(iClient client) throws ClientInconnuException {
        // TODO
    }

    @Override
    public void terminerLaCommande(iClient client) throws ClientInconnuException {
        // TODO
    }

    @Override
    public List<Commande> listerCommandesTerminees(iClient client) throws ClientInconnuException {
        // TODO
        return null;
    }

    @Override
    public double consulterMontantTotalCommandes(iClient client) throws ClientInconnuException {
        // TODO
        return -1.0;
    }


}