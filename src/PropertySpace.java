public class PropertySpace extends Space {

    /**
     * Constructor for an abstract Space with only a name.
     *
     * @param name the name that should be displayed on this space
     */
    public PropertySpace(String name) {
        super(name);
    }

    public Property getProperty(){
        return null;
    }
    public void onEndTurn(Player p){

    }
    public String getDescription(){
        return "";
    }

}
