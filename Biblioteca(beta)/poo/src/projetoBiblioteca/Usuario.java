package projetoBiblioteca;

import java.util.ArrayList;

public class Usuario {
    // Atributos da classe Usuario
    private String nome;                 // Nome do usuário
    private String numeroDeRegsitro;     // Número de registro do usuário
    private ArrayList<Livro> livroEmprestado; // Lista de livros emprestados pelo usuário

    // Construtor da classe Usuario
    public Usuario(String nome, String numeroDeRegistro) {
        this.nome = nome;                    // Inicializa o nome
        this.numeroDeRegsitro = numeroDeRegistro; // Inicializa o número de registro
        livroEmprestado = new ArrayList<Livro>(); // Inicializa a lista de livros emprestados
    }

    // Método para obter o nome do usuário
    public String getNome() {
        return nome; // Retorna o nome do usuário
    }

    // Método para obter o número de registro do usuário
    public String getNumeroDeRegistro() {
        return numeroDeRegsitro; // Retorna o número de registro
    }

    // Método para obter a lista de livros emprestados
    public ArrayList<Livro> getLivroEmprestado() {
        return livroEmprestado; // Retorna a lista de livros emprestados
    }

    // Método para exibir informações do usuário
    public void informacoesUsuario() {
        System.out.println("Nome: " + nome + "\nNúmero do registro: " + numeroDeRegsitro + "\nLivros com " + nome + ":");
        // Verifica se o usuário tem livros emprestados
        if (livroEmprestado.isEmpty()) {
            System.out.println("Nenhum Livro com " + nome); // Mensagem quando não há livros emprestados
        } else {
            // Exibe títulos dos livros emprestados
            for (Livro i : livroEmprestado) {
                System.out.println(i.getTitulo()); // Mostra o título de cada livro
            }
        }
    }

    // Método para adicionar um livro à lista de livros emprestados
    public void inserirLivroNaLista(Livro livro) {
        if (livro.getDisponibilidade()) { // Verifica se o livro está disponível
            livroEmprestado.add(livro);    // Adiciona o livro à lista
            livro.alterarDisponibilidade(); // Marca o livro como indisponível
        }
    }

    // Método para remover um livro da lista de livros emprestados
    public void removerLivroDaLista(Livro livro) {
        if (!livro.getDisponibilidade()) { // Verifica se o livro está emprestado
            livroEmprestado.remove(livro);  // Remove o livro da lista
            livro.alterarDisponibilidade();  // Marca o livro como disponível
        }
    }
}
