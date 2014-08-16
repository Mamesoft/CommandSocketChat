package jp.mamesoft.commandsocketchat;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.*;

import org.json.JSONObject;

public class Standby extends Thread {
	Boolean in = false;
	String res = "";
	String ch = "";
	public void run(){
			while(true) {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			try {
				String str = br.readLine();
				Pattern in_p = Pattern.compile("^/in (.*)");
				Matcher in_m = in_p.matcher(str);
				Pattern out_p = Pattern.compile("^/out$");
				Matcher out_m = out_p.matcher(str);
				Pattern list_p = Pattern.compile("^/list$");
				Matcher list_m = list_p.matcher(str);
				Pattern list_ua_p = Pattern.compile("^/list -ua$");
				Matcher list_ua_m = list_ua_p.matcher(str);
				Pattern res_p = Pattern.compile("^/res ([0-9]*)");
				Matcher res_m = res_p.matcher(str);
				Pattern res_i_p = Pattern.compile("^/res -i (.*)");
				Matcher res_i_m = res_i_p.matcher(str);
				Pattern res_c_p = Pattern.compile("^/res -c");
				Matcher res_c_m = res_c_p.matcher(str);
				Pattern ch_p = Pattern.compile("^/ch (.*)");
				Matcher ch_m = ch_p.matcher(str);
				Pattern ch_c_p = Pattern.compile("^/ch -c");
				Matcher ch_c_m = ch_c_p.matcher(str);
				Pattern ch_v_p = Pattern.compile("^/ch -v");
				Matcher ch_v_m = ch_v_p.matcher(str);
				Pattern open_p = Pattern.compile("^/open ([0-9]*)");
				Matcher open_m = open_p.matcher(str);
				Pattern open_n_p = Pattern.compile("^/open ([0-9]*) -n ([0-9]*)");
				Matcher open_n_m = open_n_p.matcher(str);
				Pattern dischannel_d_p = Pattern.compile("^/dischannel -d (.*)");
				Matcher dischannel_d_m = dischannel_d_p.matcher(str);
				Pattern dischannel_p = Pattern.compile("^/dischannel (.*)");
				Matcher dischannel_m = dischannel_p.matcher(str);
				Pattern dischannel_l_p = Pattern.compile("^/dischannel$");
				Matcher dischannel_l_m = dischannel_l_p.matcher(str);
				Pattern disip_d_p = Pattern.compile("^/disip -d (.*)");
				Matcher disip_d_m = disip_d_p.matcher(str);
				Pattern disip_p = Pattern.compile("^/disip (.*)");
				Matcher disip_m = disip_p.matcher(str);
				Pattern disip_l_p = Pattern.compile("^/disip$");
				Matcher disip_l_m = disip_l_p.matcher(str);
				Pattern stop_p = Pattern.compile("^/stop$");
				Matcher stop_m = stop_p.matcher(str);
				Pattern help_p = Pattern.compile("^/help$");
				Matcher help_m = help_p.matcher(str);
				Pattern version_p = Pattern.compile("^/version$");
				Matcher version_m = version_p.matcher(str);
				Pattern slash_p = Pattern.compile("^/");
				Matcher slash_m = slash_p.matcher(str);
				if(in_m.find()){
					if(!Commandsocketchat.in){
						Commandsocketchat.socket.emit("inout", new JSONObject().put("name", in_m.group(1)));
					}else{
						if(Commandsocketchat.color){
							System.out.print("\u001b[31m");
						}
						System.out.println("すでに入室しています！");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}else if(out_m.find()){
					if(Commandsocketchat.in){
						Commandsocketchat.socket.emit("inout", new JSONObject().put("name", ""));
					}else{
						if(Commandsocketchat.color){
							System.out.print("\u001b[31m");
						}
						System.out.println("未入室です！");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}else if(list_m.find()){
					int userint = Commandsocketchat.users.size();
					int romint = Commandsocketchat.roms.size();
					if(Commandsocketchat.color){
						System.out.print("\u001b[35m");
					}
					System.out.println("入室者: " + userint + " ROM: " + romint);
					System.out.println("＜入室者＞");
					if(Commandsocketchat.color){
						System.out.print("\u001b[32m");
					}
					for( Integer id : Commandsocketchat.users.keySet()){
						HashMap<String, String> data = Commandsocketchat.users.get(id);
						System.out.print(data.get("name"));
						if(Commandsocketchat.color){
							System.out.print("\u001b[37m");
						}
						System.out.println(" (" + data.get("ip") + ")");
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
					}
					if(Commandsocketchat.color){
						System.out.print("\u001b[35m");
					}
					System.out.println("＜ROM＞");
					if(Commandsocketchat.color){
						System.out.print("\u001b[32m");
					}
					for( Integer id : Commandsocketchat.roms.keySet()){
						HashMap<String, String> data = Commandsocketchat.roms.get(id);
						System.out.println(data.get("ip"));
					}
					if(Commandsocketchat.color){
						System.out.print("\u001b[m");
					}
				}else if(list_ua_m.find()){
					int userint = Commandsocketchat.users.size();
					int romint = Commandsocketchat.roms.size();
					if(Commandsocketchat.color){
						System.out.print("\u001b[35m");
					}
					System.out.println("入室者: " + userint + " ROM: " + romint);
					System.out.println("＜入室者＞");
					if(Commandsocketchat.color){
						System.out.print("\u001b[32m");
					}
					for( Integer id : Commandsocketchat.users.keySet()){
						HashMap<String, String> data = Commandsocketchat.users.get(id);
						System.out.print(data.get("name"));
						if(Commandsocketchat.color){
							System.out.print("\u001b[37m");
						}
						System.out.println(" (" + data.get("ip") + "; " + data.get("ua") + ")");
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
					}
					if(Commandsocketchat.color){
						System.out.print("\u001b[35m");
					}
					System.out.println("＜ROM＞");
					if(Commandsocketchat.color){
						System.out.print("\u001b[32m");
					}
					for( Integer id : Commandsocketchat.roms.keySet()){
						HashMap<String, String> data = Commandsocketchat.roms.get(id);
						System.out.print(data.get("ip"));
						if(Commandsocketchat.color){
							System.out.print("\u001b[37m");
						}
						System.out.println(" (" + data.get("ua") + ")");
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
					}
					if(Commandsocketchat.color){
						System.out.print("\u001b[m");
					}
				}else if(res_c_m.find()){
					if(!res.equals("")){
						res = "";
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
						System.out.println("返信をキャンセルしました");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}else{
						if(Commandsocketchat.color){
							System.out.print("\u001b[31m");
						}
						System.out.println("返信先は指定されていません。");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}else if(res_i_m.find()){
					res = res_i_m.group(1);
					if(Commandsocketchat.color){
						System.out.print("\u001b[32m");
					}
					System.out.println("次の発言はid:" + res + " の発言に返信します");
					if(Commandsocketchat.color){
						System.out.print("\u001b[m");
					}
				}else if(res_m.find()){
					try{
						int id = Integer.parseInt(res_m.group(1));
						if(id < Commandsocketchat.logs.size()){
							res = Commandsocketchat.logs.get(id).get("_id");
							if(Commandsocketchat.color){
								System.out.print("\u001b[32m");
							}
							System.out.println("次の発言は" + id + "番の発言に返信します");
							if(Commandsocketchat.color){
								System.out.print("\u001b[m");
							}
						}else{
							if(Commandsocketchat.color){
								System.out.print("\u001b[31m");
							}
							System.out.println("そのIDの発言はありません");
							if(Commandsocketchat.color){
								System.out.print("\u001b[32m");
							}
						}
					}catch(NumberFormatException e){
						
					}
				}else if(ch_v_m.find()){
					if(!ch.equals("")){
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
						System.out.println("現在、#" + ch + "が設定されています。");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}else{
						System.out.println("チャネルは設定されていません！");
					}
				}else if(ch_c_m.find()){
					if(!ch.equals("")){
						ch = "";
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
						System.out.println("チャネルを解除しました。");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}else{
						System.out.println("チャネルは設定されていません！");
					}
				}else if(ch_m.find()){
					ch = ch_m.group(1);
					if(Commandsocketchat.color){
						System.out.print("\u001b[32m");
					}
					System.out.println("次以降の発言は#" + ch + " に属します。解除するには/ch -cを入力してください。");
					if(Commandsocketchat.color){
						System.out.print("\u001b[m");
					}
				}else if(open_n_m.find()){
					try{
						int id = Integer.parseInt(open_n_m.group(1));
						if(id < Commandsocketchat.logs.size()){
							String comment = Commandsocketchat.logs.get(id).get("comment");
							Pattern url_p = Pattern.compile("(http|https):([^\\x00-\\x20()\"<>\\x7F-\\xFF])*");
						    Matcher url_m = url_p.matcher(comment);
						       try {
								    List<String> urls = new ArrayList<String>();
								    while(url_m.find()){
								    	urls.add(url_m.group());
								    }
								    int num = Integer.parseInt(open_n_m.group(2));
								    if(urls.size() >= num && num != 0){
								    	Desktop desktop = Desktop.getDesktop();
								    	desktop.browse(new URI(urls.get(num - 1)));
								    	if(Commandsocketchat.color){
											System.out.print("\u001b[32m");
										}
								    	System.out.println("ブラウザを起動します...");
										if(Commandsocketchat.color){
											System.out.print("\u001b[m");
										}
								    }else{
								    	if(Commandsocketchat.color){
											System.out.print("\u001b[31m");
										}
								    	System.out.println("その発言に" + num + "番目のURLはありません！");
										if(Commandsocketchat.color){
											System.out.print("\u001b[m");
										}
								    }
						        } catch (URISyntaxException ex) {
						        } catch (IOException ex) {
						        }
						}else{
							if(Commandsocketchat.color){
								System.out.print("\u001b[31m");
							}
							System.out.println("そのIDの発言はありません！");
							if(Commandsocketchat.color){
								System.out.print("\u001b[32m");
							}
						}
					}catch(NumberFormatException e){
						
					}
				}else if(open_m.find()){
					try{
						int id = Integer.parseInt(open_m.group(1));
						if(id < Commandsocketchat.logs.size()){
							String comment = Commandsocketchat.logs.get(id).get("comment");
							Pattern url_p = Pattern.compile("(http|https):([^\\x00-\\x20()\"<>\\x7F-\\xFF])*");
						    Matcher url_m = url_p.matcher(comment);
						       try {
								    List<String> urls = new ArrayList<String>();
								    while(url_m.find()){
								    	urls.add(url_m.group());
								    }
								    if(urls.size() != 0){
								    	Desktop desktop = Desktop.getDesktop();
								    	for (int i = 0; i < urls.size(); i++){
									    	desktop.browse(new URI(urls.get(i)));
								    	}
								    	if(Commandsocketchat.color){
											System.out.print("\u001b[32m");
										}
								    	System.out.println("ブラウザを起動します...");
										if(Commandsocketchat.color){
											System.out.print("\u001b[m");
										}
								    }else{
								    	if(Commandsocketchat.color){
											System.out.print("\u001b[31m");
										}
								    	System.out.println("その発言にはURLが含まれていません！");
										if(Commandsocketchat.color){
											System.out.print("\u001b[m");
										}
								    }
						        } catch (URISyntaxException ex) {
						        } catch (IOException ex) {
						        }
						}else{
							if(Commandsocketchat.color){
								System.out.print("\u001b[31m");
							}
							System.out.println("そのIDの発言はありません！");
							if(Commandsocketchat.color){
								System.out.print("\u001b[32m");
							}
						}
					}catch(NumberFormatException e){
						
					}
				}else if(dischannel_d_m.find()){
					if(Commandsocketchat.dischannels.contains(dischannel_d_m.group(1))){
						int i = Commandsocketchat.dischannels.indexOf(dischannel_d_m.group(1));
						Commandsocketchat.dischannels.remove(i);
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
						System.out.println("チャネル #" + dischannel_d_m.group(1) + "のdischannelを解除しました");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}else{
						if(Commandsocketchat.color){
							System.out.print("\u001b[31m");
						}
						System.out.println("そのチャネルはdischannelされていません！");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}else if(dischannel_m.find()){
					if(!Commandsocketchat.dischannels.contains(dischannel_m.group(1))){
						Commandsocketchat.dischannels.add(dischannel_m.group(1));
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
						System.out.println("チャネル #" + dischannel_m.group(1) + "をdischannelしました");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}else{
						if(Commandsocketchat.color){
							System.out.print("\u001b[31m");
						}
						System.out.println("そのチャネルはすでにdischannelされています！");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}else if(dischannel_l_m.find()){
					for (int i = 0; i < Commandsocketchat.dischannels.size(); i++){
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
						System.out.println(Commandsocketchat.dischannels.get(i));
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}else if(disip_d_m.find()){
					if(Commandsocketchat.disips.contains(disip_d_m.group(1))){
						int i = Commandsocketchat.disips.indexOf(disip_d_m.group(1));
						Commandsocketchat.disips.remove(i);
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
						System.out.println("IPアドレス" + disip_d_m.group(1) + "のdisipを解除しました");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}else{
						if(Commandsocketchat.color){
							System.out.print("\u001b[31m");
						}
						System.out.println("そのIPアドレスはdisipされていません！");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}else if(disip_m.find()){
					if(!Commandsocketchat.disips.contains(disip_m.group(1))){
						Commandsocketchat.disips.add(disip_m.group(1));
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
						System.out.println("IPアドレス" + disip_m.group(1) + "をdisipしました");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}else{
						if(Commandsocketchat.color){
							System.out.print("\u001b[31m");
						}
						System.out.println("そのIPアドレスはすでにdisipされています！");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}else if(disip_l_m.find()){
					for (int i = 0; i < Commandsocketchat.disips.size(); i++){
						if(Commandsocketchat.color){
							System.out.print("\u001b[32m");
						}
						System.out.println(Commandsocketchat.disips.get(i));
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}else if(stop_m.find()){
					System.exit(0);
				}else if(help_m.find()){
					Commandsocketchat.help();
				}else if(version_m.find()){
					if(Commandsocketchat.color){
						System.out.print("\u001b[32m");
					}
					System.out.println("CommandSocketChat Version " + Commandsocketchat.version);
					if(Commandsocketchat.color){
						System.out.print("\u001b[m");
					}
			        Thread thread2 = new NewVersion();
			        thread2.start();
				}else if(slash_m.find()){
					if(Commandsocketchat.color){
						System.out.print("\u001b[31m");
					}
					System.out.println("コマンドが存在しないか書式が間違っています。");
					System.out.println("コマンドの使い方は/helpで表示されます。");
					if(Commandsocketchat.color){
						System.out.print("\u001b[m");
					}
				}else{
					if(Commandsocketchat.in){
						JSONObject data = new JSONObject();
						data.put("comment", str);
						if(!res.equals("")){
							data.put("response", res);
							res = "";
						}
						if(!ch.equals("")){
							data.put("channel", ch);
						}
						Commandsocketchat.socket.emit("say", data);
					}else{
						if(Commandsocketchat.color){
							System.out.print("\u001b[31m");
						}
						System.out.println("入室してください！");
						if(Commandsocketchat.color){
							System.out.print("\u001b[m");
						}
					}
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
}
