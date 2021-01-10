package mazgani.amongus.commands.sub;

import mazgani.amongus.commands.Command;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.maps.TestMap;
import mazgani.amongus.players.AUPlayer;
import mazgani.amongus.players.AUPlayersManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateLobbyCommand extends Command {

    private final LobbiesManager lobbiesManager;
    private final AUPlayersManager auPlayersManager;

    public CreateLobbyCommand(String name, LobbiesManager lobbiesManager, AUPlayersManager auPlayersManager, GameLobby tempLobby) {
        super(name, tempLobby);

        this.lobbiesManager = lobbiesManager;
        this.auPlayersManager = auPlayersManager;
    }

    @Override
    public String getDescription() {
        return "Among us create lobby command";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String[] getPermissions() {
        return new String[] { "amongus.admin.createlobby" };
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

        if(requestedTempLobby(player, true))
        {
            return;
        }
        Block block = player.getTargetBlock(null, 10);

        if(!(block.getState() instanceof Sign))
        {
            player.sendMessage(ChatColor.RED + "You must be looking at the Join Sign.");
            return;
        }
        Sign sign = (Sign) block.getState();

        this.tempLobby = this.lobbiesManager.new GameLobbyBuilder(player.getLocation(), new TestMap(), 1, 1)
                .joinableBy(sign)
                .build();

        player.sendMessage(ChatColor.GREEN + "A lobby has been created in your location.");

        AUPlayer auPlayer = this.auPlayersManager.getPlayer(player.getUniqueId());
        this.tempLobby.addPlayer(auPlayer);
        player.sendMessage(ChatColor.GREEN + "You've been sent to Lobby " + shortenGameID(this.tempLobby.getUUID().toString()));
    }
}