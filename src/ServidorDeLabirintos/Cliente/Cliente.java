package ServidorDeLabirintos.Cliente;

import java.net.*;
import java.util.*;
import BancoDeDados.daos.*;
import BancoDeDados.dbos.*;
import ServidorDeLabirintos.Compartilhado.*;
import App.EditorDeLabirinto;
import java.io.*;

/**
 * Classe responsável por instanciar o cliente. Esse cliente é capaz de realizar
 * operações ao se comunicar com o servidor e pode ser terminado por ordem do
 * usuário ou por ordem do servidor.
 */
public class Cliente {
	public static final String HOST_PADRAO = "localhost";
	public static final int PORTA_PADRAO = 3000; // Não devemos mudar essa porta.

	public static void main(String[] args) {
		if (args.length > 2) {
			System.err.println("Uso esperado: java Cliente [HOST [PORTA]]\n");
			return;
		}

		Socket conexao = null;
		try {
			String host = Cliente.HOST_PADRAO;
			int porta = Cliente.PORTA_PADRAO;

			if (args.length > 0)
				host = args[0];

			if (args.length == 2)
				porta = Integer.parseInt(args[1]);

			conexao = new Socket(host, porta);
		} catch (Exception erro) {
			System.err.println("Indique o servidor e a porta corretos!\n");
			return;
		}

		ObjectOutputStream transmissor = null;
		try {
			transmissor = new ObjectOutputStream(conexao.getOutputStream());
		} catch (Exception erro) {
			System.err.println("Indique o servidor e a porta corretos!\n");
			try {
				conexao.close();
			} catch (IOException e) {
			}
			return;
		}

		ObjectInputStream receptor = null;
		try {
			receptor = new ObjectInputStream(conexao.getInputStream());
		} catch (Exception erro) {
			System.err.println("Indique o servidor e a porta corretos!\n");
			try {
				conexao.close();
			} catch (IOException e) {
			}
			return;
		}

		Parceiro servidor = null;
		try {
			servidor = new Parceiro(conexao, receptor, transmissor);
		} catch (Exception erro) {
			System.err.println("Indique o servidor e a porta corretos!\n");
			return;
		}

		TratadoraDeComunicadoDeDesligamento tratadoraDeComunicadoDeDesligamento = null;
		try {
			tratadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento(servidor);
		} catch (Exception erro) {
		} // sei que servidor foi instanciado

		tratadoraDeComunicadoDeDesligamento.start();

		String email = "";
		do {
			System.out.printf("Digite o seu email: ");
			try {
				email = Teclado.getUmString().toLowerCase();
			} catch (Exception e) {
				System.err.println("Opcao invalida!\n");
				continue;
			}
			if (email.indexOf('@') == -1 || email.length() < 7) {
				System.err.println("E-mail inválido!");
				continue;
			}
		} while (email.indexOf('@') == -1 || email.length() < 7);

		char opcao = ' ';
		do {
			System.out.println("O que deseja fazer?");
			System.out.println("[ N ] - Criar um novo labirinto.");
			System.out.println("[ E ] - Editar um labirinto existente.");
			System.out.println("[ T ] - Terminar programa.");
			System.out.println("Opção: ");

			try {
				opcao = Character.toUpperCase(Teclado.getUmChar());
			} catch (Exception erro) {
				System.err.println("Opcao invalida!\n");
				continue;
			}

			if ("NET".indexOf(opcao) == -1) {
				System.err.println("Opcao invalida!\n");
				continue;
			}

			try {
				if (opcao == 'N') {
					// Aqui é para abrir o editor de labirinto sem ter nenhum labirinto dentro. Acho
					// que devemos remover o botão importar.
					System.out.print("Você selecionou a opção de criar um novo labirinto!");
					try {
						new EditorDeLabirinto(servidor, email);
					} catch (Exception e) {
						System.err.println(e.getMessage());
						continue;
					}

					// Enviamos um comunicado de pedido de salvamento de labirinto para o servidor.
					// servidor.receba (new PedidoDeSalvamento (labirinto));
				} else if (opcao == 'E') {
					int opcaoID = 0;
					Vector<Integer> idsDisponiveis = new Vector<Integer>();
					do {
						System.out.println("Os labirintos disponíveis são:");

						servidor.receba(new PedidoDeLabirintos(email));
						Comunicado comunicado = null;
						do {
							comunicado = (Comunicado) servidor.espie();
						} while (!(comunicado instanceof PedidoDeLabirintos));
						PedidoDeLabirintos pedido = (PedidoDeLabirintos) servidor.envie();

						Vector<Labirinto> vLabirintos = pedido.getLabirintos();
						if (vLabirintos.size() > 0) {
							for (int i = 0; i < vLabirintos.size(); i++) {
								System.out.println(vLabirintos.get(i));
								idsDisponiveis.add(vLabirintos.get(i).getId());
							}
							System.out.println("Insira o valor do ID do labirinto que deseja editar.");
							System.out.printf("ID: ");

							try {
								opcaoID = Teclado.getUmInt();
							} catch (Exception erro) {
								System.err.println("ID invalido!\n");
								continue;
							}

							if (idsDisponiveis.indexOf(opcaoID) == -1) {
								System.err.println("ID invalido!\n");
							}
						} else {
							System.out.println("Nenhum Labirinto registrado neste e-mail. . .");
							break;
						}
					} while (idsDisponiveis.indexOf(opcaoID) == -1);

					// Abrir editor com o labirinto selecionado.
					servidor.receba(new PedidoDeLabirinto(opcaoID));
					Comunicado comunicado = null;
					do {
						comunicado = (Comunicado) servidor.espie();
					} while (!(comunicado instanceof PedidoDeLabirinto));
					PedidoDeLabirinto resultado = (PedidoDeLabirinto) servidor.envie();

					Labirinto labirintoImportado = resultado.getLabirinto();

					System.out.println(labirintoImportado.getConteudo());
					new EditorDeLabirinto(servidor, labirintoImportado);
				}
			} catch (Exception erro) {
				System.err.println("Erro de comunicacao com o servidor;");
				System.err.println("Tente novamente!");
				System.err.println("Caso o erro persista, termine o programa");
				System.err.println("e volte a tentar mais tarde!\n");
			}
		} while (opcao != 'T');

		try {
			servidor.receba(new PedidoParaSair());
		} catch (Exception erro) {
		}

		System.out.println("Obrigado por usar este programa!");
		System.exit(0);
	}
}
