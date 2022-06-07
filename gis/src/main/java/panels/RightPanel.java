package panels;

import util.Controller;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {

    JLabel headerLabel = new JLabel("Информационная панель");
    JTextArea infoTextArea = new JTextArea("Hello");

    public RightPanel(){
        this.setLayout(new GridLayout(2,1));
        this.add(headerLabel);
        this.add(infoTextArea);
    }

}
