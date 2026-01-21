package com.devops.cicd.order;

/**
 * Représente une commande client.
 *
 * Attributs attendus :
 * - id : identifiant unique (non null, non vide)
 * - quantity : nombre d’articles (> 0)
 * - unitPrice : prix unitaire (> 0)
 * - priority : commande prioritaire ou non
 *
 * TODO :
 * - compléter les champs
 * - écrire le constructeur
 * - écrire les getters
 */
public class Order {
    private final String id;
    private final int quantity;
    private final double unitPrice;
    private final boolean priority;

    private Order(String id, int quantity, double unitPrice, boolean priority) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.priority = priority;
    }

    public static Order create(String id, int quantity, double unitPrice, boolean priority) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if (quantity <= 0 ) {
            throw new IllegalArgumentException("quantity must be > 0");
        }

        if (unitPrice <= 0 ) {
            throw new IllegalArgumentException("unitPrice must be > 0");
        }

        return new Order(id, quantity,unitPrice, priority);
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public boolean isPriority() {
        return priority;
    }
}

