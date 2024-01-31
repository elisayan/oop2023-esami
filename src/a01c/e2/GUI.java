package a01c.e2;

import javax.swing.*;

import a01c.e2.Logics.NumberClick;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private Logics logics;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);
        this.logics = new LogicsImpl(size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            var position = cells.get(jb);

            switch (logics.hit(position.getX(), position.getY())) {
                case FIRST:
                    jb.setText("1");
                    break;
                case SECOND:
                    jb.setText("2");

                default:
                    for (var entry : cells.entrySet()) {
                        if (logics.isSquare(entry.getValue().getX(), entry.getValue().getY())) {
                            entry.getKey().setText("0");
                        }
                    }
                    break;
            }

            if (logics.isOver()) {
                System.exit(0);
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton();
                this.cells.put(jb, new Pair<Integer, Integer>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

}
