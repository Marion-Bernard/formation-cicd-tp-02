package cicd.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.devops.cicd.order.OrderValidator;
import com.devops.cicd.order.Order;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Nested
    @DisplayName("Validation of Order")
    class OrderValidation {

        @Test
        void should_reject_null_order() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> OrderValidator.validate(null)
            );
            assertEquals("order must not be null", ex.getMessage());
        }

        @Test
        void should_reject_null_or_blank_id() {
            IllegalArgumentException ex1 = assertThrows(
                    IllegalArgumentException.class,
                    () -> OrderValidator.validate(new Order(null, 1, 10.0, false))
            );
            assertEquals("id must not be blank", ex1.getMessage());

            IllegalArgumentException ex2 = assertThrows(
                    IllegalArgumentException.class,
                    () -> OrderValidator.validate(new Order("   ", 1, 10.0, false))
            );
            assertEquals("id must not be blank", ex2.getMessage());
        }

        @Test
        void should_reject_invalid_quantity() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> OrderValidator.validate(new Order("A123", 0, 10.0, false))
            );
            assertEquals("quantity must be > 0", ex.getMessage());
        }

        @Test
        void should_reject_invalid_unitPrice() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> OrderValidator.validate(new Order("A123", 1, 0.0, false))
            );
            assertEquals("unitPrice must be > 0", ex.getMessage());
        }

        @Test
        void should_accept_valid_order() {
            Order order = new Order("A123", 5, 10.0, true);
            assertDoesNotThrow(() -> OrderValidator.validate(order));
        }
    }
}
