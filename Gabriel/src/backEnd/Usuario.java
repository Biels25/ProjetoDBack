package backEnd;

import java.util.ArrayList;

public class Usuario {
    private String nome;
    private String numeroDeRegistro;
    private ArrayList<Livro> livrosEmprestados;

    public Usuario(String nome, String numeroDeRegistro) {
        this.nome = nome;
        this.numeroDeRegistro = numeroDeRegistro;
        this.livrosEmprestados = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getNumeroDeRegistro() {
        return numeroDeRegistro;
    }

    public ArrayList<Livro> getLivrosEmprestados() {
        return livrosEmprestados;
    }

    public void inserirLivroNaLista(Livro livro) {
        livrosEmprestados.add(livro);
    }

    public void removerLivroDaLista(Livro livro) {
        livrosEmprestados.remove(livro);
    }

    public void informacoesUsuario() {
        System.out.println("Nome: " + nome + "\nNúmero de Registro: " + numeroDeRegistro);
    }
}

