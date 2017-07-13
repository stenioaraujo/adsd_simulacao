package br.com.stenioelson.adsd;

import br.com.stenioelson.adsd.modelos.*;

import java.util.*;

public class Simulador extends Observable {
    private Integer duracao;
    private Integer tempoAtual = 1;
    private Processador processador = new Processador();
    private List<FreguesClasseUm> filaUm = new ArrayList<>();
    private List<FreguesClasseDois> filaDois = new ArrayList<>();
    private Map<Integer, List<Fregues>> chegadaAlocada = new HashMap<>();

    public Simulador(Integer duracao) {
        this.duracao = duracao;
    }

    public void run() throws Exception {
        gerarFreguesesIniciais();
        for (;tempoAtual <= duracao; tempoAtual++) {
            realizarEtapas();
        }
    }

    private void realizarEtapas() throws Exception {
        trataFreguesesChegada();
        tratarFreguesesFilas();
    }

    /**
     * Verifica se existe algum Fregues na filaUm e depois na Dois, se houver Fregues tenta processar ele.
     *
     * A filaUm tem prioridade em todas as operações.
     * @throws Exception Se tentar tratar um Fregues com outro sendo processado
     */
    private void tratarFreguesesFilas() throws Exception {
        if (!filaUm.isEmpty()) {
            processar(filaUm);
        } else if (!filaDois.isEmpty()) {
            processar(filaDois);
        }
    }

    /**
     * 1 - Se processador esta livre, remove cliente na frente da fila e coloca cliente no processador
     *     com tempo de processamento entre 3 e 7.
     * 2 - Se processador esta ocupado, decrementa tempo ate terminar no processador. Se tempo ficou zero,
     *     marca cliente como atendido, tira ele do processador
     *
     * @param fila uma fila de fregueses
     * @throws Exception Se tentar tratar um Fregues com outro sendo processado
     */
    private void processar(List<? extends Fregues> fila) throws Exception {
        if (processador.isBusy()) {
            processador.fazClock();
            if (!processador.isBusy()) {
                criarEvento("Término de serviço");
            }
        } else {
            Fregues fregues = fila.get(0);
            fila.remove(0);
            processador.trataFregues(fregues, getRandom(3, 7));
            criarEvento("Inicio de serviço");
        }
    }

    /**
     * Adiciona todos os fregueses alocados para o tempo a uma lista especifica.
     * Pra cada Fregues adicionado um novo fregues vai ser alocado pra um tempo futuro.
     * @throws Exception Se não existir o tempo de Fregues
     */
    private void trataFreguesesChegada() throws Exception {
        List<Fregues> chegada = getChegadaOuCrieListaSeNaoExiste(this.tempoAtual);
        if (!chegada.isEmpty()) {
            for (Fregues fregues: chegada) {
                adicionaFreguesNaFila(fregues);
                criaNovoFreguesChegada(fregues.getClass());
            }
            chegada.clear();
        }
    }

    /**
     * Aloca um fregues de um tipo para um tmepo futuro
     * @param freguesClasse o tipo do Fregues a ser alocado
     * @throws Exception Se não existir este tipo
     */
    private void criaNovoFreguesChegada(Class<? extends Fregues> freguesClasse) throws Exception {
        Fregues fregues = null;
        int tempoFuturo;
        if (freguesClasse.equals(FreguesClasseUm.class)) {
            fregues = new FreguesClasseUm();
            tempoFuturo = this.tempoAtual + getRandom(1, 10);
        } else if (freguesClasse.equals(FreguesClasseDois.class)) {
            fregues = new FreguesClasseDois();
            tempoFuturo = this.tempoAtual + getRandom(1, 5);
        } else {
            throw new Exception("Essa classe ainda não existe");
        }

        adicionaFreguesNaChegada(tempoFuturo, fregues);
    }

    /**
     * Adiciona o fregues a sua fila especifica. FreguesClasseUm vai para filaUm, FreguesClasseDois vai para filaDois
     * @param fregues o Fregues a ser alocado
     * @throws Exception Se a fila não existir
     */
    private void adicionaFreguesNaFila(Fregues fregues) throws Exception {
        if (fregues.getClass().equals(FreguesClasseUm.class)) {
            filaUm.add((FreguesClasseUm)fregues);
        } else if (fregues.getClass().equals(FreguesClasseDois.class)) {
            filaDois.add((FreguesClasseDois)fregues);
        } else {
            throw new Exception("Essa classe ainda não existe");
        }
        criarEvento("colocar freguês na fila");
    }

    /**
     * Adicoina o fregues para o tempo futuro específico
     * @param tempoFuturo o tempo que o fregues deve ser adicionado
     * @param fregues O fregues a ser adicionado
     */
    private void adicionaFreguesNaChegada(int tempoFuturo, Fregues fregues) {
        List<Fregues> chegada = getChegadaOuCrieListaSeNaoExiste(tempoFuturo);
        chegada.add(fregues);
    }

    /**
     * Gera os fregueses iniciais
     */
    private void gerarFreguesesIniciais() {
        adicionaFreguesNaChegada(1, new FreguesClasseUm());
        adicionaFreguesNaChegada(1, new FreguesClasseDois());
    }

    private Integer getRandom(Integer begin, Integer end) {
        return new Random().nextInt((end+1) - begin) + begin;
    }

    /**
     * Recupera os fregueses alocados para um determinado tempo.
     * Ele cria uma lista vazia se não existir fregueses para este tempo.
     * @param tempo O tempo a ser recuperado a lista de fregueses alocados
     * @return Lista de fregueses alocados para o tempo tempo
     */
    private List<Fregues> getChegadaOuCrieListaSeNaoExiste(Integer tempo) {
        if (!chegadaAlocada.containsKey(tempo)) {
            chegadaAlocada.put(tempo, new ArrayList<>());
        }

        return chegadaAlocada.get(tempo);
    }

    private void criarEvento(String tipo) {
        setChanged();
        notifyObservers(new Evento(tipo, this.tempoAtual, this.toString()));
    }

    @Override
    public String toString() {
        String elFilaUm = "";
        String elFilaDois = "";
        String elServico = "";

        for (Fregues fregues: filaUm) {
            elFilaUm += fregues + " ";
        }
        for (Fregues fregues: filaDois) {
            elFilaDois += fregues + " ";
        }

        if (processador.isBusy())
            elServico = processador.getFregues().toString();

        return "Elementos na Fila 1: " + elFilaUm + System.lineSeparator() +
               "Elementos na Fila 2: " + elFilaDois + System.lineSeparator() +
               "Elemento no serviço: " + elServico;
    }
}
