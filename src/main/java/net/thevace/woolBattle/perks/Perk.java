package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Perk {
    protected final int preis;
    protected final long cooldown;
    protected final WoolbattlePlayer player;

    protected String itemName;
    protected Material material;
    protected String itemDescription;


    ItemStack item;


    public PlayerInteractEvent event;

    public Perk(long cooldown, int preis, WoolbattlePlayer player, String itemName, Material material, String itemDescription) {
        this.material = material;
        this.itemName = itemName;
        this.player = player;
        this.cooldown = cooldown;
        this.preis = preis;
        this.itemDescription = itemDescription;

        item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        List<String> lore = new ArrayList<String>();
        lore.add(itemDescription);
        lore.add("Preis: " + preis + " Wolle");
        lore.add("Cooldown: " + cooldown + " Sekunden");
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public String getItemName() {
        return itemName;
    }

}
