package com.company;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

    public static void main(String[] args) throws Exception  {
        Socket soc = new Socket("localhost",8080);// этой строкой мы запрашиваем
        //  у сервера доступ на соединение
        Scanner sc = new Scanner(System.in);
        BufferedReader nis = new BufferedReader(  // поток чтения из сокета
                new InputStreamReader(
                        soc.getInputStream()
                )
        );
        PrintWriter nos = new PrintWriter( // поток записи в сокет
                new BufferedWriter(
                        new OutputStreamWriter(
                                soc.getOutputStream()
                        )
                ),true
        );
        Font font = new Font("", Font.BOLD, 18); //Шрифт
        JFrame frame = new JFrame("Chat");
        String uname = new String();
        uname = JOptionPane.showInputDialog(frame, "Никнейм:");
        if (uname == null) {
            uname = "Mikhail";
        }
        JFrame f1 = new JFrame(uname);
        f1.setBackground(Color.PINK);
        JButton b1 = new JButton("Отправить");
        b1.setBackground(Color.WHITE);
        JTextArea ta = new JTextArea();
        ta.setBackground(Color.WHITE);
        ta.setFont(font);
        ta.setEditable(false);//Метод для фиксации
        JTextField tf = new JTextField(20);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.PINK);
        p1.add(tf);
        p1.add(b1);
        f1.add(ta);
        f1.add(BorderLayout.SOUTH,p1);
        ChatListener l1 = new ChatListener(tf,nos,uname);
        b1.addActionListener(l1);
        tf.addActionListener(l1);
        f1.setSize(500,500);
        f1.setVisible(true);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String str = nis.readLine();
        while(!str.equals("End")){ // ожидание клиента
            ta.append(str + "\n" );
            str = nis.readLine(); // отправляем сообщение на сервер
        }
        ta.append("Отключился");
        Thread.sleep(1000);
        System.exit(0);
    }
}
class ChatListener implements ActionListener{
    JTextField tf ;
    PrintWriter pw;
    String uname;

    public ChatListener(JTextField tf,PrintWriter nos,String uname){
        this.tf = tf;
        this.pw = nos;
        this.uname = uname;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String str  = tf.getText();
        pw.println(uname+" : "+str);
        tf.setText("");
    }

}