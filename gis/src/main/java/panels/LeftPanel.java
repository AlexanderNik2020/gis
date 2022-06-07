package panels;

import util.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeftPanel extends JPanel {

    JLabel headerLabel = new JLabel("Выбор параметров отображения");
    JLabel chooseElementLabel = new JLabel("Выберите элемент");
    JLabel moreThanLabel = new JLabel("Содержание больше чем");
    String[] elements = Controller.getInstance().getElements().toArray(new String[0]);
    String[] moreThanValues = new String[]{"Неважно","0.5", "1", "2", "5"};
    JComboBox elementsBox = new JComboBox(elements);
    JComboBox moreThanBox = new JComboBox(moreThanValues);
    JButton activateButton = new JButton("Искать");


    public LeftPanel(){
        this.setLayout(new GridLayout(6,1));
        this.add(headerLabel);
        this.add(chooseElementLabel);
        //this.add(elementsBox);
        this.add(moreThanLabel);
        this.add(moreThanBox);
        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().getMainWindow().rightPanel.infoTextArea.setText(Controller.getInstance().getCurrentSample().toString());
                //controller.updateSamples();
            }
        });
        this.add(activateButton);
    }

    public JComboBox getElementsBox() {
        return elementsBox;
    }

    public void setElementsBox(JComboBox elementsBox) {
        this.elementsBox = elementsBox;
    }

    public JComboBox getMoreThanBox() {
        return moreThanBox;
    }

    public void setMoreThanBox(JComboBox moreThanBox) {
        this.moreThanBox = moreThanBox;
    }
}
