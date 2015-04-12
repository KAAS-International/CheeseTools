package main.java.com.kaasintl.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by David on 12-4-2015.
 * Contains all kinds of server-screwing goodness
 */
public class Main
{
	public static int SESSIONCOUNT = 1000;
	public String host;
	public int port;
	public ArrayList<Socket>      sessions = new ArrayList<Socket>();
	public ArrayList<PrintWriter> outs     = new ArrayList<PrintWriter>();
	public Random                 random   = new Random(System.nanoTime());

	public Main()
	{
		this.host = "localhost";
		this.port = 7789;

		ready();
		charge();
		fire();
	}

	public Main(String host, int port)
	{
		this.host = host;
		this.port = port;

		ready();
		charge();
		fire();
	}

	public static void main(String args[])
	{
		if (args.length > 1)
		{
			Main main = new Main(args[0], Integer.parseInt(args[1]));
		} else
		{
			Main main = new Main();
		}
	}

	public void ready()
	{
		for (int i = 0; i < SESSIONCOUNT; i++)
		{
			try
			{
				sessions.add(new Socket(host, port));
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public void charge()
	{
		for (Socket session : sessions)
		{
			try
			{
				outs.add(new PrintWriter(session.getOutputStream(), true));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void fire()
	{
		for (PrintWriter out : outs)
		{
			String data = "login " + random.nextInt();
			System.out.println(data);
			out.write(data + "\n");
		}

		while (true)
		{
			for (PrintWriter out : outs)
			{
				out.write(random.nextLong() + random.nextLong() + "\n");
			}
		}
	}
}
