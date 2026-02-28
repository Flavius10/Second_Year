CREATE TABLE request(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sender VARCHAR(50),
    receiver VARCHAR(50),
    status VARCHAR(50)
);


CREATE TABLE request_user(
    id_request INT NOT NULL,
    id_user INT NOT NULL,

    CONSTRAINT pk_user_request PRIMARY KEY (id_request, id_user),
    CONSTRAINT fk_id_user FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_id_request FOREIGN KEY (id_request) REFERENCES request(id) ON DELETE CASCADE
)