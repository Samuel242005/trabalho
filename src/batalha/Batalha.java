package batalha;
import model.Pokemon;
import java.util.Scanner;

public class Batalha {
    public static void iniciar(Pokemon jogador, Pokemon inimigo)
    {
        System.out.println(jogador.getNome() + " VS " + inimigo.getNome());
        Scanner scanner = new Scanner(System.in);
        while (jogador.getHp() > 0 && inimigo.getHp() > 0)
        {
            System.out.println("\n1 - Atacar");
            System.out.println("2 - Usar Poção");
            System.out.println("3 - Fugir");

            int opcao = scanner.nextInt();

            if (opcao == 1 ){
                atacar(jogador, inimigo);
            } else if (opcao == 2) {
                System.out.println("Pocão nao implementada");
            } else {
                System.out.println("Voce fugiu");
                break;
            }

            atacar(jogador, inimigo);
            if (inimigo.getHp() <= 0) {
                System.out.println(inimigo.getNome() + " foi derrotado! ");
                break;
            }

            atacar(inimigo, jogador);

            if (jogador.getHp() <= 0) {
                System.out.println(jogador.getNome() + " foi derrotado! ");
                break;
            }
        }
    }

        public static void atacar (Pokemon atacante, Pokemon alvo){

            int dano = atacante.getAtaque() - alvo.getDefesa() / 2;
                if (dano < 1) {
                    dano = 1;
                }
            alvo.setHp(alvo.getHp() - dano);
            System.out.println(atacante.getNome() + " causou " + dano + " de dano ");

            System.out.println(
                    alvo.getNome() + " HP " + alvo.getHp() + "/" + alvo.getHpMax()
            );
            System.out.println();
        }
    }