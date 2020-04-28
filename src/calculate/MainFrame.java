package calculate;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        new MainFrame();
    }
    private JTextField txtF;
    private int width=400;
    private int height=600;
    private double fNum;
    private double sNum;
    private String operation;
    private double result;
    private JLabel lblTxt;
    private JPanel btnPanel;
    private boolean[] isWriteNum=new boolean[3];                  //число1, число2, точка

    public boolean[] getIsWriteNum() {
        return isWriteNum;
    }

    public JPanel getBtnPanel() {
        return btnPanel;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public JLabel getLblTxt() {
        return lblTxt;
    }

    public JTextField getTxtF() {
        return txtF;
    }

    public double getfNum() {
        return fNum;
    }

    public void setfNum(double fNum) {
        this.fNum = fNum;
    }

    public double getsNum() {
        return sNum;
    }

    public void setsNum(double sNum) {
        this.sNum = sNum;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }


    public MainFrame()
    {
        setSize(width,height);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(0,51,51));
        setJMenuBar(new Menu(this));
        createTXTF();
        createNumP(this,1);
        fNum=0;
        sNum=0;
        operation="";
        lblTxt =new JLabel("Результат");
        lblTxt.setFont(new Font(null,1,20));
        JPanel p=new JPanel(null);
        p.setBounds(0,height-(height/11)-50,this.getWidth(),(height-50)/11);
        lblTxt.setBounds(20,10,300,25);
        p.setBorder(BorderFactory.createEtchedBorder());
        p.add(lblTxt);
        add(p);
        setVisible(true);
    }


    void createTXTF()
    {
        var pnl=new JPanel(null);
        pnl.setBounds(0,28,this.getWidth(),25+(height-50)/11);
        pnl.setBorder(BorderFactory.createEtchedBorder());
        JLabel lbl=new JLabel("Ввод");
        lbl.setFont(new Font(null,Font.BOLD,15));
        lbl.setBounds(10,12,50,50);
        pnl.add(lbl);
        txtF=new JTextField(40);
        txtF.setBounds(70,12,300,50);
        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new DocumentFilter()
        {
            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)throws BadLocationException
            {
                fb.insertString(off, str.replaceAll("[^0-9.]", ""), attr);  //включать только цифры и точки
            }
            @Override
            public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)throws BadLocationException
            {
                fb.replace(off, len, str.replaceAll("[^0-9.]", ""), attr);  //включать только цифры и точки
            }
        });
        txtF.setDocument(doc);
        txtF.setFont(new Font("Serif", Font.PLAIN,35));
        txtF.setText("");
        pnl.add(txtF);
        add(pnl);
    }


    void createNumP(MainFrame mf,int size)
    {
        btnPanel=new JPanel(null);
        btnPanel.setBorder( BorderFactory.createEtchedBorder());
        btnPanel.setBounds(0,100,400,400);
        btnPanel.add(new Numb(mf,size));
        add(btnPanel);
    }
}


class Numb extends JPanel
{
        private char [] array={'1','2','+','-','3','4','5','*','6','7','8','/','9','0','С','='};
        private char [] array2={'1','2','+','-','3','4','5','*','6','7','8','/','9','0','С','=','.',' ',' ',' ',};
    MainFrame mf;
    ActionListener act;

    public Numb(MainFrame mf,int size)
    {
        this.mf=mf;
        setBounds(10,10,370,380);
        createAct();
        createPanel(size);
        setFocusable(true);
    }


    void createPanel(int size)                           //добавление кнопок на панель
    {
        int v;
        char[] arr;
        int sGrid;
        int font;
        int form;
        if (size==2){v=20; arr=array2;sGrid=5;font =25; form=2;}
        else{v=16; arr=array;sGrid=4;font =35;form=1;}
        setLayout(new GridLayout(sGrid,4,5,5));
        for (int i = 0; i < v; i++)
        {
            var bt= new Butn(String.valueOf(arr[i]),act,form);
            bt.setFont(new Font(null, Font.PLAIN,font));
            add(bt);
        }
    }


    void createAct()
    {
        act= e ->
        {                                  //действия для кнопок
            String event=e.getActionCommand();
            switch (event)
            {
                case "+" :  writeOper(event);  break;
                case "-" :  writeOper(event);  break;
                case "*" :  writeOper(event);  break;
                case "/" :  writeOper(event);  break;
                case "С" :  delete();          break;
                case "=" :  calculateRes();    break;
                case "0" :  writetoTF(event);  break;
                case "1" :  writetoTF(event);  break;
                case "2" :  writetoTF(event);  break;
                case "3" :  writetoTF(event);  break;
                case "4" :  writetoTF(event);  break;
                case "5" :  writetoTF(event);  break;
                case "6" :  writetoTF(event);  break;
                case "7" :  writetoTF(event);  break;
                case "8" :  writetoTF(event);  break;
                case "9" :  writetoTF(event);  break;
                case "." :  writePoint(event);  break;
            }
        };
    }


    void writePoint(String str)
    {                    //дробная точка
        if (mf.getTxtF().getText().equals("")&!mf.getIsWriteNum()[2])
        {
            mf.getTxtF().setText("0.");
            mf.getIsWriteNum()[2]=true;                                    //точка поставлена
        }
        else if (!mf.getTxtF().getText().equals("")&!mf.getIsWriteNum()[2])
            {
                mf.getTxtF().setText(mf.getTxtF().getText()+str);
                mf.getIsWriteNum()[2]=true;                                    //точка поставлена
            }

    }

    void writetoTF(String str)
    {
         mf.getTxtF().setText(mf.getTxtF().getText()+str);
    }


    void writeOper(String str)
    {
        String numBoard=mf.getTxtF().getText();
        boolean num1=mf.getIsWriteNum()[0];
        boolean num2=mf.getIsWriteNum()[1];


         if (!numBoard.equals("")&!num1&!num2)           //операция нажата, нет обоих чисел, число введено
        {
                mf.setfNum(Double.parseDouble(mf.getTxtF().getText()));        //записываю первое число
                mf.setOperation(str);                                          //записываю операцию
                mf.getLblTxt().setText(mf.getTxtF().getText()+" "+str);        //запись операции и числа в лейбл
                mf.getTxtF().setText("");                                      //стираю число с циферблата
                mf.getIsWriteNum()[0]=true;                                    //помечаю первое число как записанное
                mf.getIsWriteNum()[2]=false;                                    //стирание точки, если была
        }


        else if (numBoard.equals("")&num1&!num2)                  //операция нажата,есть первое число, второе не введено
        {
            mf.getLblTxt().setText(mf.getfNum()+" "+str);        //запись операции и числа в лейбл
            mf.setOperation(str);                                      //записываю операцию
        }


        else if (!numBoard.equals("")&num1&!num2&mf.getOperation().equals(""))     //операция нажата,есть первое число, второе введено, операция не записана
        {
            mf.setsNum(Double.parseDouble(mf.getTxtF().getText()));       //записываю второе число
            mf.getIsWriteNum()[2]=false;                                  //стирание точки, если была
            mf.setOperation(str);
            mf.getIsWriteNum()[1]=true;                                   //помечаю второе число как записанное
            calculateRes();
        }


        else if (!numBoard.equals("")&num1&!num2&!mf.getOperation().equals(""))                  //операция нажата,есть первое число, второе введено, операция записана
        {
            mf.setsNum(Double.parseDouble(mf.getTxtF().getText()));       //записываю второе число
            mf.getIsWriteNum()[2]=false;                                    //стирание точки, если была
            mf.getIsWriteNum()[1]=true;                                   //помечаю второе число как записанное
            calculateRes();
            mf.setOperation(str);
            mf.getLblTxt().setText(mf.getLblTxt().getText()+" "+str);                        //запись результата в лейбл
        }


        else if (numBoard.equals("")&num1&num2)                   //операция нажата, есть оба числа
        {
            calculateRes();
        }

    }


    void calculateRes()
    {
        if (!mf.getOperation().equals("")&mf.getIsWriteNum()[0]&!mf.getTxtF().getText().equals(""))                //операция записана,первое число есть, второе введено
        {
            createSum(mf.getfNum(),Double.parseDouble(mf.getTxtF().getText()));         //подсчитываю результат
            mf.getLblTxt().setText(String.valueOf(mf.getResult()));                        //запись результата в лейбл
            mf.setfNum(mf.getResult());                                                    //записываю первое число
            mf.setsNum(0);                                                                 //стираю второе число
            mf.setOperation("");                                                           //стираю операцию
            mf.getTxtF().setText("");                                                      //стираю число с циферблата
            mf.getIsWriteNum()[1]=false;                                                   //помечаю второе число как не записанное
            mf.getIsWriteNum()[2]=false;                                                   //стирание точки, если была
        }

        if (!mf.getOperation().equals("")&mf.getIsWriteNum()[0]&mf.getIsWriteNum()[1])                                   //операция записана, есть оба числа
        {
            createSum(mf.getfNum(),mf.getsNum());                     //подсчитываю результат
            mf.getLblTxt().setText(String.valueOf(mf.getResult()));        //запись результата в лейбл
            mf.setfNum(mf.getResult());                                    //записываю первое число
            mf.setsNum(0);                                                 //стираю второе число
            mf.setOperation("");                                           //стираю операцию
            mf.getTxtF().setText("");                                      //стираю число с циферблата
            mf.getIsWriteNum()[1]=false;                                   //помечаю второе число как не записанное
            mf.getIsWriteNum()[2]=false;                                    //стирание точки, если была
        }
    }


     void createSum(double f,double s)
     {
        switch (mf.getOperation())
        {
            case "+" : mf.setResult(f+s);  break;
            case "-" : mf.setResult(f-s);  break;
            case "*" : mf.setResult(f*s);  break;
            case "/" : mf.setResult(f/s);  break;
        }
    }

    void delete()
    {
        mf.getLblTxt().setText("");                                    //стираю лейбл
        mf.setfNum(0);                                                 //стираю первое число
        mf.setsNum(0);                                                 //стираю второе число
        mf.getIsWriteNum()[0]=false;                                   //помечаю первое число как не записанное
        mf.getIsWriteNum()[1]=false;                                   //помечаю второе число как не записанное
        mf.getIsWriteNum()[2]=false;                                   //стирание точки, если была
        mf.setOperation("");                                           //стираю операцию
        mf.getTxtF().setText("");                                      //стираю число с циферблата
    }

}





class Butn extends JButton                 //создание кнопки
{
    int form;
    public Butn(String label,ActionListener act,int form)
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


    Shape shape;
    public boolean contains(int x, int y)
    {
        if (shape == null ||
                !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0,
                    getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }
}



class Menu extends JMenuBar                          //меню
{
    MainFrame mf;
    public Menu(MainFrame mf)
    {
        this.mf=mf;
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
        menu2.add(item3);
        add(menu);
        add(menu2);
        mit.addActionListener(e -> System.exit(0));
        item.addItemListener(e ->
        {
            if (gb.getSelection().getActionCommand().equals("Обычный"))
            {
                mf.remove(mf.getBtnPanel());
                mf.createNumP(mf,1);
                mf.revalidate(); mf.repaint();
            }
        });


        item3.addItemListener(e ->
        {
            if (gb.getSelection().getActionCommand().equals("Расширенный"))
            {
                mf.remove(mf.getBtnPanel());
                mf.createNumP(mf, 2);
                mf.revalidate(); mf.repaint();
            }
        });
    }

}


