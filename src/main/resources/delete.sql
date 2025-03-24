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
delete from `users` where id != 52;

ALTER TABLE users AUTO_INCREMENT=53;
ALTER TABLE customer_login_info AUTO_INCREMENT=19;
ALTER TABLE customer AUTO_INCREMENT=43;
ALTER TABLE trigger_lead AUTO_INCREMENT=56;
ALTER TABLE trigger_ticket AUTO_INCREMENT=47;
ALTER TABLE trigger_contract AUTO_INCREMENT=19;

-- Réactivation des contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS = 1;