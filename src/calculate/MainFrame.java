package calculate;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame
{

    public static void main(String[] args) {
        new MainFrame();
    }

    private JTextField txtF;
    private int height=605;
    private String fNum;
    private String sNum;
    private String operation;
    private String result;
    private JLabel lblTxt;
    private JPanel btnPanel;
    private NumberPnl numP;
    private boolean[] writeNum =new boolean[3];                  //число1, число2, точка
    private InputMap inputMap;
    private ActionMap actionMap;

    boolean[] isWriteNum() {
        return writeNum;
    }

    JPanel getBtnPanel() {
        return btnPanel;
    }

    public NumberPnl getNumP() {
        return numP;
    }

    void setResult(String result) {
        this.result = result;
    }

    String getResult() {
        return result;
    }

    JLabel getLblTxt() {
        return lblTxt;
    }

    JTextField getTxtF() {
        return txtF;
    }

    String getfNum() {
        return fNum;
    }

    void setfNum(String fNum) {
        this.fNum = fNum;
    }

    String getsNum() {
        return sNum;
    }

    void setsNum(String sNum) {
        this.sNum = sNum;
    }

    String getOperation() {
        return operation;
    }

    void setOperation(String operation) {
        this.operation = operation;
    }


    private MainFrame()
    {
        int width=400;
        setSize(width,height);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(0,51,51));
        setJMenuBar(new Menu(this));
        createIn();
        createNumPnl(this,1);
        fNum="";
        sNum="";
        operation="";
        createOut();
        setVisible(true);
        setFocusable(true);

    }



    private void createIn()
    {
        JPanel pnl=new JPanel(null);
        pnl.setBounds(0,28,this.getWidth(),25+(height-50)/11);
        pnl.setBorder(BorderFactory.createEtchedBorder());
        JLabel lbl=new JLabel("Enter");
        lbl.setFont(new Font(null,Font.BOLD,15));
        lbl.setBounds(10,12,50,50);
        pnl.add(lbl);
        txtF=new JTextField(40);
        txtF.setBounds(70,12,300,50);
        txtF.setEditable(false);
        txtF.setBorder(BorderFactory.createLoweredBevelBorder());
        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new DocumentFilter()
        {
            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)throws BadLocationException
            {
                fb.insertString(off, str.replaceAll("[^0-9.-]", ""), attr);
            }
            @Override
            public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)throws BadLocationException
            {
                fb.replace(off, len, str.replaceAll("[^0-9.-]", ""), attr);
            }
        });
        txtF.setDocument(doc);
        txtF.setFont(new Font("Serif", Font.PLAIN,35));
        txtF.setText("");
        pnl.add(txtF);
        add(pnl);
    }


    void createNumPnl(MainFrame mf, int size)
    {
        btnPanel=new JPanel(null);
        btnPanel.setBorder( BorderFactory.createEtchedBorder());
        btnPanel.setBounds(0,100,400,400);
        numP=new NumberPnl(mf,size);
        inputMap=numP.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMap=numP.getActionMap();
        handlerKey();
        btnPanel.add(numP);
        add(btnPanel);
    }


    private void createOut()
    {
        lblTxt =new JLabel("Result");
        lblTxt.setFont(new Font(null, Font.BOLD,20));
        JPanel p=new JPanel(null);
        p.setBounds(0,495,this.getWidth(),105);
        p.setBackground(Color.ORANGE);
        lblTxt.setBounds(20,10,350,30);
        p.setBorder(BorderFactory.createEtchedBorder());
        p.add(lblTxt);
        add(p);
    }


    private void handlerKey()                  //ввод с клавиатуры цифр и обработка enter
    {
        String strN[]={"0","1","2","3","4","5","6","7","8","9"};

        for (int i = 0; i < strN.length; i++)
        {
            final int c=i;
            inputMap.put(KeyStroke.getKeyStroke("released "+strN[c]),"f"+strN[c]);
            actionMap.put("f"+strN[c], new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    txtF.setText(txtF.getText()+strN[c]);
                }
            });
        }
        inputMap.put(KeyStroke.getKeyStroke("released ENTER"),"fN1");
        actionMap.put("fN1", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getNumP().calculate();
            }
        });
    }
}





