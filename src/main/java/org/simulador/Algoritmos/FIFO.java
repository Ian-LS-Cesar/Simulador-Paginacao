
package org.simulador.Algoritmos;

import java.util.LinkedList;
import java.util.Queue;

public class FIFO implements AlgoritmoPaginacao {

    @Override
    public int executar(int[] paginas, int quadros) {
        if (quadros <= 0) {
            System.out.println("O número de quadros deve ser maior que zero.");
            return 0;
        }

        if (paginas == null || paginas.length == 0) {
            System.out.println("A lista de páginas não pode ser nula ou vazia.");
            return 0;
        }

        Queue<Integer> memoria = new LinkedList<>();
        int falhasDePagina = 0;

        System.out.println("Memória inicial: " + memoria);
        for (int i = 0; i < paginas.length; i++) {
            int pagina = paginas[i];
            System.out.println("Processando página: " + pagina);

            // Preenche os quadros até que estejam todos ocupados
            if (memoria.size() < quadros) {
                if (!memoria.contains(pagina)) {
                    memoria.add(pagina);
                    System.out.println("Página " + pagina + " adicionada (preenchendo quadros).");
                } else {
                    System.out.println("Página " + pagina + " já está na memória.");
                }
                System.out.println("Memória atual: " + memoria);
                continue;
            }

            // Começa o processamento normal após os quadros estarem cheios
            if (!memoria.contains(pagina)) {
                int removida = memoria.poll();
                System.out.println("Página removida: " + removida);
                falhasDePagina++;

                memoria.add(pagina);
                System.out.println("Página " + pagina + " adicionada.");
            } else {
                System.out.println("Página " + pagina + " já está na memória.");
            }

            System.out.println("Memória atual: " + memoria);
        }

        System.out.println("Total de falhas de página em FIFO: " + falhasDePagina);
        return falhasDePagina;
    }


}