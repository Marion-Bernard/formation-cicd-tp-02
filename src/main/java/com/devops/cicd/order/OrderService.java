package com.devops.cicd.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service de calcul du montant total d’une commande.
 *
 * Règles métier :
 * 1) Valider la commande
 * 2) Calculer le sous-total : quantity * unitPrice
 * 3) Si le sous-total >= 100 €, appliquer une remise de 5 %
 * 4) Si la commande est prioritaire, ajouter 9.99 € de frais
 * 5) Arrondir le total final à 2 décimales
 *
 * TODO :
 * - implémenter la méthode computeTotal
 */
public class OrderService {


    private static final double PRIORITY_FEE = 9.99;
    private static final double DISCOUNT_THRESHOLD = 100.0;
    private static final double DISCOUNT_RATE = 0.05;

    public double computeTotal(Order order) {
        // 1. Validation
        OrderValidator.validate(order);

        // 2. Calcul du sous-total
        double subtotal = order.getQuantity() * order.getUnitPrice();

        // 3. Application éventuelle de la remise
        if (subtotal >= DISCOUNT_THRESHOLD) {
            subtotal = subtotal * (1 - DISCOUNT_RATE);
        }

        // 4. Ajout éventuel des frais de priorité
        if (order.isPriority()) {
            subtotal += PRIORITY_FEE;
        }

        // 5. Arrondi à 2 décimales
        BigDecimal rounded = BigDecimal.valueOf(subtotal)
                .setScale(2, RoundingMode.HALF_UP);

        // 6. Retour du montant final
        return rounded.doubleValue();
    }
}