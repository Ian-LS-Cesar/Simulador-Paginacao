# Projeto de Simulador de Paginação

Feito por: Ian Lucas S. César 2310294
## Algoritmos de Paginação Selecionados:
- FIFO: No algoritmo FIFO (**First In First Out**) as págínas mais antigas são removidas dando lugar para novas páginas. Infelizmente esse algoritmo não oferece bons resultados por não levar em conta a idade das páginas. Além disso memórias carreegadas antyeriormente podem estar sendo acessada com muita frequência.
- LRU: **Least Recently Used**, ou LRU, é um algoritmo de substituição de página que leva em conta detalhes como quais páginas são mais antigas e menos usadas serão dadas prioridades. No entanto, esse algoritmo não é barato, já que existe a necessidade de manter listas que devem ser atualizadas a cada referência de memória.
- NFU: O **Not Frequently Used** implementa um contador para cada página com a incrementação do valor a cada vez que aquela página foi referenciada, com a substituição sendo feita para as páginas com menor valor no contador de referências.
- Segunda Chance (Clock): O método de paginação **Segunda Chance (Clock)** atribui a abstração de um relógio circular, nesse algoritmo existe um bit verificador para verificar se aquela página foi usada recentemente durante uma falta de página, caso sim, o ponteira irá avançar, caso contrário, ocorrerá a substituição de páginas

## Informações do Projeto
- Java JDK 23
- Maven
- JavaX
- Java AWT
- JFreeChart 1.5.5

### Execução:
1. Executar na classe Main.java (**Importante:** Insira a dependência JFreeChart conforme o formato abaixo no *pom.xml* do projeto Maven com a versão 1.5.5 dentro de <dependencies></dependencies>)
```
<dependency>
            <groupId>org.jfree</groupId>
            <artifactId>jfreechart</artifactId>
            <version>1.5.5</version>
</dependency>
```
2. Ao executar o código, abra a janela do programa com interface gráfica feita pelo Java Swing, preencha as páginas que serão lidas no programa, deixando um espaço entre cada uma.
3. Em seguida, digite um número inteiro para inserir a quantidade de quadros na execução dos 4 algoritmos.
4. Após as informações já estarem inseridas, clique no botão "Executar".

Link Download do JDK utilizado:
www.oracle.com/java/technologies/javase/jdk23-archive-downloads.html
Link Maven:
https://maven.apache.org/index.html
Link da Página para baixar a depedência no Projeto Maven:
https://mvnrepository.com/artifact/org.jfree/jfreechart/1.5.5
Link do Projeto no GitHub:
https://github.com/Ian-LS-Cesar/Simulador-Paginacao/

