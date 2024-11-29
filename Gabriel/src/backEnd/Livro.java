package backEnd;

public class Livro {
    private String titulo;
    private String autor;
    private String ISBN;
    private boolean disponibilidade;

    public Livro(String titulo, String autor, String ISBN) {
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.disponibilidade = true;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean getDisponibilidade() {
        return disponibilidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void informacoesLivro() {
        System.out.println("Autor: " + autor + "\nTítulo: " + titulo + "\nISBN: " + ISBN + "\nDisponibilidade: " + (disponibilidade ? "Disponível" : "Indisponível"));
    }

    public void alterarDisponibilidade() {
        this.disponibilidade = !this.disponibilidade;
    }
}

