package net.minespree.babel;

public abstract class BabelStringMessageType extends BabelMessageType<String> {

    public abstract String toString(SupportedLanguage language, Object... params);
}
