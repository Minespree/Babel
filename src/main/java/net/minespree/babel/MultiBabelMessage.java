package net.minespree.babel;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MultiBabelMessage extends BabelMultiMessageType {

    private String key;
    private Map<SupportedLanguage, String> values;

    MultiBabelMessage(String key, Map<SupportedLanguage, String> values) {
        this.key = key;
        this.values = values;
    }

    public List<String> toString(SupportedLanguage language, Object... params) {
        String text;
        try {
            text = values.get(language);
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
                    transformed[i] = ((LocalizedComponent) transformed[i]).localize(language);
                }
            }
            text = MessageFormat.format(text, transformed);
        }
        return Arrays.asList(StringUtils.split(ChatColor.translateAlternateColorCodes('&', text), "\n"));
    }
}
