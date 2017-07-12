package br.com.stenioelson.adsd.modelos;

/**
 * Created by stenio on 12/07/17.
 */
public abstract class Fregues {
    public String id;
    private static Integer numeroInstancias = 0;

    public Fregues() {
        this.id = getSuffix() + getNumeroInstancias();
        incrementaInstancias();
    }

    public abstract String getSuffix();

    private void incrementaInstancias() {
        Fregues.numeroInstancias += 1;
    }

    public Integer getNumeroInstancias() {
        return Fregues.numeroInstancias;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
