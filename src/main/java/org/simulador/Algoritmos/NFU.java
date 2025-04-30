
package org.simulador.Algoritmos;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class NFU implements AlgoritmoPaginacao {

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

        LinkedHashSet<Integer> memoria = new LinkedHashSet<>();
        HashMap<Integer, Integer> contador = new HashMap<>();
        int falhasDePagina = 0;

        System.out.println("Memória inicial: " + memoria);

        for (int pagina : paginas) {
            System.out.println("Processando página: " + pagina);

            // Preenche os quadros inicialmente
            if (memoria.size() < quadros) {
                if (!memoria.contains(pagina)) {
                    memoria.add(pagina);
                    contador.put(pagina, 1);
                    System.out.println("Página " + pagina + " adicionada (preenchendo quadros).");
                } else {
                    contador.put(pagina, contador.get(pagina) + 1);
                    System.out.println("Página " + pagina + " já está na memória. Contador atualizado.");
                }
                continue;
            }

            // Lógica de substituição começa quando os quadros estão cheios
            if (!memoria.contains(pagina)) {
                int paginaRemover = encontrarPaginaMenosUsada(memoria, contador);
                memoria.remove(paginaRemover);
                contador.remove(paginaRemover);
                System.out.println("Página removida: " + paginaRemover);
                falhasDePagina++; // Incrementa falhas de página apenas quando a memória está cheia

                memoria.add(pagina);
                contador.put(pagina, 1);
                System.out.println("Página " + pagina + " adicionada.");
            } else {
                contador.put(pagina, contador.get(pagina) + 1);
                System.out.println("Página " + pagina + " já está na memória. Contador atualizado.");
            }

            System.out.println("Memória atual: " + memoria);
            System.out.println("Contadores: " + contador);
        }

        System.out.println("Total de falhas de página em NFU: " + falhasDePagina);
        return falhasDePagina;
    }

    private static int encontrarPaginaMenosUsada(LinkedHashSet<Integer> memoria, HashMap<Integer, Integer> contador) {
        int paginaMenosUsada = -1;
        int menorContador = Integer.MAX_VALUE;

        for (int pagina : memoria) {
            int contagem = contador.getOrDefault(pagina, 0);
            if (contagem < menorContador) {
                menorContador = contagem;
                paginaMenosUsada = pagina;
            }
        }
        return paginaMenosUsada;
    }
}