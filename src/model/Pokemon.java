package model;
import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private int id;
    private String nome;
    private int hp;
    private int ataque;
    private int defesa;
    private String tipo;
    private int level;
    private int xp;
    private int hpMax;
    private List<Move> movimentos = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getLevel() {
        return level;
    }

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public Pokemon(
            int id,
            String nome,
            int hp,
            int ataque,
            int defesa,
            String tipo,
            int level,
            int xp
    ){
        this.id = id;
        this.nome = nome;
        this.hp = hp;
        this.ataque = ataque;
        this.defesa = defesa;
        this.tipo = tipo;
        this.level = level;
        this.xp = xp;
        this.hpMax = hp;
    }
    public List<Move> getMovimentos() {
        return movimentos;
    }

    public void adicionarMovimento(Move movimento) {
        movimentos.add(movimento);
    }

}


