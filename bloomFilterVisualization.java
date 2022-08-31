import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger; 

public class bloomFilterVisualization extends JFrame implements ActionListener
{
  private JPanel topPanel,topLeftPanel,topRightPanel,middlePanel,middleLeftPanel,middleRightPanel,arrayPanel;
  private JLabel hash1,hash2,check;
  private JLabel ansHash1,ansHash2,ansCheck;
  private JButton btnAdd,btnCheck;
  private JList <String> List;
  private DefaultListModel <String> model;
  private JTextField tf,tfCheck;
  private JTable table;
  private JScrollPane scrollPane;
  private byte[] myArray;
  
  public bloomFilterVisualization()
  {
    initialize();
  }
  public Integer polynomialHash(String key) 
  {
    double hashval = 0;
    int p = 33;
    int a = 10;
    int b = 9;
    double m = Math.pow(a,b) + 9;
    double power = 1;
    double arrIndex = 0;
    char ch1 = 'a'; 
    int ascii1 = (int) ch1;
    for (int j = 0; j < key.length(); j++) 
    {
      hashval = (hashval + ((key.charAt(j) - ascii1) +1) * power % m);
      power = (power * p) % m;
      arrIndex = (((2*hashval+3)%77)%100); 
    }
    int arrvalue = (int)arrIndex;
    return arrvalue;
  }

  public Integer md5(String key)
  {
      BigInteger a,b,c,d,e,f,g,h;  
      MessageDigest md = null;
      try 
      {
        md = MessageDigest.getInstance("MD5");
      } 
      catch (NoSuchAlgorithmException e1) 
      {
        e1.printStackTrace();
      } 
      md.update(key.getBytes());
      byte[] output = md.digest();
      BigInteger n = new BigInteger(1, output);
      a = BigInteger.valueOf(2);
      c = BigInteger.valueOf(3);
      e = BigInteger.valueOf(7);
      g = BigInteger.valueOf(100);
      b = a.multiply(n);
      d = b.add(c);
      f = d.mod(e);
      h = f.mod(g);
      int arrValue = h.intValue();
      return arrValue; 
  }  
  public void initialize()
  {
    setSize(1400, 800);
    myArray = new byte[100];
    
    topPanel = new JPanel(new BorderLayout());

    topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		topPanel.add(topLeftPanel, BorderLayout.WEST);

    topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPanel.add(topRightPanel, BorderLayout.EAST);

    hash1 = new JLabel("polynomial Hash Value :");
    hash1.setFont(new Font("Times New Roman", Font.BOLD, 20));
    topLeftPanel.add(hash1);

    ansHash1 = new JLabel("No hash value");
    ansHash1.setFont(new Font("Arial", Font.PLAIN, 18));
    topLeftPanel.add(ansHash1);

    hash2 = new JLabel("md5 Hash Value :");
    hash2.setFont(new Font("Times New Roman", Font.BOLD, 20));
    topLeftPanel.add(hash2);

    ansHash2 = new JLabel("No hash value");
    ansHash2.setFont(new Font("Arial", Font.PLAIN, 18));
    topLeftPanel.add(ansHash2);
    
    model = new DefaultListModel<>();
    List = new JList<>(model);
    List.setFont(new Font("Arial", Font.PLAIN, 14));
    topLeftPanel.add(List);

    middlePanel = new JPanel(new BorderLayout());

    middleLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		middlePanel.add(middleLeftPanel, BorderLayout.WEST);

    middleRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		middlePanel.add(middleRightPanel, BorderLayout.EAST);

    check = new JLabel("Check wheather the string is there:");
    check.setFont(new Font("Times New Roman", Font.BOLD, 20));
    middleLeftPanel.add(check);

    ansCheck = new JLabel("??");
    ansCheck.setFont(new Font("Arial", Font.PLAIN, 18));
    middleLeftPanel.add(ansCheck);

    arrayPanel = new JPanel();
    {
    arrayPanel.setBorder(BorderFactory.createLineBorder(Color.black)); 
    };
    Integer valTable[][] = new Integer[101][2];
    for(int i =0;i<=100;i++)
    {
       valTable[i][0]=i;
       valTable[i][1]=0;
    }
    String heading[] = {"Array Number","Value"};
    table = new JTable(valTable,heading);
    scrollPane = new JScrollPane(table);            
    scrollPane.setBounds(5, 10, 300, 150);
    scrollPane.setVisible(true);
    arrayPanel.add(scrollPane);
      
    /*temp = new JLabel("0 if no value is added to array postion. 1 if value is added to array postion");
    temp.setFont(new Font("Arial", Font.PLAIN, 18));
    arrayPanel.add(temp);*/
    
    tf = new JTextField("");
		tf.setFont(new Font("Arial", Font.BOLD, 20));
		tf.setPreferredSize(new Dimension(80, 30));
    topRightPanel.add(tf);
    tf.setVisible(true);
    
    btnAdd = new JButton("Add");
    btnAdd.addActionListener(this);
		btnAdd.setFont(new Font("Arial", Font.BOLD, 20));
    topRightPanel.add(btnAdd,BorderLayout.EAST);
   
    tfCheck = new JTextField("");
		tfCheck.setFont(new Font("Arial", Font.BOLD, 20));
		tfCheck.setPreferredSize(new Dimension(80, 30));
    middleRightPanel.add(tfCheck);
    tfCheck.setVisible(true);

    btnCheck = new JButton("Check");
    btnCheck.addActionListener(this);
		btnCheck.setFont(new Font("Arial", Font.BOLD, 20));
    middleRightPanel.add(btnCheck,BorderLayout.EAST);
    
    
    add(arrayPanel,BorderLayout.CENTER);
    add(middlePanel,BorderLayout.SOUTH);
    add(topPanel,BorderLayout.NORTH);
    setTitle("Bloom Filter");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  
  public void actionPerformed(ActionEvent evt)
  {
    try
    {
    if(tf.isEnabled())
    {
     
      if (evt.getSource() == btnAdd) 
      {
        String dataAdd = tf.getText();
         insert(dataAdd);
         
         model.addElement(dataAdd);
      }
    }
    if(tfCheck.isEnabled())
     {
      if (evt.getSource() == btnCheck) 
      {
        String dataCheck = tfCheck.getText();
        check(dataCheck);  
      }
      tf.setText("");
			tf.requestFocusInWindow();
      tfCheck.setText("");
			tfCheck.requestFocusInWindow();
     }
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null, "Please Enter String.");
    }
  }
   
  public void insert(String data)
  {
      if(data!=null)
      {
        
        int hashValue1 = polynomialHash(data);
        int hashValue2 = md5(data);
        myArray[hashValue1] = 1;
        myArray[hashValue2] = 1;
        int row1 = hashValue1;
        int row2 = hashValue2;
        table.setValueAt(1, row1, 1);
        table.setValueAt(1, row2, 1);
        String ans1 = Integer.toString(hashValue1);
        String ans2 = Integer.toString(hashValue2);
        ansHash1.setText(ans1);
        ansHash2.setText(ans2);     
      }
  }
  public boolean contain(String data)
  {
    int hashValue1 = polynomialHash(data);
    int hashValue2 = md5(data);
    if(myArray[hashValue1]==1&&myArray[hashValue2]==1)
    {
    return true;
    }
    else
    return false;
  }
  public void check(String data)
  {
      if(data!=null)
      {
        if(contain(data))
        {
            ansCheck.setText("String is already taken");
        }
        else
        {
           ansCheck.setText("U can take this string");
        }
      }
  }

  public static void main(String[] args)
  {
    long startTime = System.nanoTime();
    long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    bloomFilterVisualization p = new bloomFilterVisualization();
    long endTime = System.nanoTime();
    System.out.println("Time Taken " + (endTime - startTime) + " ns");
    long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    long actualMemUsed=afterUsedMem-beforeUsedMem;
    int dataSize = 1024 * 1024;
    System.out.println("Memory Used " + (actualMemUsed)/dataSize + "MB");
  } 
}