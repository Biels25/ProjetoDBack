package backEnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Application {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver do banco de dados: " + e.getMessage());
        }

        // Criar a janela principal
        JFrame frame = new JFrame("Biblioteca Virtual");
        frame.getContentPane().setLayout(new BorderLayout());

        // Criar um painel com botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botão "Emprestimo"
        JButton loanButton = new JButton("Emprestimo");
        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoanWindow(); // Abre a janela de empréstimo
            }
        });

        // Botão "Cadastrar Livro"
        JButton registerBookButton = new JButton("Cadastrar Livro");
        registerBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterBookWindow(); // Abre a janela de cadastro de livro
            }
        });

        // Botão "Cadastrar Usuário"
        JButton registerUserButton = new JButton("Cadastrar Usuário");
        registerUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterUserWindow(); // Abre a janela de cadastro de usuário
            }
        });

        // Botão "Ver Livros"
        JButton booksButton = new JButton("Ver Livros");
        booksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BooksWindow(); // Abre a janela de visualização de livros
            }
        });

        // Botão "Devolver Livro"
        JButton returnButton = new JButton("Devolver Livro");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReturnWindow(); // Abre a janela de devolução de livro
            }
        });

        // Botão "Sair"
        JButton exitButton = new JButton("Sair");
        exitButton.addActionListener(e -> System.exit(0));

        // Adicionar os botões ao painel
        buttonPanel.add(loanButton);
        buttonPanel.add(registerBookButton);
        buttonPanel.add(registerUserButton);
        buttonPanel.add(booksButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(exitButton);

        // Adicionar o painel de botões à janela
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Configurar a janela principal
        frame.setSize(658, 413); // Tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportamento ao fechar
        frame.setLocationRelativeTo(null); // Centralizar a janela
        frame.setVisible(true); // Tornar a janela visível
    }
}

class LoanWindow {
    public LoanWindow() {
        // Criar a janela de empréstimo
        JFrame loanFrame = new JFrame("Empréstimo de Livro");
        loanFrame.setLayout(new GridLayout(4, 2));

        JLabel userIdLabel = new JLabel("Registro do Usuário:");
        JTextField userIdField = new JTextField();

        JLabel isbnLabel = new JLabel("ISBN do Livro:");
        JTextField isbnField = new JTextField();

        JButton borrowButton = new JButton("Emprestar");
        borrowButton.addActionListener(e -> {
            String userId = userIdField.getText().trim();
            String isbn = isbnField.getText().trim();

            if (userId.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(loanFrame, "Por favor, preencha todos os campos.");
            } else {
                if (Biblioteca.emprestarLivro(userId, isbn)) {
                    JOptionPane.showMessageDialog(loanFrame, "Empréstimo realizado com sucesso!");
                    userIdField.setText("");  // Limpa os campos
                    isbnField.setText("");
                    loanFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(loanFrame, "Erro ao realizar empréstimo. Verifique os dados.");
                }
            }
        });

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(e -> loanFrame.dispose());

        loanFrame.add(userIdLabel);
        loanFrame.add(userIdField);
        loanFrame.add(isbnLabel);
        loanFrame.add(isbnField);
        loanFrame.add(borrowButton);
        loanFrame.add(backButton);

        loanFrame.setSize(400, 200);
        loanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loanFrame.setLocationRelativeTo(null);
        loanFrame.setVisible(true);
    }
}


class BooksWindow {
    public BooksWindow() {
        JFrame booksFrame = new JFrame("Livros Disponíveis");
        booksFrame.setLayout(new BorderLayout());

        JTextArea bookListTextArea = new JTextArea(10, 30);
        bookListTextArea.setEditable(false);
        JScrollPane bookListScrollPane = new JScrollPane(bookListTextArea);

        Biblioteca.listarLivrosDisponiveis(bookListTextArea);

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(e -> booksFrame.dispose());

        booksFrame.add(bookListScrollPane, BorderLayout.CENTER);
        booksFrame.add(backButton, BorderLayout.SOUTH);

        booksFrame.setSize(400, 300);
        booksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        booksFrame.setLocationRelativeTo(null);
        booksFrame.setVisible(true);
    }
}

class RegisterBookWindow {
    public RegisterBookWindow() {
        JFrame registerBookFrame = new JFrame("Cadastrar Livro");
        registerBookFrame.setLayout(new GridLayout(4, 2));

        JLabel titleLabel = new JLabel("Título:");
        JTextField titleField = new JTextField();

        JLabel authorLabel = new JLabel("Autor:");
        JTextField authorField = new JTextField();

        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField();

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            String titulo = titleField.getText();
            String autor = authorField.getText();
            String isbn = isbnField.getText();

            if (Biblioteca.cadastrarLivro(titulo, autor, isbn)) {
                JOptionPane.showMessageDialog(registerBookFrame, "Livro cadastrado com sucesso!");
                registerBookFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(registerBookFrame, "Erro ao cadastrar livro. Verifique os dados.");
            }
        });

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(e -> registerBookFrame.dispose());

        registerBookFrame.add(titleLabel);
        registerBookFrame.add(titleField);
        registerBookFrame.add(authorLabel);
        registerBookFrame.add(authorField);
        registerBookFrame.add(isbnLabel);
        registerBookFrame.add(isbnField);
        registerBookFrame.add(saveButton);
        registerBookFrame.add(backButton);

        registerBookFrame.setSize(400, 200);
        registerBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerBookFrame.setLocationRelativeTo(null);
        registerBookFrame.setVisible(true);
    }
}

class RegisterUserWindow {
    public RegisterUserWindow() {
        JFrame registerUserFrame = new JFrame("Cadastrar Usuário");
        registerUserFrame.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Nome:");
        JTextField nameField = new JTextField();

        JLabel registrationLabel = new JLabel("Registro:");
        JTextField registrationField = new JTextField();

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            String nome = nameField.getText();
            String registro = registrationField.getText();

            if (Biblioteca.cadastrarUsuario(nome, registro)) {
                JOptionPane.showMessageDialog(registerUserFrame, "Usuário cadastrado com sucesso!");
                registerUserFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(registerUserFrame, "Erro ao cadastrar usuário. Verifique os dados.");
            }
        });

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(e -> registerUserFrame.dispose());

        registerUserFrame.add(nameLabel);
        registerUserFrame.add(nameField);
        registerUserFrame.add(registrationLabel);
        registerUserFrame.add(registrationField);
        registerUserFrame.add(saveButton);
        registerUserFrame.add(backButton);

        registerUserFrame.setSize(400, 200);
        registerUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerUserFrame.setLocationRelativeTo(null);
        registerUserFrame.setVisible(true);
    }
}

class ReturnWindow {
    public ReturnWindow() {
        // Criar a janela de devolução
        JFrame returnFrame = new JFrame("Devolução de Livro");
        returnFrame.setLayout(new GridLayout(4, 2));

        JLabel userIdLabel = new JLabel("Registro do Usuário:");
        JTextField userIdField = new JTextField();

        JLabel isbnLabel = new JLabel("ISBN do Livro:");
        JTextField isbnField = new JTextField();

        JButton returnButton = new JButton("Devolver");
        returnButton.addActionListener(e -> {
            String userId = userIdField.getText().trim();
            String isbn = isbnField.getText().trim();

            if (userId.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(returnFrame, "Por favor, preencha todos os campos.");
            } else {
                // Tenta devolver o livro
                if (Biblioteca.devolverLivro(userId, isbn)) {
                    JOptionPane.showMessageDialog(returnFrame, "Livro devolvido com sucesso!");
                    userIdField.setText("");  // Limpa os campos
                    isbnField.setText("");
                    returnFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(returnFrame, "Erro ao devolver livro. Verifique os dados.");
                }
            }
        });

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(e -> returnFrame.dispose());

        returnFrame.add(userIdLabel);
        returnFrame.add(userIdField);
        returnFrame.add(isbnLabel);
        returnFrame.add(isbnField);
        returnFrame.add(returnButton);
        returnFrame.add(backButton);

        returnFrame.setSize(400, 200);
        returnFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        returnFrame.setLocationRelativeTo(null);
        returnFrame.setVisible(true);
    }
}


