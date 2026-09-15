package br.com.mpontes.hamburgueria.model;

public class Pedido {

    private int id;
    private String dataHora;
    private String cliente;
    private String tipo;
    private String status;
    private double total;

    public Pedido(String dataHora,
                  String cliente,
                  String tipo,
                  String status,
                  double total) {

        this.dataHora = dataHora;
        this.cliente = cliente;
        this.tipo = tipo;
        this.status = status;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
