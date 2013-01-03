package ClientAndServer;

import java.net.*;

import java.io.*;

public class Server

{

	private ServerSocket ss;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	public Server()
	{
		System.out.println("server running");
		try
		{
			ss = new ServerSocket(8888);
			while (true)
			{
				socket = ss.accept();
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				String line = in.readLine();
				out.println("you input is :" + line);
				out.close();
				in.close();
				socket.close();
			}
		}
		catch (IOException e)
		{
		}
	}
	public static void main(String[] args)
	{
		new Server();
	}

}