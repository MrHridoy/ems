import java.sql.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginClass {

    private  JFrame frame;
    private  JPanel panel_login;
    private JLabel lbl_nam, lbl_pass;
    private JTextField txt_nam;
    private JPasswordField txt_password;
    private JButton btn_login, btn_clear;
    private final Image login;
    private final Image clear;
    private final Cursor select;
    private final Color clrB;
    private final ImageIcon home;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public LoginClass() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        conn = DatabaseClass.ConnectDB(); //this code belongs to database connected

         //this code belongs to jframe
        frame = new JFrame("Login");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
       

        //adding image for jframe image icon
        home = new ImageIcon(getClass().getResource("home.png"));
        frame.setIconImage(home.getImage());
        
        //this code belongs to image file set button
        login = new ImageIcon(this.getClass().getResource("login.png")).getImage();
        clear = new ImageIcon(this.getClass().getResource("clear.png")).getImage();
        
        

        //this code belongs to set color this is use to panel
        clrB = new Color(60, 179, 113);

         //this code belongs to main panel set by jframe
        panel_login = new JPanel();
        panel_login.setSize(800, 500);
        panel_login.setVisible(true);
        panel_login.setLayout(null);
        panel_login.setBackground(clrB);
        frame.add(panel_login);
        
         

        //this code belongs to set mouse cursor
        select = new Cursor(Cursor.HAND_CURSOR);
        
        
        
         //this is method 
        iniComponent();
        

    }

    public final void iniComponent() {
        
       

       

        //this code belongs to email label set by panel
        lbl_nam = new JLabel("User Name");
        lbl_nam.setBounds(200, 110, 200, 20);
        lbl_nam.setFont(new Font("Dialog", Font.BOLD, 20));
        panel_login.add(lbl_nam);

        //this code belongs to email text field set by panel
        txt_nam = new JTextField("Write User Name");
        txt_nam.setBounds(320, 100, 350, 40);
        txt_nam.setFont(new Font("Dialog", Font.BOLD, 15));
        panel_login.add(txt_nam);
        
        //this code belongs to password label set by panel
        lbl_pass = new JLabel("Password");
        lbl_pass.setBounds(200, 190, 200, 20);
        lbl_pass.setFont(new Font("Dialog", Font.BOLD, 20));
        panel_login.add(lbl_pass);
        
        
         //this code belongs to jpassword field set by panel
        txt_password = new JPasswordField("Password");
        txt_password.setBounds(320, 180, 350, 40);
        txt_password.setFont(new Font("Dialog", Font.BOLD, 15));
        panel_login.add(txt_password);
        
        
        //this code belongs to set login button
        btn_login = new JButton();
        btn_login.setBounds(320, 280, 130, 45);
        btn_login.setIcon(new ImageIcon(login));
        btn_login.setCursor(select); //this code belongs to button select than change mouse cursor
        panel_login.add(btn_login);
       
        
        
        btn_clear = new JButton();
        btn_clear.setBounds(550, 280, 130, 45);
        btn_clear.setIcon(new ImageIcon(clear));
        btn_clear.setCursor(select);    //this code belongs to button select than change mouse cursor
        panel_login.add(btn_clear);
        

        //this code belongs to set a email text field listener
        txt_nam.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

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
                if (txt_nam.getText().equals("Write User Name")) {
                    txt_nam.setText("");
                }
            }
        });

       

       

        //this code belongs to password field action listener
        txt_password.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

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
                
                txt_password.setText("");
            }
        });

        

        
        
        btn_login.addActionListener((ActionEvent ae) -> {
            String query = "SELECT * FROM `tbl_login` WHERE NAME=? AND PASSWORD=?";
            
            try {
                pst = conn.prepareStatement(query);
                pst.setString(1, txt_nam.getText());
                pst.setString(2, txt_password.getText());
                
                rs = pst.executeQuery();
                
                int count = 0;
                while (rs.next()) {
                    count = count + 1;
                }
                
                if (count == 1) {
                    
                    JOptionPane.showMessageDialog(null, "Logged in successfully");
                    EmployeeClass emplyeeInfo = new EmployeeClass();
                    frame.dispose();
                    
                } else if (count > 1) {
                    JOptionPane.showMessageDialog(null, "Duplicate User Name & Password");
                } else {
                    JOptionPane.showMessageDialog(null, "User Name & password is not correct");
                }
                rs.close();
                pst.close();
            } catch (HeadlessException | SQLException e1) {
                System.out.println(e1);
                
            } //To change body of generated methods, choose Tools | Templates.
        });
        
        
        

        //this code belongs to clear buuton action listener
        btn_clear.addActionListener((ActionEvent e) -> {
            // TODO Auto-generated method stub
            txt_nam.setText("");
            txt_password.setText("");
        });
    }

}
