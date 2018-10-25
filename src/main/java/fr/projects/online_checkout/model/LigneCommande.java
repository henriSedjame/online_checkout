package fr.projects.online_checkout.model;

import lombok.Data;

@Data
public class LigneCommande {
  private Article article;
  private int nombreArticle;
}
