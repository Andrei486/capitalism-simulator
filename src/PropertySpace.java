/**This class implements the property space class, which extends the space class
 * Milestone 1
 * @author Mohammad Alkhaledi
 * */
public class PropertySpace extends Space {
    private Property property;
    /**Default constructor for propertySpace
     * @param property property for the space
     * */
    public PropertySpace(Property property){
        super(property.getName());
        this.property = property;
    }
    /**Returns property of the property space
     * @return property
     * */
    public Property getProperty(){
        return property;
    }

    /**
     * Resolves forced effects resulting from player landing on the space
     * such as having to pay rent, or landing on the "go to jail" space etc.
     * @param p the Player landing on this space
     */
    public void onEndTurn(Player p){
        // if player is not the owner of property and property has an owner
        if(!(p.equals(property.getOwner())) && (property.getOwner() != null)) {
            p.payRent(property);
        }
    }
    /**Returns description of property space
     * @return property.toString()
     * */
    public String getDescription(){
        return property.toString();
    }

}
