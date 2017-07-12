package br.com.stenioelson.adsd.modelos;

/**
 * Created by stenio on 12/07/17.
 */
public class Processador {
    private Integer tempoRestante = 0;
    private Fregues fregues;

    public boolean isBusy() {
        return fregues != null;
    }

    public void trataFregues(Fregues fregues, Integer tempoProcessar) throws Exception{
        if (isBusy())
            throw new Exception("Só pode um fregues por vez!");

        this.fregues = fregues;
        this.tempoRestante = tempoProcessar;
    }

    public void fazClock() {
        this.tempoRestante -= 1;

        if (this.tempoRestante <= 0) {
            this.tempoRestante = 0;
            fregues = null;
        }
    }

    public Fregues getFregues() {
        return fregues;
    }

    public Integer getTempoRestante() {
        return this.tempoRestante;
    }
}
