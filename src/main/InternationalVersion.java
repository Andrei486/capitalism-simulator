package main;

public enum InternationalVersion {
    NORTH_AMERICA("North America"),
    FRANCE("France"),
    UNITED_KINGDOM("United Kingdom");

    private final String name;

    /**
     * Creates a ColorGroup object with the given name.
     * @param name the string representation of this color
     */
    private InternationalVersion(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
