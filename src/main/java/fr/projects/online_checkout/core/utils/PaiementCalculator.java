package fr.projects.online_checkout.core.utils;

import fr.projects.online_checkout.core.model.Article;
import fr.projects.online_checkout.core.model.DetailPaiement;
import fr.projects.online_checkout.core.model.Paiement;
import fr.projects.online_checkout.core.model.Panier;

import java.math.BigDecimal;


public class PaiementCalculator {

  public static BigDecimal calculTotalPanier(Panier panier) {
    // TODO implémenter le contenu de la méthode
    return null;
  }

  public static DetailPaiement construireDetailPaiement(Paiement paiement) {

    final BigDecimal totalTTC = paiement.getPanier().getLigneCommandes()
            .stream()
            .map(ligne -> {
              BigDecimal prixTTC = ligne.getArticle().getPrixTTC();
              BigDecimal nbre = BigDecimalUtils.create(ligne.getNombreArticle());
              return BigDecimalUtils.mutiply(prixTTC, nbre);
            })
            .reduce(BigDecimalUtils::mutiply)
            .orElse(BigDecimal.ZERO);


    return DetailPaiement.builder()
            .description(null)
            .devisePaiement(null)
            .montantFraisGestion(null)
            .montantTaxe(null)
            .pourcentageTaxe(0)
            .montantReduction(null)
            .poucentageReduction(0)
            .montantHT(null)
            .montantLivraison(null)
            .montantTTC(totalTTC)
            .build();
  }

  private BigDecimal calculPrixArticle(Article article) {
    // TODO implémenter le contenu de la méthode
    return null;
  }
}
