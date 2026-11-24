# Jogo da Velha em Java

Jogo da velha para o terminal, feito para a matéria de Programação. Dá para jogar contra o computador ou com outra pessoa.

## Como funciona

- No início, o jogador escolhe jogar contra o **computador** ou em **dois jogadores**.
- Cada jogador informa o nome, e o primeiro escolhe X ou O.
- As jogadas são feitas digitando um número de 1 a 9, que corresponde a uma posição do tabuleiro.
- O jogo controla a vez de cada um, recusa jogadas em posições ocupadas e avisa quando alguém vence ou quando dá empate.
- O computador tenta ganhar, bloqueia a jogada do adversário quando precisa e, se não houver nada urgente, prefere o centro e depois os cantos.

## O que pratiquei

- Programação orientada a objetos (classes `Jogo` e `Principal`)
- Matrizes (o tabuleiro 3x3)
- Lógica de turnos e verificação de vitória em linhas, colunas e diagonais
- Entrada de dados com `Scanner` e uso de `Random`

## Como rodar

É preciso ter o Java (JDK) instalado.

```
git clone https://github.com/Lotth00/Jogo-da-velha-em-Java.git
cd Jogo-da-velha-em-Java
javac -d out Jogo.java Principal.java
java -cp out TrabalhoJogoDaVelha.Principal
```

## Estrutura

```
Jogo.java       regras do jogo, tabuleiro e jogadas do computador
Principal.java  ponto de entrada (método main)
```
