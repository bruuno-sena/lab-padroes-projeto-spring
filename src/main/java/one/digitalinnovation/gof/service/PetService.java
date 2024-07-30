package one.digitalinnovation.gof.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import one.digitalinnovation.gof.model.Breed;

@FeignClient(name = "dogapi", url = "https://api.thedogapi.com/v2")
public interface PetService {

    @GetMapping("/breeds/{breed_id}")
    Breed getBreedById(@PathVariable("breed_id") String breedId);
}
