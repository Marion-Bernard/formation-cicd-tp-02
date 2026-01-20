# AI_NOTES.md

## Outil IA utilisé
- **ChatGPT-5 mini** pour générer les tests unitaires et fonctionnels en TDD.
- Aucun autre outil IA utilisé pour ce projet.

---

## Prompts utilisés

### Prompt initial
> "Je suis sur un projet Java Maven en TDD. Voici les spécifications pour le User, peux-tu générer les tests ?"

### Prompts améliorés
- "Relis ces tests : quels cas manquent ? Est-ce que les assertions sont suffisantes ?"
- "Pour le UserService, crée les tests correspondant aux spécifications."

---

## Comparaison tests sans IA / tests générés par l’IA

| Type de test | Sans IA | Avec IA |
|--------------|---------|---------|
| User (email) | Quelques cas principaux | Cas nominaux, blancs, multiples @, trim |
| User (password) | Cas faibles et forts | Cas null, vides, non conformes, forts |
| User (role) | Test null | Test null uniquement (role limité à USER/ADMIN) |
| UserService | Tests manuels de base | Propagation d’exceptions et trim sur email |

---

## Cas de tests proposés par l’IA

### Liste structurée

#### Email (User)
- **Nominal :** `alice@test.com`,
- **Limite :** `"   "`, `"alice@"`, `"alice@@test.com", "alice","@test.com","alice@test","alice@@test.com"`
- **Erreur :** `null`, email sans `@` ou sans `.` après `@`

#### Password (User)
- **Nominal :** `"Strong1!"`
- **Limite :** ' -> les tests des cas limites sont dans PasswordPolicyTest
- **Erreur :** `null`, `"   "`, `"password"`

#### Role (User)
- **Nominal :** `Role.USER`, `Role.ADMIN`
- **Erreur :** `null`
- 
#### User.canAccessAdminArea(...)
- **Nominal :** `Role.USER`, `Role.ADMIN`

#### UserService.register(...)
- **Nominal :** utilisateur valide
- **Limite :** email avec espaces → trim
- **Erreur :** email invalide, password faible, role null

---

## Analyse critique

### Tests conservés
- Cas nominaux pour email, password et role
- Trim de l’email
- Vérification de `canAccessAdminArea()`
- Propagation des exceptions dans `UserService.register`

### Tests rejetés
