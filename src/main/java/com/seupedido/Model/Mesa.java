package com.seupedido.Model;

public class Mesa {
    private Long id;
    private boolean ocupada;

    public Mesa() { }

    public Mesa(Long id, boolean ocupada) {
        this.id = id;
        this.ocupada = ocupada;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isOcupada() { return ocupada; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }
}
