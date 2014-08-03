package ua.kiev.sa;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.text.MessageFormat;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class RunningString extends JPanel {
    private int x, y;
    private String runLab = "";
    JPanel RunStr;
    Graphics g;
    ArrayList<String> arrStr;
    // ;

    public RunningString() {
        runnn();
        updateUI();
        repaint();

    }


    /**
     * @param g
     */
    public void paint(Graphics gr) {
        this.g = gr;
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        x += 1;
        drawingString(g2);
        if (x ==
                (this.getWidth() + g2.getFontMetrics().getStringBounds(runLab, g2).getMaxX())) {
            x = 0;
        }
    }

    private String masOMenos(float fl1, float fl2) {
        String s = "";
        if (fl1 > fl2)
            s = " больше";
        if (fl1 < fl2)
            s = " меньше";
        if (fl1 == fl2)
            s = "столько же ";
        return s;
    }

    private void drawingString(Graphics2D g2) {
        if (this.getWidth() - x >
                (-g2.getFontMetrics().getStringBounds(runLab, g2).getMaxX())) {
            g2.drawString(runLab, this.getWidth() - x, this.getHeight());
        }
    }

    private ArrayList<String> runnn() {
        XMLAnalize xmla = new XMLAnalize();
        // определяем количество категорий
        int catCount = xmla.getTm().size() - 1;
        System.out.println(catCount);
        //выбираем случайную категорию
        int cr1 = (int)(Math.random() * catCount);
        int cr2 = (int)(Math.random() * catCount);

        Object[] cats = xmla.getTm().keySet().toArray();
        String catString1 = (String)cats[cr1];
        String catString2 = (String)cats[cr2];

        System.out.println(catString1);
        System.out.println(catString2);
        // выбираем подкатегорию
        int sr1 = (int)(Math.random() * (xmla.getTm().get(catString1).size()));
        int sr2 = (int)(Math.random() * (xmla.getTm().get(catString2).size()));

        String subCat1 = xmla.getTm().get(catString1).get(sr1);
        String subCat2 = xmla.getTm().get(catString2).get(sr2);

        System.out.println(subCat1);
        System.out.println(subCat2);
        float subcatCost1 = 0f;
        float subcatCost2 = 0f;
        float catCost1 = 0f;
        float catCost2 = 0f;

        for (int i = 0; i < Operation.operate.size(); i++) {
            if (Operation.operate.get(i).getCat().equals(catString1)) {
                catCost1 += Operation.operate.get(i).getF();
                if (Operation.operate.get(i).getSubcat().equals(subCat1)) {
                    subcatCost1 += Operation.operate.get(i).getF();
                }
            } else if (Operation.operate.get(i).getCat().equals(catString2)) {
                catCost2 += Operation.operate.get(i).getF();
                if (Operation.operate.get(i).getSubcat().equals(subCat2)) {
                    subcatCost2 += Operation.operate.get(i).getF();
                }
            }
        }
        float f = catCost1 / catCost2;
        runLab =
                "За все время на категорию " + catString1 + " Вы потратили " + catCost1 +
                        ", что в " + f + " раз " + masOMenos(catCost1, catCost2) +
                        ", чем на категорию " + catString2 + " (" + catCost2 + ")";
        return arrStr;
    }

}
