/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displaysort;


import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 *
 * @author stimpy
 */
public class DisplayPanel extends JPanel {
    private int[] data;
    private int dataSize;
    
    private int width, height;
    
    public DisplayPanel() {
        width = 1500;
        height = 500;
        
        addMouseListener(new ClickListener());
        
        add(new JLabel("hello"));
        initializeData();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.white);
    }
    
    
    private void initializeData() {
        Random rand = new Random();
        dataSize = 230; // rand.nextInt(20) + 200;
        data = new int[dataSize];
        for (int i = 0; i < dataSize; ++i)
            data[i] = rand.nextInt(100) + 1;
    }
    
    
    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        
        int hMargin = 60; // width / 10;
        int vMargin = 20; // height / 9;
        
        int barMax = 0;
        for (int value: data) {
            barMax = (barMax < value) ? value : barMax;
        }
        
        int barWidth = (width - (2 * hMargin)) / dataSize;
        int barBottom = height - vMargin;
        int barUnit = (height - (vMargin * 2)) / barMax;
        
        int switchColor = 0;
        for (int index = 0; index < dataSize; ++index) {
            int x = (barWidth * index) + hMargin;
            int y = barUnit * data[index];
            
            if (switchColor == 0) {
                gr.setColor(Color.red);
                switchColor = 1;
            }
            else {
                gr.setColor(Color.magenta);
                switchColor = 0;
            }
            gr.fillRect(x, barBottom - y, barWidth - 5, y);
        }
        
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    private void selectionSort() {
        
        int min;
        int temp;
        for (int index = 0; index < dataSize - 1; ++index) {
            
            min = index;
            for (int scan = index + 1; scan < dataSize; ++scan) {
                if (data[scan] < data[min])
                    min = scan;
            }
            
            // swap and redraw
            temp = data[min];
            data[min] = data[index];
            data[index] = temp;
            
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            paintImmediately(0, 0, width, height);
        }
        
        repaint();
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    private class ClickListener extends MouseAdapter {
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1)
                selectionSort();
            else if (e.getButton() == MouseEvent.BUTTON3) {
                initializeData();
                paintImmediately(0, 0, width, height);
            }
            
        }
        
    }
    
}
