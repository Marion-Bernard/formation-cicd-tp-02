package cicd.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.devops.cicd.order.OrderService;
import com.devops.cicd.order.OrderValidator;
import com.devops.cicd.order.Order;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private final OrderService service = new OrderService();

    @Nested
    @DisplayName("Compute total for valid orders")
    class ComputeTotal {

        @Test
        void simple_order_no_discount_no_priority() {
            Order order = new Order("O1", 2, 10.0, false);
            double total = service.computeTotal(order);
            assertEquals(20.00, total);
        }

        @Test
        void order_with_discount_only() {
            Order order = new Order("O2", 10, 10.0, false); // subtotal 100
            double total = service.computeTotal(order);
            assertEquals(95.00, total); // 5% discount
        }

        @Test
        void order_with_priority_only() {
            Order order = new Order("O3", 2, 10.0, true); // subtotal 20 + 9.99
            double total = service.computeTotal(order);
            assertEquals(29.99, total);
        }

        @Test
        void order_with_discount_and_priority() {
            Order order = new Order("O4", 10, 10.0, true); // subtotal 100 -> discount 5% = 95 + 9.99
            double total = service.computeTotal(order);
            assertEquals(104.99, total);
        }

        @Test
        void rounding_test() {
            Order order = new Order("O5", 3, 33.333, true); // subtotal 99.999, no discount, +9.99
            double total = service.computeTotal(order);
            assertEquals(109.99, total); // arrondi à 2 décimales
        }

        @Test
        void edge_discount_threshold() {
            Order order = new Order("O6", 10, 10.0, false); // subtotal 100
            double total = service.computeTotal(order);
            assertEquals(95.00, total);
        }

        @Test
        void edge_discount_just_below_threshold() {
            Order order = new Order("O7", 9, 10.0, false); // subtotal 90
            double total = service.computeTotal(order);
            assertEquals(90.00, total); // no discount
        }
    }
}

