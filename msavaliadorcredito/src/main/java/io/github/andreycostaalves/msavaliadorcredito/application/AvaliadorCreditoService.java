package io.github.andreycostaalves.msavaliadorcredito.application;

import feign.FeignException;
import feign.Response;
import io.github.andreycostaalves.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.andreycostaalves.msavaliadorcredito.application.ex.ErroComunicacaoMicrosservicesException;
import io.github.andreycostaalves.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import io.github.andreycostaalves.msavaliadorcredito.domain.model.*;
import io.github.andreycostaalves.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.andreycostaalves.msavaliadorcredito.infra.clients.ClienteResourceClient;
import io.github.andreycostaalves.msavaliadorcredito.infra.msqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obertSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicrosservicesException {
        //obter dados do msClientes -  Obter cartões do msCartões.
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCliente(cpf);


            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicrosservicesException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)throws DadosClienteNotFoundException, ErroComunicacaoMicrosservicesException{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity <List<Cartao>> cartaoResponse = cartoesResourceClient.getCartoesRendaAteh(renda);

             List<Cartao> cartoes = cartaoResponse.getBody();
             var listaCartoesAprovados = cartoes. stream().map(cartao -> {
                 DadosCliente dadosCliente = dadosClienteResponse.getBody();

                 BigDecimal limiteBasico = cartao.getLimiteBasico();
                 //BigDecimal rendaBD = BigDecimal.valueOf(renda);
                 BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                 var fator = idadeBD.divide(BigDecimal.valueOf(10));
                 BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                 CartaoAprovado aprovado = new CartaoAprovado();
                 aprovado.setCartao(cartao.getNome());
                 aprovado.setBandeira(cartao.getBandeira());
                 aprovado.setLimiteAprovado(limiteAprovado);

                 return aprovado;
             }).collect(Collectors.toList());

             return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicrosservicesException(e.getMessage(), status);
        }

    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try {
            emissaoCartaoPublisher.solicitarCartao(dados);
            /** Criando um protocolo aleatorio...*/
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        }catch (Exception e){
            throw new ErroSolicitacaoCartaoException(e.getMessage());

        }
    }
}
