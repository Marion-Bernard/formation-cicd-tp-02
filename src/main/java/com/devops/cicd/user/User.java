package com.devops.cicd.user;

import com.devops.cicd.PasswordPolicy;

public class User {

    private final String email;
    private final String password;
    private final Role role;

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;

        // Validation email
        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            throw new IllegalArgumentException("email must be valid");
        }
        // Validation password
        if (password == null || password.trim().isEmpty() || !PasswordPolicy.isStrong(password)) {
            throw new IllegalArgumentException("password must be strong");
        }
        //Validation Role
        if (role == null) {
            throw new IllegalArgumentException("role must not be null");
        }

        email = email.trim();


    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean canAccessAdminArea() {
        return role == Role.ADMIN;
    }

    // Méthode utilitaire pour valider l’email
    private boolean isValidEmail(String email) {
        String trimmed = email.trim();
        int atIndex = trimmed.indexOf("@");
        return atIndex > 0 &&
            trimmed.indexOf("@", atIndex + 1) == -1 &&  // un seul @
            trimmed.indexOf(".", atIndex) > atIndex + 1; // au moins un . après @
    }

    // BONUS: vous pouvez ajouter equals/hashCode/toString si utile (non obligatoire)
}
