package mazgani.amongus.commands.sub;

import mazgani.amongus.commands.Command;
import mazgani.amongus.utilities.items.ClickSuffix;
import mazgani.amongus.utilities.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TestCommand extends Command {

    public TestCommand(String name) {
        super(name);
    }

    @Override
    public String getDescription() {
        return "Among us test command";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String[] getPermissions() {
        return new String[] { "amongus.admin.test" };
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

        ItemBuilder builder = new ItemBuilder(Material.DIAMOND_SWORD, ChatColor.LIGHT_PURPLE + "Hero's Sword")
                .withLore(ChatColor.WHITE + "Time to kill everyone.", ChatColor.DARK_GREEN + "Right / Left Click to have Anti Knockback.")
                .withClickableSuffix(ClickSuffix.RIGHT_FIRST, ChatColor.GRAY, ChatColor.GREEN, ChatColor.GRAY)
                .enchantedWith(Enchantment.DAMAGE_ALL, 12)
                .glowing();

        player.getInventory().addItem(builder.createCopy());

				/*Block block = player.getTargetBlock(null, 10);

				if(!(block.getState().getBlockData() instanceof Openable))
				{
					player.sendMessage(ChatColor.RED + "You must look on an openable block.");
					return true;
				}
				changeDoor(block, Material.IRON_DOOR);*/

				/*GamePlayer gamePlayer = new GamePlayer(new AUPlayer(player.getUniqueId()), null);

				BasicGameCorpse corpse = new TestCorpse(gamePlayer, null);
				corpse.spawn(player.getLocation());

				Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), corpse::despawn, 20 * 5);*/

				/*WoolsCompositeCorpse corpse = new WoolsCompositeCorpse(gamePlayer, null);
				corpse.spawn(player.getLocation());*/


				/*SignCorpse corpse = new SignCorpse(gamePlayer, null, "Mizrahi", "raped a ", "goat here.");
				corpse.spawn(player.getLocation());

				Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), corpse::despawn, 20 * 5);*/

				/*Openable openable = (Openable) block.getState().getBlockData();
				openable.setOpen(!openable.isOpen());

				state.setBlockData(openable);
				state.update();*/

				/*Sabotage sabotage = new DoorsSabotage(block);
				sabotage.sabotage();

				Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), sabotage::unsabotage, 20 * 5);*/

				/*Hologram h1 = new CommonEquallableHologram(HologramsAPI.createHologram(AmongUs.getInstance(), player.getLocation().add(0, 3, 0)));
				Hologram h2 = new CommonEquallableHologram(HologramsAPI.createHologram(AmongUs.getInstance(), player.getLocation().add(0, 6, 0)));

				h1.appendTextLine("Hey!");
				h2.appendTextLine("Hey2");

				player.sendMessage(h1.equals(h2) + " equals.");*/

				/*if(requestedBeingInAGame(player))
				{
					return false;
				}
				AUGame playerGame = this.gamesManager.getPlayerGame(player.getUniqueId()).get();*/

				/*Location[] spawns = new TestMap().getSpawnLocations();

				for(Location spawn : spawns)
				{
					player.sendMessage(spawn.getBlock().getType().name());
				}*/



				/*Queue<Location> spawnsQueue = Arrays.stream(spawns).collect(toCollection(LinkedList::new));

				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						if(spawnsQueue.isEmpty())
						{
							player.sendMessage(ChatColor.GREEN + "All locations were checked.");
							cancel();
							return;
						}
						Location nextSpawn = spawnsQueue.poll();
						player.teleport(nextSpawn);

						if(nextSpawn.getBlock() == null || !nextSpawn.getBlock().getType().name().endsWith("WOOL"))
						{
							cancel();
							player.sendMessage(ChatColor.RED + "What are you standing on? It's not a wool.");
							return;
						}
					}
				}.runTaskTimer(AmongUs.getInstance(), 0, 20 * 2);*/
    }
}