package client.src.main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class Chat_Frame extends JFrame{

    private String client_name;  //nome de cliente

    private JButton btnSend;
    private JButton btnExit;
    private JTextArea area_messegers;

    private JPanel panel;
    private JTextField user_msg;

    private JLabel lbl_user_msg;
    private JLabel buf_msg;//historico de chat

    public Chat_Frame() {
        JFrame chat = new JFrame("chat");
        chat.setVisible(true);
        chat.setSize(500,500);
        JOptionPane.showMessageDialog(null,client_name); //talvez addicionar

        panel =  new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);

        buf_msg = new JLabel("Historico");

        btnExit = new JButton("Sair");
        btnExit.addActionListener((ActionListener) this);

        btnSend = new JButton("Enviar");
        btnSend.addActionListener((ActionListener) this);
        btnSend.addKeyListener((KeyListener) this);

        user_msg = new JTextField(500);
        user_msg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));

        lbl_user_msg=new JLabel("Sua Mensagem");


        area_messegers =new JTextArea(10,20);
        area_messegers.setEditable(false);
        JScrollPane scroll = new JScrollPane(area_messegers);
        area_messegers.setBackground(Color.GREEN);
        area_messegers.setLineWrap(true);
        area_messegers.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE));



        panel.add(buf_msg);
        panel.add(scroll);
        panel.add(lbl_user_msg);
        panel.add(user_msg);
        panel.add(btnSend);
        panel.add(btnExit);


        chat.setContentPane(panel);
        chat.setTitle(client_name);
        chat.setLocationRelativeTo(null);
        chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }


    public JTextArea get_area() {
        return area_messegers;
    }
    public JTextField getUser_msg() {
        return user_msg;
    }
    public String getClient_name() {
        return client_name;
    }

    public JButton getBtnSend() {
        return btnSend;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

    public void Sair(ClientThread client) {
        client.CloseThread();
    }
}
