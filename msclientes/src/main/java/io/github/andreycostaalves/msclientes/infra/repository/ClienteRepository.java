package io.github.andreycostaalves.msclientes.infra.repository;

import io.github.andreycostaalves.msclientes.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    Optional<Cliente> findByCpf(String cpf);
}
