package panels;

import util.Controller;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class RightPanel extends JPanel {

    JLabel headerLabel = new JLabel("Информационная панель");
    JTextArea infoTextArea = new JTextArea(20,50);

    public RightPanel(){

        JPanel forLabel = new JPanel(new FlowLayout());
        forLabel.add(headerLabel);

        this.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;

        this.add(forLabel,gridBagConstraints);
        JPanel scrollable = new JPanel();
        scrollable.setBorder(new TitledBorder(new EtchedBorder(), "Info"));
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollable.add(scrollPane);
        gridBagConstraints.gridx+=1;
        this.add(scrollable, gridBagConstraints);
    }

    public JLabel getHeaderLabel() {
        return headerLabel;
    }

    public void setHeaderLabel(JLabel headerLabel) {
        this.headerLabel = headerLabel;
    }

    public JTextArea getInfoTextArea() {
        return infoTextArea;
    }

    public void setInfoTextArea(JTextArea infoTextArea) {
        this.infoTextArea = infoTextArea;
    }
}
