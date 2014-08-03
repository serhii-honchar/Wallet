package ua.kiev.sa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.Image;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class AboutWindow extends JFrame {
    public AboutWindow() {

        setTitle("About MyWallet");
        setSize(250, 200);
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        setVisible(true);

        Toolkit tool=Toolkit.getDefaultToolkit();
        String img="e://Download//cop.jpg ";
        Image im=tool.getImage(img);
        this.setIconImage(im);

        String text="Спасибо за то что используете MyWallet! " +
                "\nЭто мое первое приложение на Java! " +
                "\nПрошу не судить строго!!";
        JTextArea tf=new JTextArea();
        tf.setFont(new Font("Times New Roman", Font.BOLD, 16 ));
        tf.setText(text);
        tf.setEditable(false);
        tf.setBackground(Color.BLUE);
        add(tf, BorderLayout.CENTER);
        String autor="Автор приложения Sergio Alfarero";
        JTextField autorTF=new JTextField();
        autorTF.setText(autor);
        add(autorTF, BorderLayout.SOUTH);
    }
}