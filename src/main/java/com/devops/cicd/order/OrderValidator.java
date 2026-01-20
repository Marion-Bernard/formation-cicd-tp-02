package com.devops.cicd.order;

/**
 * Classe utilitaire de validation des commandes.
 *
 * Règles de validation :
 * - la commande ne doit pas être null
 * - id ne doit pas être null ni vide
 * - quantity doit être strictement positive
 * - unitPrice doit être strictement positif
 *
 * En cas d’erreur, lever IllegalArgumentException.
 *
 * TODO :
 * - implémenter la méthode validate
 */
public final class OrderValidator {

    private OrderValidator() {
        // classe utilitaire
    }

    public static void validate(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order must not be null");
        }

        String id = order.getId();
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }

        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }

        if (order.getUnitPrice() <= 0) {
            throw new IllegalArgumentException("unitPrice must be > 0");
        }
    }
}
