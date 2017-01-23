package me.dabpessoa.test;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

     public MyFrame(){
          super("My Frame");

          //you can set the content pane of the frame 
          //to your custom class.

          setContentPane(new DrawPane());

          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          setSize(400, 400);

          setVisible(true);
     }



      //create a component that you can actually draw on.
      class DrawPane extends JPanel{
          @Override
          public boolean getIgnoreRepaint() {
              return true;
          }

          public void paintComponent(Graphics g){
            System.out.println("teste");
          //draw on g here e.g.
           g.fillRect(20, 20, 100, 200);
         }
     }

     public static void main(String args[]){
            new MyFrame();
     }

  }