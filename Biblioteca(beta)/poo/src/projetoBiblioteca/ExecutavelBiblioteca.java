package projetoBiblioteca;

import java.util.Scanner;

public class ExecutavelBiblioteca {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); // Cria um scanner para entrada de dados
        Biblioteca biblioteca = new Biblioteca(); // Instancia a biblioteca
        
        int menu = 0; // Variável para controlar o menu
        
        System.out.println("Bem-vindo ao software de Biblioteca!");
        do {
            try {
                // Exibe o menu de opções
                System.out.println("------------------------------------------------------");
                System.out.println("0. Sair");
                System.out.println("1. Cadastrar livro");
                System.out.println("2. Cadastrar usuário");
                System.out.println("3. Mostrar lista de livros");
                System.out.println("4. Mostrar lista de usuários");
                System.out.println("5. Emprestar livro");
                System.out.println("6. Devolver livro");
                System.out.print("Digite uma das opções acima: ");
                menu = scan.nextInt(); // Lê a opção do usuário
                
                // Opção para cadastrar livro
                if(menu == 1) {
                    System.out.println();
                    System.out.print("Digite o nome do livro: ");
                    String titulo = scan.next();
                    scan.nextLine();
                    System.out.print("Digite o autor do livro: ");
                    String autor = scan.next();
                    scan.nextLine();
                    System.out.print("Digite o ISBN do livro: ");
                    String isbn = scan.next();
                    // Cadastra o livro e verifica se foi bem-sucedido
                    if(biblioteca.cadastrarLivro(titulo, autor, isbn)) {
                        System.out.println("Livro cadastrado com sucesso!");
                    } else {
                        System.out.println("Não foi possível cadastrar o livro!");
                    }
                }
                // Opção para cadastrar usuário
                else if(menu == 2) {
                    System.out.println();
                    System.out.print("Digite o nome do usuário: ");
                    String nome = scan.next();
                    scan.nextLine();
                    System.out.print("Digite o número de registro do usuário: ");
                    String numero = scan.next();
                    scan.nextLine();
                    // Cadastra o usuário e verifica se foi bem-sucedido
                    if(biblioteca.cadastrarUsuario(nome, numero)) {
                        System.out.println("Usuário cadastrado com sucesso!");
                    } else {
                        System.out.println("Não foi possível cadastrar o usuário.");
                    }
                }
                // Opção para mostrar a lista de livros
                else if (menu == 3) {
                    System.out.println();
                    System.out.println("Esta é a lista de livros cadastrados:");
                    biblioteca.livrosDisponiveis(); // Exibe livros disponíveis
                }
                // Opção para mostrar a lista de usuários
                else if (menu == 4) {
                    System.out.println();
                    System.out.println("Esta é a lista de usuários cadastrados:");
                    biblioteca.usuariosDisponiveis(); // Exibe usuários disponíveis
                }
                // Opção para emprestar livro
                else if (menu == 5) {
                    System.out.println();
                    System.out.print("Digite o ISBN do livro a ser emprestado: ");
                    String isbn = scan.next();
                    scan.nextLine();
                    System.out.print("Digite o número de registro do usuário para qual o livro será emprestado: ");
                    String numero = scan.next();
                    scan.nextLine();
                    // Tenta emprestar o livro e verifica se foi bem-sucedido
                    if(biblioteca.emprestarLivro(numero, isbn)) {
                        System.out.println("Livro emprestado com sucesso!");
                    } else {
                        System.out.println("Não foi possível emprestar o livro!");
                    }
                }
                // Opção para devolver livro
                else if (menu == 6) {
                    System.out.println();
                    System.out.print("Digite o ISBN do livro a ser devolvido: ");
                    String isbn = scan.next();
                    scan.nextLine();
                    System.out.print("Digite o número de registro do usuário que estava com o livro: ");
                    String numero = scan.next();
                    scan.nextLine();
                    // Tenta devolver o livro e verifica se foi bem-sucedido
                    if(biblioteca.devolverLivro(numero, isbn)) {
                        System.out.println("Livro devolvido com sucesso!");
                    } else {
                        System.out.println("Não foi possível devolver o livro!");
                    }
                }
                // Opção para sair
                else if(menu == 0) {
                    System.out.println();
                    System.out.println("Sessão finalizada!");
                    biblioteca.Sair(); // Salva dados antes de sair
                }
                // Caso de opção inválida
                else {
                    System.out.println("Número digitado não disponível");
                }
            } catch (Exception e) {
                // Tratamento de exceção para entradas inválidas
                System.out.println("Ocorreu um erro: " + e.getMessage());
                scan.nextLine(); // Limpa o buffer
                menu = 7; // Força o loop a continuar
            }
        } while (menu != 0); // Continua até que o usuário escolha sair
        
        scan.close(); // Fecha o scanner ao final
    }
}
