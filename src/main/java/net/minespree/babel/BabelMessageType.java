package net.minespree.babel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @since 29/03/2017
 */
public abstract class BabelMessageType<T> {

    @SuppressWarnings("unchecked")
    public void sendMessage(Player player, Object... params) {
        T msg = toString(player, params);
        if(msg instanceof String) {
            player.sendMessage((String) toString(player, params));
        } else if(msg instanceof List<?>) {
            for(String m : (List<String>) msg) {
                player.sendMessage(m);
            }
        }
    }

    public void broadcast(Object... params) {
        Bukkit.getOnlinePlayers().forEach(player -> sendMessage(player, params));
    }

    public T toString(Player player, Object... params) {
        if(player == null)
            return toString(params);
        return toString(SupportedLanguage.from(player.spigot().getLocale().split(" ")[0]), params);
    }

    public T toString(Object... params) {
        return toString(SupportedLanguage.ENGLISH, params);
    }

    public abstract T toString(SupportedLanguage language, Object... params);
}
