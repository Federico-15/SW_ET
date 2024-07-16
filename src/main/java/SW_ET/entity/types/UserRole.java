package SW_ET.entity.types;

public enum UserRole {
    ADMIN,
    USER; // Add as many roles as needed

    @Override
    public String toString() {
        return this.name();
    }
}