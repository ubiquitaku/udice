package ubiquitaku.udice;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Udice extends JavaPlugin {
    String prefix = "§a§lUDICE§r§b";
    Random r = new Random();
    boolean ud = false;
    List<Player> list = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equals("ud")) {
            if (args.length == 0) {
                sender.sendMessage(prefix+"\n/ud j : udに参加します");
                if (sender.isOp()) {
                    sender.sendMessage("----------------isOp-----------------");
                    sender.sendMessage("/ud start : udを開始します");
                    sender.sendMessage("/ud stop : ud中に何か想定外の事があった時などにudを中止します");
                    sender.sendMessage("/ud end  : udの結果を出します");
                    sender.sendMessage("-----------------------------------------");
                }
                return true;
            }
            if (args[0].equals("j")) {
                if (!(ud)) {
                    sender.sendMessage(prefix+"ud中ではありません");
                    return true;
                }
                if (list.contains((Player)sender)) {
                    sender.sendMessage(prefix+"あなたは既に参加しています");
                    return true;
                }
                list.add((Player)sender);
                sender.sendMessage(prefix+"参加しました あなたは"+list.size()+"番目です");
                return true;
            }
            if (!(sender.isOp())) {
                return true;
            }
            if (args[0].equals("start")) {
                if (ud) {
                    sender.sendMessage(prefix+"別のudが行われているため始める前に終了してください");
                    return true;
                }
                ud = true;
                Bukkit.broadcastMessage(prefix+sender.getName()+"がudを開始しました\n/ud j で参加できます");
                return true;
            }
            if(args[0].equals("stop")) {
                if (!(ud)) {
                    sender.sendMessage(prefix+"現在udは行われていません");
                    return true;
                }
                ud = false;
                list.clear();
                Bukkit.broadcastMessage(prefix+"udを中断しました");
                return true;
            }
            if (args[0].equals("end")) {
                if (!(ud)) {
                    sender.sendMessage(prefix+"現在udは行われていません");
                    return true;
                }
                ud = false;
                if (list.size() == 0) {
                    Bukkit.broadcastMessage(prefix+"今回のudの参加者はいませんでした");
                    return true;
                }
                int t = r.nextInt(list.size());
                Bukkit.broadcastMessage(prefix+sender.getName()+"がダイスを回して"+(t+1)+"が出た");
                Bukkit.broadcastMessage(prefix+"当選者は"+(t+1)+"番目に参加した"+list.get(t).getName());
                list.clear();
                return true;
            }
        }
        return true;
    }
}
