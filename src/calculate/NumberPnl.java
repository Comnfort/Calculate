package calculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

class NumberPnl extends JPanel
{
    private char [] array={'1','2','+','-','3','4','5','*','6','7','8','/','9','0','С','='};
    private char [] array2={'1','2','+','-','3','4','5','*','6','7','8','/','9','0','С','=','.',' ',' ',' ',};
    private MainFrame mf;
    private ActionListener act;

    NumberPnl(MainFrame mf, int size)
    {
        this.mf=mf;
        setBounds(10,10,370,380);
        createAct();
        createPanel(size);
        setFocusable(true);
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
            var bt= new Butn(String.valueOf(arr[i]),act,form);
            bt.setFont(new Font(null, Font.PLAIN,font));
            add(bt);
        }
    }


    private void createAct()                     //действия для кнопок
    {
        act= e ->
        {
            String event=e.getActionCommand();
            switch (event)
            {
                case "+" :  writeOper(event);  break;
                case "-" :  writeOper(event);  break;
                case "*" :  writeOper(event);  break;
                case "/" :  writeOper(event);  break;
                case "С" :  delete();          break;
                case "=" :  calculateRes();    break;
                case "0" :  writeToTF(event);  break;
                case "1" :  writeToTF(event);  break;
                case "2" :  writeToTF(event);  break;
                case "3" :  writeToTF(event);  break;
                case "4" :  writeToTF(event);  break;
                case "5" :  writeToTF(event);  break;
                case "6" :  writeToTF(event);  break;
                case "7" :  writeToTF(event);  break;
                case "8" :  writeToTF(event);  break;
                case "9" :  writeToTF(event);  break;
                case "." :  writePoint(event); break;
            }
        };
    }


    private void writePoint(String str)                  //дробная точка
    {
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


    private void writeToTF(String str)
    {
         mf.getTxtF().setText(mf.getTxtF().getText()+str);
    }


    private void writeOper(String str)
    {
        String numBoard= mf.getTxtF().getText();
        if (!numBoard.equals("")){numBoard=roundBD(Double.valueOf(numBoard));}          //приведение к общему виду
        boolean num1=mf.getIsWriteNum()[0];
        boolean num2=mf.getIsWriteNum()[1];


         if (!numBoard.equals("")&!num1&!num2)                   //операция нажата, нет обоих чисел, число введено
        {
                mf.setfNum(numBoard);                                          //записываю первое число
                mf.setOperation(str);                                          //записываю операцию
                mf.getLblTxt().setText(numBoard+" "+str);                      //запись операции и числа в лейбл
                mf.getTxtF().setText("");                                      //стираю число с циферблата
                mf.getIsWriteNum()[0]=true;                                    //помечаю первое число как записанное
                mf.getIsWriteNum()[2]=false;                                   //стирание точки, если была
        }


        else if (numBoard.equals("")&num1&!num2)                  //операция нажата,есть первое число, второе не введено
        {
            mf.getLblTxt().setText(mf.getfNum()+" "+str);        //запись операции и числа в лейбл
            mf.setOperation(str);                                      //записываю операцию
        }


        else if (!numBoard.equals("")&num1&!num2&mf.getOperation().equals(""))     //операция нажата,есть первое число, второе введено, операция не записана
        {
            mf.setsNum(numBoard);       //записываю второе число
            mf.getIsWriteNum()[2]=false;                                  //стирание точки, если была
            mf.setOperation(str);
            mf.getIsWriteNum()[1]=true;                                   //помечаю второе число как записанное
            calculateRes();
        }


        else if (!numBoard.equals("")&num1&!num2&!mf.getOperation().equals(""))                  //операция нажата,есть первое число, второе введено, операция записана
        {
            mf.setsNum(numBoard);       //записываю второе число
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


    private void calculateRes()
    {
        if (!mf.getOperation().equals("")&mf.getIsWriteNum()[0]&!mf.getTxtF().getText().equals(""))                //операция записана,первое число есть, второе введено
        {
            createSum(Double.valueOf(mf.getfNum()), Double.valueOf(mf.getTxtF().getText()));         //подсчитываю результат
            mf.setfNum(mf.getResult());                                                    //записываю первое число
            mf.getLblTxt().setText(mf.getResult()+" ");                                    //запись результата в лейбл
            mf.setsNum("");                                                                //стираю второе число
            mf.setOperation("");                                                           //стираю операцию
            mf.getTxtF().setText("");                                                      //стираю число с циферблата
            mf.getIsWriteNum()[1]=false;                                                   //помечаю второе число как не записанное
            mf.getIsWriteNum()[2]=false;                                                   //стирание точки, если была
        }

        if (!mf.getOperation().equals("")&mf.getIsWriteNum()[0]&mf.getIsWriteNum()[1])                                   //операция записана, есть оба числа
        {
            createSum(Double.valueOf(mf.getfNum()), Double.valueOf(mf.getsNum()));                     //подсчитываю результат
            mf.setfNum(mf.getResult());                                    //записываю первое число
            mf.getLblTxt().setText(mf.getResult()+" ");                    //запись результата в лейбл
            mf.setsNum("");                                                //стираю второе число
            mf.setOperation("");                                           //стираю операцию
            mf.getTxtF().setText("");                                      //стираю число с циферблата
            mf.getIsWriteNum()[1]=false;                                   //помечаю второе число как не записанное
            mf.getIsWriteNum()[2]=false;                                   //стирание точки, если была
        }
    }

    private  String roundBD(double value)                //округление и приведение к общему виду
    {
        if (String.valueOf(value).equals("Infinity") |String.valueOf(value).equals("NaN")){return "0";}     //при делении на ноль
        BigDecimal bd = new BigDecimal(String.valueOf(value));
        if (value>=1.0E12)
        {
            bd = bd.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros();     return bd.toString();          //отображать с экспонентой при недостатке точности дабла
        }
        else
        {
            bd = bd.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros();   return bd.toPlainString();       //округление и отбрасывание нулей
        }
    }


     private void createSum(double f, double s)
     {
        switch (mf.getOperation())
        {
            case "+" : mf.setResult(roundBD(f+s));  break;
            case "-" : mf.setResult(roundBD(f-s));  break;
            case "*" : mf.setResult(roundBD(f*s));  break;
            case "/" : mf.setResult(roundBD(f/s));  break;
        }
    }

    
    private void delete()
    {
        mf.getLblTxt().setText("");                                    //стираю лейбл
        mf.setfNum("");                                                 //стираю первое число
        mf.setsNum("");                                                 //стираю второе число
        mf.getIsWriteNum()[0]=false;                                   //помечаю первое число как не записанное
        mf.getIsWriteNum()[1]=false;                                   //помечаю второе число как не записанное
        mf.getIsWriteNum()[2]=false;                                   //стирание точки, если была
        mf.setOperation("");                                           //стираю операцию
        mf.getTxtF().setText("");                                      //стираю число с циферблата
    }
}
