package storeroom.api.model;

import java.util.UUID;

public class Department {
    public Department(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    private UUID id;
    public UUID getId() { return this.id; }
    public void setId(UUID id) { this.id = id; }

    private String name;
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
}
