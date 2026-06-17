-- Database name: DB_GAMELOG
-- GameLog - Fase 1

-- Tabela de utilizadores do sistema
CREATE TABLE usuario (
    id SERIAL NOT NULL,
    nome TEXT NOT NULL,
    email TEXT NOT NULL,
    senha TEXT NOT NULL,
    datahorareg TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id),
    CONSTRAINT uq_usuario_email UNIQUE (email)
);

-- Tabela principal de jogos (mantida para compatibilidade de dados históricos)
CREATE TABLE jogo (
    id SERIAL NOT NULL,
    titulo TEXT NOT NULL,
    plataforma TEXT NOT NULL,
    genero TEXT NOT NULL,
    formato TEXT NOT NULL,
    avaliacao INTEGER,
    observacao TEXT,
    datahorareg TIMESTAMP WITH TIME ZONE NOT NULL,
    id_usuario INTEGER NOT NULL,
    CONSTRAINT pk_jogo PRIMARY KEY (id),
    CONSTRAINT fk_jogo_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Tabela de backlog (controle de progresso dos jogos)
-- status: 'Por Jogar' | 'A Jogar' | 'Concluído' | 'Platinado'
CREATE TABLE backlog (
    id SERIAL NOT NULL,
    titulo_jogo TEXT NOT NULL,
    status TEXT NOT NULL,
    horas_jogadas INTEGER,
    avaliacao INTEGER CHECK (avaliacao >= 1 AND avaliacao <= 10),
    observacao TEXT,
    capa_url TEXT,
    review TEXT,
    datahorareg TIMESTAMP WITH TIME ZONE NOT NULL,
    data_inicio DATE,
    data_termino DATE,
    id_usuario INTEGER NOT NULL,
    CONSTRAINT pk_backlog PRIMARY KEY (id),
    CONSTRAINT fk_backlog_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Tabela de empréstimos
CREATE TABLE emprestimo (
    id SERIAL NOT NULL,
    titulo_jogo TEXT NOT NULL,
    nome_amigo TEXT NOT NULL,
    data_emprestimo DATE NOT NULL,
    previsao_devolucao DATE,
    devolvido BOOLEAN NOT NULL DEFAULT FALSE,
    observacao TEXT,
    datahorareg TIMESTAMP WITH TIME ZONE NOT NULL,
    id_usuario INTEGER NOT NULL,
    CONSTRAINT pk_emprestimo PRIMARY KEY (id),
    CONSTRAINT fk_emprestimo_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- Tabela de wishlist (lista de desejos)
CREATE TABLE wishlist (
    id SERIAL NOT NULL,
    titulo TEXT NOT NULL,
    plataforma TEXT NOT NULL,
    prioridade TEXT NOT NULL,
    observacao TEXT,
    datahorareg TIMESTAMP WITH TIME ZONE NOT NULL,
    id_usuario INTEGER NOT NULL,
    CONSTRAINT pk_wishlist PRIMARY KEY (id),
    CONSTRAINT fk_wishlist_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

-- ========================
-- DADOS INICIAIS DE TESTE
-- ========================

-- Utilizador de teste
INSERT INTO usuario (nome, email, senha, datahorareg)
VALUES ('Manuela Fortes', 'manuela@gmail.com', '123', NOW());

ALTER SEQUENCE usuario_id_seq RESTART WITH 2;

-- Backlog de exemplo com capas
INSERT INTO backlog (titulo_jogo, status, horas_jogadas, avaliacao, observacao, capa_url, datahorareg, data_inicio, data_termino, id_usuario)
VALUES ('The Last of Us Part II', 'Concluido', 25, 10, 'Obra-prima absoluta',
        'https://image.api.playstation.com/vulcan/ap/rnd/202306/0219/1c5f5e12fb30a8ddf98d53e574b3bcc2f3a0b7dbe37cb1c7.png',
        NOW(), '2026-01-05', '2026-01-20', 1);

INSERT INTO backlog (titulo_jogo, status, horas_jogadas, avaliacao, observacao, capa_url, datahorareg, data_inicio, data_termino, id_usuario)
VALUES ('Hollow Knight', 'Platinado', 55, 9, 'Dificil mas incrivel',
        'https://m.media-amazon.com/images/I/81MEojPgcML._AC_UF894,1000_QL80_.jpg',
        NOW(), '2025-10-01', '2025-12-15', 1);

INSERT INTO backlog (titulo_jogo, status, observacao, capa_url, datahorareg, id_usuario)
VALUES ('Red Dead Redemption 2', 'A Jogar', 'Na fila para jogar',
        'https://image.api.playstation.com/cdn/UP1004/CUSA03041_00/Hpl5MtwQgOVF9vJqlfui6SDB5Jl4oBSq.png',
        NOW(), 1);

INSERT INTO backlog (titulo_jogo, status, observacao, capa_url, datahorareg, id_usuario)
VALUES ('Elden Ring', 'Por Jogar', 'Comprei na promocao',
        'https://image.api.playstation.com/vulcan/ap/rnd/202110/2000/aGhopp3MHppi7kooGE2Dtt8C.png',
        NOW(), 1);

ALTER SEQUENCE backlog_id_seq RESTART WITH 5;

-- Empréstimo de exemplo
INSERT INTO emprestimo (titulo_jogo, nome_amigo, data_emprestimo, previsao_devolucao, devolvido, observacao, datahorareg, id_usuario)
VALUES ('Hollow Knight', 'João Silva', '2026-01-10', '2026-02-10', FALSE, 'Emprestei para meu amigo João', NOW(), 1);

ALTER SEQUENCE emprestimo_id_seq RESTART WITH 2;

-- Wishlist de exemplo
INSERT INTO wishlist (titulo, plataforma, prioridade, observacao, datahorareg, id_usuario)
VALUES ('God of War Ragnarök', 'PlayStation 5', 'Alta', 'Continuação incrível', NOW(), 1);

INSERT INTO wishlist (titulo, plataforma, prioridade, observacao, datahorareg, id_usuario)
VALUES ('Cyberpunk 2077', 'PC', 'Média', 'Agora que corrigiram os bugs', NOW(), 1);

ALTER SEQUENCE wishlist_id_seq RESTART WITH 3;

ALTER TABLE backlog ADD COLUMN IF NOT EXISTS review TEXT;
ALTER TABLE backlog ADD COLUMN IF NOT EXISTS capa_url TEXT;
-- Colunas de perfil do usuario (adicionadas na Fase 4)
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS apelido TEXT;
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS bio TEXT;
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS foto_perfil TEXT;
