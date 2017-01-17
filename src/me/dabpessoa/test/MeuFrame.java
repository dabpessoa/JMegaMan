package me.dabpessoa.test;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dabpessoa on 14/01/2017.
 */
public class MeuFrame extends JFrame {

    public static void main(String[] args) {

        JFrame canvas = createCanvas();

        while(true) {

            Graphics g = canvas.getBufferStrategy().getDrawGraphics();

            g.setColor( Color.BLACK );
            g.fillRect( 0, 0, 50, 50 );

            g.setColor( Color.GRAY );
            g.fillRect( 0, 50, 50, 50 );

            g.setColor( Color.GREEN );
            g.fillRect( 80, 50, 50, 50 );

            g.setColor( Color.BLUE );
            g.fillRect( 130, 50, 50, 50 );

//            g.setColor( Color.YELLOW );
//            g.fillRect( 30, 50, 50, 50 );

//            g.setColor(Color.ORANGE);
//            g.fillRect(35,45,75,95);

            g.dispose();

            if (!canvas.getBufferStrategy().contentsLost())
                canvas.getBufferStrategy().show();

        }

    }

    private static JFrame createCanvas() {
        JFrame canvas = new JFrame();
        canvas.setSize(500, 500);
        canvas.setIgnoreRepaint(true);
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setLocationRelativeTo(null);
        canvas.setBackground(Color.WHITE);


        canvas.setVisible(true);

        //Criamos a estrat�gia de double buffering
        canvas.createBufferStrategy(2);

        return canvas;
    }

}
