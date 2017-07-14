# Como usar

Para executar o simulador utilizando o terminal, algumas etapas simples são necessárias.

## Compile
Pra compilar, será necessário criar uma pasta destino para as classes compiladas.
  
    mkdir ~/adsd_simulacao_compilada

Compilar todas as classes para o diretório criado acima, (este comando deve ser executado na raiz deste repositorio):

    javac -d ~/adsd_simulacao_compilada $(find src -name *.java)
  
## Rode a simulação

    cd ~/adsd_simulacao_compilada
    java br.com.stenioelson.adsd.Simulacao <tempo_simulacao>

