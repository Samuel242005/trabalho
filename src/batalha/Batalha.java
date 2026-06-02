package batalha;
import model.Pokemon;
import java.util.Scanner;

public class Batalha {


    public static void iniciar(Pokemon jogador, Pokemon inimigo)
    {
        int pocoes = 5;
        int turno = 1;
        System.out.println(jogador.getNome() + " VS " + inimigo.getNome());
        Scanner scanner = new Scanner(System.in);
        while (jogador.getHp() > 0 && inimigo.getHp() > 0)
        {
            System.out.println("\n=== TURNO " + turno + " ====");
            System.out.println(jogador.getNome() +" HP: "+ jogador.getHp() + "/" + jogador.getHpMax());
            System.out.println(inimigo.getNome() + " HP: "+ inimigo.getHp() + "/" + inimigo.getHpMax());

            System.out.println("Poções restantes: " + pocoes);


            System.out.println("\n1 - Atacar");
            System.out.println("2 - Usar Poção");
            System.out.println("3 - Fugir");

            int opcao = scanner.nextInt();

            if (opcao == 1 )
            {
                atacar(jogador, inimigo);
            }

            else if (opcao == 2) {
                if (pocoes > 0) {
                    jogador.setHp(Math.min(jogador.getHp() + 50, jogador.getHpMax()));
                    pocoes--;
                    System.out.println("Poção usada! HP atual: " + jogador.getHp() +"/"+ jogador.getHpMax());
                    System.out.println("Poções restantes: " + pocoes);
                    atacar(inimigo, jogador);

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
                    jogador.setXp(jogador.getXp() - 100);

                    System.out.println(jogador.getNome() + " Subiu para o nivel "
                                     + jogador.getLevel() + " !");
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
    }