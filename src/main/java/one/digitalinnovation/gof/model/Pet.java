package one.digitalinnovation.gof.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String tipo; // Ex.: "Dog"
    private String breed; // Ex.: "labrador"

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    // Getters e Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
