package it.unibo.dashboard;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Smart Waste Disposal System");
        frame.setSize(640, 480);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Dashboard"), BorderLayout.NORTH);


        panel.add(new JButton("Center"), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton proceedButton = new JButton("Proceed");
        JButton restoreButton = new JButton("Restore");
        buttonPanel.add(proceedButton);
        buttonPanel.add(restoreButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);
    }
}
