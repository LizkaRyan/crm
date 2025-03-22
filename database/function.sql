DELIMITER //
CREATE PROCEDURE deleteAllUselessData()
BEGIN
SET FOREIGN_KEY_CHECKS = 0;

delete from contract_settings;
delete from email_template;
delete from ticket_settings;
delete from lead_settings;
delete from trigger_ticket;
delete from employee;
delete from trigger_lead;
delete from trigger_contract;
delete from lead_action;
delete from file;
delete from google_drive_file;

ALTER TABLE contract_settings AUTO_INCREMENT=1;
ALTER TABLE email_template AUTO_INCREMENT=1;
ALTER TABLE ticket_settings AUTO_INCREMENT=1;
ALTER TABLE lead_settings AUTO_INCREMENT=1;
ALTER TABLE trigger_ticket AUTO_INCREMENT=1;
ALTER TABLE employee AUTO_INCREMENT=1;
ALTER TABLE trigger_lead AUTO_INCREMENT=1;
ALTER TABLE trigger_contract AUTO_INCREMENT=1;
ALTER TABLE lead_action AUTO_INCREMENT=1;
ALTER TABLE file AUTO_INCREMENT=1;
ALTER TABLE google_drive_file AUTO_INCREMENT=1;

SET FOREIGN_KEY_CHECKS = 1;
END //
DELIMITER ;