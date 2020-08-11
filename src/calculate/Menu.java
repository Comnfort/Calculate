package calculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class Menu extends JMenuBar                          //меню
{
    Menu(MainFrame mf)
    {
        Font f=new Font(null, Font.BOLD,15);
        UIManager.put("Menu.font",f);
        UIManager.put("RadioButtonMenuItem.font",f);
        UIManager.put("MenuItem.font",f);
        JMenu menu = new JMenu("Menu");
        JMenuItem mit=new JMenuItem("Exit");

        JMenu menuF = new JMenu("Func");
        JMenuItem it1=new JMenuItem("addition");
        it1.setAccelerator(KeyStroke .getKeyStroke ( "typed +"));
        JMenuItem it2=new JMenuItem("subtraction");
        it2.setAccelerator(KeyStroke .getKeyStroke ( "typed -"));
        JMenuItem it3=new JMenuItem("multiplication    ");
        it3.setAccelerator(KeyStroke .getKeyStroke ( "typed *"));
        JMenuItem it4=new JMenuItem("division");
        it4.setAccelerator(KeyStroke .getKeyStroke ( "typed /"));
        JMenuItem it5=new JMenuItem("result");
        it5.setAccelerator(KeyStroke .getKeyStroke ( "typed ="));
        JMenuItem it6=new JMenuItem("point");
        it6.setAccelerator(KeyStroke .getKeyStroke ( "typed ."));
        JMenuItem it7=new JMenuItem("delete");
        it7.setAccelerator(KeyStroke.getKeyStroke ((char) KeyEvent.VK_BACK_SPACE));
        menuF.add(it1);
        menuF.addSeparator();
        menuF.add(it2);
        menuF.addSeparator();
        menuF.add(it3);
        menuF.addSeparator();
        menuF.add(it4);
        menuF.addSeparator();
        menuF.add(it5);
        menuF.addSeparator();
        menuF.add(it6);
        menuF.addSeparator();
        menuF.add(it7);

        JMenu menu2 = new JMenu("Options  ");
        menu2.setMnemonic('O');
        mit.setAccelerator(KeyStroke .getKeyStroke ( "ctrl E"));
        ButtonGroup gb=new ButtonGroup();
        JRadioButtonMenuItem item=new JRadioButtonMenuItem("Ordinary",true);
        item.setAccelerator(KeyStroke .getKeyStroke ("O"));
        JRadioButtonMenuItem item2=new JRadioButtonMenuItem ("Complex    ",false);
        item2.setAccelerator(KeyStroke .getKeyStroke ( "P"));
        item2.setActionCommand("Complex");
        item.setActionCommand("Ordinary");
        gb.add(item2);
        gb.add(item);
        menu2.add(item2);
        menu2.addSeparator();
        menu2.add(item);
        menu.add(menu2);
        menu.add(mit);
        add(menu);
        add(menuF);


        mit.addActionListener(e -> System.exit(0));
        item2.addItemListener(e ->
        {
            if (gb.getSelection().getActionCommand().equals("Ordinary"))
            {
                mf.remove(mf.getBtnPanel());
                mf.createNumPnl(mf,1);
                mf.revalidate(); mf.repaint();
            }
        });


        item.addItemListener(e ->
        {
            if (gb.getSelection().getActionCommand().equals("Complex"))
            {
                mf.remove(mf.getBtnPanel());
                mf.createNumPnl(mf, 2);
                mf.revalidate(); mf.repaint();
            }
        });

        it1.addActionListener(e -> mf.getNumP().writeOper("+"));
        it2.addActionListener(e -> mf.getNumP().writeOper("-"));
        it3.addActionListener(e -> mf.getNumP().writeOper("*"));
        it4.addActionListener(e -> mf.getNumP().writeOper("/"));
        it5.addActionListener(e -> mf.getNumP().calculate());
        it6.addActionListener(e -> mf.getNumP().writePoint("."));
        it7.addActionListener(e -> mf.getNumP().erasure());


    }
}
