package TrabalhoJogoDaVelha;

import java.util.Scanner;
import java.util.Random;

public class Jogo {
    private char[][] tabuleiro;
    private String nomeJogador1;
    private String nomeJogador2;
    private char simbolo1;
    private char simbolo2;
    private boolean vezDoJogador1; // true = jogador1, false = jogador2
    private boolean contraComputador;
    private Scanner scanner;
    private Random random;

    public Jogo() {
        tabuleiro = new char[3][3];
        scanner = new Scanner(System.in);
        random = new Random();
        inicializarTabuleiro();
    }

    public void inicializarTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
    }

    public void mostrarTabuleiro() {
        System.out.println("\n1   2   3");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j]);

                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    // Converte número de 1 a 9 em linha e coluna, e marca a jogada
    private boolean fazerJogada(int posicao, char simbolo) {
        int linha, coluna;
        switch (posicao) {
            case 1: linha = 0; coluna = 0; break;
            case 2: linha = 0; coluna = 1; break;
            case 3: linha = 0; coluna = 2; break;
            case 4: linha = 1; coluna = 0; break;
            case 5: linha = 1; coluna = 1; break;
            case 6: linha = 1; coluna = 2; break;
            case 7: linha = 2; coluna = 0; break;
            case 8: linha = 2; coluna = 1; break;
            case 9: linha = 2; coluna = 2; break;
            default: return false;
        }
        if (tabuleiro[linha][coluna] == ' ') {
            tabuleiro[linha][coluna] = simbolo;
            return true;
        }
        return false;
    }

    private char verificarVencedor() {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] != ' ' && tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2])
                return tabuleiro[i][0];
        }

        for (int j = 0; j < 3; j++) {
            if (tabuleiro[0][j] != ' ' && tabuleiro[0][j] == tabuleiro[1][j] && tabuleiro[1][j] == tabuleiro[2][j])
                return tabuleiro[0][j];
        }
        // Diagonais
        if (tabuleiro[0][0] != ' ' && tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2])
            return tabuleiro[0][0];
        if (tabuleiro[0][2] != ' ' && tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0])
            return tabuleiro[0][2];
        return ' ';
    }

    private boolean tabuleiroCheio() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (tabuleiro[i][j] == ' ') return false;
        return true;
    }

    private void jogadaComputador() {
        char letraPC = simbolo2;
        char letraHumano = simbolo1;

        int pos = melhorJogada(letraPC);
        if (pos != -1) {
            fazerJogada(pos, letraPC);
            System.out.println("Computador jogou na posição " + pos);
            return;
        }

        pos = melhorJogada(letraHumano);
        if (pos != -1) {
            fazerJogada(pos, letraPC);
            System.out.println("Computador bloqueou na posição " + pos);
            return;
        }

        if (tabuleiro[1][1] == ' ') {
            fazerJogada(5, letraPC);
            System.out.println("Computador jogou no centro (5)");
            return;
        }

        int[] cantos = {1, 3, 7, 9};
        for (int c : cantos) {
            int l = (c - 1) / 3;
            int col = (c - 1) % 3;
            if (tabuleiro[l][col] == ' ') {
                fazerJogada(c, letraPC);
                System.out.println("Computador jogou no canto " + c);
                return;
            }
        }

        for (int i = 1; i <= 9; i++) {
            int l = (i - 1) / 3;
            int col = (i - 1) % 3;
            if (tabuleiro[l][col] == ' ') {
                fazerJogada(i, letraPC);
                System.out.println("Computador jogou na posição " + i);
                return;
            }
        }
    }

    private int melhorJogada(char letra) {
        int[][] linhas = {{1,2,3}, {4,5,6}, {7,8,9}};
        int[][] colunas = {{1,4,7}, {2,5,8}, {3,6,9}};
        int[][] diagonais = {{1,5,9}, {3,5,7}};

        for (int[] grupo : linhas) {
            int pos = verificaGrupo(grupo, letra);
            if (pos != -1) return pos;
        }
        for (int[] grupo : colunas) {
            int pos = verificaGrupo(grupo, letra);
            if (pos != -1) return pos;
        }
        for (int[] grupo : diagonais) {
            int pos = verificaGrupo(grupo, letra);
            if (pos != -1) return pos;
        }
        return -1;
    }

    private int verificaGrupo(int[] posicoes, char letra) {
        int contLetra = 0;
        int vazia = -1;
        for (int p : posicoes) {
            int l = (p - 1) / 3;
            int c = (p - 1) % 3;
            if (tabuleiro[l][c] == letra) contLetra++;
            else if (tabuleiro[l][c] == ' ') vazia = p;
            else return -1; // posição ocupada pelo adversário
        }
        if (contLetra == 2 && vazia != -1) return vazia;
        return -1;
    }

    private void jogadaHumana(String nome, char simbolo) {
        int pos;
        boolean valido;
        do {
            System.out.print(nome + " (" + simbolo + "), escolha uma posição (1-9): ");
            pos = scanner.nextInt();
            valido = (pos >= 1 && pos <= 9) && fazerJogada(pos, simbolo);
            if (!valido) System.out.println("Posição inválida ou ocupada! Tente novamente.");
        } while (!valido);
    }

    public void iniciarJogo() {
        System.out.println("===== JOGO DA VELHA =====");
        System.out.print("Jogar contra:\n 1. Computador\n 2. Dois jogadores\nDigite:  ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        contraComputador = (opcao == 1);

        System.out.print("Nome do Jogador 1: ");
        nomeJogador1 = scanner.nextLine();
        char simboloEscolhido;
        do {
            System.out.print(nomeJogador1 + ", escolha X ou O: ");
            simboloEscolhido = scanner.nextLine().toUpperCase().charAt(0);
        } while (simboloEscolhido != 'X' && simboloEscolhido != 'O');
        simbolo1 = simboloEscolhido;
        simbolo2 = (simbolo1 == 'X') ? 'O' : 'X';

        if (contraComputador) {
            nomeJogador2 = "Computador";
            System.out.println("Jogador 2 é o Computador com símbolo " + simbolo2);
        } else {
            System.out.print("Nome do Jogador 2: ");
            nomeJogador2 = scanner.nextLine();
            System.out.println(nomeJogador2 + " será o símbolo " + simbolo2);
        }

        vezDoJogador1 = random.nextBoolean();
        if (vezDoJogador1)
            System.out.println("\n" + nomeJogador1 + " começa!");
        else
            System.out.println("\n" + nomeJogador2 + " começa!");

        boolean jogoRodando = true;
        while (jogoRodando) {
            mostrarTabuleiro();

            if (vezDoJogador1) {
                jogadaHumana(nomeJogador1, simbolo1);
            } else {
                if (contraComputador && nomeJogador2.equals("Computador")) {
                    jogadaComputador();
                } else {
                    jogadaHumana(nomeJogador2, simbolo2);
                }
            }

            char vencedor = verificarVencedor();
            if (vencedor != ' ') {
                mostrarTabuleiro();
                if (vencedor == simbolo1)
                    System.out.println("Parabéns, " + nomeJogador1 + " venceu!");
                else
                    System.out.println("Parabéns, " + nomeJogador2 + " venceu!");
                jogoRodando = false;
            } else if (tabuleiroCheio()) {
                mostrarTabuleiro();
                System.out.println("Empate! Ninguém venceu.");
                jogoRodando = false;
            } else {
                vezDoJogador1 = !vezDoJogador1;
            }
        }
        scanner.close();
    }
}