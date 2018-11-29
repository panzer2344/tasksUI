package com.podval;

import javax.swing.*;
import java.awt.*;

public class NewRowFrame extends JFrame {

    private Dimension WINDOW_SIZE;
    private Dimension textFieldsSize;

    NewRowFrame(){

        WINDOW_SIZE = new Dimension(460, 370);
        //textFieldsSize = new Dimension((int)(0.8 * WINDOW_SIZE.width / Book.getFieldsNumber()), 10);

        setPreferredSize(WINDOW_SIZE);

        setLayout(new BorderLayout(20, 20));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(30, 30));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JScrollPane inputScrollPanel = new JScrollPane(inputPanel);

        JPanel eastPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel northPanel = new JPanel();

        JPanel btnsPanel = new JPanel();
        btnsPanel.setLayout(new FlowLayout());

        for(int i = 0; i < Book.getFieldsNumber(); i++){

            String curParamName = Book.getFieldById(i).getName();

            JLabel label = new JLabel(curParamName + ": ");

            JTextField textField = new JTextField();
            textField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            //textField.setPreferredSize(textFieldsSize);
            textField.setText(curParamName);

            inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            inputPanel.add(label);
            inputPanel.add(textField);
            inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        }

        JButton addBtn = new JButton("add");
        btnsPanel.add(addBtn);

        //contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(inputScrollPanel, BorderLayout.CENTER);
        contentPanel.add(btnsPanel, BorderLayout.SOUTH);

        contentPanel.add(eastPanel, BorderLayout.EAST);
        contentPanel.add(westPanel, BorderLayout.WEST);
        contentPanel.add(northPanel, BorderLayout.NORTH);

        getContentPane().add(contentPanel, BorderLayout.CENTER);


        setResizable(false);
        pack();
        setVisible(true);
    }
}
