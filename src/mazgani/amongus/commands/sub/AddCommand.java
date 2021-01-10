package mazgani.amongus.commands.sub;

import mazgani.amongus.commands.Command;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.players.AUPlayer;
import mazgani.amongus.players.AUPlayersManager;
import mazgani.amongus.players.GameRole;
import mazgani.amongus.shiptasks.ShipTask;
import mazgani.amongus.shiptasks.inventorytasks.wires.WiresTask;
import mazgani.amongus.utilities.items.ClickSuffix;
import mazgani.amongus.utilities.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

import static java.util.stream.Collectors.joining;

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
        return null;
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