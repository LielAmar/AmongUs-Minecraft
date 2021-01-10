package mazgani.amongus.commands.sub;

import static java.util.stream.Collectors.toCollection;

import mazgani.amongus.commands.Command;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.players.AUPlayer;
import mazgani.amongus.players.AUPlayersManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AddCommand extends Command {

    private final AUPlayersManager auPlayersManager;

    public AddCommand(String name, AUPlayersManager auPlayersManager, GameLobby tempLobby) {
        super(name, tempLobby);

        this.auPlayersManager = auPlayersManager;
    }

    @Override
    public String getDescription() {
        return "Among us add command";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String[] getPermissions() {
        return new String[] { "amongus.admin.add" };
    }

    @Override
    public List<String> getTabList(CommandSender sender, String[] args) {
        if(this.tempLobby == null)
            return new ArrayList<>();

        List<String> onlinePlayersNames = Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(toCollection(LinkedList::new));

        //if the sender is a player - they must've been in the game - so their name is excluded
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            onlinePlayersNames.remove(player.getName());
        }
        return onlinePlayersNames;
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

        if(requestedTempLobby(sender, true))
        {
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null)
        {
            player.sendMessage(ChatColor.RED + args[1] + " is not online!");
            return;
        }

        AUPlayer targetAUPlayer = this.auPlayersManager.getPlayer(target.getUniqueId());
        this.tempLobby.addPlayer(targetAUPlayer);
        target.teleport(this.tempLobby.getSpawnLocation());

        player.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.GREEN + " was sent to Lobby " + shortenGameID(this.tempLobby.getUUID().toString()));
        target.sendMessage(ChatColor.GREEN + "You were sent to Lobby " + shortenGameID(this.tempLobby.getUUID().toString()));

        AUGame game = this.tempLobby.getCurrentGame();

        if(game == null)
            return;

        printGameData(game);
        openWireTaskInventory(game);
    }
}