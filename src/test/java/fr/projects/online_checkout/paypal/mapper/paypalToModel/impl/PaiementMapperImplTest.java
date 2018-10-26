package fr.projects.online_checkout.paypal.mapper.paypalToModel.impl;

import com.paypal.api.payments.Payment;
import fr.projects.online_checkout.core.model.*;
import fr.projects.online_checkout.core.utils.BigDecimalUtils;
import fr.projects.online_checkout.paypal.mapper.paypalToModel.PaiementMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 26/10/2018
 * @Class purposes : .......
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PaiementMapperImplTest {

  Paiement paiement;
  String fraisGestion = "5";
  String fraisLivraison = "10";
  double pourcentageTaxe = 2;

  PaiementMapper mapper;

  @Before
  public void setUp() {

    mapper = new PaiementMapperImpl();

    final Identite identite1 = Identite.builder()
      .nom("SEDJAME")
      .prenom("Henri Joel")
      .dateNaissance(LocalDate.of(1987, 02, 14))
      .build();

    final Coordonnees coordonnees1 = Coordonnees.builder()
      .adresse(Adresse.builder()
        .numero("25")
        .rue("rue de la paix")
        .codePostal("87000")
        .ville("Poitiers")
        .build())
      .phone(Phone.builder()
        .indicatif("00336")
        .numero("0658181250")
        .build())
      .build();

    Beneficiaire beneficiaire = new Beneficiaire();
    beneficiaire.setCoordonnees(coordonnees1);
    beneficiaire.setIdentite(identite1);
    beneficiaire.setType(TypePersonne.PHYSIQUE);

    final Identite identite2 = Identite.builder()
      .nom("LE NAIN")
      .prenom("Chloe")
      .dateNaissance(LocalDate.of(1991, 02, 06))
      .build();
    final Coordonnees coordonnees2 = Coordonnees.builder()
      .adresse(Adresse.builder()
        .numero("45")
        .rue("avenue Remain")
        .codePostal("76000")
        .ville("Paris")
        .build())
      .phone(Phone.builder()
        .indicatif("00336")
        .numero("0647851254")
        .build())
      .build();
    Payeur payeur = new Payeur();
    payeur.setIdentite(identite2);
    payeur.setCoordonnees(coordonnees2);
    payeur.setType(TypePersonne.PHYSIQUE);


    Article article = Article.builder()
      .nom("soins")
      .categorie("sante")
      .description("massage corporel bien être")
      .devise(Devise.EUR)
      .prix(BigDecimalUtils.create("75"))
      .reduction(Reduction.builder()
        .montant(10)
        .build())
      .build();

    LigneCommande ligneCommande = LigneCommande.builder()
      .article(article)
      .nombreArticle(1)
      .build();

    Set<LigneCommande> lc = new TreeSet<>();
    lc.add(ligneCommande);

    Panier panier = Panier.builder()
      .ligneCommandes(lc)
      .build();

    paiement = Paiement.builder()
      .description("Paiement de test")
      .devisePaiement(Devise.EUR)
      .methodeDePaiement(PaiementMethods.PAYPAL)
      .montantFraisGestion(BigDecimalUtils.create(fraisGestion))
      .montantLivraison(BigDecimalUtils.create(fraisLivraison))
      .pourcentageTaxe(pourcentageTaxe)
      .motif(MotifPaiement.VENTE)
      .beneficiaire(beneficiaire)
      .payeur(payeur)
      .panier(panier)
      .build();
  }

  @After
  public void tearDown() {
  }

  @Test
  public void toPaypalPayment() {
    final Payment payment = mapper.toPaypalPayment(paiement);
    assertNotNull(payment);
    log.info(payment.toJSON());
  }
}
