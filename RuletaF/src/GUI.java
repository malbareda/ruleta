/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pkamaljo
 */
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;
import javax.swing.*;


public class GUI extends javax.swing.JFrame implements WindowListener {
    RouletteWheel wheel;
    Button spin,bet,reset, win, fail;
    Label lBalance,lNumberBet,lBetType,lBetAmount,lBettingReport;
    int score;
   TextField tBalance,tNumberBet,tBetAmount;
   JCheckBox red,black,high,low;
   //HashMap<JCheckBox,Integer> checkBoxCount=new HashMap<JCheckBox,Integer>();
   Set<String> selectedCheckBoxes=new TreeSet<String>();//need to be reset
   int iNumberBet;
   TextArea tBettingReport;
   JPanel wheelPanel;
   FlowLayout layout;
   String actuser;
   DataBase xd = new DataBase();
   HashMap<String, Integer> users;
   
     /**
     * Creates a new instance of Java2DFrame
     */
    public GUI()
    {
        Container container=getContentPane();
        layout=new FlowLayout();
        container.setLayout(layout);
        
        layout.setVgap(50);
        //layout.setAlignment(layout.LEFT);
       // layout.layoutContainer(container);
        addWindowListener(this);

      
        
        wheel=new RouletteWheel();
        wheel.setBallDrawn(true);
        wheel.setScore(0);
       
        container.add(wheel);
       
        
        try {
			users = xd.readFile();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        JComboBox lusers = new JComboBox();
        
        
        lusers.setModel(new DefaultComboBoxModel(users.keySet().toArray(new String[users.size()]))); 
        lusers.addItemListener(new ItemListener() { 
            public void itemStateChanged(ItemEvent e) { 
            	actuser = lusers.getSelectedItem().toString();
            	tBalance.setText(""+users.get(actuser));
            	
            } 
         }); 
        container.add(lusers);
        
         lBalance=new Label("COSOS");
        container.add(lBalance);
        
        
        tBalance=new TextField();
        tBalance.setEditable(false);
        actuser = lusers.getSelectedItem().toString();
        tBalance.setText(""+users.get(actuser));
        container.add(tBalance);
        
        
        
        //Girar
        
        bet=new Button("GIRAR");
        container.add(bet);
        bet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
               betActionPerformed( e); 
            }});
        
        
        win = new Button("WIN");
        container.add(win);
        win.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
            	winActionPerformed( e); 
            }});
        
        
        fail = new Button("FAIL");
        container.add(fail);
        fail.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
            	failActionPerformed( e); 
            }});
            
        lBettingReport=new Label("Betting Report");
        container.add(lBettingReport);
        tBettingReport=new TextArea();
        tBettingReport.setEditable(false);
        container.add(tBettingReport);
        
        reset=new Button("RESET");
        reset.addActionListener(new ActionListener(){
           
            public void actionPerformed(ActionEvent evt)
            {
                resetActionPerformed(evt);
            }
        
        });
        container.add(reset);
        
        setSize(555,670);
        
        setVisible(true);
    }
    
    public void resetActionPerformed(ActionEvent evt)
    {
       red.setSelected(false);
       black.setSelected(false);
       high.setSelected(false);
       low.setSelected(false);
       tBalance.setText(""+Roulette.initialBalance);
       tBettingReport.setText("");
       tNumberBet.setText("");
       tBetAmount.setText("");
       wheel.setScore(0);
       repaint();
       
       selectedCheckBoxes=new TreeSet<String>(); //reset selected check boxes
       
       iNumberBet=0;
    }
    public void checkBoxHandler(ItemEvent evt)
    {
        Integer temp;
        JCheckBox jTemp=null;
        if(evt.getStateChange()==ItemEvent.SELECTED)
            {
                jTemp=(JCheckBox)evt.getSource();
                selectedCheckBoxes.add(jTemp.getName());
                System.out.println(""+jTemp.getName());
            
            }
            else
                if(evt.getStateChange()==ItemEvent.DESELECTED)
                {
                    jTemp=(JCheckBox)evt.getSource();
                    System.out.println(""+selectedCheckBoxes);
                    System.out.println("current one"+jTemp.getName());
                    selectedCheckBoxes.remove(jTemp.getName());
                }
        
    }
    public void betActionPerformed(ActionEvent e)
    {
       /**
        * Betting amount should be greater than 0 and
        * less than currently available balance
        */

        
        spintheWheel();
        
    }
    
    public void winActionPerformed(ActionEvent e)
    {
       /**
        * Betting amount should be greater than 0 and
        * less than currently available balance
        */

        
        resultats(true);
        
    }
    public void failActionPerformed(ActionEvent e)
    {
       /**
        * Betting amount should be greater than 0 and
        * less than currently available balance
        */

        
        resultats(false);
        
    }
    
    public void resultats(boolean b) {
        int currentBalance=Integer.parseInt(tBalance.getText());
        System.out.println(currentBalance);
		// TODO Auto-generated method stub
    	if (b) currentBalance = currentBalance + score; 
    	else currentBalance = currentBalance - score;
    	
    	users.put(actuser, currentBalance);
        tBalance.setText(""+users.get(actuser));
    	
    	win.setEnabled(false);
    	fail.setEnabled(false);
    	bet.setEnabled(true);
    	
    	
    	try {
			xd.saveFile(users);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void spintheWheel()
    {
        
        int currentBalance=Integer.parseInt(tBalance.getText());
        int finalBalance=currentBalance;
        score=wheel.spin();
        repaint();//to reflect the change.
        tBettingReport.append("JUGAREMOS POR "+wheel.getLabel()+" COSOS\n");
        
    	win.setEnabled(true);
    	fail.setEnabled(true);
    	bet.setEnabled(false);

  }
  
  
   
    	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
        public void windowClosing(WindowEvent e) {}

    
    
    
    /**
     * Starts the program
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
                GUI gui=new GUI();
                gui.setDefaultCloseOperation(GUI.EXIT_ON_CLOSE);
                
               
          
    }
}

class Roulette
{
    public static final int initialBalance=1000;
    public final static int NUMBER=35;
     
    public final static int HIGH=1;
    
    public final static int LOW=1;
    
    public final static int EVEN=1;
    
    public final static int ODD=1;
    
    public final static int COLOR=1;
}
