package client.src.main.java;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.*;



public class Entra_Frame extends JFrame{
    private String name;



    public Entra_Frame() {

        JFrame frame = new JFrame("Welcome");
        frame.setLocation(100, 100);
        frame.setSize(500,500);

        JPanel panel = new JPanel();

        JTextField text_field = new JTextField("Escreve o seu nome"); //addicionar verification
        panel.add(text_field);


        JButton btn = new JButton("Send");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = text_field.getText();
                System.out.println(name);
                frame.dispose();
                frame.setVisible(false);
            }
        });
        panel.add(btn);


        frame.add(panel);
        frame.setVisible(true);

    }



    public String getName() {
        return name;
    }
}
