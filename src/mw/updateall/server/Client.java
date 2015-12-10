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
	public int playerSlot;
	
	public Client(Socket s, int id)
	{
		this.socket = s;
		try 
		{
			this.in = new Scanner(socket.getInputStream());
			this.playerName = in.nextLine(); //potentially unsafe...
			this.playerSlot =  Integer.parseInt(in.nextLine());
			System.out.printf("received name : %s and slot %d \n",this.playerName, this.playerSlot);
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
					String msg = String.format("%s", line);
					System.out.println(String.format("client: %s [%s:%d]: sent data ",this.playerName ,socket.toString(),id));
					if(line.toLowerCase().equals("###"))
					{	
						this.running = false;
						break;
					}
					else if(line.toLowerCase().equals("give players"))
					{
						Server.getInstance().sendMessageToAll(Server.getInstance().getPlayerList());
						Server.getInstance().sendMessageToClient(Server.getInstance().clients.get(0), "become leader");
					}
					else
					{
						Server.getInstance().sendMessageToAll(msg);
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
