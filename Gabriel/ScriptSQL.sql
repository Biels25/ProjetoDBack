-- Criação do banco de dados
CREATE DATABASE BibliotecaDB;

-- Seleciona o banco de dados a ser usado
USE BibliotecaDB;

-- Criação da tabela para armazenar os livros
CREATE TABLE livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    disponibilidade BOOLEAN DEFAULT TRUE
);

-- Criação da tabela para armazenar os usuários
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    numero_de_registro VARCHAR(20) UNIQUE NOT NULL
);

-- Criação da tabela para registrar os empréstimos ativos
CREATE TABLE emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    livro_id INT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (livro_id) REFERENCES livros(id)
);

-- Criação da tabela para armazenar o histórico de empréstimos
CREATE TABLE historico_emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    livro_id INT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (livro_id) REFERENCES livros(id)
);

-- Inserção de dados de exemplo na tabela de livros
INSERT INTO livros (titulo, autor, isbn, disponibilidade) VALUES
('1984', 'George Orwell', '9780451524935', TRUE),
('Dom Quixote', 'Miguel de Cervantes', '9788491050533', TRUE),
('O Senhor dos Anéis', 'J.R.R. Tolkien', '9780544003415', TRUE);

-- Inserção de dados de exemplo na tabela de usuários
INSERT INTO usuarios (nome, numero_de_registro) VALUES
('João Silva', 'USR001'),
('Maria Oliveira', 'USR002'),
('Carlos Santos', 'USR003');
