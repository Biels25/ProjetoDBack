package swingapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Application {
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
        buttonPanel.add(loanButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(booksButton);
        buttonPanel.add(registerUserButton);

        // Adicionar o painel de botões à janela
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Configurar a janela principal
        frame.setSize(512, 392); // Tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportamento ao fechar
        frame.setLocationRelativeTo(null); // Centralizar a janela
        frame.setVisible(true); // Tornar a janela visível
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
