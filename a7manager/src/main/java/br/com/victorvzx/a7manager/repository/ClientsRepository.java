package br.com.victorvzx.a7manager.repository;

import br.com.victorvzx.a7manager.model.ClientsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsModel, Long> {
    Optional<ClientsModel> findByEmail(String email);
}
