-- Désactivation temporaire des contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS = 0;

-- Suppression des données de chaque table
TRUNCATE TABLE `trigger_lead`;
TRUNCATE TABLE `customer`;
TRUNCATE TABLE `customer_login_info`;
TRUNCATE TABLE `email_template`;
TRUNCATE TABLE `employee`;
TRUNCATE TABLE `user_roles`;
TRUNCATE TABLE `roles`;
TRUNCATE TABLE `user_profile`;
TRUNCATE TABLE `oauth_users`;
TRUNCATE TABLE `users`;

-- Réactivation des contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS = 1;