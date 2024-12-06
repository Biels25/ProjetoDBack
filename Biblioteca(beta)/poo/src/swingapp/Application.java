package swingapp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import java.util.ArrayList;

public class Application {
    static ArrayList<String> books = new ArrayList<>();
	protected static Object users;

    public static void main(String[] args) {
        // Criar a janela principal
        JFrame frame = new JFrame("Biblioteca Virtual");

        // Definir o layout da janela
        frame.getContentPane().setLayout(new BorderLayout());

        // Criar um painel com botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botão "Emprestimo"
        JButton loanButton = new JButton("Emprestimo");
        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar, abre a janela de empréstimo
                new LoanWindow();
            }
        });

        // Botão "Salvar"
        JButton saveButton = new JButton("Salvar Dados");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar em salvar, exibe uma mensagem
                JOptionPane.showMessageDialog(frame, "Dados salvos com sucesso!");
            }
        });

        // Botão "Sair"
        JButton exitButton = new JButton("Sair");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar em sair, fecha o aplicativo
                System.exit(0);
            }
        });

        // Botão "Ver Livros"
        JButton booksButton = new JButton("Ver Livros");
        booksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar, abre a nova janela mostrando a lista de livros
                new BooksWindow();
            }
        });

        // Botão "Cadastrar Usuário"
        JButton registerUserButton = new JButton("Cadastrar Usuário");
        registerUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar, abre a janela de cadastro de usuário
                new RegisterUserWindow();
            }
        });

        // Adicionar os botões ao painel
        buttonPanel.add(loanButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(booksButton);
        buttonPanel.add(registerUserButton);

        // Adicionar o painel de botões à janela
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Configurar a janela principal
        frame.setSize(658, 413); // Tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportamento ao fechar
        frame.setLocationRelativeTo(null); // Centralizar a janela
        frame.setVisible(true); // Tornar a janela visível
    }

    // Método para atualizar a lista de livros exibida na página principal
    public static void updateBookList(JTextArea textArea) {
        StringBuilder booksList = new StringBuilder("Livros Disponíveis:\n");
        for (String book : books) {
            booksList.append(book).append("\n");
        }
        textArea.setText(booksList.toString());
    }
}

class LoanWindow {
    public LoanWindow() {
        // Criar a janela de empréstimo
        JFrame loanFrame = new JFrame("Empréstimo de Livro");

        // Definir o layout da janela
        loanFrame.setLayout(new GridLayout(4, 2));

        // Criar os campos de texto e labels
        JLabel userIdLabel = new JLabel("ID do Usuário:");
        JTextField userIdField = new JTextField(20);

        JLabel isbnLabel = new JLabel("ISBN do Livro:");
        JTextField isbnField = new JTextField(20);

        // Botão para realizar o empréstimo
        JButton borrowButton = new JButton("Emprestar");
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar em emprestar, exibe uma mensagem de sucesso
                String userId = userIdField.getText();
                String isbn = isbnField.getText();
                if (!userId.isEmpty() && !isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(loanFrame, "Empréstimo realizado com sucesso!\nUsuário ID: " + userId + "\nISBN: " + isbn);
                    loanFrame.dispose(); // Fecha a janela após o empréstimo
                } else {
                    JOptionPane.showMessageDialog(loanFrame, "Por favor, preencha todos os campos.");
                }
            }
        });

        // Botão para voltar à página principal
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loanFrame.dispose(); // Fecha a janela de empréstimo
            }
        });

        // Adicionar os componentes à janela
        loanFrame.add(userIdLabel);
        loanFrame.add(userIdField);
        loanFrame.add(isbnLabel);
        loanFrame.add(isbnField);
        loanFrame.add(borrowButton);
        loanFrame.add(backButton);

        // Configurar a nova janela
        loanFrame.setSize(400, 200);
        loanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha somente esta janela
        loanFrame.setLocationRelativeTo(null); // Centralizar a janela
        loanFrame.setVisible(true); // Tornar a nova janela visível
    }
}

class BooksWindow {
    public BooksWindow() {
        // Criar a janela de livros
        JFrame booksFrame = new JFrame("Livros Disponíveis");

        // Definir o layout da janela
        booksFrame.setLayout(new BorderLayout());

        // Criar área de texto para mostrar os livros
        JTextArea bookListTextArea = new JTextArea(10, 30);
        bookListTextArea.setEditable(false); // Tornar a área de texto somente leitura
        JScrollPane bookListScrollPane = new JScrollPane(bookListTextArea);

        // Atualizar a lista de livros na área de texto
        Application.updateBookList(bookListTextArea);

        // Botão para cadastrar um novo livro
        JButton registerBookButton = new JButton("Cadastrar Livro");
        registerBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterBookWindow(); // Abre a janela de cadastro de livro
            }
        });

        // Adicionar os componentes à janela
        booksFrame.add(bookListScrollPane, BorderLayout.CENTER);
        booksFrame.add(registerBookButton, BorderLayout.SOUTH);

        // Configurar a nova janela
        booksFrame.setSize(400, 300);
        booksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha somente esta janela
        booksFrame.setLocationRelativeTo(null); // Centralizar a janela
        booksFrame.setVisible(true); // Tornar a nova janela visível
    }
}

class RegisterUserWindow {
    public RegisterUserWindow() {
        // Criar a janela de cadastro de usuário
        JFrame registerUserFrame = new JFrame("Cadastrar Usuário");

        // Definir o layout da janela
        registerUserFrame.setLayout(new GridLayout(3, 2));

        // Criar campos de texto para nome e ID
        JLabel nameLabel = new JLabel("Nome do Usuário:");
        JTextField nameField = new JTextField(20);

        JLabel idLabel = new JLabel("ID do Usuário:");
        JTextField idField = new JTextField(20);

        // Botão para salvar o cadastro
        JButton saveButton = new JButton("Salvar Cadastro");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Adicionar o nome e o ID à lista de usuários
                String user = nameField.getText() + " - ID: " + idField.getText();
                ((Container) Application.users).add(user);
                JOptionPane.showMessageDialog(registerUserFrame, "Usuário cadastrado com sucesso!");
                registerUserFrame.dispose(); // Fecha a janela de cadastro de usuário
            }
        });

        // Botão para voltar à página principal
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUserFrame.dispose(); // Fecha a janela de cadastro de usuário
            }
        });

        // Adicionar os componentes à janela
        registerUserFrame.add(nameLabel);
        registerUserFrame.add(nameField);
        registerUserFrame.add(idLabel);
        registerUserFrame.add(idField);
        registerUserFrame.add(saveButton);
        registerUserFrame.add(backButton);

        // Configurar a nova janela
        registerUserFrame.setSize(400, 200);
        registerUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha somente esta janela
        registerUserFrame.setLocationRelativeTo(null); // Centralizar a janela
        registerUserFrame.setVisible(true); // Tornar a nova janela visível
    }
}

class RegisterBookWindow {
    public RegisterBookWindow() {
        // Criar a janela de cadastro de livro
        JFrame registerBookFrame = new JFrame("Cadastrar Livro");

        // Definir o layout da janela
        registerBookFrame.setLayout(new GridLayout(4, 2));

        // Criar campos de texto para título, ISBN e autor
        JLabel titleLabel = new JLabel("Título do Livro:");
        JTextField titleField = new JTextField(20);

        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField(20);

        JLabel authorLabel = new JLabel("Autor:");
        JTextField authorField = new JTextField(20);

        // Botão para salvar o cadastro do livro
        JButton saveButton = new JButton("Salvar Livro");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Adicionar o livro à lista de livros
                String book = titleField.getText() + " - ISBN: " + isbnField.getText() + " - Autor: " + authorField.getText();
                Application.books.add(book);
                JOptionPane.showMessageDialog(registerBookFrame, "Livro cadastrado com sucesso!");
                registerBookFrame.dispose(); // Fecha a janela de cadastro de livro
            }
        });

        // Botão para voltar à página de livros
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerBookFrame.dispose(); // Fecha a janela de cadastro de livro
            }
        });

        // Adicionar os componentes à janela
        registerBookFrame.add(titleLabel);
        registerBookFrame.add(titleField);
        registerBookFrame.add(isbnLabel);
        registerBookFrame.add(isbnField);
        registerBookFrame.add(authorLabel);
        registerBookFrame.add(authorField);
        registerBookFrame.add(saveButton);
        registerBookFrame.add(backButton);

        // Configurar a nova janela
        registerBookFrame.setSize(400, 200);
        registerBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha somente esta janela
        registerBookFrame.setLocationRelativeTo(null); // Centralizar a janela
        registerBookFrame.setVisible(true); // Tornar a nova janela visível
    }
}
