package model;

public class Ingrediente {
    
    private int id;
    private int quantidade;
    private int cod_pizza;
    private int cod_produto;

    public Ingrediente() {
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getCod_pizza() {
        return cod_pizza;
    }

    public void setCod_pizza(int cod_pizza) {
        this.cod_pizza = cod_pizza;
    }

    public int getCod_produto() {
        return cod_produto;
    }

    public void setCod_produto(int cod_produto) {
        this.cod_produto = cod_produto;
    }
    
    
}
