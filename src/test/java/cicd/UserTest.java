package cicd;

import com.devops.cicd.user.Role;
import com.devops.cicd.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Nested
    @DisplayName("Email validation")
    class EmailValidation {

        @Test
        void should_reject_null_email() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> new User(null, "Strong1!", Role.USER)
            );

            assertEquals("email must be valid", ex.getMessage());
        }

        @Test
        void should_reject_blank_email() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> new User("   ", "Strong1!", Role.USER)
            );

            assertEquals("email must be valid", ex.getMessage());
        }

        @Test
        void should_reject_invalid_email_formats() {
            String[] invalidEmails = {
                    "alice",
                    "alice@",
                    "@test.com",
                    "alice@test",
                    "alice@@test.com"
            };

            for (String email : invalidEmails) {
                IllegalArgumentException ex = assertThrows(
                        IllegalArgumentException.class,
                        () -> new User(email, "Strong1!", Role.USER)
                );

                assertEquals("email must be valid", ex.getMessage());
            }
        }

        @Test
        void should_accept_valid_email_and_trim_it() {
            User user = new User("  alice@test.com  ", "Strong1!", Role.USER);

            assertEquals("alice@test.com", user.getEmail());
        }
    }

    @Nested
    @DisplayName("Password validation")
    class PasswordValidation {

        @Test
        void should_reject_null_password() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> new User("alice@test.com", null, Role.USER)
            );

            assertEquals("password must be strong", ex.getMessage());
        }

        @Test
        void should_reject_blank_password() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> new User("alice@test.com", "   ", Role.USER)
            );

            assertEquals("password must be strong", ex.getMessage());
        }

        @Test
        void should_reject_weak_password() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> new User("alice@test.com", "password", Role.USER)
            );

            assertEquals("password must be strong", ex.getMessage());
        }

        @Test
        void should_accept_strong_password_without_modification() {
            String password = "Strong1!";

            User user = new User("alice@test.com", password, Role.USER);

            assertEquals(password, user.getPassword());
        }
    }

    @Nested
    @DisplayName("Role validation")
    class RoleValidation {

        @Test
        void should_reject_null_role() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> new User("alice@test.com", "Strong1!", null)
            );

            assertEquals("role must not be null", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Admin access")
    class AdminAccess {

        @Test
        void admin_should_access_admin_area() {
            User admin = new User("admin@test.com", "Strong1!", Role.ADMIN);

            assertTrue(admin.canAccessAdminArea(), "Role ADMIN should access admin area");
        }

        @Test
        void user_should_not_access_admin_area() {
            User user = new User("user@test.com", "Strong1!", Role.USER);

            assertFalse(user.canAccessAdminArea(), "Role USER should not access admin area");
        }
    }
}
