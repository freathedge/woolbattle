package net.thevace.woolBattle;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueueManager {
    private static final Map<WoolbattlePlayer, WoolBattleQueue> playerQueues = new HashMap<>();
    private static final List<WoolBattleQueue> allQueues = new ArrayList<>();

    private WoolBattlePlayerManager playerManager;


    public QueueManager(WoolBattlePlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void addToQueue(WoolbattlePlayer player, WoolBattleQueue queue) {
        playerQueues.put(player, queue);
        queue.joinQueue(player);
    }

    public void removeFromQueue(WoolbattlePlayer player) {
        WoolBattleQueue queue = getQueue(player);
        if (queue != null) {
            queue.leaveQueue(player);
        }
        playerQueues.remove(player);
    }

    public WoolBattleQueue getQueue(WoolbattlePlayer player) {
        return playerQueues.get(player);
    }

    public Map<WoolbattlePlayer, WoolBattleQueue> getAllQueues() {
        return playerQueues;
    }

    public void listAllQueues() {
        for (WoolBattleQueue queue : allQueues) {
            for (WoolbattlePlayer player : queue.getQueue()) {
                System.out.println("Queue: " + queue.getQueue() + " Player: " + player);
            }
        }
    }

    public void joinAvailableQueue(WoolbattlePlayer player, int teamSize) {
        if (playerQueues.containsKey(player)) {
            player.getPlayer().sendMessage("Du bist bereits in einer Warteschlange.");
            return;
        }

        // Nach existierender Warteschlange mit demselben TeamSize suchen
        for (WoolBattleQueue queue : allQueues) {
            if (queue.getTeamSize() == teamSize && queue.getTotalPlayers() < queue.getTeamSize() * 2) {
                addToQueue(player, queue);
                return;
            }
        }

        // Falls keine passende Warteschlange existiert, erstelle eine neue
        WoolBattleQueue newQueue = new WoolBattleQueue(teamSize, playerManager, this);
        allQueues.add(newQueue);
        addToQueue(player, newQueue);
    }



    public void removeQueue(WoolBattleQueue queue) {
        allQueues.remove(queue);
        playerQueues.entrySet().removeIf(entry -> entry.getValue() == queue);
        for(WoolbattlePlayer player : playerQueues.keySet()) {
            System.out.println("Queue: " + queue.getQueue() + " Player: " + player.getPlayer().getName());
        }
    }
}