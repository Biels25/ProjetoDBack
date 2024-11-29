package projetoBiblioteca;

public class Livro {

    // Atributos da classe Livro
    private String titulo;          // Título do livro
    private String autor;           // Autor do livro
    private String ISBN;            // ISBN do livro
    private boolean disponibilidade; // Indica se o livro está disponível

    // Construtor da classe Livro
    public Livro(String titulo, String autor, String iSBN) {
        this.titulo = titulo;            // Inicializa o título
        this.autor = autor;              // Inicializa o autor
        this.ISBN = iSBN;                // Inicializa o ISBN
        disponibilidade = true;          // O livro começa disponível
    }

    // Método para obter o ISBN do livro
    public String getISBN() {
        return ISBN; // Retorna o ISBN
    }

    // Método para verificar a disponibilidade do livro
    public boolean getDisponibilidade() {
        return disponibilidade; // Retorna true se disponível, false caso contrário
    }

    // Método para obter o título do livro
    public String getTitulo() {
        return titulo; // Retorna o título do livro
    }

    // Método para obter o autor do livro
    public String getAutor() {
        return autor; // Retorna o autor do livro
    }

    // Método para exibir as informações do livro
    public void informacoesLivro() {
        // Exibe as informações com base na disponibilidade
        if (disponibilidade) {
            System.out.println(
                    "Autor: " + autor + "\nTítulo: " + titulo + "\nISBN: " + ISBN + "\nDisponibilidade: Disponível");
        } else {
            System.out.println(
                    "Autor: " + autor + "\nTítulo: " + titulo + "\nISBN: " + ISBN + "\nDisponibilidade: Indisponível");
        }
    }

    // Método para alterar a disponibilidade do livro
    public void alterarDisponibilidade() {
        // Alterna a disponibilidade entre disponível e indisponível
        if (disponibilidade) {
            disponibilidade = false; // Marca como indisponível
        } else {
            disponibilidade = true;  // Marca como disponível
        }
    }
}
