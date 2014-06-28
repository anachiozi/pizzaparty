package model;

import java.util.ArrayList;

public class Pedido {
    private int id;
    private String data_hora;
    private int mesa;
    private Usuario usuario;
    private String tipo_interacao; /*LF = local-funcionario, LC = local-cliente, RM = remoto*/
    private boolean forma_pag; /*0 = cartão, 1 = dinheiro*/
    private ArrayList<ItemPedido> itens;
    private double total;

    public Pedido() {
        this.itens = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData_hora(String data_hora) {
        String dt[] = data_hora.split(" ");
        
        String dmy[] = dt[0].split("-");
        String hm[] = dt[1].split(":");
        
        this.data_hora = dmy[2]+"/"+dmy[1]+"/"+dmy[0]+" "+hm[0]+":"+hm[1];
    }

    public String getData_hora() {
        return data_hora;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipo_interacao() {
        return tipo_interacao;
    }
    
    /*retorna em formato amigável*/
    public String getInteracao() {
        /*LF = local-funcionario, LC = local-cliente, RM = remoto*/
        if (this.getTipo_interacao().equalsIgnoreCase("LF"))
            return "Local: funcionário";
        else if (this.getTipo_interacao().equalsIgnoreCase("LC"))
            return "Local: cliente";
        else
            return "Remoto";
    }

    public void setTipo_interacao(String tipo_interacao) {
        this.tipo_interacao = tipo_interacao;
    }

    public boolean getForma_pag() {
        return forma_pag;
    }
    
    /*retorna em formato amigável*/
    public String getPag() {
        /*0 = cartão, 1 = dinheiro*/
        if (this.getForma_pag())
            return "Dinheiro";
        else
            return "Cartão";
    }

    public void setForma_pag(boolean forma_pag) {
        this.forma_pag = forma_pag;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }
    
    public void setItens(ArrayList<ItemPedido> itens) {
        this.itens = itens;
    }
    
    public void addItem(ItemPedido item) {
        this.itens.add(item);
        this.total += item.getSubtotal();
    }
    
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
