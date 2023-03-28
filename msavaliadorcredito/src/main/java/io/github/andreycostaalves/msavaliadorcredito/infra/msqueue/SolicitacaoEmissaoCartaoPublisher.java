package io.github.andreycostaalves.msavaliadorcredito.infra.msqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.andreycostaalves.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartoes;

    public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        var json = converteIntoJson(dados);
        rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);

    }

    /** CONVERTENDO STRING PARA JSON.... */
    private String converteIntoJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(dados);
        return json;
    }


}
