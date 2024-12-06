package backEnd;

import java.sql.*;
import db.DB;

public class Biblioteca {
    public static boolean cadastrarLivro(String titulo, String autor, String ISBN) {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT COUNT(*) FROM livros WHERE isbn = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, ISBN);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    return false;  // Livro já existe
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

    public static boolean cadastrarUsuario(String nome, String numeroDeRegistro) {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT COUNT(*) FROM usuarios WHERE numero_de_registro = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, numeroDeRegistro);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    return false;  // Usuário já existe
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

    public static boolean emprestarLivro(String numeroDeRegistro, String ISBN) {
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

                    // Registra o empréstimo com a data de empréstimo
                    String insertEmprestimo = "INSERT INTO emprestimos (usuario_id, livro_id, data_emprestimo) VALUES (?, ?, CURRENT_TIMESTAMP)";
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

    public static boolean devolverLivro(String numeroDeRegistro, String ISBN) {
        try (Connection conn = DB.getConnection()) {
            // Verifica se o empréstimo existe
            String queryEmprestimo = """
                SELECT emprestimos.id AS emprestimo_id, livros.id AS livro_id
                FROM emprestimos
                INNER JOIN livros ON emprestimos.livro_id = livros.id
                INNER JOIN usuarios ON emprestimos.usuario_id = usuarios.id
                WHERE livros.isbn = ? AND usuarios.numero_de_registro = ? AND emprestimos.data_devolucao IS NULL
                """;
            try (PreparedStatement stmt = conn.prepareStatement(queryEmprestimo)) {
                stmt.setString(1, ISBN);
                stmt.setString(2, numeroDeRegistro);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    return false;  // Nenhum empréstimo encontrado ou livro já foi devolvido
                }

                int emprestimoId = rs.getInt("emprestimo_id");
                int livroId = rs.getInt("livro_id");

                // Atualiza a data de devolução no empréstimo
                String updateEmprestimo = "UPDATE emprestimos SET data_devolucao = CURRENT_TIMESTAMP WHERE id = ?";
                try (PreparedStatement stmtUpdate = conn.prepareStatement(updateEmprestimo)) {
                    stmtUpdate.setInt(1, emprestimoId);
                    stmtUpdate.executeUpdate();
                }

                // Atualiza a disponibilidade do livro
                String updateLivro = "UPDATE livros SET disponibilidade = TRUE WHERE id = ?";
                try (PreparedStatement stmtUpdate = conn.prepareStatement(updateLivro)) {
                    stmtUpdate.setInt(1, livroId);
                    stmtUpdate.executeUpdate();
                }

                return true;  // Devolução realizada com sucesso
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void listarLivrosDisponiveis() {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT * FROM livros WHERE disponibilidade = TRUE";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Título: " + rs.getString("titulo"));
                    System.out.println("Autor: " + rs.getString("autor"));
                    System.out.println("ISBN: " + rs.getString("isbn"));
                    System.out.println("Disponibilidade: Disponível");
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarUsuarios() {
        try (Connection conn = DB.getConnection()) {
            String query = "SELECT * FROM usuarios";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Número de Registro: " + rs.getString("numero_de_registro"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}