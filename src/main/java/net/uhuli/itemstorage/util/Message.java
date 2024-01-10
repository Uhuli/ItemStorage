package net.uhuli.itemstorage.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Message {
    public static Component miniMessage(String input) {
        return MiniMessage.miniMessage().deserialize(input);
    }
}
