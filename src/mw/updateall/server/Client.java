package mw.updateall.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable
{
	private Scanner in;
	public Socket socket;
	private Boolean running;
	private int id;
	public String playerName; //id
	
	public Client(Socket s, int id)
	{
		this.socket = s;
		try 
		{
			this.in = new Scanner(socket.getInputStream());
			this.playerName = in.nextLine(); //potentially unsafe...
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.running = true;
		this.id =id;
	}
	public void  end()
	{
		running = false;
	}
	@Override
	public void run() 
	{
		System.out.println("as");
		try {
			in = new Scanner(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(this.running)
		{
			System.out.println("running");
				if  (in.hasNextLine())
				{
					String line = in.nextLine();
					String msg = String.format("client: %s [%d] : %s",this.playerName, this.id, line);
					System.out.println(String.format("%s [%d]: %s", socket.toString(),id,line));
					Server.getInstance().sendMessageToAll(msg);
					if(line.toLowerCase().equals("###"))
					{	
						this.running = false;
						break;
					}
					System.out.println("stood");	
				}
				else
				{
					this.running = false;
					break;
				}
				try 
				{
					Thread.sleep(1000);                 //1000 milliseconds is one second.
				} 
				catch(InterruptedException ex) 
				{
			   
				}
		}
		in.close();
		System.out.println("fuck");
		Server.getInstance().removeClient(this);
		this.end();
		
		
		
	}

}
