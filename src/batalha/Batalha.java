package batalha;
import model.Move;
import model.Pokemon;
import service.EvolutionService;
import java.util.Random;
import java.util.Scanner;
import service.MoveLearningService;

public class Batalha {


    public static void iniciar(Pokemon jogador, Pokemon inimigo)
    {
        int pocoes = 5;
        int turno = 1;
        System.out.println("\n==============================");
        System.out.println("          BATALHA");
        System.out.println("==============================");
        System.out.println(jogador.getNome() + " VS " + inimigo.getNome());
        System.out.println("==============================");

        Scanner scanner = new Scanner(System.in);

        while (jogador.getHp() > 0 && inimigo.getHp() > 0)
        {
            System.out.println("\n=== TURNO " + turno + " ====");
            System.out.println(jogador.getNome() +" HP: "+ jogador.getHp() + "/" + jogador.getHpMax());
            System.out.println(inimigo.getNome() + " HP: "+ inimigo.getHp() + "/" + inimigo.getHpMax());

            System.out.println("Poções restantes: " + pocoes);


            System.out.println("\n1 - Usar movimento");
            System.out.println("2 - Usar Poção");
            System.out.println("3 - Fugir");

            int opcao = scanner.nextInt();

            if (opcao == 1 )
            {
                if (jogador.getMovimentos().isEmpty()) {

                    System.out.println(
                            "Nenhum movimento carregado para esse Pokémon!"
                    );

                    continue;
                }
                Move movimento = jogador.getMovimentos().get(0);
                System.out.println("\nEscolha um movimento:");

                for (int i = 0; i < jogador.getMovimentos().size(); i++) {

                    Move move = jogador.getMovimentos().get(i);

                    System.out.println((i + 1) + " - " + move.getNome() + " | PP: " +
                            move.getPpAtual() + "/" + move.getPpMax() +
                            " | Tipo: " + move.getTipo() + " | Precisão: " + move.getPrecisao());
                }

                int escolhaMovimento = scanner.nextInt();

                Move movimentoEscolhido = jogador.getMovimentos().get(escolhaMovimento - 1);

                atacarComMovimento(jogador, inimigo, movimentoEscolhido);
            }

            else if (opcao == 2) {
                if (pocoes > 0) {
                    jogador.setHp(Math.min(jogador.getHp() + 50, jogador.getHpMax()));
                    pocoes--;
                    System.out.println("Poção usada! HP atual: " + jogador.getHp() +"/"+ jogador.getHpMax());
                    System.out.println("Poções restantes: " + pocoes);

                    turno++;
                    continue;
                } else {
                    System.out.println("Você não tem possui mais poções!");
                }
            }

            else if(opcao == 3){
                System.out.println("Voce fugiu");
                break;
            }else{
                System.out.println("opção invalida");
                continue;
            }

            if (inimigo.getHp() <= 0) {
                System.out.println(inimigo.getNome() + " foi derrotado! ");

                //sistema de ganho de xp
                jogador.setXp(jogador.getXp() + 50);
                if (jogador.getXp() >= 100){
                    jogador.setLevel(jogador.getLevel() + 1);
                    jogador.setHpMax(jogador.getHpMax() + 5);
                    jogador.setHp(jogador.getHp() + 5);
                    jogador.setAtaque(jogador.getAtaque() + 2);
                    jogador.setDefesa(jogador.getDefesa() + 2);
                    jogador.setXp(jogador.getXp() - 100);
                    EvolutionService.verificarEvolucao(jogador);
                    MoveLearningService.verificarNovosGolpes(jogador);

                    System.out.println(jogador.getNome() + " Subiu para o nivel " + jogador.getLevel() + " !");
                    System.out.println("HP +5");
                    System.out.println("Ataque +2");
                    System.out.println("Defesa +2");
                }
                System.out.println(jogador.getNome() + " ganhou 50 XP !");
                System.out.println("XP ATUAL: " + jogador.getXp());

                break;
            }

            atacar(inimigo, jogador);
            if (jogador.getHp() <= 0) {
                System.out.println(jogador.getNome() + " foi derrotado! ");
                break;
            }
            turno++;
        }
    }


        public static void atacar (Pokemon atacante, Pokemon alvo){

            int dano = atacante.getAtaque() - alvo.getDefesa() / 2;
                if (dano < 1) {
                    dano = 1;
                }
            alvo.setHp(Math.max(0, alvo.getHp()-dano));
            System.out.println(atacante.getNome() + " causou " + dano + " de dano ");

            System.out.println(
                    alvo.getNome() + " HP " + alvo.getHp() + "/" + alvo.getHpMax()
            );
            System.out.println();
        }
    public static void atacarComMovimento(Pokemon atacante, Pokemon alvo,Move movimento) {

        Random random = new Random();

        double sorteio = random.nextDouble() * 100;

        if (movimento.getPpAtual() <= 0) {
            System.out.println("Esse movimento não tem mais PP!");
            return;
        }

        if (sorteio > movimento.getPrecisao()) {

            System.out.println(atacante.getNome() + " errou o golpe!");

            movimento.gastarPP();

            return;
        }

        movimento.gastarPP();

        int dano = movimento.getPoder() + atacante.getAtaque() - alvo.getDefesa();

        if (dano < 1) {
            dano = 1;
        }

        alvo.setHp(alvo.getHp() - dano);

        System.out.println("\n" + atacante.getNome() + " usou " + movimento.getNome() + "!");

        System.out.println("PP: " + movimento.getPpAtual() + "/" + movimento.getPpMax());

        System.out.println(alvo.getNome() + " HP: " + alvo.getHp() + "/" + alvo.getHpMax());
    }
}