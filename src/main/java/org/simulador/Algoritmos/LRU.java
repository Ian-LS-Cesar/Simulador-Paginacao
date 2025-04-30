
package org.simulador.Algoritmos;

import java.util.ArrayList;

public class LRU implements AlgoritmoPaginacao {

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

        ArrayList<Integer> memoria = new ArrayList<>();
        int falhasDePagina = 0;

        System.out.println("Memória inicial: " + memoria);

        for (int pagina : paginas) {
            System.out.println("Processando página: " + pagina);

            // Preencher a memória até que todos os quadros estejam ocupados
            if (memoria.size() < quadros) {
                if (!memoria.contains(pagina)) {
                    memoria.add(pagina);
                    System.out.println("Página " + pagina + " adicionada (preenchendo memória).");
                } else {
                    System.out.println("Página " + pagina + " já está na memória (preenchendo memória).");
                }
                System.out.println("Memória atual: " + memoria);
                continue;
            }

            // Processar páginas apenas quando a memória está cheia
            if (!memoria.contains(pagina)) {
                int removida = memoria.remove(0);
                System.out.println("Página removida: " + removida);
                falhasDePagina++;
                memoria.add(pagina);
                System.out.println("Página " + pagina + " adicionada.");
            } else {
                memoria.remove((Integer) pagina);
                memoria.add(pagina);
                System.out.println("Página " + pagina + " já está na memória. Posição atualizada.");
            }

            System.out.println("Memória atual: " + memoria);
        }

        System.out.println("Total de falhas de página em LRU: " + falhasDePagina);
        return falhasDePagina;
    }
}