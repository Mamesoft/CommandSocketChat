package jp.mamesoft.commandsocketchat;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Commandsocketchat {
	public static SocketIO socket;
	public static List<String> dischannels = new ArrayList<String> ();
	public static List<String> disips = new ArrayList<String> ();
	public static List<HashMap<String, String>> logs = new ArrayList<HashMap<String, String>> ();
	public static HashMap<Integer, HashMap<String, String>> roms = new HashMap<Integer, HashMap<String, String>> ();
	public static HashMap<Integer, HashMap<String, String>> users = new HashMap<Integer, HashMap<String, String>> ();
	public static String version = "1.00";
	public static Boolean color;
	public static Boolean in;
	static String osName = System.getProperty("os.name");
	static String osVer = System.getProperty("os.version");
	static String javaver = System.getProperty("java.version");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		String url = "";
		if(args.length == 2){
			if(args[1].equals("Mono")){
				color = false;
			}else if(args[1].equals("Colorful")){
				color = true;
			}else{
				Pattern os_p = Pattern.compile("Windows");
				Matcher os_m = os_p.matcher(osName);
				if(os_m.find()){
					color = false;
				}else{
					color = true;
				}
			}
		}else{
			Pattern os_p = Pattern.compile("Windows");
			Matcher os_m = os_p.matcher(osName);
			if(os_m.find()){
				color = false;
			}else{
				color = true;
			}
		}
		if(args.length < 1){
			url = nourl();
		}else{
			url = args[0];
			if(url.equals("http://")){
				url = nourl();
			}
		}

        Thread thread2 = new NewVersion();
        thread2.start();

    	if(Commandsocketchat.color){
			System.out.print("\u001b[32m");
		}
		System.out.println("何も入力せずEnterキーを押すと接続します");
        System.out.println("/helpで使い方を表示します。");
		while(true) {
	    	if(Commandsocketchat.color){
				System.out.print("\u001b[m");
			}
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			try {
				String str = br.readLine();
				Pattern slash_p = Pattern.compile("^/");
				Matcher slash_m = slash_p.matcher(str);
				if(str.equals("/help")){
					help();
				}else if(str.equals("/version")){

					if(Commandsocketchat.color){
						System.out.print("\u001b[32m");
					}
					System.out.println("CommandSocketChat Version " + Commandsocketchat.version);
					if(Commandsocketchat.color){
						System.out.print("\u001b[m");
					}
					thread2 = new NewVersion();
			        thread2.start();
				}else if(str.equals("/stop")){
					System.exit(0);
				}else if(slash_m.find()){
					System.out.println("コマンドが存在しないかSocketChatに接続しないと使用できないコマンドです。");
					System.out.println("接続前は/help, /version, /stopのみが使用できます。");
				}else{
					break;
				}
			} catch (IOException e2) {
				// TODO 自動生成された catch ブロック
				e2.printStackTrace();
			}
		}
		try {
			Properties headers = new Properties();
			headers.setProperty("user-agent", "CommandSocketChat/" + version + " (" + osName + " " + osVer + ") Java/" + javaver + " (Mamesoft Web)");
			socket = new SocketIO(url, headers);
	        socket.connect(new IOCallback() {
	            @Override
	            public void onMessage(JSONObject json, IOAcknowledge ack) {
	                try {
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	            }
	
	            @Override
	            public void onMessage(String data, IOAcknowledge ack) {
	            }
	
	            @Override
	            public void onError(SocketIOException socketIOException) {
	            	if(Commandsocketchat.color){
	    				System.out.print("\u001b[31m");
	    			}
	    	        System.out.println("エラーが発生しました！");
	    			if(Commandsocketchat.color){
	    				System.out.print("\u001b[m");
	    			}
					System.exit(0);
	            }
	
	            @Override
	            public void onDisconnect() {
	            	if(Commandsocketchat.color){
	    				System.out.print("\u001b[31m");
	    			}
	    	        System.out.println("サーバーから切断されました！");
	    			if(Commandsocketchat.color){
	    				System.out.print("\u001b[m");
	    			}
					System.exit(0);
	            }
	
	            @Override
	            public void onConnect() {
	    	        socket.emit("register", new JSONObject().put("mode", "client").put("lastid", 1));
	            	if(Commandsocketchat.color){
	    				System.out.print("\u001b[32m");
	    			}
	    	        System.out.println("SocketChatに接続しました。");
	    			if(Commandsocketchat.color){
	    				System.out.print("\u001b[m");
	    			}
	    	        Thread thread1 = new Standby();
	    	        thread1.start();
	            }

	            @Override
	            public void on(String event, IOAcknowledge ack, Object... args) {
	            	if (event.equals("log")){
		            	JSONObject jsondata = (JSONObject)args[0];
		            	Logperse(jsondata);
	            	}
	            	if (event.equals("init")){
		            	JSONObject jsondata = (JSONObject)args[0];
		            	JSONArray logs = jsondata.getJSONArray("logs");
		            	for (int i = logs.length() - 1; i >= 0; i--){
		            		JSONObject log = logs.getJSONObject(i);
		            		Logperse(log);
		            	}
	            	}
	            	if (event.equals("result")){
		            	JSONObject jsondata = (JSONObject)args[0];
	            		System.out.println(jsondata);
	            	}
	            	if (event.equals("users")){
		            	JSONObject jsondata = (JSONObject)args[0];
		            	JSONArray users = jsondata.getJSONArray("users");
		            	for (int i = 0; i < users.length(); i++){
		            		JSONObject user = users.getJSONObject(i);
		            		userchange(user);
		            	}
	            	}
	            	if (event.equals("newuser")){
		            	JSONObject jsondata = (JSONObject)args[0];
	            		userchange(jsondata);
	            	}
	            	if (event.equals("inout")){
		            	JSONObject jsondata = (JSONObject)args[0];
	            		userchange(jsondata);
	            	}
	            	if (event.equals("deluser")){
		            	Integer id = (Integer)args[0];
		    			if(users.containsKey(id)){
		    				users.remove(id);
		    			}
		    			if(roms.containsKey(id)){
		    				roms.remove(id);
		    			}
		            	
	            	}
	            	if (event.equals("userinfo")){
		            	JSONObject jsondata = (JSONObject)args[0];
		            	if(jsondata.getBoolean("rom")){
		            		in = false;
		            	}else{
		            		in = true;
		            	}
	            	}
	            }
	        });
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			if(Commandsocketchat.color){
				System.out.print("\u001b[31m");
			}
			System.out.println("URLがおかしいです！");
			if(Commandsocketchat.color){
				System.out.print("\u001b[m");
			}
			return;
		}
	}
	
	static void Logperse(JSONObject jsondata){
		if (!jsondata.isNull("comment")){
    		String name = jsondata.getString("name");
    		String comment = jsondata.getString("comment");
    		String ip = jsondata.getString("ip");
    		String time_js = jsondata.getString("time");
    		Pattern time_p = Pattern.compile("([0-9]{4}).([0-9]{2}).([0-9]{2})T([0-9]{2}):([0-9]{2}):([0-9]{2})");
			Matcher time_m = time_p.matcher(time_js);
			String time = "";
			if(time_m.find()){
				int year = Integer.parseInt(time_m.group(1));
				int month = Integer.parseInt(time_m.group(2));
				int day = Integer.parseInt(time_m.group(3));
				int hour = Integer.parseInt(time_m.group(4));
				int min = Integer.parseInt(time_m.group(5));
				int sec = Integer.parseInt(time_m.group(6));
				hour = hour + 9;
				if(hour >= 24){
					hour = hour - 24;
					day = day + 1;
				}
				time = String.format("%1$04d", year) + "-" + String.format("%1$02d", month) + "-" + String.format("%1$02d", day) + " " + String.format("%1$02d", hour) + ":" + String.format("%1$02d", min) + ":" + String.format("%1$02d", sec);
			}
    		String channel = "";
    		
			Boolean disip = false;
    		Boolean dischannel = false;
			
    		if(disips.contains(ip)){
				disip = true;
			}
    		HashMap<String, String> log = new HashMap<String, String> ();
    		log.put("_id", jsondata.getString("_id"));
    		log.put("comment", comment);
    		log.put("ip", ip);
    		log.put("time", time);
    		if(!jsondata.isNull("response")){
    			log.put("res", jsondata.getString("response"));
    		}else{
    			log.put("res", "");
    		}
        	if (!jsondata.isNull("channel")){
        		HashSet<String> channels_hash = new HashSet<String>();
        		for (int i = 0; i < jsondata.getJSONArray("channel").length(); i++){
        			channels_hash.add(jsondata.getJSONArray("channel").getString(i));
        		}
        		String channels[] = (String[])channels_hash.toArray(new String[0]);
        		for (int i = 0; i < channels.length ; i++){
        			channel = channel + " #" + channels[i];
        			if(dischannels.contains(channels[i])){
        				dischannel = true;
        			}
        		}
        		log.put("channel", channel);
        	}
        	if(!disip && !dischannel){
    			logs.add(log);
    			int id = logs.size() - 1;
        		//名前を出力
				if(Commandsocketchat.color && !jsondata.isNull("syslog")){
					System.out.print("\u001b[34m");
				}
				System.out.print(name + "> ");
				if(Commandsocketchat.color && !jsondata.isNull("syslog")){
					System.out.print("\u001b[m");
				}
				//発言を出力
	        	System.out.print(comment);
	        	//チャネルを出力
	        	if(!channel.equals("")){
        			if(Commandsocketchat.color){
        				System.out.print("\u001b[36m");
        			}
        				System.out.print(channel);
	        	}
	        	//情報を出力
    			if(Commandsocketchat.color){
    				System.out.print("\u001b[37m");
    			}
	        	System.out.print(" (" + id + "; ");
	        	if(!jsondata.isNull("response")){
	        		System.out.print("res; ");
	        	}
	        	System.out.println(ip + "; " + time + ")");
    			if(Commandsocketchat.color){
    				System.out.print("\u001b[m");
    			}
        	}
    	}
	}
	

	static void userchange(JSONObject jsondata){
		int id = jsondata.getInt("id");
		Boolean rom = jsondata.getBoolean("rom");
		String ip = jsondata.getString("ip");
		String ua = jsondata.getString("ua");
		HashMap<String, String> userinfo = new HashMap<String, String> ();
		userinfo.put("ip", ip);
		userinfo.put("ua", ua);
		if(rom){
			roms.put(id, userinfo);
			if(users.containsKey(id)){
				users.remove(id);
			}
		}else{
			String name = jsondata.getString("name");
			userinfo.put("name", name);
			users.put(id, userinfo);
			if(roms.containsKey(id)){
				roms.remove(id);
			}
		}
	}
	
	static String nourl(){
		if(Commandsocketchat.color){
			System.out.print("\u001b[35m");
		}
		System.out.println("接続先のURLを入力してください");
		System.out.print("http://");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		try {
			String url = "http://" + br.readLine();
			if(Commandsocketchat.color){
				System.out.print("\u001b[m");
			}
			return url;
		} catch (IOException e) {
			if(Commandsocketchat.color){
				System.out.print("\u001b[m");
			}
			return "";
		}
	}
	
	public static void help(){

		if(Commandsocketchat.color){
			System.out.print("\u001b[32m");
		}
		System.out.println("/help");
		System.out.println("	コマンドの情報を表示します");
		System.out.println("/stop");
		System.out.println("	プログラムを終了します");
		System.out.println("/in [name]");
		System.out.println("	[name]という名前でチャットに入室します");
		System.out.println("/out");
		System.out.println("	チャットから退室します");
		System.out.println("/res [id]");
		System.out.println("	次の発言を[id](各発言後ろのカッコ内に表示されている1つめの値)の発言への返信にします");
		System.out.println("/res -i [_id]");
		System.out.println("	次の発言を[_id](SocketChatサーバー内MongoID)の発言への返信にします");
		System.out.println("/res -c");
		System.out.println("	返信をキャンセルします");
		System.out.println("/list");
		System.out.println("	接続者のリストを表示します。 -ua を指定することでUserAgentも表示します");
		System.out.println("/open [id]");
		System.out.println("	[id](各発言後ろのカッコ内に表示されている1つめの値)の発言に含まれている全てのURLをブラウザで開きます");
		System.out.println("/open [id] -n [num]");
		System.out.println("	[id](各発言後ろのカッコ内に表示されている1つめの値)の発言に含まれている左から[num]番目のURLをブラウザで開きます");
		System.out.println("/disip [ip]");
		System.out.println("	IPアドレスが[ip]の人の発言を非表示(disip)にします");
		System.out.println("/disip -d [ip]");
		System.out.println("	IPアドレスが[ip]の人のdisipを解除します");
		System.out.println("/dischannel [channel]");
		System.out.println("	[channel]のハッシュタグがつけられた発言を非表示(dischannel)にします");
		System.out.println("/dischannel -d [channel]");
		System.out.println("	ハッシュタグ[channel]のdischannelを解除します");
		System.out.println("/version");
		System.out.println("	プログラムのバージョン情報を表示します");
		if(Commandsocketchat.color){
			System.out.print("\u001b[m");
		}
	}
}