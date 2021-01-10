package mazgani.amongus.commands.sub;

import mazgani.amongus.commands.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HelpCommand extends Command {

    private Set<Command> commands;

    public HelpCommand(String name, Set<Command> commands) {
        super(name);

        this.commands = commands;
    }

    @Override
    public String getDescription() {
        return "All of among us admin commands";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String[] getPermissions() {
        return new String[] { "amongus.admin.help" };
    }

    @Override
    public List<String> getTabList(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!hasPermissions(sender)) {
            sender.sendMessage(ChatColor.RED + "You don't have enough permissions to execute this command.");
            return;
        }

        sender.sendMessage(ChatColor.GRAY + "---- " + ChatColor.AQUA + "Among Us Help" + ChatColor.GRAY + " ----");
        this.commands.forEach(cmd -> {
            if(cmd.hasPermissions(sender)) {
                sender.sendMessage(ChatColor.AQUA + "• " + cmd.getName() + ChatColor.GRAY + ": " + cmd.getDescription());
                sender.sendMessage(ChatColor.AQUA + "Aliases " + Arrays.toString(cmd.getAliases()));
            }
        });
        sender.sendMessage(ChatColor.GRAY + "---- --------------- ----");
    }
}