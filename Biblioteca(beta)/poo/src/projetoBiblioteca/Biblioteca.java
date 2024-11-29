package projetoBiblioteca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Biblioteca {
	// Listas para armazenar livros e usuários
	private ArrayList<Livro> listaDeLivro;
	private ArrayList<Usuario> listaDeUsuario;

	// Construtor da classe Biblioteca
	public Biblioteca() {
		listaDeLivro = new ArrayList<Livro>();
		listaDeUsuario = new ArrayList<Usuario>();
		carregarListaDeLivro(); // Carrega a lista de livros do arquivo CSV
		carregarListaDeUsuario(); // Carrega a lista de usuários do arquivo CSV
		carregarListaDeEmprestimo(); // Carrega os empréstimos do arquivo CSV
	}

	// Método para carregar a lista de livros a partir de um arquivo CSV
	private void carregarListaDeLivro() {
		String path = "C:\\Users\\Aluno\\Downloads\\pooV2\\poo\\poo\\dados CSV\\ListaDeLivros.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String linha = br.readLine();
			while (linha != null) {
				String[] livro = linha.split(","); // Divide a linha em partes
				cadastrarLivro(livro[0], livro[1], livro[2]); // Cadastra o livro
				linha = br.readLine(); // Lê a próxima linha
			}
		} catch (IOException e) {
			System.out.println(e); // Trata exceção de I/O
		}
	}

	// Método para carregar a lista de usuários a partir de um arquivo CSV
	private void carregarListaDeUsuario() {
		String path = "C:\\Users\\Aluno\\Downloads\\pooV2\\poo\\poo\\dados CSV\\ListaDeUsuario.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String linha = br.readLine();
			while (linha != null) {
				String[] Usuario = linha.split(","); // Divide a linha em partes
				cadastrarUsuario(Usuario[0], Usuario[1]); // Cadastra o usuário
				linha = br.readLine(); // Lê a próxima linha
			}
		} catch (IOException e) {
			System.out.println(e); // Trata exceção de I/O
		}
	}

	// Método para carregar a lista de empréstimos a partir de um arquivo CSV
	private void carregarListaDeEmprestimo() {
		String path = "C:\\Users\\Aluno\\Downloads\\pooV2\\poo\\poo\\dados CSV\\ListaDeEmprestimo.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String linha = br.readLine();
			while (linha != null) {
				String[] Emprestimo = linha.split(","); // Divide a linha em partes
				emprestarLivro(Emprestimo[0], Emprestimo[1]); // Registra o empréstimo
				linha = br.readLine(); // Lê a próxima linha
			}
		} catch (IOException e) {
			System.out.println(e); // Trata exceção de I/O
		}
	}

	// Método para emprestar um livro a um usuário
	public boolean emprestarLivro(String NumeroDoRegistro, String ISBN) {
		// Verifica se o livro e o usuário existem
		if (!verificarLivro(ISBN) & !verificarUsuario(NumeroDoRegistro)) {
			Livro livro = buscarLivroPorISBN(ISBN); // Busca o livro pelo ISBN
			Usuario usuario = buscarUsuarioPorNumeroDeRegistro(NumeroDoRegistro); // Busca o usuário
			// Verifica se o livro está disponível
			if (livro.getDisponibilidade()) {
				usuario.inserirLivroNaLista(livro); // Adiciona o livro à lista do usuário
				return true; // Empréstimo realizado com sucesso
			} else {
				return false; // Livro não disponível
			}
		} else {
			return false; // Livro ou usuário não encontrado
		}
	}

	// Método para devolver um livro
	public boolean devolverLivro(String NumeroDoRegistro, String ISBN) {
		// Verifica se o livro e o usuário existem
		if (!verificarLivro(ISBN) & !verificarUsuario(NumeroDoRegistro)) {
			Livro livro = buscarLivroPorISBN(ISBN); // Busca o livro pelo ISBN
			Usuario usuario = buscarUsuarioPorNumeroDeRegistro(NumeroDoRegistro); // Busca o usuário
			// Verifica se o livro está emprestado ao usuário
			if (!livro.getDisponibilidade() & procurarlivroemprestado(livro, usuario)) {
				usuario.removerLivroDaLista(livro); // Remove o livro da lista do usuário
				return true; // Devolução realizada com sucesso
			} else {
				return false; // Livro não estava emprestado
			}
		} else {
			return false; // Livro ou usuário não encontrado
		}
	}

	// Método para cadastrar um novo livro
	public boolean cadastrarLivro(String titulo, String autor, String ISBN) {
		if (verificarLivro(ISBN)) { // Verifica se o livro já existe
			Livro livro = new Livro(titulo, autor, ISBN); // Cria um novo livro
			listaDeLivro.add(livro); // Adiciona o livro à lista
			return true; // Cadastro realizado com sucesso
		} else {
			return false; // Livro já existe
		}
	}

	// Método para verificar se um livro existe na lista
	private boolean verificarLivro(String ISBN) {
		for (Livro i : listaDeLivro) {
			if (ISBN.equals(i.getISBN())) {
				return false; // Livro encontrado
			}
		}
		return true; // Livro não encontrado
	}

	// Método para listar livros disponíveis
	public void livrosDisponiveis() {
		for (Livro i : listaDeLivro) {
			i.informacoesLivro(); // Exibe informações do livro
			System.out.println();
		}
	}

	// Método para listar usuários disponíveis
	public void usuariosDisponiveis() {
		for (Usuario i : listaDeUsuario) {
			i.informacoesUsuario(); // Exibe informações do usuário
			System.out.println();
		}
	}

	// Método para cadastrar um novo usuário
	public boolean cadastrarUsuario(String nome, String numeroDoRegistro) {
		if (verificarUsuario(numeroDoRegistro)) { // Verifica se o usuário já existe
			Usuario usuario = new Usuario(nome, numeroDoRegistro); // Cria um novo usuário
			listaDeUsuario.add(usuario); // Adiciona o usuário à lista
			return true; // Cadastro realizado com sucesso
		} else {
			return false; // Usuário já existe
		}
	}

	// Método para verificar se um usuário existe na lista
	private boolean verificarUsuario(String NumeroDoRegistro) {
		for (Usuario i : listaDeUsuario) {
			if (NumeroDoRegistro.equals(i.getNumeroDeRegistro())) {
				return false; // Usuário encontrado
			}
		}
		return true; // Usuário não encontrado
	}

	// Método para buscar um livro pelo ISBN
	private Livro buscarLivroPorISBN(String isbn) {
		for (Livro i : listaDeLivro) {
			if (i.getISBN().equals(isbn)) {
				return i; // Livro encontrado
			}
		}
		return null; // Livro não encontrado
	}

	// Método para verificar se um livro está emprestado a um usuário
	private boolean procurarlivroemprestado(Livro livro, Usuario usuario) {
		for (Livro i : usuario.getLivroEmprestado()) {
			if (livro.getISBN().equals(i.getISBN())) {
				return true; // Livro encontrado na lista de empréstimos do usuário
			}
		}
		return false; // Livro não encontrado
	}

	// Método para buscar um usuário pelo número de registro
	private Usuario buscarUsuarioPorNumeroDeRegistro(String numeroDeRegistro) {
		for (Usuario i : listaDeUsuario) {
			if (i.getNumeroDeRegistro().equals(numeroDeRegistro)) {
				return i; // Usuário encontrado
			}
		}
		return null; // Usuário não encontrado
	}

	// Método para salvar os dados ao sair da aplicação
	public void Sair() {
		// Caminhos para os arquivos CSV
		String pathLivro = "C:\\Users\\Aluno\\Downloads\\pooV2\\poo\\poo\\dados CSV\\ListaDeLivros.csv";
		String pathUsuario = "C:\\Users\\Aluno\\Downloads\\pooV2\\poo\\poo\\dados CSV\\listaDeUsuario.csv";
		String pathEmprestimo = "C:\\Users\\Aluno\\Downloads\\pooV2\\poo\\poo\\dados CSV\\listaDeEmprestimo.csv";

		// Salva a lista de livros no arquivo CSV
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathLivro))) {
			for (Livro i : listaDeLivro) {
				bw.write(i.getTitulo() + "," + i.getAutor() + "," + i.getISBN());
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println(e);
		}

		// Salva a lista de usuários no arquivo CSV
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathUsuario))) {
			for (Usuario i : listaDeUsuario) {
				bw.write(i.getNome() + "," + i.getNumeroDeRegistro());
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		// Salva a lista de Emprestimos no arquivo CSV
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathEmprestimo))) {
			for (Usuario i : listaDeUsuario) {
				for (Livro j : i.getLivroEmprestado()) {
					bw.write(i.getNumeroDeRegistro() + "," + j.getISBN());
					bw.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
