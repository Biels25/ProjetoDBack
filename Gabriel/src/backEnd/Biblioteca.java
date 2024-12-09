package backEnd;

import java.sql.*;
import javax.swing.JTextArea;

public class Biblioteca {

    private static final String URL = "jdbc:mysql://localhost:3306/BibliotecaDB";
    private static final String USER = "root";
    private static final String PASSWORD = "aluno";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver do banco de dados: " + e.getMessage());
        }
    }

    public static boolean cadastrarLivro(String titulo, String autor, String isbn) {
        String sql = "INSERT INTO livros (titulo, autor, isbn, disponibilidade) VALUES (?, ?, ?, TRUE)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, autor);
            stmt.setString(3, isbn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar livro: " + e.getMessage());
            return false;
        }
    }

    public static boolean cadastrarUsuario(String nome, String numeroDeRegistro) {
        String sql = "INSERT INTO usuarios (nome, numero_de_registro) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, numeroDeRegistro);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }

    public static boolean emprestarLivro(String numeroDeRegistro, String isbn) {
        String selectUsuario = "SELECT id FROM usuarios WHERE numero_de_registro = ?";
        String selectLivro = "SELECT id, disponibilidade FROM livros WHERE isbn = ?";
        String insertEmprestimo = "INSERT INTO emprestimos (usuario_id, livro_id) VALUES (?, ?)";
        String updateLivro = "UPDATE livros SET disponibilidade = FALSE WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmtUsuario = conn.prepareStatement(selectUsuario);
             PreparedStatement stmtLivro = conn.prepareStatement(selectLivro);
             PreparedStatement stmtEmprestimo = conn.prepareStatement(insertEmprestimo);
             PreparedStatement stmtUpdateLivro = conn.prepareStatement(updateLivro)) {

            conn.setAutoCommit(false);

            // Buscar ID do usuário
            stmtUsuario.setString(1, numeroDeRegistro);
            ResultSet rsUsuario = stmtUsuario.executeQuery();
            if (!rsUsuario.next()) {
                System.err.println("Usuário não encontrado.");
                return false;
            }
            int usuarioId = rsUsuario.getInt("id");

            // Buscar ID do livro
            stmtLivro.setString(1, isbn);
            ResultSet rsLivro = stmtLivro.executeQuery();
            if (!rsLivro.next()) {
                System.err.println("Livro não encontrado.");
                return false;
            }
            int livroId = rsLivro.getInt("id");
            boolean disponibilidade = rsLivro.getBoolean("disponibilidade");

            if (!disponibilidade) {
                System.err.println("Livro indisponível para empréstimo.");
                return false;
            }

            // Inserir o empréstimo
            stmtEmprestimo.setInt(1, usuarioId);
            stmtEmprestimo.setInt(2, livroId);
            stmtEmprestimo.executeUpdate();

            // Atualizar disponibilidade do livro
            stmtUpdateLivro.setInt(1, livroId);
            stmtUpdateLivro.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao emprestar livro: " + e.getMessage());
            return false;
        }
    }

    public static void listarLivrosDisponiveis(JTextArea textArea) {
        String sql = "SELECT titulo, autor, isbn FROM livros WHERE disponibilidade = TRUE";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Título: ").append(rs.getString("titulo"))
                  .append(", Autor: ").append(rs.getString("autor"))
                  .append(", ISBN: ").append(rs.getString("isbn"))
                  .append("\n");
            }
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros disponíveis: " + e.getMessage());
        }
    }
}
