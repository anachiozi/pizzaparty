package model;

import java.util.ArrayList;

public class Carrinho {
    private ArrayList<ItemPedido> itens;
    private double total;

    public Carrinho() {
        this.itens = new ArrayList<>();
        this.total = 0;
    }
    
    public ArrayList<ItemPedido> getItens() {
        return itens;
    }
    
    public int buscaProduto(int id) {
        for (ItemPedido item : this.itens) {
            if (item.getProduto().getId() == id) {
                return item.getQuantidade();
            }
        }
        return -1;
    }
    
    public void addItem(ItemPedido item) {
        this.itens.add(item);
    }
    
    public void removeItem(int id) {
        for (ItemPedido item : this.itens) {
            if (item.getProduto().getId() == id) {
                this.itens.remove(item);
                this.calculaTotal();
            }
        } 
    }
    
    public boolean isEmpty() {
        return this.itens.isEmpty();
    }
    
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public void clear() {
        this.itens.clear();
        this.total = 0;
    }
    
    private void calculaTotal() {
        double novoTotal = 0;
        for (ItemPedido item : this.itens) novoTotal += item.getSubtotal();
        this.setTotal(novoTotal);
    }
    
    public void updateQtd(int id, int qtd) {
        for (ItemPedido item : this.itens) {
            if (item.getProduto().getId() == id) {
                item.setQuantidade(qtd);
                item.calculaSubtotal();
                this.calculaTotal();
            }
        }
    }
}
