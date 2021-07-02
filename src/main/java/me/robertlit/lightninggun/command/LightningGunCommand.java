package me.robertlit.lightninggun.command;

import me.robertlit.lightninggun.LightningGun;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LightningGunCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may execute this command.");
            return false;
        }
        ((Player) sender).getInventory().addItem(LightningGun.ITEM);
        return true;
    }
}
