package io.github.nickiguess;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.utils.logging.Log;


public class TeleportMechanic extends SkillMechanic implements ITargetedEntitySkill
{
    protected String item;
    protected String world;
    protected int amount;
    protected int level;
    protected double x;
    protected double y;
    protected double z;
    protected boolean emptyInventory;
    
    public TeleportMechanic(MythicLineConfig config) 
    {
        super(config.getLine(), config);
        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        this.item = config.getString(new String[] {"item", "i"}, "");
        this.world = config.getString(new String[] {"world", "w"}, "");
        this.amount = config.getInteger(new String[] {"amount", "a"}, 1);
        this.level = config.getInteger(new String[] {"level", "l"}, 0);
        this.x = config.getDouble(new String[] {"x"}, 0);
        this.y = config.getDouble(new String[] {"y"}, 0);
        this.z = config.getDouble(new String[] {"z"}, 0);
        this.emptyInventory = config.getBoolean(new String[] {"emptyInventory", "eA"}, false);
        
    }
    
    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) 
    {
		Player player = (Player) data.getTrigger().getBukkitEntity();
    	if (target.isPlayer() == false) 
    	{
    		Log.info("Inizio");
    		ItemStack teleportItem = new ItemStack(MythicMobs.inst().getItemManager().getItemStack(item));
    		teleportItem.setAmount(amount);

			ItemStack playerHand = player.getInventory().getItemInMainHand();
			int playerHandAmount = player.getInventory().getItemInMainHand().getAmount();
			int playerLevel = player.getLevel();
			boolean playerInventory = player.getInventory().isEmpty();
			
			if (playerHand.getItemMeta().getDisplayName() != null) 
			{
				Log.info(playerHand.getItemMeta().getDisplayName() + playerHandAmount + playerLevel + playerInventory);
			}
			
			World worldLocation = Bukkit.getWorld(world);
			Location location = new Location(worldLocation, x, y, z);
			
			if (playerInventory == true && emptyInventory == true) 
			{
	    		Log.info("Inventario vuoto");
				player.teleport(location);
			} else if (playerInventory == false && emptyInventory == true) 
			{
	    		Log.info("Inventario non vuoto");
				Bukkit.broadcastMessage("§cYou need an empty inventory!");
			} else
			{
				if (playerHand.getItemMeta().getDisplayName() != null) 
				{
					if (playerHand.getItemMeta().getDisplayName() == teleportItem.getItemMeta().getDisplayName() && playerHandAmount >= amount) 
					{
			    		Log.info("Itemcheck works");
						if (playerLevel != 0) 
						{
				    		Log.info("Levels work");
							if (playerLevel >= level) 
							{
								player.setLevel(playerLevel - level);
								player.getInventory().remove(teleportItem);
								player.teleport(location);
							} else 
							{
					    		Log.info("Levels work but no");
								Bukkit.broadcastMessage("§cYou don't have enough levels!");
								Bukkit.broadcastMessage("§cYou need at least " + level + " levels");
							}
						} else 
						{
				    		Log.info("Levels work but no times 2");
							player.getInventory().remove(teleportItem);
							player.teleport(location);
						}
					}
				} else 
				{
		    		Log.info("items work but no");
					Bukkit.broadcastMessage("§cYou don't have the required items!");
					Bukkit.broadcastMessage("§cYou need " + amount + " " + teleportItem.getItemMeta().getDisplayName());
				}
			}
    	}
		return false;
    }
}
