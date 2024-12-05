package swingapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Application {
    public static void main(String[] args) {
        // Criar a janela principal
        JFrame frame = new JFrame("Biblioteca Virtual");

        // Definir o layout da janela
        frame.getContentPane().setLayout(new BorderLayout());

        // Criar uma área de texto para mostrar os dados
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false); // Tornar a área de texto somente leitura
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        // Criar um painel com botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botão "Salvar"
        JButton saveButton = new JButton("Salvar Dados");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar em salvar, exibe uma mensagem na área de texto
                textArea.setText("Dados salvos com sucesso!");
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
                // Ao clicar, abre uma nova janela mostrando a lista de livros
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
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(booksButton);
        buttonPanel.add(registerUserButton);

        // Criar a lista de usuários
        JTextArea userListTextArea = new JTextArea(5, 30);
        userListTextArea.setEditable(false);
        userListTextArea.setText("Lista de Usuários:\n");
        JScrollPane userListScrollPane = new JScrollPane(userListTextArea);

        // Adicionar a lista de usuários e painel de botões à janela
        frame.getContentPane().add(userListScrollPane, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Configurar a janela principal
        frame.setSize(512, 392); // Tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportamento ao fechar
        frame.setLocationRelativeTo(null); // Centralizar a janela
        frame.setVisible(true); // Tornar a janela visível
    }
}

class RegisterUserWindow {
    private static ArrayList<String> users = new ArrayList<>(); // Lista de usuários cadastrados

    public RegisterUserWindow() {
        // Criar a janela de cadastro de usuário
        JFrame registerFrame = new JFrame("Cadastrar Usuário");

        // Definir o layout da janela
        registerFrame.setLayout(new GridLayout(4, 2));

        // Criar campos de texto para nome e número de registro
        JLabel nameLabel = new JLabel("Nome do Usuário:");
        JTextField nameField = new JTextField(20);

        JLabel regNumberLabel = new JLabel("Número de Registro:");
        JTextField regNumberField = new JTextField(20);

        // Botão para salvar o cadastro
        JButton saveButton = new JButton("Salvar Cadastro");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Adicionar o nome e o número de registro à lista de usuários
                String user = nameField.getText() + " - Registro: " + regNumberField.getText();
                users.add(user);
                JOptionPane.showMessageDialog(registerFrame, "Usuário cadastrado com sucesso!");
            }
        });

        // Botão para voltar à página inicial
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerFrame.dispose(); // Fecha a janela de cadastro de usuário
            }
        });

        // Adicionar os componentes à janela
        registerFrame.add(nameLabel);
        registerFrame.add(nameField);
        registerFrame.add(regNumberLabel);
        registerFrame.add(regNumberField);
        registerFrame.add(saveButton);
        registerFrame.add(backButton);

        // Configurar a nova janela
        registerFrame.setSize(400, 200);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha somente esta janela
        registerFrame.setLocationRelativeTo(null); // Centralizar a janela
        registerFrame.setVisible(true); // Tornar a nova janela visível
    }
}

class BooksWindow {
    static ArrayList<String> books = new ArrayList<>(); // Lista de livros cadastrados

    public BooksWindow() {
        // Criar a nova janela para mostrar os livros
        JFrame booksFrame = new JFrame("Lista de Livros");

        // Definir o layout da janela
        booksFrame.setLayout(new BorderLayout());

        // Lista de livros disponíveis
        String[] booksArray = {
            "O Senhor dos Anéis - J.R.R. Tolkien",
            "Harry Potter e a Pedra Filosofal - J.K. Rowling",
            "1984 - George Orwell",
            "O Grande Gatsby - F. Scott Fitzgerald",
            "Dom Casmurro - Machado de Assis"
        };

        // Criar uma área de texto para mostrar os livros
        JTextArea booksTextArea = new JTextArea(10, 30);
        booksTextArea.setEditable(false); // Tornar a área de texto somente leitura
        booksTextArea.setText(String.join("\n", booksArray)); // Adiciona os livros à área de texto

        JScrollPane scrollPane = new JScrollPane(booksTextArea);

        // Criar o painel com botões de cadastro e voltar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Botão de cadastro de livro
        JButton registerBookButton = new JButton("Cadastrar Livro");
        registerBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterBookWindow();
            }
        });

        // Botão de voltar à página inicial
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                booksFrame.dispose(); // Fecha a janela de livros
            }
        });

        // Adicionar os botões ao painel
        buttonPanel.add(registerBookButton);
        buttonPanel.add(backButton);

        // Adicionar a área de texto e o painel de botões à janela
        booksFrame.add(scrollPane, BorderLayout.CENTER);
        booksFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Configurar a nova janela
        booksFrame.setSize(400, 300);
        booksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha somente a janela dos livros
        booksFrame.setLocationRelativeTo(null); // Centralizar a janela
        booksFrame.setVisible(true); // Tornar a nova janela visível
    }
}

class RegisterBookWindow {
    public RegisterBookWindow() {
        // Criar a janela de cadastro de livro
        JFrame registerBookFrame = new JFrame("Cadastrar Livro");

        // Definir o layout da janela
        registerBookFrame.setLayout(new GridLayout(6, 2));

        // Criar campos de texto para o nome do livro, autor, ISBN e ID
        JLabel nameLabel = new JLabel("Nome do Livro:");
        JTextField nameField = new JTextField(20);

        JLabel authorLabel = new JLabel("Autor:");
        JTextField authorField = new JTextField(20);

        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField(20);

        // Botão para salvar o cadastro do livro
        JButton saveBookButton = new JButton("Salvar Cadastro");
        saveBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookDetails = "Nome: " + nameField.getText() + ", Autor: " + authorField.getText() + ", ISBN: " + isbnField.getText();
                // Salvar os dados do livro na lista
                BooksWindow.books.add(bookDetails);
                JOptionPane.showMessageDialog(registerBookFrame, "Livro cadastrado com sucesso!");
            }
        });

        // Botão para voltar à tela de livros
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerBookFrame.dispose(); // Fecha a janela de cadastro de livro
            }
        });

        // Adicionar os componentes à janela
        registerBookFrame.add(nameLabel);
        registerBookFrame.add(nameField);
        registerBookFrame.add(authorLabel);
        registerBookFrame.add(authorField);
        registerBookFrame.add(isbnLabel);
        registerBookFrame.add(isbnField);
        registerBookFrame.add(saveBookButton);
        registerBookFrame.add(backButton);

        // Configurar a nova janela
        registerBookFrame.setSize(400, 300);
        registerBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha somente esta janela
        registerBookFrame.setLocationRelativeTo(null); // Centralizar a janela
        registerBookFrame.setVisible(true); // Tornar a nova janela visível
    }
}
