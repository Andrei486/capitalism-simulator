package main;

/**
 * This class implements the property space class, which extends the space class.
 * Represents a space with a property on it.
 * @author Mohammad Alkhaledi
 * */
public class PropertySpace extends Space {
    private Property property;
    /**
     * Default constructor for PropertySpace
     * @param property property for the space
     * */
    public PropertySpace(Property property){
        super(property.getName());
        this.property = property;
    }
    /**
     * Returns property of the property space.
     * @return property
     * */
    public Property getProperty(){
        return property;
    }

    /**
     * Resolves forced effects resulting from player landing on the space.
     * Players landing on a property space owned by another player must pay rent.
     * @param p the layer landing on this space
     */
    public void onEndTurn(Player p){
        // if player is not the owner of property and property has an owner
        if(!(p.equals(property.getOwner())) && (property.getOwner() != null)) {
            p.payRent(property);
        }
    }
    /**
     * Returns description of property space
     * @return String representation of the property on this space
     * */
    public String getDescription(){
        return property.toString();
    }

}
