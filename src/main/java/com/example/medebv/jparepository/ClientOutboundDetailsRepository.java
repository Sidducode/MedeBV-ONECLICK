package com.example.medebv.jparepository;
import com.example.medebv.entity.ClientBoundDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ClientOutboundDetailsRepository extends JpaRepository<ClientBoundDetails, String> {

    List<ClientBoundDetails> findBynClientId(String nClientId);
}
