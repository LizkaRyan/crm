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

SET FOREIGN_KEY_CHECKS = 1;
END //
DELIMITER ;