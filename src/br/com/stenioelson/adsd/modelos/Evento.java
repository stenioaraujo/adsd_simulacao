package br.com.stenioelson.adsd.modelos;

/**
 * Created by stenio on 12/07/17.
 */
public class Evento {
    private String tipo;
    private Integer quando;
    private String descricao;

    public Evento(String tipo,  Integer quando, String descricao) {
        this.tipo = tipo;
        this.quando = quando;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Tipo de evento: " + tipo + ", Momento do evento: " + quando + System.lineSeparator() +
               descricao;
    }
}
