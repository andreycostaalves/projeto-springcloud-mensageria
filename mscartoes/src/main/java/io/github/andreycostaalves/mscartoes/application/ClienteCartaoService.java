package io.github.andreycostaalves.mscartoes.application;

import io.github.andreycostaalves.mscartoes.domain.ClienteCartao;
import io.github.andreycostaalves.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartoesByCpf(String cpf){
        return repository.findBycpf(cpf);
    }
}
