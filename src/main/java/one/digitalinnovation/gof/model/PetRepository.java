package one.digitalinnovation.gof.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    // Métodos personalizados, se necessário
}
