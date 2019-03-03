
import javax.swing.JFrame;




public class Main {

	public static void main(String[] args) {           
		LoadPage loadPage=new LoadPage();
                loadPage.setVisible(true);
                
                
   try{
                            for(int i=0;i<=100;i++)
                            {
                            Thread.sleep(25);   //increment progressBar value after every 25ms
                            loadPage.loadingbar.setValue(i);
                            loadPage.loadingnum.setText(Integer.toString(i)+"%");                             
                              if(i==100){
                                  
                                  loadPage.setVisible(false);
                                  loadPage.dispose();
                                 LoginClass log=new LoginClass();
                                 
                        
                              }
                              
                            
                            }
                          
                         
                         
                        
                         
                         
                     }
                        catch(Exception ex){
                            System.out.println(ex);
                     }

	}

}
