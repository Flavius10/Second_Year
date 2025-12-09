-- 1. Ștergem coloana veche (asta rupe legătura veche 1-la-1)
ALTER TABLE messages DROP COLUMN receiver_id;

-- 2. Creăm tabela nouă de legătură (Many-to-Many)
CREATE TABLE message_recipients (
                                    message_id BIGINT NOT NULL,
                                    recipient_id BIGINT NOT NULL,

    -- Cheie primară compusă (evită duplicatele)
                                    CONSTRAINT pk_message_recipients PRIMARY KEY (message_id, recipient_id),

    -- Dacă ștergi un mesaj, se șterg și destinatarii din această tabelă
                                    CONSTRAINT fk_mr_message
                                        FOREIGN KEY (message_id)
                                            REFERENCES messages(id)
                                            ON DELETE CASCADE,

    -- Dacă ștergi un user, dispare din listele de destinatari
                                    CONSTRAINT fk_mr_user
                                        FOREIGN KEY (recipient_id)
                                            REFERENCES users(id)
                                            ON DELETE CASCADE
);