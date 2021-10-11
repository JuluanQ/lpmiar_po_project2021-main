package magasin;

import magasin.exceptions.*;

import java.util.*;

/**
 * défini une commande, c'est-à-dire des articles associés à leur quantité commandée
 */

public class Commande implements Comparable<Commande> {

    public HashMap<iArticle,Integer> commande;

    public Commande() {
        this.commande = new HashMap<>();
    }

    /**
     * indique si la commande est vide
     *
     * @return
     */
    public boolean estVide() {
        return commande.isEmpty();
    }

    /**
     * ajoute la quantité indiquée de l'article considéré  à la commande
     *
     * @param quantite        quantité à ajouter
     * @param articleCommande article à considérer
     * @throws QuantiteNegativeOuNulleException si la quantité indiquée est négative ou nulle
     */
    public void ajout(int quantite, iArticle articleCommande)
            throws QuantiteNegativeOuNulleException {
        if(quantite <= 0 ){
            throw new QuantiteNegativeOuNulleException();
        }
        if(commande.containsKey(articleCommande)){
            Integer qtt = commande.get(articleCommande);
            qtt+=quantite;
            commande.replace(articleCommande, qtt);
        }
        else{
            commande.put(articleCommande,quantite);
        }
    }

    /**
     * retire de la commande la quantité indiquée de l'article considéré
     *
     * @param quantite        quantité à retirer
     * @param articleCommande article à considérer
     * @throws QuantiteNegativeOuNulleException si la quantité indiquée est négative ou nulle
     * @throws QuantiteSuppPanierException      si la quantité indiquée est supp à celle dans da commande
     * @throws ArticleHorsPanierException       si l'article considéré n'est pas dans la commande
     */
    public void retirer(int quantite, iArticle articleCommande)
            throws QuantiteNegativeOuNulleException,
            QuantiteSuppPanierException, ArticleHorsPanierException {
        if(quantite<=0)throw new QuantiteNegativeOuNulleException();
        Integer qtt = commande.get(articleCommande);
        if(qtt==null)throw new ArticleHorsPanierException();
        if(quantite>qtt)throw new QuantiteSuppPanierException();
        qtt -= quantite;
        commande.replace(articleCommande, qtt);
    }

    /**
     * donne une liste de tous les articles présent dans la commande
     * (trié par nom d'article)
     *
     * @return
     */
    public List<iArticle> listerArticlesParNom() {
        List<iArticle> listeArticle = new ArrayList<>(commande.keySet());
        listeArticle.sort(iArticle.COMPARATEUR_NOM);
        return listeArticle;
    }

    /**
     * donne une liste de tous les articles présent dans la commande
     * (trié par reference)
     *
     * @return
     */
    public List<iArticle> listerArticlesParReference() {
        List<iArticle> listeArticle = new ArrayList<>(commande.keySet());
        listeArticle.sort(iArticle.COMPARATEUR_REFERENCE);
        return listeArticle;
    }

    /**
     * donne une liste de tous les couples (articleCommande, quantiteCommandee)
     * présent dans la commande
     *
     * @return
     */
    public List<Map.Entry<iArticle, Integer>> listerCommande() {
        List<Map.Entry<iArticle,Integer>> listeStock = new ArrayList<>();
        for(iArticle a : this.listerArticlesParNom()){
            Map.Entry<iArticle, Integer> entry = new AbstractMap.SimpleEntry<>(a, commande.get(a));
            listeStock.add(entry);
        }
        return listeStock;
    }


    /**
     * donne la quantité commandée de l'article considéré
     *
     * @param article l'article à considérer
     * @return la quantité commmandée
     */
    public int quantite(iArticle article) {
        return commande.get(article);
    }

    /**
     * donne le montant actuel de la commande
     *
     * @return
     */
    public double montant() {
        double montant = 0.0;
        for(iArticle a: commande.keySet()){
            montant+= a.prix()*commande.get(a);
        }
        return montant;
    }

    @Override
    public int compareTo(Commande o) {
        // TODO
        return 0;
    }
}
