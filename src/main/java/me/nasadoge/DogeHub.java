package me.nasadoge;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

public class DogeHub extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.reloadConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[DogeHub] " + ChatColor.GREEN + "DogeHub is now enabled.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[DogeHub] " + ChatColor.GREEN + "Thanks for using our plugin!");
        super.onEnable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("hub-reload")) {
            if(sender.hasPermission("dogehub.reload")) {
                this.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("reload")));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("lack")));
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[DogeHub] " + ChatColor.RED + "DogeHub has been disabled.");
        super.onDisable();
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase(this.getConfig().getString("world")) && event.getEntity() != null) {
            if(this.getConfig().getString("anti-hunger").contentEquals("true")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase(this.getConfig().getString("world")) && event.getPlayer() != null) {
            if (this.getConfig().getString("anti-break").contentEquals("true")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase(this.getConfig().getString("world")) && event.getPlayer() != null) {
            double x = Double.parseDouble(this.getConfig().getString("spawn-position.x")),
                    y = Double.parseDouble(this.getConfig().getString("spawn-position.y")),
                    z = Double.parseDouble(this.getConfig().getString("spawn-position.z"));

            float yaw = Float.parseFloat(this.getConfig().getString("spawn-position.yaw")),
                    pitch = Float.parseFloat(this.getConfig().getString("spawn-position.pitch"));

            World world = this.getServer().getWorld(this.getConfig().getString("world"));
            Location location = new Location(world, x, y, z, yaw, pitch);

            player.teleport(location);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if (event.getPlayer().getWorld().getName().equalsIgnoreCase(this.getConfig().getString("world")) && event.getPlayer() != null) {
            if(this.getConfig().getString("anti-interact.enabled").contentEquals("true")
             && action == Action.RIGHT_CLICK_BLOCK) {
                if ((block.getType() == Material.WOODEN_DOOR
                        || block.getType() == Material.IRON_DOOR
                        || block.getType() == Material.ACACIA_DOOR
                        || block.getType() == Material.BIRCH_DOOR
                        || block.getType() == Material.DARK_OAK_DOOR
                        || block.getType() == Material.JUNGLE_DOOR
                        || block.getType() == Material.SPRUCE_DOOR)
                        && this.getConfig().getString("anti-interact.doors").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if ((block.getType() == Material.IRON_TRAPDOOR
                        || block.getType() == Material.TRAP_DOOR)
                        && this.getConfig().getString("anti-interact.trapdoors").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if ((block.getType() == Material.CHEST
                        || block.getType() == Material.TRAPPED_CHEST)
                        && this.getConfig().getString("anti-interact.chests").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if ((block.getType() == Material.FURNACE
                        || block.getType() == Material.BURNING_FURNACE)
                        && this.getConfig().getString("anti-interact.furnaces").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if (block.getType() == Material.ENCHANTMENT_TABLE
                        && this.getConfig().getString("anti-interact.tables").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if (block.getType() == Material.BEACON
                        && this.getConfig().getString("anti-interact.beacons").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if (block.getType() == Material.ARMOR_STAND
                        && this.getConfig().getString("anti-interact.armor-stands").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if (block.getType() == Material.MINECART
                        && this.getConfig().getString("anti-interact.minecarts").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if (block.getType() == Material.DISPENSER
                        && this.getConfig().getString("anti-interact.dispensers").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if (block.getType() == Material.DROPPER
                        && this.getConfig().getString("anti-interact.droppers").contentEquals("true")) {
                    event.setCancelled(true);
                }

                if ((block.getType() == Material.HOPPER
                        || block.getType() == Material.HOPPER_MINECART)
                        && this.getConfig().getString("anti-interact.hoppers").contentEquals("true")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity player = event.getWhoClicked();
        if (player.getWorld().getName().equalsIgnoreCase(this.getConfig().getString("world"))) {
            if (event.getInventory().getType().equals(InventoryType.CRAFTING)
                    && this.getConfig().getString("anti-interact.tables").contentEquals("true")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase(this.getConfig().getString("world")) && event.getPlayer() != null) {
            if (player.getWorld() == getServer().getWorlds().get(0)
                    && player.getLocation().getY() <= 0.0
                    && this.getConfig().getString("void-teleport").contentEquals("true")) {
                double x = Double.parseDouble(this.getConfig().getString("spawn-position.x")),
                        y = Double.parseDouble(this.getConfig().getString("spawn-position.y")),
                        z = Double.parseDouble(this.getConfig().getString("spawn-position.z"));

                float yaw = Float.parseFloat(this.getConfig().getString("spawn-position.yaw")),
                        pitch = Float.parseFloat(this.getConfig().getString("spawn-position.pitch"));

                World world = this.getServer().getWorld(this.getConfig().getString("world"));
                Location location = new Location(world, x, y, z, yaw, pitch);

                player.teleport(location);
            }
        }
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player)event.getEntity();
            if (player.getWorld().getName().equalsIgnoreCase(this.getConfig().getString("world"))) {
                event.setCancelled(true);
            }
        }
    }

}
