package fr.projects.online_checkout.core.model;

import lombok.Data;

@Data
public class LigneCommande {
  private Article article;
  private int nombreArticle;
}
