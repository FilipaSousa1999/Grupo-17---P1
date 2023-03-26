package client.src.main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chat_Frame extends JFrame implements ActionListener {
    private String client_name;  //nome de cliente
    private JButton btnSend;
    private JButton btnExit;
    private JTextArea area_messegers;
    private JPanel panel;
    private JTextField user_msg;
    private JTextField name_input;
    private JLabel lbl_user_msg;
    private JLabel buf_msg;//historico de chat
    private boolean btnSend_isClicked;
    private boolean btnExit_isClicked;

    public Chat_Frame() {

        btnSend_isClicked = false;
        btnExit_isClicked = false;

        name_input = new JTextField("Escreve o nome");
        Object[] texts = {name_input};

        //JFrame chat = new JFrame("chat");

        JOptionPane.showMessageDialog(null,texts); //talvez addicionar

        panel =  new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);

        buf_msg = new JLabel("Historico");

        btnExit = new JButton("Sair");
        btnExit.addActionListener((ActionListener) this);

        btnSend = new JButton("Enviar");
        btnSend.addActionListener(this);

        user_msg = new JTextField(50);
        user_msg.setBorder(BorderFactory.createEtchedBorder(Color.ORANGE, Color.ORANGE));

        lbl_user_msg=new JLabel("Sua Mensagem");

        client_name=name_input.getName();
        area_messegers =new JTextArea(30,50);
        area_messegers.setEditable(false);
        JScrollPane scroll = new JScrollPane(area_messegers);
        area_messegers.setBackground(Color.WHITE);
        area_messegers.setLineWrap(true);
        area_messegers.setBorder(BorderFactory.createEtchedBorder(Color.GREEN,Color.GREEN));



        panel.add(buf_msg);
        panel.add(scroll);
        panel.add(lbl_user_msg);
        panel.add(user_msg);
        panel.add(btnSend);
        panel.add(btnExit);

        setSize(600,650);
        setContentPane(panel);
        setTitle(name_input.getText());
        setClient_name(name_input.getText());
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }


    public void set_area(String let) {
        this.area_messegers.append(let);
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

    public boolean isBtnSend_isClicked() {
        return btnSend_isClicked;
    }

    public void setBtnSend_isClicked(boolean btnSend_isClicked) {
        this.btnSend_isClicked = btnSend_isClicked;
    }

    public boolean isBtnExit_isClicked() {
        return btnExit_isClicked;
    }

    public void setBtnExit_isClicked(boolean btnExit_isClicked) {
        this.btnExit_isClicked = btnExit_isClicked;
    }


    /** Se nos temos evento, obtemos true
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Enviar")) {
            btnSend_isClicked = true;
        } else if (e.getActionCommand().equals("Sair"))
            btnExit_isClicked = true;
    }

    /*
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            btnSend_isClicked = true;
        }
    }
    */



}
