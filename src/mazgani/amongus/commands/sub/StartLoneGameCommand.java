package mazgani.amongus.commands.sub;

import mazgani.amongus.commands.Command;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.maps.TestMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StartLoneGameCommand extends Command {

    private final GamesManager gamesManager;

    public StartLoneGameCommand(String name, GamesManager gamesManager) {
        super(name);

        this.gamesManager = gamesManager;
    }

    @Override
    public String getDescription() {
        return "Among us start lone game command";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String[] getPermissions() {
        return new String[] { "amongus.admin.startlonegame" };
    }

    @Override
    public List<String> getTabList(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!hasPermissions(sender)) {
            sender.sendMessage(ChatColor.RED + "You don't have enough permissions to execute this command.");
            return;
        }

        if(!(sender instanceof Player))
            return;

        Player player = (Player)sender;

        if(requestedTempLobby(sender, false))
            return;

        player.performCommand("amongus createlobby");
        AUGame game = this.gamesManager.createGame(this.tempLobby, new TestMap());
        printGameData(game);
        openWireTaskInventory(game);
    }
}