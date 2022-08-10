package io.github.andreycostaalves.mscartoes.infra.repository;

import io.github.andreycostaalves.mscartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {

    List<ClienteCartao> findBycpf(String cpf);

}
