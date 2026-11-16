package br.com.starlog.model;

/**
 * CAMADA MODEL (Modelo de Domínio)
 * Representa uma carga a ser despachada pela estação StarLog.
 *
 * Esta classe contém as regras de negócio e não depende da interface gráfica.
 */
public class Carga {

    private final String codigoRastreio;
    private final String categoria;
    private final double peso;
    private final double valorDeclarado;

    public Carga(String codigoRastreio, String categoria,
                 double peso, double valorDeclarado) {

        // Validação do código de rastreio
        if (codigoRastreio == null || codigoRastreio.isBlank()) {
            throw new IllegalArgumentException(
                "Código de rastreio não pode ser nulo ou vazio."
            );
        }

        // Validação da categoria
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException(
                "Categoria não pode ser nula ou vazia."
            );
        }

        // Validação do peso
        if (!Double.isFinite(peso) || peso <= 0) {
            throw new IllegalArgumentException(
                "Peso deve ser um número válido e maior que zero."
            );
        }

        // Validação do valor declarado
        if (!Double.isFinite(valorDeclarado) || valorDeclarado <= 0) {
            throw new IllegalArgumentException(
                "Valor declarado deve ser um número válido e maior que zero."
            );
        }

        this.codigoRastreio = codigoRastreio.trim().toUpperCase();
        this.categoria = categoria.trim().toUpperCase();
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPeso() {
        return peso;
    }

    public double getValorDeclarado() {
        return valorDeclarado;
    }

    /**
     * Calcula o seguro da carga.
     * Taxa base de 2% do valor declarado.
     * Sobretaxa de R$ 150 para cargas perigosas ou acima de 100 kg.
     */
    public double calcularSeguro() {

        double seguro = valorDeclarado * 0.02;

        if (categoria.equals("PERIGOSA") || peso > 100) {
            seguro += 150.00;
        }

        return seguro;
    }

    @Override
    public String toString() {
        return String.format(
            "[%s] Categoria: %s | Peso: %.2f kg | Valor: R$ %.2f | Seguro: R$ %.2f",
            codigoRastreio,
            categoria,
            peso,
            valorDeclarado,
            calcularSeguro()
        );
    }
}
