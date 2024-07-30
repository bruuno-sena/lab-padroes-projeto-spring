package one.digitalinnovation.gof.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Breed {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("hypoallergenic")
    private boolean hypoallergenic;

    // Getters e Setters...

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHypoallergenic() {
        return hypoallergenic;
    }

    public void setHypoallergenic(boolean hypoallergenic) {
        this.hypoallergenic = hypoallergenic;
    }
}
