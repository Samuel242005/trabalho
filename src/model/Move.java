package model;

public class Move {

    private String nome;
    private int ppAtual;
    private int ppMax;
    private String tipo;
    private double precisao;
    private int poder;

    public Move(String nome, int ppAtual, int ppMax, String tipo, double precisao, int poder) {
        this.nome = nome;
        this.ppAtual = ppAtual;
        this.ppMax = ppMax;
        this.tipo = tipo;
        this.precisao = precisao;
        this.poder = poder;
    }

    public String getNome() {
        return nome;
    }

    public int getPpAtual() {
        return ppAtual;
    }

    public int getPpMax() {
        return ppMax;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecisao() {
        return precisao;
    }

    public int getPoder() {
        return poder;
    }

    public void gastarPP() {
        if (ppAtual > 0) {
            ppAtual--;
        }
    }

}