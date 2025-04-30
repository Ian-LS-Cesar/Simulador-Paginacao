
package org.simulador;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.simulador.Algoritmos.AlgoritmoPaginacao;
import org.simulador.Algoritmos.FIFO;
import org.simulador.Algoritmos.LRU;
import org.simulador.Algoritmos.NFU;
import org.simulador.Algoritmos.SegundaChance;

public class Main {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = criarJanelaPrincipal();
            frame.setVisible(true);
        });
    }

    private static JFrame criarJanelaPrincipal() {
        JFrame frame = new JFrame("Simulador de Paginação");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new FlowLayout()); // Alterado para FlowLayout para maior controle

        JLabel labelPaginas = new JLabel("Digite as páginas (Separe por espaço):");
        JTextField inputPaginas = new JTextField();
        inputPaginas.setPreferredSize(new Dimension(300, 30)); // Define o tamanho da caixa de texto

        JLabel labelQuadros = new JLabel("Quantidade de quadros disponíveis na memória:");
        JTextField inputQuadros = new JTextField();
        inputQuadros.setPreferredSize(new Dimension(300, 30)); // Define o tamanho da caixa de texto

        JButton executarBotao = new JButton("Executar");
        executarBotao.setPreferredSize(new Dimension(150, 40)); // Define o tamanho do botão

        frame.add(labelPaginas);
        frame.add(inputPaginas);
        frame.add(labelQuadros);
        frame.add(inputQuadros);
        frame.add(executarBotao);

        executarBotao.addActionListener((ActionEvent e) -> {
            executarSimulacao(inputPaginas.getText(), inputQuadros.getText(), frame);
        });

        return frame;
    }

    private static void executarSimulacao(String paginasInput, String quadrosInput, JFrame frame) {
        try {
            int quadros = Integer.parseInt(quadrosInput);
            int[] paginas = parsePaginas(paginasInput);

            AlgoritmoPaginacao fifo = new FIFO();
            AlgoritmoPaginacao lru = new LRU();
            AlgoritmoPaginacao nfu = new NFU();
            AlgoritmoPaginacao segundaChance = new SegundaChance();

            int fifoFaltas = fifo.executar(paginas, quadros);
            int lruFaltas = lru.executar(paginas, quadros);
            int nfuFaltas = nfu.executar(paginas, quadros);
            int segundaChanceFaltas = segundaChance.executar(paginas, quadros);

            // Exibe o gráfico na janela principal
            exibirGrafico(fifoFaltas, lruFaltas, nfuFaltas, segundaChanceFaltas, frame);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor insira valores válidos.");
        }
    }

    private static int[] parsePaginas(String input) {
        String[] partes = input.trim().split("\\s+");
        int[] paginas = new int[partes.length];
        for (int i = 0; i < partes.length; i++) {
            paginas[i] = Integer.parseInt(partes[i]);
        }
        return paginas;
    }

    private static void exibirGrafico(int fifo, int lru, int nfu, int segundaChance, JFrame frame) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(fifo, "FIFO", "");
        dataset.addValue(lru, "LRU", "");
        dataset.addValue(nfu, "NFU", "");
        dataset.addValue(segundaChance, "Segunda Chance", "");

        JFreeChart graficoBarra = ChartFactory.createBarChart(
                "Falta de Páginas por Algoritmo",
                "Algoritmo",
                "Faltas de Páginas",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(graficoBarra);
        chartPanel.setPreferredSize(new Dimension(750, 400));

        // Remove gráficos anteriores e o JLabel, se existirem
        for (Component comp : frame.getContentPane().getComponents()) {
            if (comp instanceof ChartPanel || comp instanceof JLabel) {
                frame.getContentPane().remove(comp);
            }
        }

        frame.add(chartPanel);

        // Determina o melhor algoritmo considerando empates
        int[] faltas = {fifo, lru, nfu, segundaChance};
        String[] algoritmos = {"FIFO", "LRU", "NFU", "Segunda Chance"};
        int menorFaltas = Integer.MAX_VALUE;
        int maiorFaltas = Integer.MIN_VALUE;
        StringBuilder melhoresAlgoritmos = new StringBuilder();

        for (int falta : faltas) {
            if (falta < menorFaltas) menorFaltas = falta;
            if (falta > maiorFaltas) maiorFaltas = falta;
        }

        int countMenor = 0;
        for (int i = 0; i < faltas.length; i++) {
            if (faltas[i] == menorFaltas) {
                melhoresAlgoritmos.append(algoritmos[i]).append(", ");
                countMenor++;
            }
        }

        String mensagem;
        mensagem = switch (countMenor) {
            case 4 -> "Todos os algoritmos tiveram a mesma quantidade de faltas: " + menorFaltas + ".";
            case 3 -> "Todos os algoritmos, exceto um tiveram a mesma quanrtidade de faltas.";
            case 2 -> "Os melhores algoritmos foram: " + melhoresAlgoritmos.substring(0, melhoresAlgoritmos.length() - 2) + " com " + menorFaltas + " faltas.";
            default -> "O melhor algoritmo foi: " + melhoresAlgoritmos.substring(0, melhoresAlgoritmos.length() - 2) + " com " + menorFaltas + " faltas.";
        };

        JLabel melhorAlgoritmoLabel = new JLabel(mensagem);
        melhorAlgoritmoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        melhorAlgoritmoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(melhorAlgoritmoLabel);

        frame.revalidate();
        frame.repaint();
    }
}
