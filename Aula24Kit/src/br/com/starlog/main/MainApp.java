package br.com.starlog.main;

import br.com.starlog.controller.CargaController;
import br.com.starlog.view.TelaCadastroCarga;

import javax.swing.SwingUtilities;

/**
 * PONTO DE ENTRADA DO SISTEMA (Bootstrap)
 * Responsável por instanciar o Controller e conectar a View
 * de forma segura na Event Dispatch Thread (EDT).
 */
public class MainApp {

    public static void main(String[] args) {

        // Inicialização segura da interface gráfica
        SwingUtilities.invokeLater(() -> {

            // 1. Instancia o Controller
            CargaController controller = new CargaController();

            // 2. Injeta o Controller na View
            TelaCadastroCarga tela = new TelaCadastroCarga(controller);

            // 3. Torna a janela visível
            tela.setVisible(true);
        });
    }
}
