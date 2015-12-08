import java.io.IOException;
import java.net.UnknownHostException;


import mw.updateall.server.*;


//Every one else caller
public class main 
{
	public static void main(String[] args) 
	{
		
		Server s = Server.getInstance();
		
		
		try {
			s.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	
	

}
