CREATE TABLE budget
(
    id_budget   INT AUTO_INCREMENT,
    name        VARCHAR(50)    NOT NULL,
    budget      DECIMAL(15, 2) NOT NULL,
    customer_id INT unsigned   NOT NULL,
    PRIMARY KEY (id_budget),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

CREATE TABLE expense
(
    id_expense  INT AUTO_INCREMENT,
    amount      DECIMAL(15, 2) NOT NULL,
    customer_id INT unsigned   NOT NULL,
    ticket_id   INT unsigned,
    lead_id     INT unsigned,
    PRIMARY KEY (id_expense),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    FOREIGN KEY (ticket_id) REFERENCES trigger_ticket (ticket_id),
    FOREIGN KEY (lead_id) REFERENCES trigger_lead (lead_id)
);

CREATE TABLE seuil_budget
(
    id_seuil_budget INT AUTO_INCREMENT,
    taux_seuil      DECIMAL(5, 2) NOT NULL,
    date_seuil      DATETIME      NOT NULL,
    PRIMARY KEY (id_seuil_budget)
);

CREATE TABLE token_api
(
    id_token_api    INT AUTO_INCREMENT,
    token           VARCHAR(100) NOT NULL,
    date_expiration DATETIME     NOT NULL,
    PRIMARY KEY (id_token_api),
    UNIQUE (token)
);

INSERT INTO seuil_budget(taux_seuil, date_seuil)
values (60, '2024-10-10 10:10');