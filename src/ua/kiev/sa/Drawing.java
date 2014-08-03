package ua.kiev.sa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Shape;

import java.awt.geom.Arc2D;

import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Spring;
import javax.swing.SpringLayout;

public class Drawing extends JPanel {
    private int i1;
    private int i2;
    private int i3;
    private String cat1;
    private String cat2;
    private float sumCat1;
    private float sumCat2;

    private void calculationOfGraphic() {
        XMLAnalize xmla = new XMLAnalize();
        // определяем количество категорий
        int catCount = xmla.getTm().size() - 1;
        //выбираем случайную категорию
        int cr1 = (int)(Math.random() * catCount);
        int cr2 = (int)(Math.random() * catCount);

        Object[] cats = xmla.getTm().keySet().toArray();
        String catString1 = (String)cats[cr1];
        String catString2 = (String)cats[cr2];
        cat1 = catString1;
        cat2 = catString2;
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
        sumCat1 = catCost1 / (catCost1 + catCost2);
        sumCat2 = catCost2 / (catCost1 + catCost2);
        System.out.println(sumCat1 + "////////////////////////");
        System.out.println(sumCat2 + "////////////////////////");
    }


    public Drawing() {
        Dimension d = new Dimension(400, 400);
        setPreferredSize(d);
        repaint();
    }

    /**
     * @param g
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        calculationOfGraphic();
        Arc2D s = new Arc2D.Float();
        s.setArc(0, 0, 200, 200, 0, (int)(360f * sumCat1), Arc2D.PIE);
        Rectangle2D b = s.getBounds2D();
        Color col1 = colorMade();
        g2.setColor(col1);
        g2.fill(s);
        double w = s.getCenterX();
        double h = s.getCenterY();
        Color col2 = colorMade();
        Arc2D s1 = new Arc2D.Float();
        s1.setArc(0, 0, 200, 200, (int)(360f * sumCat1),
                (int)(360 * sumCat2) + 1, Arc2D.PIE);
        Rectangle2D b1 = s1.getBounds2D();
        g2.setColor(col2);
        g2.fill(s1);
        double w1 = s1.getCenterX();
        double h1 = s1.getCenterY();
        drawLegend(g2, col1, col2);
    }

    private Color colorMade() {
        i1 = (int)(Math.random() * 255);
        i2 = (int)(Math.random() * 255);
        i3 = (int)(Math.random() * 255);
        return new Color(i1, i2, i3);
    }

    private void drawLegend(Graphics2D g2, Color c1, Color c2) {
        Font f = new Font("SansSerif", Font.BOLD, 12);
        g2.setFont(f);
        FontMetrics metrics = this.getFontMetrics(f);
        int acsent = metrics.getMaxAscent();
        int offsetY = acsent + 2;
        g2.setColor(c1);
        g2.fillRect(210, offsetY, acsent, acsent);
        g2.setColor(c2);
        g2.fillRect(210, offsetY + acsent + 2, acsent, acsent);
        g2.setColor(Color.BLACK);
        g2.drawString(cat1, 225, offsetY + acsent - 2);
        g2.setColor(Color.BLACK);
        g2.drawString(cat2, 225, offsetY + acsent - 2 + acsent + 2);
    }
}
