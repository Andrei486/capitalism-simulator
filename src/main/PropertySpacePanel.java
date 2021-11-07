package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for displaying a property space on the GUI.
 * @author Waleed Majbour
 */
public class PropertySpacePanel extends SpacePanel{
    private JButton infoButton;


    /**
     * Constructs a panel for a given property.
     * @param property the property to make a GUI panel for
     */
    public PropertySpacePanel(Property property) {

        super();

        infoButton = new JButton("i");
        infoButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(infoButton, BorderLayout.EAST);

        bottomLabel.setText(String.format("%s", property.getName()));

        Color c;
        ColorGroup cg = property.getColorGroup();

        if(cg == ColorGroup.BROWN){
            c = new Color(139,69,19);
            topPanel.setBackground(c);
        }
        else if(cg == ColorGroup.LIGHT_BLUE){
            topPanel.setBackground(Color.CYAN);
        }
        else if(cg == ColorGroup.PINK) {
            topPanel.setBackground(Color.PINK);
        }
        else if(cg == ColorGroup.ORANGE){
            topPanel.setBackground(Color.ORANGE);
        }
        else if(cg == ColorGroup.RED){
            topPanel.setBackground(Color.RED);
        }
        else if(cg == ColorGroup.YELLOW){
            topPanel.setBackground(Color.YELLOW);
        }
        else if(cg == ColorGroup.GREEN){
            topPanel.setBackground(Color.green);
        }
        else if(cg == ColorGroup.BLUE){
            topPanel.setBackground(Color.BLUE);
        }

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, property.toString());
            }
        });
    }
}
