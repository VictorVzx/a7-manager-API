package br.com.victorvzx.a7manager.repository;

import br.com.victorvzx.a7manager.model.ClientesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutletRepository extends JpaRepository<ClientesModel, Long> {
}
