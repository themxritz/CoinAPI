package de.mxritz.coinapi.manager;

import de.mxritz.coinapi.CoinAPI;
import de.mxritz.coinapi.event.CoinsUpdateEvent;
import de.mxritz.coinapi.utils.session.Session;
import de.mxritz.coinapi.utils.session.query.QueryStatement;
import de.mxritz.coinapi.utils.session.query.UpdateStatement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 2022
 *
 * @author Moritz Selz (moritzselz@gmail.com)
 */
public class CoinManager {
    private static CoinManager instance;

    private final QueryStatement getCoins;

    private final UpdateStatement setCoins;

    public static CoinManager getInstance() {
        return instance;
    }

    public CoinManager() {
        instance = this;
        Session session = CoinAPI.getInstance().getSession();
        this.getCoins = session.prepareQueryStatement("SELECT coins FROM players WHERE uuid = ?");
        this.setCoins = session.prepareUpdateStatement("UPDATE players SET coins = ? WHERE uuid = ?");
    }

    public void disable() {
        instance = this;
    }

    public int getCoins(UUID uuid) {
        try {
            ResultSet resultSet = this.getCoins.execute(new Object[] { uuid });
            if (resultSet.next())
                return resultSet.getInt("coins");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setCoins(UUID uuid, int coins) {
        try {
            this.setCoins.execute(new Object[] { Integer.valueOf(coins), uuid });
            Player player = Bukkit.getPlayer(uuid);
            if (player.isOnline()) {
                CoinsUpdateEvent coinsUpdateEvent = new CoinsUpdateEvent(player);
                Bukkit.getPluginManager().callEvent((Event)coinsUpdateEvent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
