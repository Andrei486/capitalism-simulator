package main;

/**
 * Enum representing a color group of properties.
 */
public enum ColorGroup {

    BROWN("Brown"),
    LIGHT_BLUE("Light Blue"),
    PINK("Pink"),
    ORANGE("Orange"),
    RED("Red"),
    YELLOW("Yellow"),
    GREEN("Green"),
    BLUE("Blue");

    private final String name;

    /**
     * Creates a ColorGroup object with the given name.
     * @param name the string representation of this color
     */
    private ColorGroup(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
