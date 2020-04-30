package calculate;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class MainFrame extends JFrame
{

    public static void main(String[] args) {
        new MainFrame();
    }

    private JTextField txtF;
    private int height=600;
    private String fNum;
    private String sNum;
    private String operation;
    private String result;
    private JLabel lblTxt;
    private JPanel btnPanel;
    private boolean[] isWriteNum=new boolean[3];                  //число1, число2, точка

    boolean[] getIsWriteNum() {
        return isWriteNum;
    }

    JPanel getBtnPanel() {
        return btnPanel;
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
        createInPnl();
        createNumPnl(this,1);
        fNum="";
        sNum="";
        operation="";
        createResPnl();
        setVisible(true);
    }


    private void createInPnl()
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
                fb.insertString(off, str.replaceAll("[^0-9.]", ""), attr);  //включать только цифры и точку
            }
            @Override
            public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)throws BadLocationException
            {
                fb.replace(off, len, str.replaceAll("[^0-9.]", ""), attr);  //включать только цифры и точку
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
        btnPanel.add(new NumberPnl(mf,size));
        add(btnPanel);
    }

    private void createResPnl()
    {
        lblTxt =new JLabel("Результат");
        lblTxt.setFont(new Font(null, Font.BOLD,20));
        JPanel p=new JPanel(null);
        p.setBounds(0,height-(height/11)-50,this.getWidth(),(height-50)/11);
        p.setBackground(Color.ORANGE);
        lblTxt.setBounds(20,10,300,25);
        p.setBorder(BorderFactory.createEtchedBorder());
        p.add(lblTxt);
        add(p);
    }
}





