package br.com.starlog.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.starlog.model.Carga;
import br.com.starlog.controller.CargaController;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SUÍTE DE TESTES TDD • AULA 24 (STARLOG LOGISTICS)
 * Valida as regras de negócio do Model e do Controller.
 */
public class CargaMvcTest {

    @Test
    @DisplayName("TDD 01: Seguro padrão de 2% para carga normal")
    void deveCalcularSeguroPadrao() {
        Carga c = new Carga("CARG-01", "GERAL", 50.0, 1000.0);

        assertEquals(20.0, c.calcularSeguro(), 0.001);
    }

    @Test
    @DisplayName("TDD 02: Sobretaxa de R$ 150 para carga perigosa")
    void deveAplicarSobretaxaCargaPerigosa() {
        Carga c = new Carga("CARG-02", "PERIGOSA", 20.0, 1000.0);

        assertEquals(170.0, c.calcularSeguro(), 0.001);
    }

    @Test
    @DisplayName("TDD 03: Sobretaxa para carga com peso superior a 100kg")
    void deveAplicarSobretaxaCargaPesada() {
        Carga c = new Carga("CARG-03", "ALIMENTAR", 120.0, 1000.0);

        assertEquals(170.0, c.calcularSeguro(), 0.001);
    }

    @Test
    @DisplayName("TDD 04: Construtor deve barrar peso zero ou negativo")
    void deveBarrarPesoInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carga("CARG-04", "GERAL", 0.0, 500.0)
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Carga("CARG-04", "GERAL", -10.0, 500.0)
        );
    }

    @Test
    @DisplayName("TDD 05: Construtor deve barrar código vazio ou em branco")
    void deveBarrarCodigoInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carga("   ", "GERAL", 10.0, 500.0)
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Carga(null, "GERAL", 10.0, 500.0)
        );
    }

    @Test
    @DisplayName("TDD 06: Controller deve iniciar com repositório vazio")
    void controllerDeveIniciarComRepositorioVazio() {
        CargaController controller = new CargaController();

        assertEquals(0, controller.getRepositorio().size());
    }

    @Test
    @DisplayName("TDD 07: Construtor deve barrar categoria vazia ou nula")
    void deveBarrarCategoriaInvalida() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carga("CARG-07", "", 10.0, 500.0)
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Carga("CARG-07", null, 10.0, 500.0)
        );
    }

    @Test
    @DisplayName("TDD 08: Construtor deve barrar valor declarado zero ou negativo")
    void deveBarrarValorDeclaradoInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
            new Carga("CARG-08", "GERAL", 10.0, 0.0)
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Carga("CARG-08", "GERAL", 10.0, -500.0)
        );
    }

    @Test
    @DisplayName("TDD 09: Carga perigosa e pesada deve receber apenas uma sobretaxa")
    void deveAplicarUmaUnicaSobretaxa() {
        Carga c = new Carga("CARG-09", "PERIGOSA", 150.0, 1000.0);

        assertEquals(170.0, c.calcularSeguro(), 0.001);
    }

    @Test
    @DisplayName("TDD 10: Seguro deve considerar corretamente o valor declarado")
    void deveCalcularSeguroComValorDiferente() {
        Carga c = new Carga("CARG-10", "GERAL", 50.0, 2500.0);

        assertEquals(50.0, c.calcularSeguro(), 0.001);
    }
}
