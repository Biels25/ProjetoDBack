package backEnd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.DB;

public class Biblioteca {
    private ArrayList<Livro> listaDeLivro;
    private ArrayList<Usuario> listaDeUsuario;

    public Biblioteca() {
        listaDeLivro = new ArrayList<>();
        listaDeUsuario = new ArrayList<>();
        carregarListaDeLivro(); 
        carregarListaDeUsuario();
    }

    // Método para carregar a lista de livros a partir do banco de dados
    private void carregarListaDeLivro() {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT * FROM livros";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String isbn = rs.getString("isbn");
                    boolean disponibilidade = rs.getBoolean("disponibilidade");
                    Livro livro = new Livro(titulo, autor, isbn);
                    livro.alterarDisponibilidade();  // Atualiza a disponibilidade conforme o banco
                    listaDeLivro.add(livro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar a lista de usuários a partir do banco de dados
    private void carregarListaDeUsuario() {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT * FROM usuarios";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String numeroDeRegistro = rs.getString("numero_de_registro");
                    Usuario usuario = new Usuario(nome, numeroDeRegistro);
                    listaDeUsuario.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cadastrar um livro no banco de dados
    public boolean cadastrarLivro(String titulo, String autor, String ISBN) {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT COUNT(*) FROM livros WHERE isbn = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, ISBN);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {  // Livro já existe
                    return false;
                }
            }

            String insert = "INSERT INTO livros (titulo, autor, isbn) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                stmt.setString(1, titulo);
                stmt.setString(2, autor);
                stmt.setString(3, ISBN);
                stmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para cadastrar um usuário no banco de dados
    public boolean cadastrarUsuario(String nome, String numeroDeRegistro) {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT COUNT(*) FROM usuarios WHERE numero_de_registro = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, numeroDeRegistro);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {  // Usuário já existe
                    return false;
                }
            }

            String insert = "INSERT INTO usuarios (nome, numero_de_registro) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                stmt.setString(1, nome);
                stmt.setString(2, numeroDeRegistro);
                stmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar livros disponíveis
    public void livrosDisponiveis() {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT * FROM livros WHERE disponibilidade = TRUE";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String isbn = rs.getString("isbn");
                    boolean disponibilidade = rs.getBoolean("disponibilidade");
                    System.out.println("Título: " + titulo);
                    System.out.println("Autor: " + autor);
                    System.out.println("ISBN: " + isbn);
                    System.out.println("Disponibilidade: " + (disponibilidade ? "Disponível" : "Indisponível"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar usuários
    public void usuariosDisponiveis() {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT * FROM usuarios";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String numeroDeRegistro = rs.getString("numero_de_registro");
                    System.out.println("Nome: " + nome);
                    System.out.println("Número de Registro: " + numeroDeRegistro);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para emprestar um livro
    public boolean emprestarLivro(String numeroDeRegistro, String ISBN) {
        try (Connection conn = DB.getConnection()) {
            // Verifica se o livro existe e está disponível
            String queryLivro = "SELECT id, disponibilidade FROM livros WHERE isbn = ?";
            try (PreparedStatement stmt = conn.prepareStatement(queryLivro)) {
                stmt.setString(1, ISBN);
                ResultSet rsLivro = stmt.executeQuery();
                if (!rsLivro.next() || !rsLivro.getBoolean("disponibilidade")) {
                    return false;  // Livro não encontrado ou indisponível
                }
                int livroId = rsLivro.getInt("id");

                // Verifica se o usuário existe
                String queryUsuario = "SELECT id FROM usuarios WHERE numero_de_registro = ?";
                try (PreparedStatement stmtUsuario = conn.prepareStatement(queryUsuario)) {
                    stmtUsuario.setString(1, numeroDeRegistro);
                    ResultSet rsUsuario = stmtUsuario.executeQuery();
                    if (!rsUsuario.next()) {
                        return false;  // Usuário não encontrado
                    }
                    int usuarioId = rsUsuario.getInt("id");

                    // Registra o empréstimo
                    String insertEmprestimo = "INSERT INTO emprestimos (usuario_id, livro_id) VALUES (?, ?)";
                    try (PreparedStatement stmtEmprestimo = conn.prepareStatement(insertEmprestimo)) {
                        stmtEmprestimo.setInt(1, usuarioId);
                        stmtEmprestimo.setInt(2, livroId);
                        stmtEmprestimo.executeUpdate();
                    }

                    // Atualiza a disponibilidade do livro
                    String updateLivro = "UPDATE livros SET disponibilidade = FALSE WHERE id = ?";
                    try (PreparedStatement stmtUpdate = conn.prepareStatement(updateLivro)) {
                        stmtUpdate.setInt(1, livroId);
                        stmtUpdate.executeUpdate();
                    }
                    return true;  // Empréstimo realizado com sucesso
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

