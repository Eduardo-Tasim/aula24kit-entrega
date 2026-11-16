package br.com.starlog.controller;

import br.com.starlog.model.Carga;
import br.com.starlog.view.TelaCadastroCarga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CAMADA CONTROLLER
 * Responsável por controlar o fluxo entre View e Model.
 */
public class CargaController {

    private final List<Carga> repositorio = new ArrayList<>();

    /**
     * Recebe os dados da tela, valida e cadastra uma nova carga.
     */
    public void cadastrarCarga(
            String codigoRaw,
            String categoriaRaw,
            String pesoRaw,
            String valorRaw,
            TelaCadastroCarga tela) {

        // Verifica se a tela foi informada
        if (tela == null) {
            throw new IllegalArgumentException(
                "A tela de cadastro não pode ser nula."
            );
        }

        try {

            // PASSO 1: Validar campos numéricos vazios
            if (pesoRaw == null || pesoRaw.isBlank()
                    || valorRaw == null || valorRaw.isBlank()) {

                throw new NumberFormatException();
            }

            // PASSO 2: Converter os valores para double
            double peso = Double.parseDouble(
                pesoRaw.trim().replace(",", ".")
            );

            double valor = Double.parseDouble(
                valorRaw.trim().replace(",", ".")
            );

            // PASSO 3: Criar o objeto Carga
            Carga novaCarga = new Carga(
                codigoRaw,
                categoriaRaw,
                peso,
                valor
            );

            // PASSO 4: Adicionar ao repositório
            repositorio.add(novaCarga);

            // PASSO 5: Informar sucesso e atualizar relatório
            tela.exibirMensagemSucesso(
                "Carga cadastrada com sucesso!\n"
                + novaCarga.toString()
            );

            tela.atualizarRelatorio(gerarTextoRelatorio());

        } catch (NumberFormatException e) {

            // Erro quando o usuário digita valores numéricos inválidos
            tela.exibirMensagemErro(
                "Erro de formato numérico: "
                + "Digite apenas números válidos para Peso e Valor!"
            );

        } catch (IllegalArgumentException e) {

            // Erro quando alguma regra de negócio é violada
            tela.exibirMensagemErro(
                "Regra de Negócio Violada: " + e.getMessage()
            );
        }
    }

    /**
     * Gera o relatório de todas as cargas cadastradas.
     */
    public String gerarTextoRelatorio() {

        if (repositorio.isEmpty()) {
            return "Nenhuma carga registrada na esteira.";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("--- RELATÓRIO OFICIAL DE DESPACHO ---\n");

        double totalSeguro = 0.0;
        double pesoTotal = 0.0;

        for (int i = 0; i < repositorio.size(); i++) {

            Carga c = repositorio.get(i);

            sb.append(
                String.format("%02d. %s\n", (i + 1), c.toString())
            );

            totalSeguro += c.calcularSeguro();
            pesoTotal += c.getPeso();
        }

        sb.append("-------------------------------------\n");

        sb.append(
            String.format(
                "Total de Cargas: %d | Peso Total: %.2f kg | Seguro Acumulado: R$ %.2f\n",
                repositorio.size(),
                pesoTotal,
                totalSeguro
            )
        );

        return sb.toString();
    }

    /**
     * Retorna uma versão não modificável do repositório.
     */
    public List<Carga> getRepositorio() {
        return Collections.unmodifiableList(repositorio);
    }
}
