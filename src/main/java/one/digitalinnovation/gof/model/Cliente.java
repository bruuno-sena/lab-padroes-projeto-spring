package one.digitalinnovation.gof.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	@ManyToOne
	private Endereco endereco;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
	private List<Pet> pets;

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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
}
