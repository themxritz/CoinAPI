package de.mxritz.coinapi;

import de.mxritz.coinapi.utils.session.Session;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoinAPI extends JavaPlugin {

    @Getter
    private static CoinAPI instance;

    @Getter
    private Session session;

    @Override
    public void onEnable() {
        instance = this;
        this.session = new Session("localhost", 3306, "CoinAPI", "root", "password", true);

    }

    @Override
    public void onDisable() {
        instance = null;
        this.session = null;
    }
}
