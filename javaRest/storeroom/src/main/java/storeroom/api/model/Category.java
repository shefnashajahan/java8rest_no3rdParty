package storeroom.api.model;

import java.util.UUID;

public class Category {
    public Category(UUID id, UUID departmentId, String name) {
        this.id = id;
        this.departmentId = departmentId;
        this.name = name;
    }

    private UUID id;
    public UUID getId() { return this.id; }
    public void setId(UUID id) { this.id = id; }

    private UUID departmentId;
    public UUID getDepartmentId() { return this.departmentId; }
    public void setDepartmentId(UUID departmentId) { this.departmentId = departmentId; }

    private String name;
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
}
