package model;


public class Produto {
     private int id;
     private String nome;
     private String tipo;
     private int qtd;
     private double preco;
     private boolean cardapio;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tpo) {
        this.tipo = tpo;
    }
    
    

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isCardapio() {
        return cardapio;
    }

    public void setCardapio(boolean cardapio) {
        this.cardapio = cardapio;
    }
    
    
}