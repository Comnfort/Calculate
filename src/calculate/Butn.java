package calculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class Butn extends JButton                 //создание кнопки
{
    private int form;               //выбор формы
    private Shape shape;            //фигура под форму
    Butn(String label, ActionListener act, int form)
    {
        super(label);
        setFocusPainted(false);
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width,size.height);
        setPreferredSize(size);
        setContentAreaFilled(false);              //убирает фон
        addActionListener(act);
        this.form=form;
        setBorder(BorderFactory.createRaisedSoftBevelBorder());
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setForeground(Color.ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setForeground(Color.BLACK);
            }
        });
    }


    protected void paintComponent(Graphics g)
    {
        if (getModel().isArmed()) {g.setColor(Color.lightGray);
        } else  { g.setColor(getBackground()); }

        if (form==1) {g.fillOval(0, 0, getSize().width-1,getSize().height-1);}
        else {g.fillRect(0, 0, getSize().width-1,  getSize().height-1);}

        super.paintComponent(g);
    }


    protected void paintBorder(Graphics g)
    {
        if (form==1)
        {
        g.setColor(Color.DARK_GRAY);
        g.drawOval(0, 0, getSize().width-1,getSize().height-1);
        }else {super.paintBorder(g);}
    }


    public boolean contains(int x, int y)
    {
        if (form==1)
        {
            if (shape == null || !shape.getBounds().equals(getBounds())){ shape = new Ellipse2D.Float(0, 0,getWidth(), getHeight());
        }
        }else {shape = new Rectangle2D.Float(0, 0,getWidth(), getHeight());}

        return shape.contains(x, y);
    }
}
