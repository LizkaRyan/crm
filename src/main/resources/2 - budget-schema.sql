CREATE TABLE budget
(
    id_budget   INT AUTO_INCREMENT,
    name        VARCHAR(50)    NOT NULL,
    budget      DECIMAL(15, 2) NOT NULL,
    customer_id INT            NOT NULL,
    PRIMARY KEY (id_budget),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

CREATE TABLE depense
(
    id_depense INT AUTO_INCREMENT,
    amount     DECIMAL(15, 2) NOT NULL,
    ticket_id  INT unsigned,
    id_budget  INT            NOT NULL,
    lead_id    int unsigned,
    PRIMARY KEY (id_depense),
    FOREIGN KEY (ticket_id) REFERENCES trigger_ticket (ticket_id),
    FOREIGN KEY (id_budget) REFERENCES budget (id_budget),
    FOREIGN KEY (lead_id) REFERENCES trigger_lead (lead_id)
);

CREATE TABLE seuil_budget
(
    id_seuil_budget INT AUTO_INCREMENT,
    taux_seuil      DECIMAL(5, 2) NOT NULL,
    date_seuil      DATETIME      NOT NULL,
    PRIMARY KEY (id_seuil_budget)
);

INSERT INTO seuil_budget(taux_seuil,date_seuil) values(60,'2024-10-10 10:10');