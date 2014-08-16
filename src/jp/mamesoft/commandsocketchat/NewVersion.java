package jp.mamesoft.commandsocketchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class NewVersion extends Thread {
	public void run(){
		try {
            URL url = new URL("http://mamesoft.jp/soft/commandsocketchat/latestversion.php");
            Object content = url.getContent();
            if (content instanceof InputStream) {
                BufferedReader bf = new BufferedReader(new InputStreamReader( (InputStream)content) );
                String latestversion_str = bf.readLine();
                double version = Double.parseDouble(Commandsocketchat.version);
                double latestversion = Double.parseDouble(latestversion_str);
                if(version < latestversion){
					if(Commandsocketchat.color){
						System.out.print("\u001b[31m");
					}
					System.out.println("新しいバージョンが公開されています！");
					System.out.println("http://mamesoft.jp/soft/commandsocketchat/");
					if(Commandsocketchat.color){
						System.out.print("\u001b[m");
					}
                }
            }
        }
        catch (IOException e) {
        	
        }
	}
}
