package net.minespree.babel;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.Map;

public class BabelMessage extends BabelStringMessageType {

    private String key;
    private Map<SupportedLanguage, String> values;

    BabelMessage(String key, Map<SupportedLanguage, String> values) {
        this.key = key;
        this.values = values;
    }

    public void sendMessage(Player player, Object... params) {
        player.sendMessage(toString(player, params));
    }

    public void broadcast(Object... params) {
        Bukkit.getOnlinePlayers().forEach(player -> sendMessage(player, params));
    }

    public String toString(Player player, Object... params) {
        if(player == null)
            return toString(params);
        String locale = player.spigot().getLocale().split(" ")[0];
        SupportedLanguage language = SupportedLanguage.from(locale);
        return toString(language, params);
    }

    public String toString() {
        return toString(SupportedLanguage.ENGLISH);
    }

    public String toString(Object... params) {
        return toString(SupportedLanguage.ENGLISH, params);
    }

    public String toString(SupportedLanguage locale, Object... params) {
        String text;
        try {
            text = values.get(locale);
        } catch (Exception e) {
            try {
                text = values.get(SupportedLanguage.ENGLISH);
            } catch (Exception ex) {
                text = "<" + key + ">";
            }
        }

        if (params != null && params.length > 0) {
            Object[] transformed = params.clone();
            for (int i = 0; i < transformed.length; i++) {
                if (transformed[i] instanceof LocalizedComponent) {
                    transformed[i] = ((LocalizedComponent) transformed[i]).localize(locale);
                }
            }
            text = MessageFormat.format(text, transformed);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public String getKey() {
        return key;
    }

}
