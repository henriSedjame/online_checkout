package fr.projects.online_checkout.paypal.mapper.paypalToModel;

import com.paypal.api.payments.Payment;
import fr.projects.online_checkout.core.model.Paiement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Project online_checkout
 * @Author Henri Joel SEDJAME
 * @Date 25/10/2018
 * @Class purposes : .......
 */
@Mapper(componentModel = "spring")
public interface ModelMapper {
  ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

  @Mappings({
    @Mapping(source = "motif.paypalLibelle", target = "intent"),
    @Mapping(source = "methodeDePaiement.libelle", target = "payer.paymentMethod"),

    @Mapping(source = "payeur.identite.prenom", target = "payer.payerInfo.firstName"),
    @Mapping(source = "payeur.identite.nom", target = "payer.payerInfo.lastName"),
    @Mapping(source = "payeur.coordonnees.email", target = "payer.payerInfo.email"),
    @Mapping(source = "payeur.coordonnees.phone.numero", target = "payer.payerInfo.phone"),
    @Mapping(source = "payeur.coordonnees.adresse.numero", target = "payer.payerInfo.billingAddress.line1"),
    @Mapping(source = "payeur.coordonnees.adresse.rue", target = "payer.payerInfo.billingAddress.line2"),
    @Mapping(source = "payeur.coordonnees.adresse.codePostal", target = "payer.payerInfo.billingAddress.postalCode"),
    @Mapping(source = "payeur.coordonnees.adresse.ville", target = "payer.payerInfo.billingAddress.city"),
    @Mapping(source = "payeur.coordonnees.adresse.pays", target = "payer.payerInfo.billingAddress.countryCode"),

    @Mapping(source = "beneficiaire.coordonnees.email", target = "payee.email"),
    @Mapping(source = "beneficiaire.identite.prenom", target = "payee.firstName"),
    @Mapping(source = "beneficiaire.identite.nom", target = "payee.lastName"),
    @Mapping(source = "beneficiaire.coordonnees.phone.indicatif", target = "payee.phone.countryCode"),
    @Mapping(source = "beneficiaire.coordonnees.phone.numero", target = "payee.phone.nationalNumber"),

  })
  Payment toModel(Paiement paiement);
}
