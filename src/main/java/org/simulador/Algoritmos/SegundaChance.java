package org.simulador.Algoritmos;

import java.util.LinkedList;

public class SegundaChance implements AlgoritmoPaginacao{

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

        LinkedList<Integer> memoria = new LinkedList<>();
        LinkedList<Integer> bitsUso = new LinkedList<>();

        int ponteiro = 0;
        int falhasDePagina = 0;

        System.out.println("Memória inicial: " + memoria);

        // Preenche os quadros inicialmente
        int i = 0;
        while (memoria.size() < quadros && i < paginas.length) {
            if (!memoria.contains(paginas[i])) {
                memoria.add(paginas[i]);
                bitsUso.add(1);
                falhasDePagina++;
                System.out.println("Página " + paginas[i] + " adicionada inicialmente.");
            }
            i++;
        }

        System.out.println("Memória após preenchimento inicial: " + memoria);
        System.out.println("Bits de uso após preenchimento inicial: " + bitsUso);

        // Processa o restante das páginas
        for (; i < paginas.length; i++) {
            int pagina = paginas[i];
            System.out.println("Processando página: " + pagina);

            if (!memoria.contains(pagina)) {
                // Substituição de página quando os quadros estão cheios
                while (true) {
                    if (bitsUso.get(ponteiro) == 0) {
                        System.out.println("Página removida: " + memoria.get(ponteiro));
                        memoria.set(ponteiro, pagina);
                        bitsUso.set(ponteiro, 1);
                        falhasDePagina++;
                        ponteiro = (ponteiro + 1) % quadros;
                        break;
                    } else {
                        bitsUso.set(ponteiro, 0);
                        ponteiro = (ponteiro + 1) % quadros;
                    }
                }

                System.out.println("Falha da página! Página " + pagina + " adicionada.");
            } else {
                int indice = memoria.indexOf(pagina);
                bitsUso.set(indice, 1);
                System.out.println("Página " + pagina + " já está na memória. Bit de uso atualizado.");
            }

            System.out.println("Memória atual: " + memoria);
            System.out.println("Bits de uso: " + bitsUso);
        }

        System.out.println("Total de falhas de página em Segunda Chance: " + falhasDePagina);
        return falhasDePagina;
    }
}