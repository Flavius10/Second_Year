-- 1. Ștergem tabela greșită
DROP TABLE IF EXISTS race_event_duck;

-- 2. O recreăm corect (FĂRĂ coloana 'id')
CREATE TABLE race_event_duck (
                                 event_id INT,
                                 duck_id INT,

    -- Cheia primară este combinația celor două
                                 PRIMARY KEY (event_id, duck_id),

    -- Constrângerile Foreign Key
                                 FOREIGN KEY (event_id) REFERENCES race_event(id) ON DELETE CASCADE,
                                 FOREIGN KEY (duck_id) REFERENCES duck(id) ON DELETE CASCADE
);