package datalink;

import java.util.Scanner;
import org.bson.Document;
import java.util.List;

public class App {

    public static void main(String[] args) {
        
        // Inicializa o Scanner para leitura de entrada
        Scanner scanner = new Scanner(System.in);
        
        // Inicializa a classe de serviço CRUD
        // Esta chamada já conecta ao MongoDB e ao Redis (Cache)
        CRUD service = new CRUD();
        
        int opcao = -1;
        
        // Loop principal do menu interativo
        while (opcao != 0) {
            
            // --- Exibe o Menu ---
            System.out.println("=============================================");
            System.out.println("1 - CADASTRAR Usuário (CREATE)");
            System.out.println("2 - LISTAR TODOS Usuários (READ com Cache)");
            System.out.println("3 - ATUALIZAR Usuário (UPDATE)");
            System.out.println("4 - EXCLUIR Usuário (DELETE)");
            System.out.println("0 - SAIR da Aplicação");
            System.out.println("=============================================");
            System.out.print("Escolha uma opção: ");

            // Tenta ler a opção do menu
            try {
                // Lê a opção (número)
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer
                
                // --- Execução da Opção Escolhida (Switch Case) ---
                switch (opcao) {
                    case 1:
                        // CREATE
                        System.out.println("\n--- CADASTRAR NOVO USUÁRIO ---");
                        System.out.print("Nome do novo usuário: ");
                        String nome = scanner.nextLine();
                        
                        System.out.print("Idade de " + nome + ": ");
                        // Captura o inteiro e trata possíveis erros
                        int idade = scanner.nextInt(); 
                        scanner.nextLine(); // Limpa o buffer após o int
                        
                        service.cadastrarUsuario(nome, idade);
                        break;
                        
                    case 2:
                        // READ (Listar Todos)
                        System.out.println("\n--- LISTA DE USUÁRIOS ---");
                        List<Document> usuarios = service.ListarUsuarios();
                        
                        if (usuarios.isEmpty()) {
                            System.out.println("Nenhum usuário encontrado.");
                            break;
                        }
                        
                        for (Document doc : usuarios) {
                            System.out.println(doc.toJson()); 
                        }
                        System.out.println("-------------------------\n");
                        break;
                        
                    case 3:
                        // UPDATE
                        System.out.println("\n--- ATUALIZAR USUÁRIO ---");
                        System.out.print("Digite o NOME ATUAL do usuário a ser alterado: ");
                        String nomeAntigo = scanner.nextLine();
                        
                        System.out.print("Digite o NOVO NOME para " + nomeAntigo + ": ");
                        String novoNome = scanner.nextLine();
                        
                        service.atualizarUsuario(nomeAntigo, novoNome);
                        break;
                        
                    case 4:
                        // DELETE
                        System.out.println("\n--- EXCLUIR USUÁRIO ---");
                        System.out.print("Digite o NOME do usuário a ser excluído: ");
                        String nomeExcluir = scanner.nextLine();
                        
                        service.excluirUsuario(nomeExcluir);
                        break;
                        
                    case 0:
                        // SAIR
                        System.out.println("?Encerrando a aplicação. Desconectando do Redis.");
                        RedisConnection.closeConnection(); 
                        break;
                        
                    default:
                        System.out.println("?Opção inválida. Digite um número de 0 a 4.");
                }
            } catch (java.util.InputMismatchException e) {
                // Captura se o usuário digitar texto onde é esperado um número (ex: idade)
                System.out.println("Erro de formato! Por favor, use APENAS números para as opções e idade.");
                scanner.nextLine(); // Limpa o buffer para o próximo loop
                opcao = -1; 
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        scanner.close();
    }
}