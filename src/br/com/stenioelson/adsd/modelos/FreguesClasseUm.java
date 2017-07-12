package br.com.stenioelson.adsd.modelos;

/**
 * Created by stenio on 12/07/17.
 */
public class FreguesClasseUm extends Fregues{
    private static Integer numeroInstancias = 0;

    @Override
    public String getSuffix() {
        return "FreguesClasseUm_";
    }
}
