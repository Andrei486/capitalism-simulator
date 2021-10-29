import javax.swing.*;

public class PropertySpacePanel{
    private Property property;
    private JButton infoButton;


    public void PropertySpacePanel(Property property){

        JFrame f = new JFrame("Property Info");
        infoButton = new JButton("Display Selected Property");
        infoButton.setBounds(50,100,95,30);
        f.add(infoButton);
        f.setSize(200, 400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
