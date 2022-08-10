package io.github.andreycostaalves.msavaliadorcredito.application.ex;

public class DadosClienteNotFoundException extends Exception{

    public DadosClienteNotFoundException() {
        super("Dados do cliente Não encontrados para o cpf Informado.");
    }
}
