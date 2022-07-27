package ui;

/*In side this class i am going to create all ui components and ui based things ui means user interface */
import java.io.*;//input and output
import javax.swing.*;
import java.awt.*;//capturing the events like onclick button
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;//open the exesting file

public class Notepad extends JFrame implements ActionListener, WindowListener{
JTextArea jta = new JTextArea();
File fnameContainer;

//constructor
public Notepad(){
    Font fnt = new Font("Arial",Font.PLAIN,15);
    Container con =getContentPane();
    //creating manu bar           file      edit     help
    JMenuBar jmb = new JMenuBar();
    JMenu jmfile = new JMenu("File");
    JMenu jmedit = new JMenu("Edit");
    JMenu jmhelp = new JMenu("Help");
     
    con.setLayout(new BorderLayout());
    
    JScrollPane sbrText = new JScrollPane(jta);
    sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    sbrText.setVisible(true);

    jta.setFont(fnt);
    jta.setLineWrap(true);
    jta.setWrapStyleWord(true);
     
    con.add(sbrText);
    //file menu
    createMenuItem(jmfile,"New");
    createMenuItem(jmfile,"Open");
    createMenuItem(jmfile,"save");

    jmfile.addSeparator();
    createMenuItem(jmfile,"Exit");
    //edit menu
    createMenuItem(jmedit,"Cut");
    createMenuItem(jmedit,"ctrl+c");
    createMenuItem(jmedit,"ctrl+v");
     //Help menu
    createMenuItem(jmhelp,"About Notepad");
// Adding to menu bar
    jmb.add(jmfile);
    jmb.add(jmedit);
    jmb.add(jmhelp);
     
    setJMenuBar(jmb);
     
    setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
    addWindowListener(this);
    setSize(500,500);
    setTitle("Untitle.txt - NotePad");
    setVisible(true);

}
// method for creating menu
public void createMenuItem(JMenu jm ,String txt){
    JMenuItem jmi  = new JMenuItem(txt);
    jmi.addActionListener(this);
    jm.add(jmi);
}
//crating another method for performing action like ation event
public void actionPerformed(ActionEvent e){
    JFileChooser jfc = new JFileChooser();
    if(e.getActionCommand().equals("New")){//if equal to new 
    this.setTitle("Untitled.txt - NotePad");
    jta.setText("");
    fnameContainer  = null;
    }
    else if(e.getActionCommand().equals("open")){
        //equal to open
        int ret = jfc.showDialog(null,"open");
        if(ret == JFileChooser.APPROVE_OPTION){
            try{ //error my occur
                File fyl = jfc.getSelectedFile();
                OpenFile(fyl.getAbsolutePath());
                this.setTitle(fyl.getName()+" - Notepad");
                fnameContainer = fyl;
            }
            catch(IOException ers){

            }
        }
        
    }else if(e.getActionCommand().equals("save")){
        if(fnameContainer!=null){
            jfc.setCurrentDirectory(fnameContainer);
            jfc.setSelectedFile(fnameContainer);

        }
        else{
            jfc.setSelectedFile(new File("Untitled.txt"));
        }
        int ret  = jfc.showSaveDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION){
            try{
                File fyl = jfc.getSelectedFile();
                SaveFile(fyl.getAbsolutePath());
                this.setTitle(fyl.getName()+" - NotePad");
                fnameContainer = fyl;

            }
            catch(Exception ets){}

        }

    }
    else if(e.getActionCommand().equals("Exit")){
        //exit
        Exiting();

    }
    else if(e.getActionCommand().equals("ctrl+c")){
        jta.copy();
    }
    else if(e.getActionCommand().equals("ctrl+v")){
        jta.paste();
    }
    else if(e.getActionCommand().equals("About Notepad")){
        JOptionPane.showMessageDialog(this,"Created by : Manidhar Reddy  26/07/2022","Notepad",JOptionPane.INFORMATION_MESSAGE);
    }
    else if(e.getActionCommand().equals("Cut")){
        jta.cut();
    }

}
public void OpenFile(String fname) throws IOException{
    BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
    String l;
    jta.setText("");
    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    while((l=d.readLine())!=null){
        jta.setText(jta.getText()+l+"\r\n");

    }
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    d.close();

}
public void SaveFile(String fname) throws IOException{
    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    DataOutputStream o = new DataOutputStream(new FileOutputStream(fname));
    o.writeBytes(jta.getText());
    o.close();
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

}
 
public void windowDeactivated(WindowEvent e){}
public void windowActivated(WindowEvent e){}
public void windowDeiconified(WindowEvent e){}
public void windowIconified(WindowEvent e){}
public void windowClosed(WindowEvent e){}
public void windowClosing(WindowEvent e){
    Exiting();
}
public void windowOpened(WindowEvent e){}
public void Exiting(){
    System.exit(0);
}
}