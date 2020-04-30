package calculate;

import javax.swing.*;
import java.awt.*;

class Menu extends JMenuBar                          //меню
{
    Menu(MainFrame mf)
    {
        setFont(new Font(null, Font.BOLD,35));
        var menu = new JMenu("File");
        var menu2 = new JMenu("Edit");
        JMenuItem mit=new JMenuItem("Close");
        menu.add(mit);
        var gb=new ButtonGroup();
        var item3=new JRadioButtonMenuItem("Обычный",true);
        var item=new JRadioButtonMenuItem ("Расширенный",false);
        item.setActionCommand("Расширенный");
        item3.setActionCommand("Обычный");
        gb.add(item);
        gb.add(item3);
        menu2.add(item);
        menu2.addSeparator();
        menu2.add(item3);
        add(menu);
        add(menu2);
        mit.addActionListener(e -> System.exit(0));
        item.addItemListener(e ->
        {
            if (gb.getSelection().getActionCommand().equals("Обычный"))
            {
                mf.remove(mf.getBtnPanel());
                mf.createNumPnl(mf,1);
                mf.revalidate(); mf.repaint();
            }
        });


        item3.addItemListener(e ->
        {
            if (gb.getSelection().getActionCommand().equals("Расширенный"))
            {
                mf.remove(mf.getBtnPanel());
                mf.createNumPnl(mf, 2);
                mf.revalidate(); mf.repaint();
            }
        });
    }

}
