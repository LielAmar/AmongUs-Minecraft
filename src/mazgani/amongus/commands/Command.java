package mazgani.amongus.commands;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.players.GameRole;
import mazgani.amongus.shiptasks.ShipTask;
import mazgani.amongus.shiptasks.inventorytasks.wires.WiresTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;

import java.util.List;

import static java.util.stream.Collectors.joining;

public abstract class Command {

    private final String name;
    protected GameLobby tempLobby;

    public Command(String name) {
        this(name, null);
    }

    public Command(String name, GameLobby tempLobby) {
        this.name = name;
        this.tempLobby = tempLobby;
    }

    public final String getName() {
        return this.name;
    }


    public boolean hasPermissions(CommandSender cs) {
        for(String s : getPermissions()) {
            if(cs.hasPermission(s))
                return true;
        }

        return false;
    }

    public abstract String getDescription();
    public abstract String[] getAliases();
    public abstract String[] getPermissions();
    public abstract List<String> getTabList(CommandSender sender, String[] args);

    public abstract void execute(CommandSender sender, String[] args);

    protected static void printGameData(AUGame game)
    {
        Bukkit.broadcastMessage("Impostors Left: " + game.playersLeft(GameRole.IMPOSTOR));
        Bukkit.broadcastMessage("Crewmates Left: " + game.playersLeft(GameRole.CREWMATE));
        Bukkit.broadcastMessage("The game is " + (game.isTie() ? ChatColor.GREEN + "is" : ChatColor.RED + "isn't") + " on tie.");
        Bukkit.broadcastMessage("Tasks: " + game.getTasks().stream().map(ShipTask::getName).collect(joining(", ")));

        for(GamePlayer gamePlayer : game.getGamePlayersView())
        {
            String playerName = gamePlayer.getAUPlayer().getPlayer().getName();
            String roleName = gamePlayer.getRole().getName();

            Bukkit.broadcastMessage(String.format("%s's Role is %s", playerName, roleName));
        }
    }

    protected boolean requestedTempLobby(CommandSender sender, boolean toExist)
    {
        if(toExist && this.tempLobby == null)
        {
            sender.sendMessage(ChatColor.RED + "The temp lobby was not created! Use /amongus createlobby");
            return true;
        }
        if(!toExist && this.tempLobby != null)
        {
            sender.sendMessage(ChatColor.RED + "The temp lobby already exists!");
            return true;
        }
        return false;
    }

    protected static String shortenGameID(String stringUUID)
    {
        return ChatColor.YELLOW + "#" + stringUUID.substring(0, 8);
    }

    protected static void openWireTaskInventory(AUGame game)
    {
        WiresTask wiresTask = game.getTasks().stream()
                .filter(task -> task instanceof WiresTask)
                .map(task -> (WiresTask) task)
                .findFirst()
                .orElseThrow(NullPointerException::new);

        for(GamePlayer gamePlayer : game.getGamePlayersView())
        {
            Inventory wiresInv = wiresTask.getInventoryManager().createInventory(gamePlayer);

            gamePlayer.getAUPlayer().getPlayer().openInventory(wiresInv);
        }
    }
}
