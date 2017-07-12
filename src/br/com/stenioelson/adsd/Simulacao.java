package br.com.stenioelson.adsd;

import br.com.stenioelson.adsd.modelos.Evento;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by stenio on 12/07/17.
 */
public class Simulacao {
    public static void main(String[] args) throws Exception {
        Integer duracao = 10; //Duracao padrao
        if (args.length > 0) {
            try {
                duracao = Integer.valueOf(args[0]);
            } catch (Exception e) {
                System.err.println("O primeiro argumento deve ser um inteiro representando o tempo de duracao do sistema.");
            }
        }

        Simulador simulador = new Simulador(duracao);
        simulador.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object evento) {
                if (evento.getClass().equals(Evento.class)) {
                    System.out.println(evento + System.lineSeparator());
                }
            }
        });
        simulador.run();
    }
}
