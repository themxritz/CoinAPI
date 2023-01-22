package de.mxritz.coinapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * JavaDoc this file!
 * Created: 2022
 *
 * @author Moritz Selz (moritzselz@gmail.com)
 */
public class CoinsUpdateEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Player player;

    public Player getPlayer() {
        return this.player;
    }

    public CoinsUpdateEvent(Player player) {
        this.player = player;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
