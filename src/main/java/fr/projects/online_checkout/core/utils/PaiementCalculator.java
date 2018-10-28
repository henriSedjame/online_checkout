package fr.projects.online_checkout.core.utils;

import fr.projects.online_checkout.core.model.Article;
import fr.projects.online_checkout.core.model.LigneCommande;
import fr.projects.online_checkout.core.model.Panier;
import fr.projects.online_checkout.core.model.Reduction;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;


public class PaiementCalculator {

  /**
   * Méthode permettant de calculer le total d'un panier
   *
   * @param panier
   * @return
   */
  public static BigDecimal calculTotalPanier(Panier panier) {
    final Set<LigneCommande> ligneCommandes = panier.getLigneCommandes();

    return ligneCommandes
      .stream()
      .map(ligne -> {
        BigDecimal prixHT = calculPrixHTArticle(ligne.getArticle());
        BigDecimal nbre = BigDecimalUtils.create(String.valueOf(ligne.getNombreArticle()));
        return BigDecimalUtils.mutiply(prixHT, nbre);
      })
      .reduce(BigDecimalUtils::mutiply)
      .orElse(BigDecimal.ZERO);

  }

  /**
   * Méthode permettant de calculer le prix d'un article en prenant en compte les réductions
   * @param article
   * @return
   */
  private static BigDecimal calculPrixHTArticle(Article article) {
    BigDecimal prixHT = article.getPrix();
    final Reduction reduction = article.getReduction();
    if (Objects.nonNull(reduction)) {
      final double montant = reduction.getMontant();
      final double pourcentage = reduction.getPourcentage();
      if (Objects.nonNull(montant))
        prixHT = BigDecimalUtils.substract(prixHT, BigDecimalUtils.create(String.valueOf(montant)));
      else if (Objects.nonNull(pourcentage) && pourcentage < 100)
        prixHT = BigDecimalUtils.mutiply(prixHT, BigDecimalUtils.create(String.valueOf(1 - (pourcentage / 100))));
    }
    return prixHT;
  }
}
