package mazgani.amongus.commands;

import mazgani.amongus.AmongUs;
import mazgani.amongus.commands.sub.*;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.players.AUPlayersManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class AmongUSCommand implements CommandExecutor, TabCompleter {

    private final Set<Command> commands;

    private final String mainCommand = "amongus";

    private final LobbiesManager lobbiesManager;
    private final GamesManager gamesManager;
    private final AUPlayersManager auPlayersManager;

    private GameLobby tempLobby;

    public AmongUSCommand(LobbiesManager lobbiesManager, GamesManager gamesManager, AUPlayersManager auPlayersManager)
    {
        this.lobbiesManager = lobbiesManager;
        this.gamesManager = gamesManager;
        this.auPlayersManager = auPlayersManager;

        AmongUs.getInstance().getCommand(mainCommand).setTabCompleter(this);
        AmongUs.getInstance().getCommand(mainCommand).setExecutor(this);

        this.commands = new HashSet<>();
        this.setupCommands();
    }

    public void setupCommands()
    {
        final String testCommand = "test";
        final String addCommand = "add";
        final String createlobbyCommand = "createlobby";
        final String startlonegameCommand = "startlonegame";
        final String helpCommand = "help";

        this.commands.add(new TestCommand(testCommand));
        this.commands.add(new AddCommand(addCommand, this.auPlayersManager, this.tempLobby));
        this.commands.add(new CreateLobbyCommand(createlobbyCommand, this.lobbiesManager, this.auPlayersManager, this.tempLobby));
        this.commands.add(new StartLoneGameCommand(startlonegameCommand, this.gamesManager));
        this.commands.add(new HelpCommand(helpCommand, this.commands));
    }

    public Command getCommand(String name)
    {
        for(Command cmd : this.commands) {
            if(cmd.getName().equalsIgnoreCase(name))
                return cmd;

            for(String s : cmd.getAliases()) {
                if(s.equalsIgnoreCase(name))
                    return cmd;
            }
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase(mainCommand)) {
            if(!sender.isOp())
            {
                sender.sendMessage(ChatColor.RED + "You don't have enough permissions to execute this command.");
                return false;
            }

            if(args.length == 0)
            {
                getCommand("help").execute(sender, null);
                return false;
            }

            Command cmd = getCommand(args[0]);
            if(cmd == null)
            {
                getCommand("help").execute(sender, null);
                return false;
            }

            String[] newArguments = new String[args.length - 1];
            System.arraycopy(args, 1, newArguments, 0, newArguments.length);

            cmd.execute(sender, newArguments);
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase(mainCommand))
        {
            if(!sender.isOp())
                return null;

            switch(args.length) {
                case 0:
                    break;
                case 1:
                    List<String> list = new ArrayList<>();
                    this.commands.forEach(subCmd -> list.add(subCmd.getName()));
                    return list;
                case 2:
                    Command cmd = getCommand(args[0]);
                    if(cmd == null)
                        return null;

                    return cmd.getTabList(sender, args);
            }
        }
        return null;
    }
}
