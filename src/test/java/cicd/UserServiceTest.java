package cicd;

import org.junit.jupiter.api.Test;
import com.devops.cicd.user.Role;
import com.devops.cicd.user.User;
import com.devops.cicd.user.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private final UserService userService = new UserService();

    @Test
    void should_register_valid_user() {
        User user = userService.register(
                "alice@test.com",
                "Strong1!",
                Role.USER
        );

        assertNotNull(user);
        assertEquals("alice@test.com", user.getEmail());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void should_trim_email_on_registration() {
        User user = userService.register(
                "  alice@test.com  ",
                "Strong1!",
                Role.USER
        );

        assertEquals("alice@test.com", user.getEmail());
    }

    @Test
    void should_propagate_exception_for_invalid_email() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userService.register("invalid", "Strong1!", Role.USER)
        );

        assertEquals("email must be valid", ex.getMessage());
    }

    @Test
    void should_propagate_exception_for_weak_password() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userService.register("alice@test.com", "password", Role.USER)
        );

        assertEquals("password must be strong", ex.getMessage());
    }

    @Test
    void should_propagate_exception_for_null_role() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> userService.register("alice@test.com", "Strong1!", null)
        );

        assertEquals("role must not be null", ex.getMessage());
    }

    @Test
    void should_store_password_as_is() {
        String pwd = "Strong1!";
        User user = userService.register("bob@test.com", pwd, Role.ADMIN);
        assertEquals(pwd, user.getPassword());
    }

    @Test
    void admin_should_have_admin_access() {
        User admin = userService.register("admin@test.com", "Strong1!", Role.ADMIN);
        assertTrue(admin.canAccessAdminArea());
    }
}

