
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import com.toedter.calendar.JDateChooser;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import static javax.management.Query.match;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import static jdk.nashorn.internal.objects.NativeString.match;
import net.proteanit.sql.DbUtils;
import static sun.security.x509.PolicyInformation.ID;

public class EmployeeClass {
    
    private JFrame frame;
    private JTable table;
    private JScrollPane scroll;
    private DefaultTableModel model;
    private JPanel panel;
    private JLabel lbl_id, lbl_fst_nam, lbl_lst_nam, lbl_fth_nam, lbl_blood, lbl_dob, lbl_phone, lbl_nid, lbl_gender, lbl_email, lbl_age, lbl_post, lbl_rlg, lbl_nation, lbl_relation, lbl_address, lbl_pic;
    private JTextField txt_id, txt_lst_nam, txt_fst_nam, txt_fth_nam, txt_email, txt_rlg, txt_nation, txt_search;
    private JComboBox cb_blood, cb_gender, cb_post, cb_relation, cb_search;
    private JFormattedTextField txt_f_phone, txt_f_nid;
    private MaskFormatter mask_f_phone, mask_f_nid;
    private JDateChooser j_calender_birth;
    private JSpinner spinner_age;
    private SpinnerNumberModel value;
    private JTextArea txt_area_address;
    private JButton btn_add, btn_update, btn_delete, btn_reset, btn_browse,btn_search;

    private FileNameExtensionFilter filter;
    private Image reset, add, update, delete, browse, search;
    private ImageIcon home;
    private Color clrB, clrT, clrTS,clrMB;
    
    private Font lbl_font,txt_font;

    private Cursor table_select, menuBar_select;
    private JMenuBar menuBar;
    private JMenu menu_home,menu_about,menu_logout,menu_exit;
    

    private final String[] blood_group_list = {"Unknown", "A-", "A+", "B-", "B+", "O-", "O+", "AB-", "AB+"};
    private final String[] gender_list = {"Male", "Female", "Other"};
    private final String[] post_list = {"Administrative Officer", "Assistant Administrative Officer", "Director General", "Senior Assistant", "Assistant", "Accounts Officer", "	Assistant Accounts Officer", "Security Officer", "Security Guard", "Driver"};
    private final String[] relationship_list = {"Married", "Unmarried"};
    private final String[] search_list = {"ID","FIRST_NAME", "BLOOD", "AGE"};
    
    //this is show jtable string
    public String[] colums = {"ID", "FIRST_NAME", "LAST NAME", "FATHER'S NAME", "BLOOD", "BIRTHDATE", "PHONE", "NID", "GENDER", "EMAIL", "AGE", "POST", "RELIGION", "NATIONALITY", "RELATIONSHIP", "ADDRESS", "IMAGES"};
    public String[] rows = new String[17];
    
    String filename = null;
    byte[] picture = null;
   
    
    
    File f, image;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    EmployeeClass() {
        conn = DatabaseClass.ConnectDB();

        iniComponents();
        exhandlePhone();
        exhandleNid();
        showTableData();

    }

    public final void iniComponents() {

        
         //this code belongs to jframe create	
        frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Employee Management System");
        frame.setVisible(true);
        
        
        //adding image for jframe image icon
        home = new ImageIcon(getClass().getResource("home.png"));
        frame.setIconImage(home.getImage());
        
        
         //this code belongs to set color in panel and many different field
        clrB = new Color(0, 217, 255);
        clrT = new Color(245, 245, 220);
        clrTS = new Color(219, 112, 147);
        clrMB = new Color(219, 212, 147);
        
        
        //this code belongs to panel called for jframe
        panel = new JPanel();
        panel.setSize(800, 600);
        panel.setBackground(clrB);
        panel.setVisible(true);
        panel.setLayout(null);
        frame.add(panel);
        

       

        

        //this code belongs to font style & font size
        lbl_font = new Font("ARIAL", Font.CENTER_BASELINE, 16);
        txt_font = new Font("PLAIN", Font.PLAIN, 14);

        //this code belongs to mouse cursor set by jtable select
        table_select = new Cursor(Cursor.HAND_CURSOR);
        menuBar_select = new Cursor(Cursor.HAND_CURSOR);

       

        //this code belongs to button images set on jframe by source folder
        reset = new ImageIcon(this.getClass().getResource("reset.png")).getImage();
        add = new ImageIcon(this.getClass().getResource("add.png")).getImage();
        update = new ImageIcon(this.getClass().getResource("update.png")).getImage();
        delete = new ImageIcon(this.getClass().getResource("delete.png")).getImage();
        browse = new ImageIcon(this.getClass().getResource("browse.png")).getImage();
        search = new ImageIcon(this.getClass().getResource("search.jpg")).getImage();
         
         //this code belongs to menubar
        menuBar = new JMenuBar();
        menuBar.setCursor(menuBar_select);
        menuBar.setBackground(clrMB);
        frame.setJMenuBar(menuBar);

        //this code belongs to set menubar adding menu field
        menu_about=new JMenu("About");
        menu_logout=new JMenu("Logout");
        menu_exit=new JMenu("Exit");
        

        //called to manu bar
        menuBar.add(menu_about);
        menuBar.add(menu_logout);
        menuBar.add(menu_exit);
        
       
        

        
        //this code belongs to label
        lbl_id = new JLabel("ID");
        lbl_id.setFont(lbl_font);
        lbl_id.setBounds(15, 30, 150, 25);
        panel.add(lbl_id);

        //this code belongs to textfield
        txt_id = new JTextField();
        txt_id.setFont(txt_font);
        txt_id.setBounds(165, 30, 250, 25);
        panel.add(txt_id);


        
        //this code belongs to label
        lbl_fst_nam = new JLabel("FIRST NAME");
        lbl_fst_nam.setFont(lbl_font);
        lbl_fst_nam.setBounds(15, 60, 150, 25);
        panel.add(lbl_fst_nam);

        //this code belongs to textfield
        txt_fst_nam = new JTextField();
        txt_fst_nam.setFont(txt_font);
        txt_fst_nam.setBounds(165, 60, 250, 25);
        panel.add(txt_fst_nam);

        

        //this code belongs to label
        lbl_lst_nam = new JLabel("LAST NAME");
        lbl_lst_nam.setFont(lbl_font);
        lbl_lst_nam.setBounds(15, 90, 150, 25);
        panel.add(lbl_lst_nam);

        //this code belongs to textfield
        txt_lst_nam = new JTextField();
        txt_lst_nam.setFont(txt_font);
        txt_lst_nam.setBounds(165, 90, 250, 25);
        panel.add(txt_lst_nam);



        //this code belongs to label
        lbl_fth_nam = new JLabel("FATHER'S NAME");
        lbl_fth_nam.setFont(lbl_font);
        lbl_fth_nam.setBounds(15, 120, 150, 25);
        panel.add(lbl_fth_nam);

        //this code belongs to textfield
        txt_fth_nam = new JTextField();
        txt_fth_nam.setFont(txt_font);
        txt_fth_nam.setBounds(165, 120, 250, 25);
        panel.add(txt_fth_nam);

        

        //this code belongs to label
        lbl_blood = new JLabel("BLOOD");
        lbl_blood.setFont(lbl_font);
        lbl_blood.setBounds(15, 150, 150, 25);
        panel.add(lbl_blood);

        //this code belongs to jcomboBox by blood label
        cb_blood = new JComboBox(blood_group_list);
        cb_blood.setFont(txt_font);
        cb_blood.setBounds(165, 150, 250, 25);
        panel.add(cb_blood);

        lbl_dob = new JLabel("BIRTHDATE");
        lbl_dob.setFont(lbl_font);
        lbl_dob.setBounds(15, 180, 150, 25);
        panel.add(lbl_dob);

        //this code belongs to jcalender set jpanel
        j_calender_birth = new JDateChooser();
        j_calender_birth.setBounds(165, 180, 250, 25);
        j_calender_birth.setDateFormatString("yyyy-MM-dd"); //this code use jcalender by set date format
        j_calender_birth.setMaxSelectableDate(new java.util.Date()); 
        panel.add(j_calender_birth);

        //this code belongs to phone label set jpanel
        lbl_phone = new JLabel("PHONE");
        lbl_phone.setFont(lbl_font);
        lbl_phone.setBounds(15, 210, 150, 25);
        panel.add(lbl_phone);

        //this code belongs to nid number label set jpanel
        lbl_nid = new JLabel("NID");
        lbl_nid.setFont(lbl_font);
        lbl_nid.setBounds(15, 240, 150, 25);
        panel.add(lbl_nid);

        //this code belongs to gender label set jpanel
        lbl_gender = new JLabel("GENDER");
        lbl_gender.setFont(lbl_font);
        lbl_gender.setBounds(15, 270, 150, 25);
        panel.add(lbl_gender);

        //this code belongs to jcomboBox by gender area
        cb_gender = new JComboBox(gender_list);
        cb_gender.setFont(txt_font);
        cb_gender.setBounds(165, 270, 250, 25);
        panel.add(cb_gender);

        //this code belongs to email label set jpanel
        lbl_email = new JLabel("EMAIL");
        lbl_email.setFont(lbl_font);
        lbl_email.setBounds(15, 300, 150, 25);
        panel.add(lbl_email);

        //this code belongs to formatted textfield email
        txt_email = new JTextField();
        txt_email.setFont(txt_font);
        txt_email.setBounds(165, 300, 250, 25);
        panel.add(txt_email);

        //this code belongs to age label set jpanel
        lbl_age = new JLabel("AGE");
        lbl_age.setFont(lbl_font);
        lbl_age.setBounds(500, 30, 150, 25);
        panel.add(lbl_age);

        //this code belongs to age jspinner set jpanel 
        value = new SpinnerNumberModel(18, 18, 65, 1);//this code belongs to set age number count
        spinner_age = new JSpinner(value);
        spinner_age.setFont(txt_font);
        spinner_age.setBounds(650, 30, 250, 25);
        panel.add(spinner_age);

        //this code belongs to post label set jpanel
        lbl_post = new JLabel("POST");
        lbl_post.setFont(lbl_font);
        lbl_post.setBounds(500, 60, 150, 25);
        panel.add(lbl_post);

        //this code belongs to jcomboBox By post area
        cb_post = new JComboBox(post_list);
        cb_post.setFont(txt_font);
        cb_post.setBounds(650, 60, 250, 25);
        panel.add(cb_post);

        //this code belongs to label
        lbl_rlg = new JLabel("RELIGION");
        lbl_rlg.setFont(lbl_font);
        lbl_rlg.setBounds(500, 90, 150, 25);
        panel.add(lbl_rlg);

        //this code belongs to textfield
        txt_rlg = new JTextField();
        txt_rlg.setFont(txt_font);
        txt_rlg.setBounds(650, 90, 250, 25);
        panel.add(txt_rlg);

        //this code belongs to label
        lbl_nation = new JLabel("NATIONALITY");
        lbl_nation.setFont(lbl_font);
        lbl_nation.setBounds(500, 120, 150, 25);
        panel.add(lbl_nation);

        //this code belongs to textfield
        txt_nation = new JTextField();
        txt_nation.setFont(txt_font);
        txt_nation.setBounds(650, 120, 250, 25);
        panel.add(txt_nation);

        //this code belongs to label
        lbl_relation = new JLabel("RELATIONSHIP");
        lbl_relation.setFont(lbl_font);
        lbl_relation.setBounds(500, 150, 150, 25);
        panel.add(lbl_relation);

        //this code belongs to jcomboBox by relationship area
        cb_relation = new JComboBox(relationship_list);
        cb_relation.setFont(txt_font);
        cb_relation.setBounds(650, 150, 250, 25);
        panel.add(cb_relation);

        //this code belongs to label
        lbl_address = new JLabel("ADDRESS");
        lbl_address.setFont(lbl_font);
        lbl_address.setBounds(500, 180, 150, 25);
        panel.add(lbl_address);

        //this code belongs to textfield
        txt_area_address = new JTextArea();
        txt_area_address.setFont(txt_font);
        txt_area_address.setBounds(650, 180, 250, 55);
        txt_area_address.setLineWrap(true);
        txt_area_address.setWrapStyleWord(true);
        panel.add(txt_area_address);

        //this code belongs to image icon attach
        //attach picture label
        lbl_pic = new JLabel();
        lbl_pic.setForeground(Color.GREEN);
        lbl_pic.setFont(txt_font);
        lbl_pic.setBounds(1000, 40, 200, 200);
        panel.add(lbl_pic);

        //attach picture add button
        btn_browse = new JButton();
        btn_browse.setBounds(1000, 250, 160, 36);
        btn_browse.setIcon(new ImageIcon(browse));
        panel.add(btn_browse);

        

        //this code belongs to search combox list
        cb_search = new JComboBox(search_list);
        cb_search.setBounds(440, 250, 130, 40);
        panel.add(cb_search);

        //this code belongs to search text field list
        txt_search = new JTextField();
        txt_search.setBounds(600, 250, 130, 40);
        panel.add(txt_search);
        
        //this code belongs to search button
        btn_search = new JButton("Search");
        btn_search.setBounds(780, 250, 122, 40);
        btn_search.setIcon(new ImageIcon(search));
        panel.add(btn_search);
        
        

        //this code belongs to add button
        btn_add = new JButton();
        btn_add.setBounds(440, 320, 110, 45);
        btn_add.setIcon(new ImageIcon(add));
        panel.add(btn_add);

        //this code belongs to update button
        btn_update = new JButton();
        btn_update.setIcon(new ImageIcon(update));
        btn_update.setBounds(600, 320, 120, 45);
        btn_update.setEnabled(false);
        panel.add(btn_update);

        //this code belongs to delete button
        btn_delete = new JButton();
        btn_delete.setBounds(780, 320, 120, 45);
        btn_delete.setIcon(new ImageIcon(delete));
        btn_delete.setEnabled(false);
        panel.add(btn_delete);

        //this code belongs to reset button
        btn_reset = new JButton();
        btn_reset.setBounds(960, 320, 150, 40);
        btn_reset.setIcon(new ImageIcon(reset));
        panel.add(btn_reset);
        
        //this code belongs to set jtable model field
        table = new JTable();
        model = new DefaultTableModel(); //this code belongs to model set
        model.setColumnIdentifiers(colums);
        table.setModel(model);
        table.setFont(txt_font);
        table.setBackground(clrT);
        table.setSelectionBackground(clrTS);
        table.setDefaultEditor(Object.class, null); //this code means table not editable by double click
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  //this code belongs to set all columns auto resize table show
        table.setCursor(table_select);	 //this code belongs to set mouse cursor by select table
        table.setRowHeight(100);
        scroll = new JScrollPane(table); //this code belongs to scrollpane called table
        scroll.setBounds(0, 380, 1368, 335);
        panel.add(scroll);
        
        
        
               //this code belongs to text field by only accept integer value input
        txt_id.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent id_input) {
                char input = id_input.getKeyChar();
                if ((input < '0' || input > '9') && input != '\b') {
                    id_input.consume();
                    System.out.println("Please enter a valid id");
                }
            }
        });

        
        
                
        //this code belongs to about menu action listener
        menu_about.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                ImageIcon about_pic = new ImageIcon(getClass().getResource("about_pic.jpg"));
                JOptionPane.showMessageDialog(null, "Employee Management System!"
                    + "\n"                      
                    + "Project Developed By"
                    + "\n"
                    + "Md Abdul Mannan Hridoy "
                    + "\n"
                    + "&"
                    + "\n"
                    + "Md Alim Uddin "
                    + "\n"
                    + "Than more info visit by www.hridoyking.blogspot.com",
                     "About", JOptionPane.INFORMATION_MESSAGE, about_pic);
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        
        
      
        //this code belongs to logout menu action listener
        menu_logout.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                 if (JOptionPane.showConfirmDialog(frame,
                        "Do You Really Want To Logout?", "WARNING!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    LoginClass login=new LoginClass();
                frame.dispose();
                }
                
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Do You Really Want To Logout?", "WARNING!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    LoginClass login=new LoginClass();
                frame.dispose();
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        
        
        //this code belongs to exit menu action listener
        menu_exit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Do You Really Want To Exit?", "WARNING!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Do You Really Want To Exit?", "WARNING!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        
        
        
        //this code belongs to picture add button mouseclick work
        btn_browse.addActionListener(new ActionListener() {

            private FileInputStream fis;

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File(System.getProperty("user.home")));
                filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");           
                file.setFileFilter(filter);
                file.showOpenDialog(null);
                f = file.getSelectedFile();
                filename = f.getAbsolutePath();
               ImageIcon imageicon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbl_pic.getWidth(), lbl_pic.getHeight(), Image.SCALE_SMOOTH));
               lbl_pic.setIcon(imageicon);
               

               try {
                    image = new File(filename);
                    fis = new FileInputStream(image);
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    for (int readNum; (readNum = fis.read(buf)) != -1;) {
                        output.write(buf, 0, readNum);
                    }
                    picture = output.toByteArray();

                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    System.out.println(ex);

                }
            }

        });

        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                int option = cb_search.getSelectedIndex();
                String value = txt_search.getText();

                switch (option) {
                    case 0:
                        value = value.trim();
                        value = "ID LIKE '%" + value + "%'";
                        break;
                    case 1:
                        value = value.trim();
                        value = "FIRST_NAME LIKE '%" + value + "%'";
                        break;
                    case 2:
                        value = value.trim();
                        value = "BLOOD LIKE '%" + value + "%'";
                        break;
                    case 3:
                        value = value.trim();
                        value = "AGE LIKE '%" + value + "%'";
                        break;
                }     
                 showTableData();
        try {
             String sql = "SELECT * FROM tbl_admin"
                + " WHERE " + value;
            System.out.println(sql);
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs=pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            System.out.println(e);
        }
       
            }
        });

        //this code belongs to mouse click than text box old value clear
        txt_search.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                txt_search.setText("");

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
       
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });

        //this code belongs to add button action listener
        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent addKey) {
                String id = txt_id.getText();
                String first_nam = txt_fst_nam.getText();
                String last_nam = txt_lst_nam.getText();
                String father_nam = txt_fth_nam.getText();
                String religion = txt_rlg.getText();
                String nation = txt_nation.getText();
                String address = txt_area_address.getText();

                if (id.equals("")) {
                
                    JOptionPane.showMessageDialog(null,"Id can't be empty");
                }
                 else if (first_nam.equals("")) {
                     
                    JOptionPane.showMessageDialog(null,"First name can't be empty");
                 }
                 else if(!(Pattern.matches("^[a-zA-z ]{0,30}$", txt_fst_nam.getText()))){
                            try{
                               JOptionPane.showMessageDialog(null,"First name input incorrect");
                        
                    }
                            catch(Exception ex){
                                    System.out.println("Successfully first name enter");
                                    }
                           
                            }
                 
                   
                 else if (last_nam.equals("")) {
                    JOptionPane.showMessageDialog(null,"Last name can't be empty");
                }
                 else if(!(Pattern.matches("^[a-zA-z ]{0,15}$", txt_lst_nam.getText()))){
                            try{
                                JOptionPane.showMessageDialog(null,"Last name input incorrect");
                        
                    }
                            catch(Exception ex){
                                    System.out.println("Successfully last name enter");
                                    }
                            }
                 
                 else if (father_nam.equals("")) {
                    System.out.println("Father's name can't be empty");
                } 
                 else if(!(Pattern.matches("^[a-zA-z ]{0,30}$", txt_fth_nam.getText()))){
                            try{
                                JOptionPane.showMessageDialog(null,"Father's name input incorrect");
                        
                    }
                            catch(Exception ex){
                                    System.out.println("Successfully father's name enter");
                                    }
                            }
                 else if (j_calender_birth.getDate()==null) {

                   JOptionPane.showMessageDialog(null, "Birthdate can't be empty");
                }
                 else if (txt_f_phone.getText().trim().equals("+8801")) {

                   JOptionPane.showMessageDialog(null, "Phone number can't be empty");
                } 
                 else if (txt_f_nid.getText().trim().equals("")) {
                   JOptionPane.showMessageDialog(null, "Nid number can't be empty");
                }  
                 else if (religion.equals("")) {
                    JOptionPane.showMessageDialog(null,"Religion can't be empty");
                } else if (nation.equals("")) {
                    JOptionPane.showMessageDialog(null,"Nationality can't be empty");
                } else if (address.equals("")) {
                    JOptionPane.showMessageDialog(null,"Address can't be empty");

                } else if (picture == null) {
                    JOptionPane.showMessageDialog(null,"Image can't be empty");

                } else if (!(Pattern.matches("^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$", txt_email.getText()))) {
                    try {
                       JOptionPane.showMessageDialog(null,"Please enter a valid email");

                    } catch (Exception ex) {
                        System.out.println("Email is valid");
                    }
                } else {
                    try {

                        String sql = "INSERT INTO tbl_admin"
                                + "(`ID`, `FIRST_NAME`, `LAST NAME`, `FATHER'S NAME`, `BLOOD`, `BIRTHDATE`, `PHONE`, `NID`, `GENDER`, `EMAIL`, `AGE`, `POST`, `RELIGION`, `NATIONALITY`, `RELATIONSHIP`, `ADDRESS`, `IMAGES`)"
                                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                        pst = conn.prepareStatement(sql);

                        pst.setString(1, txt_id.getText());
                        pst.setString(2, txt_fst_nam.getText());
                        pst.setString(3, txt_lst_nam.getText());
                        pst.setString(4, txt_fth_nam.getText());
                        pst.setString(5, (String) cb_blood.getSelectedItem());
                        pst.setString(6, ((JTextField) j_calender_birth.getDateEditor().getUiComponent()).getText());
                        pst.setString(7, txt_f_phone.getText());
                        pst.setString(8, txt_f_nid.getText());
                        pst.setString(9, (String) cb_gender.getSelectedItem());
                        pst.setString(10, txt_email.getText());
                        pst.setString(11, spinner_age.getModel().getValue().toString());
                        pst.setString(12, (String) cb_post.getSelectedItem());
                        pst.setString(13, txt_rlg.getText());
                        pst.setString(14, txt_nation.getText());
                        pst.setString(15, (String) cb_relation.getSelectedItem());
                        pst.setString(16, txt_area_address.getText());
                        pst.setBytes(17, picture);
                        pst.executeUpdate();
                        System.out.println("Inserted successfully");

                    } catch (SQLException | HeadlessException ex) {
                        System.out.println(ex);
                    }

                }

                showTableData();
            }
        });

        //this code belongs to update button action listener
        btn_update.addActionListener((ActionEvent e) -> {
             String id = txt_id.getText();
                String first_nam = txt_fst_nam.getText();
                String last_nam = txt_lst_nam.getText();
                String father_nam = txt_fth_nam.getText();
                String religion = txt_rlg.getText();
                String nation = txt_nation.getText();
                String address = txt_area_address.getText();

                if (id.equals("")) {
                
                    JOptionPane.showMessageDialog(null,"Id can't be empty");
                }
                 else if (first_nam.equals("")) {
                     
                   JOptionPane.showMessageDialog(null,"First name can't be empty");
                 }
                 else if(!(Pattern.matches("^[a-zA-z ]{0,30}$", txt_fst_nam.getText()))){
                            try{
                                JOptionPane.showMessageDialog(null,"First name input incorrect");
                        
                    }
                            catch(Exception ex){
                                    System.out.println("Successfully first name enter");
                                    }
                           
                            }
                 else if(!(Pattern.matches("^[a-zA-z ]{0,30}$", txt_lst_nam.getText()))){
                            try{
                                JOptionPane.showMessageDialog(null,"Last name input incorrect");
                        
                    }
                            catch(Exception ex){
                                    System.out.println("Successfully last name enter");
                                    }
                            }
                   
                 else if (last_nam.equals("")) {
                    JOptionPane.showMessageDialog(null,"Last name can't be empty");
                }
                 
                 else if (father_nam.equals("")) {
                    JOptionPane.showMessageDialog(null,"Father's name can't be empty");
                } 
                 else if(!(Pattern.matches("^[a-zA-z ]{0,30}$", txt_fth_nam.getText()))){
                            try{
                               JOptionPane.showMessageDialog(null,"Father's name input incorrect");
                        
                    }
                            catch(Exception ex){
                                    System.out.println("Successfully father's name enter");
                                    }
                            }
                 else if (j_calender_birth.getDate()==null) {

                   JOptionPane.showMessageDialog(null, "Birthdate can't be empty");
                }
                 else if (txt_f_phone.getText().trim().equals("+8801")) {

                    JOptionPane.showMessageDialog(null,"Phone number can't be empty");
                } 
                 else if (txt_f_nid.getText().trim().equals("")) {

                    JOptionPane.showMessageDialog(null,"Nid number can't be empty");
                } else if (religion.equals("")) {
                    JOptionPane.showMessageDialog(null,"Religion can't be empty");
                } else if (nation.equals("")) {
                    JOptionPane.showMessageDialog(null,"Nationality can't be empty");
                } else if (address.equals("")) {
                    JOptionPane.showMessageDialog(null,"Address can't be empty");

                } else if (!(Pattern.matches("^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$", txt_email.getText()))) {
                    try {
                       JOptionPane.showMessageDialog(null,"Please enter a valid email");

                    } catch (Exception ex) {
                        System.out.println("The email is valid");
                    }
                }
                else{
            try {
                String sql = " UPDATE `tbl_admin` SET `FIRST_NAME`=?,`LAST NAME`=?,`FATHER'S NAME`=?,`BLOOD`=?,`BIRTHDATE`=?,`PHONE`=?,`NID`=?,`GENDER`=?,`EMAIL`=?,`AGE`=?,`POST`=?,`RELIGION`=?,`NATIONALITY`=?,`RELATIONSHIP`=?,`ADDRESS`=?,`IMAGES`=? WHERE `ID`=?";
                pst = conn.prepareStatement(sql);

                pst.setString(1, txt_fst_nam.getText());
                pst.setString(2, txt_lst_nam.getText());
                pst.setString(3, txt_fth_nam.getText());
                pst.setString(4, (String) cb_blood.getSelectedItem());
                pst.setString(5, ((JTextField) j_calender_birth.getDateEditor().getUiComponent()).getText());
                pst.setString(6, txt_f_phone.getText());
                pst.setString(7, txt_f_nid.getText());
                pst.setString(8, (String) cb_gender.getSelectedItem());
                pst.setString(9, txt_email.getText());
                pst.setString(10, spinner_age.getModel().getValue().toString());
                pst.setString(11, (String) cb_post.getSelectedItem());
                pst.setString(12, txt_rlg.getText());
                pst.setString(13, txt_nation.getText());
                pst.setString(14, (String) cb_relation.getSelectedItem());
                pst.setString(15, txt_area_address.getText());
                pst.setBytes(16, picture);
                pst.setString(17, txt_id.getText());
                pst.executeUpdate();

                System.out.println("Update successfully");
            } catch (HeadlessException | SQLException ex) {
                System.out.println(ex);
            }
            showTableData();
                }
            
        });

        //this code belongs to delete button action listener
        btn_delete.addActionListener((ActionEvent e) -> {
            String id = txt_id.getText(); //id field are null than accept if condition another else condition follow
            int p = JOptionPane.showConfirmDialog(null, "Do you really want to delete","Delete",JOptionPane.YES_NO_OPTION);						
	    if(p==0) {
                if (id.equals("")) {

                   System.out.println("No data found");
                } else {
                    try {

                        String sql = "DELETE FROM tbl_admin WHERE ID =?";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, txt_id.getText());
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Delete successfully");

                        //this code belongs to all field blank after click delete button
                        txt_id.setText("");
                        txt_fst_nam.setText("");
                        txt_lst_nam.setText("");
                        txt_fth_nam.setText("");
                        cb_blood.setSelectedItem("Unknown");
                        j_calender_birth.setDate(null);
                        txt_f_phone.setText("");
                        txt_f_nid.setText("");
                        cb_gender.setSelectedItem("Male");
                        txt_email.setText("");
                        spinner_age.setValue(18);
                        cb_post.setSelectedItem("Administrative Officer");
                        txt_rlg.setText("");
                        txt_nation.setText("");
                        cb_relation.setSelectedItem("Married");
                        txt_area_address.setText("");
                        lbl_pic.setIcon(null);
                        table.getSelectionModel().clearSelection();
                        cb_search.setSelectedItem("ID");
                        txt_search.setText("");
                    } catch (SQLException | HeadlessException ex) {
                        System.out.println(ex);
                    }

                }
            
            }
            showTableData();
        });

        //this code belongs to reset button action listener
        btn_reset.addActionListener((ActionEvent resetKey) -> {
            // TODO Auto-generated method stub

            if (resetKey.getSource() == btn_reset) {

                txt_id.setText("");
                txt_fst_nam.setText("");
                txt_lst_nam.setText("");
                txt_fth_nam.setText("");
                cb_blood.setSelectedItem("Unknown");
                j_calender_birth.setDate(null);
                txt_f_phone.setText("");
                txt_f_nid.setText("");
                cb_gender.setSelectedItem("Male");
                txt_email.setText("");
                spinner_age.setValue(18);
                cb_post.setSelectedItem("Administrative Officer");
                txt_rlg.setText("");
                txt_nation.setText("");
                cb_relation.setSelectedItem("Married");
                txt_area_address.setText("");
                lbl_pic.setIcon(null);
                table.getSelectionModel().clearSelection();
                cb_search.setSelectedItem("ID");
                txt_search.setText("");

                System.out.println("All data are reset");

            }
            showTableData();
        });

        //this code belongs to table mouseclick
        table.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                int rows = table.getSelectedRow();

                //this code belongs to all data pass from jtable click and show image data have a store mysql field
                String Table_click = (table.getModel().getValueAt(rows, 0).toString());
                ImageIcon imageicon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbl_pic.getWidth(), lbl_pic.getHeight(), Image.SCALE_SMOOTH));
                lbl_pic.setIcon(imageicon);
                try {

                    String sql = " SELECT IMAGES FROM tbl_admin WHERE ID = '" + Table_click + "' ";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        byte[] imagedata = rs.getBytes("IMAGES");
                        ImageIcon formate = new ImageIcon(imagedata);
                        lbl_pic.setIcon(formate);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                //this code belongs to all data pass from jtable click and show data have a store mysql field
                try {

                    String sql = " SELECT * FROM tbl_admin WHERE ID = '" + Table_click + "' ";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        txt_id.setText(rs.getString("ID"));
                        txt_fst_nam.setText(rs.getString("FIRST_NAME"));
                        txt_lst_nam.setText(rs.getString("LAST NAME"));
                        txt_fth_nam.setText(rs.getString("FATHER'S NAME"));
                        cb_blood.setSelectedItem(rs.getString("BLOOD"));
                        Date cd = rs.getDate("BIRTHDATE");
                        j_calender_birth.setDate(cd);
                        txt_f_phone.setText(rs.getString("PHONE"));
                        txt_f_nid.setText(rs.getString("NID"));
                        cb_gender.setSelectedItem(rs.getString("GENDER"));
                        txt_email.setText(rs.getString("EMAIL"));
                        int age_con = rs.getInt("AGE");
                        spinner_age.setValue(age_con);
                        cb_post.setSelectedItem(rs.getString("POST"));
                        txt_rlg.setText(rs.getString("RELIGION"));
                        txt_nation.setText(rs.getString("NATIONALITY"));
                        cb_relation.setSelectedItem(rs.getString("RELATIONSHIP"));
                        txt_area_address.setText(rs.getString("ADDRESS"));

                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }

        });
        
        //this code belongs to table clickable than show button click
         table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                int[] indexs = table.getSelectedRows();

                //set btn_update & btn_delete clickable if exactly 1 row is selected
                if (indexs.length == 1) {
                    btn_update.setEnabled(true);
                    btn_delete.setEnabled(true);
                } else {
                    btn_update.setEnabled(false);
                    btn_delete.setEnabled(false);
                }
            }
        });

    }

    //this code belongs to method
    //this method code belongs to phone number exception handle
    public final void exhandlePhone() {

        try {
            mask_f_phone = new MaskFormatter("+8801#########"); //this code belongs to handling number input
            //mask.setPlaceholderCharacter('_');
            txt_f_phone = new JFormattedTextField(mask_f_phone);
            //txt_f_phone.setFont(fonts);
            txt_f_phone.setBounds(165, 210, 250, 25);
            panel.add(txt_f_phone);
        } catch (ParseException e1) {

            e1.printStackTrace();
        }

    }

    //this method code belongs to nid number exception handle
    public final void exhandleNid() {
        // TODO Auto-generated method stub
        try {
            mask_f_nid = new MaskFormatter("##########"); //this code belongs to handling number input
            //mask.setPlaceholderCharacter('_');
            txt_f_nid = new JFormattedTextField(mask_f_nid);
            //txt_f_nid.setFont(fonts);
            txt_f_nid.setBounds(165, 240, 250, 25);
            panel.add(txt_f_nid);
        } catch (ParseException e1) {

            e1.printStackTrace();
        }

    }

    //this method code belongs to table data show exception handle
    public final void showTableData() {

        try {
            String sql = "SELECT * FROM tbl_admin ORDER BY ID";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            System.out.println(ex);

        }

    }
    

}
