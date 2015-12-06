package mw.updateall.server;

import java.net.*; 
import java.io.*; 
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server 
{

	int socketNumber;
	List<Client> clients;
	ServerSocket serverSocket;
	
	private static Server instance;
	public static Server getInstance()
	{
		if(instance == null)
			instance = new Server(5507);
		return instance;	
	}
	
	private Server(int socketNumber)
	{
		this.socketNumber = socketNumber;
		this.clients = new CopyOnWriteArrayList<Client>();
	}
	public void start() throws IOException 
	{
		System.out.println("main server");
		serverSocket = new ServerSocket(this.socketNumber); 
		System.out.printf("Porta %d aberta!\n",this.socketNumber);
		int id = 0; //id no servidor
		do 
		{
			Socket clientSocket = serverSocket.accept();
			Client client = new Client(clientSocket,id);
			System.out.println("New Client connected" + clientSocket.getInetAddress().getHostAddress());
			Thread t = new Thread(client);
			t.start();
			this.clients.add(client);
			id = this.clients.size();
			System.out.println(id);
		}while(this.clients.size() > 0);
		this.shutdown();
	}
	public void shutdown() throws IOException
	{
		serverSocket.close(); 
		for(Client client : clients)
		{
			client.socket.close();
		}
		System.out.println("O servidor terminou de executar!");
		
	}
	public void removeClient(Client client)
	{
		this.clients.remove(client);
		System.out.printf("removed %s \n",client.toString());
	}
	void sendMessageToAll(String msg) 
	{
		for (Client client : clients)
		{
			PrintStream out_cli = null;
			try 
			{
				if(client.socket.isClosed())
				{
					this.clients.remove(client);
					System.out.printf("removed %s \n",client.toString());
				}
				else
				{
					out_cli = new PrintStream(client.socket.getOutputStream());
					out_cli.println(msg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}

		}
}



