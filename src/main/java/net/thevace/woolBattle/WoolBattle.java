package net.thevace.woolBattle;

import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolBattle.commands.*;
import net.thevace.woolBattle.commands.tabcompleter.QueueTabCompleter;
import net.thevace.woolBattle.inventorys.*;
import net.thevace.woolBattle.listener.PlayerInteraction;
import net.thevace.woolBattle.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WoolBattle extends JavaPlugin {

    @Override
    public void onEnable() {



        WoolBattlePlayerManager playerManager = new WoolBattlePlayerManager();
        PerkManager perkManager = new PerkManager();
        QueueManager queueManager = new QueueManager(playerManager);

        ViewFrame viewFrame = ViewFrame.create(this)
                .with(new TeamSelect(playerManager, queueManager))
                .with(new ActivePerk1Selector(playerManager, perkManager))
                .with(new ActivePerk2Selector(playerManager, perkManager))
                .with(new PerkSelector())
                .with(new Voting())
                .with(new LebenVoting(playerManager, queueManager))
                .with(new MapVoting())
                .register();

        this.getCommand("joinQueue").setExecutor(new joinQueue(playerManager, queueManager, viewFrame));
        this.getCommand("startGame").setExecutor(new startGame(playerManager, queueManager));
        this.getCommand("getQueue").setExecutor(new getQueue(queueManager, playerManager));
        this.getCommand("getPlayerWool").setExecutor(new getPlayerWool(playerManager));
        this.getCommand("createQueue").setExecutor(new createQueue(queueManager));
        this.getCommand("listallplayers").setExecutor(new listallplayer(playerManager));

        this.getCommand("getQueue").setTabCompleter(new QueueTabCompleter(queueManager));
        this.getCommand("joinQueue").setTabCompleter(new QueueTabCompleter(queueManager));

        Bukkit.getPluginManager().registerEvents(new PlayerInteraction(viewFrame, playerManager, queueManager), this);


    }

    @Override
    public void onDisable() {

    }
}
