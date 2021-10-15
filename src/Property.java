public class Property {

    private float cost;
    private ColorGroup colorGroup;
    private Player owner;
    private static float RENT_MULTIPLIER = 0.5f;

    public Property(float cost, ColorGroup colorGroup) {

    }

    public float getCost() {
        return 0;
    }

    public float getRent() {
        return 0;
    }

    public ColorGroup getColorGroup() {
        return null;
    }

    public Player getOwner() {
        return null;
    }

    public void setOwner(Player p) {

    }

    @Override
    public String toString() {
        return "Property{" +
                "cost=" + cost +
                ", colorGroup=" + colorGroup +
                ", owner=" + owner +
                '}';
    }
}
