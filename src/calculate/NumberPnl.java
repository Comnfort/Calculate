package calculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

class NumberPnl extends JPanel
{
    private String st= "<HTML> U2B05";
    private char [] array={'1','2','+','-','3','4','5','*','6','7','8','/','9','0','С','='};
    private char [] array2={'С','\u00B1','%','+',
                            '1','2','3','-',
                            '4','5','6','*',
                            '7','8','9','/',
                            '0','.','\u2B05','=',};
    private MainFrame mf;
    private ActionListener act;
    private BigDecimal temp;
    private BigDecimal temp2;

    NumberPnl(MainFrame mf, int size)
    {
        this.mf=mf;
        setBounds(10,10,370,380);
        workBtn();
        createPanel(size);
    }


    private void createPanel(int size)                           //добавление кнопок на панель
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
            Butn bt= new Butn(String.valueOf(arr[i]),act,form);
            bt.setFont(new Font(null, Font.PLAIN,font));
            add(bt);
        }
    }


    private void workBtn()                     //действия для кнопок
    {
        act= e ->
        {
            String event=e.getActionCommand();
            switch (event)
            {
                case "+" :  writeOper(event);   break;
                case "-" :  writeOper(event);   break;
                case "*" :  writeOper(event);   break;
                case "/" :  writeOper(event);   break;
                case "С" :  delete();           break;
                case "=" :  calculate();        break;
                case "0" :  writeDigit(event);  break;
                case "1" :  writeDigit(event);  break;
                case "2" :  writeDigit(event);  break;
                case "3" :  writeDigit(event);  break;
                case "4" :  writeDigit(event);  break;
                case "5" :  writeDigit(event);  break;
                case "6" :  writeDigit(event);  break;
                case "7" :  writeDigit(event);  break;
                case "8" :  writeDigit(event);  break;
                case "9" :  writeDigit(event);  break;
                case "." :  writePoint(event);  break;
                case "\u2B05" :  erasure();     break;
                case "\u00B1" :  invert();      break;                     //смена знака
                case "%" :       percent();     break;                     //вычисление процента
            }
        };
    }


    public void percent()
    {
        if (!mf.getTxtF().getText().equals(""))
        {
            temp=new BigDecimal(mf.getTxtF().getText());
            String s=roundBD(temp.divide(new BigDecimal(100),4,RoundingMode.HALF_UP).toPlainString());
            mf.getTxtF().setText(s);
        }
    }


    public void invert()
    {
        if (!mf.getTxtF().getText().equals(""))
        {
            String s=mf.getTxtF().getText();
            if (s.charAt(0)!='-')
            {
                mf.getTxtF().setText("-"+s);
            }else {
                mf.getTxtF().setText(s.substring(1));
            }
        }
    }


    public void erasure()
    {
        if (!mf.getTxtF().getText().equals(""))
        {
            String s= mf.getTxtF().getText();
            mf.getTxtF().setText(s.substring(0,s.length()-1));

        if (!(s.substring(0,s.length()-1)).contains(".")){ mf.isWriteNum()[2]=false;}
        if (mf.getTxtF().getText().equals("-")){mf.getTxtF().setText("");}
        }
    }


    private void delete()
    {
        mf.getLblTxt().setText("");                                    //стираю лейбл
        mf.setfNum("");                                                //стираю первое число
        mf.setsNum("");                                                //стираю второе число
        mf.isWriteNum()[0]=false;                                      //помечаю первое число как не записанное
        mf.isWriteNum()[1]=false;                                      //помечаю второе число как не записанное
        mf.isWriteNum()[2]=false;                                      //стирание точки, если была
        mf.setOperation("");                                           //стираю операцию
        mf.getTxtF().setText("");                                      //стираю число с циферблата
    }


    public void writePoint(String str)                  //дробная точка
    {
        if (mf.getTxtF().getText().equals("")&!mf.isWriteNum()[2])
        {
            mf.getTxtF().setText("0.");
            mf.isWriteNum()[2]=true;                                    //точка поставлена
        }
        else if (!mf.getTxtF().getText().equals("")&!mf.isWriteNum()[2])
        {
            mf.getTxtF().setText(mf.getTxtF().getText()+str);
            mf.isWriteNum()[2]=true;                                    //точка поставлена
        }
    }


    private void writeDigit(String str)
    {
         mf.getTxtF().setText(mf.getTxtF().getText()+str);
    }


    public void writeOper(String str)
    {
        String numBoard= mf.getTxtF().getText();
        boolean num1=mf.isWriteNum()[0];
        boolean num2=mf.isWriteNum()[1];


         if (!numBoard.equals("")&&!num1&&!num2)                               //операция нажата, нет обоих чисел, число введено
        {
                mf.setfNum(numBoard);                                          //записываю первое число
                mf.setOperation(str);                                          //записываю операцию
                mf.getLblTxt().setText(printLbl(numBoard)+" "+str);            //запись операции и числа в лейбл
                mf.getTxtF().setText("");                                      //стираю число с циферблата
                mf.isWriteNum()[0]=true;                                       //помечаю первое число как записанное
                mf.isWriteNum()[2]=false;                                      //стирание точки, если была
        }


        else if (numBoard.equals("")&&num1&&!num2)                     //операция нажата,есть первое число, второе не введено
        {
            mf.getLblTxt().setText(printLbl(mf.getfNum())+" "+str);    //запись операции и числа в лейбл
            mf.setOperation(str);                                      //записываю операцию
        }


        else if (!numBoard.equals("")&&num1&&!num2&&mf.getOperation().equals(""))     //операция нажата,есть первое число, второе введено, операция не записана
        {
            mf.setsNum(numBoard);                                      //записываю второе число
            mf.isWriteNum()[2]=false;                                  //стирание точки, если была
            mf.setOperation(str);
            mf.isWriteNum()[1]=true;                                   //помечаю второе число как записанное
            calculate();
        }


        else if (!numBoard.equals("")&&num1&&!mf.getOperation().equals(""))     //операция нажата,есть первое число, второе введено, операция записана
        {
            mf.setsNum(numBoard);                                        //записываю второе число
            mf.isWriteNum()[2]=false;                                    //стирание точки, если была
            mf.isWriteNum()[1]=true;                                     //помечаю второе число как записанное
            calculate();
            mf.setOperation(str);
            mf.getLblTxt().setText(mf.getLblTxt().getText()+" "+str);                        //запись результата в лейбл
        }
    }


    protected void calculate()
    {
        if (!mf.getTxtF().getText().equals("")&&mf.isWriteNum()[0]&&!mf.getOperation().equals(""))
        {
            createSum(mf.getfNum(), mf.getTxtF().getText());                               //подсчитываю результат
            mf.setfNum(mf.getResult());                                                    //записываю первое число
            mf.getLblTxt().setText(printLbl(mf.getResult()) + "");                         //запись результата в лейбл
            mf.setsNum("");                                                                //стираю второе число
            mf.setOperation("");                                                           //стираю операцию
            mf.getTxtF().setText("");                                                      //стираю число с циферблата
            mf.isWriteNum()[1] = false;                                                    //помечаю второе число как не записанное
            mf.isWriteNum()[2] = false;                                                    //стирание точки, если была
        }
    }

    private String printLbl(String s)
    {
        if (s.length()>25)
        {
            DecimalFormat nf= new DecimalFormat("#.####E0");
            String str=nf.format(new BigDecimal(s));
            return str;
        }
        return s;
    }

    private  String roundBD(String value)                //округление и приведение к общему виду
    {
        if (String.valueOf(value).equals("Infinity") |String.valueOf(value).equals("NaN")){return "0";}     //при делении на ноль
        temp = new BigDecimal(String.valueOf(value));
        temp = temp.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros();
        return temp.toPlainString();
    }


     private void createSum(String f, String s)
     {
         temp=new BigDecimal(f);
         temp2=new BigDecimal(s);
        switch (mf.getOperation())
        {
            case "+" : mf.setResult(roundBD(temp.add(temp2).toPlainString()));  break;
            case "-" : mf.setResult(roundBD(temp.subtract(temp2).toPlainString()));  break;
            case "*" : mf.setResult(roundBD(temp.multiply(temp2).toPlainString()));  break;
            case "/" : if (Double.parseDouble(s)!=0)
            {
                mf.setResult(roundBD(temp.divide(temp2,4,RoundingMode.HALF_UP).toPlainString())); break;
            }
                mf.setResult(roundBD(temp.toPlainString()));  break;
        }
    }

}
