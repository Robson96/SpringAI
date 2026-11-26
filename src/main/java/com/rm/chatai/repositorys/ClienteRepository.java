package com.rm.chatai.repositorys;

import com.rm.chatai.domains.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
