package io.github.andreycostaalves.msavaliadorcredito.application;

import feign.FeignException;
import io.github.andreycostaalves.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.andreycostaalves.msavaliadorcredito.application.ex.ErroComunicacaoMicrosservicesException;
import io.github.andreycostaalves.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.andreycostaalves.msavaliadorcredito.domain.model.DadosCliente;
import io.github.andreycostaalves.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.andreycostaalves.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.andreycostaalves.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;

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
}
