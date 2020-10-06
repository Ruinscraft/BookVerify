package com.ruinscraft.bookverify;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public final class BookSignatureUtil {

    private static final Gson GSON = new Gson();
    private static final NamespacedKey KEY = new NamespacedKey(BookVerifyPlugin.getInstance(), "book-signature");

    public static BookSignature read(BookMeta bookMeta) {
        String data = bookMeta.getPersistentDataContainer().get(KEY, PersistentDataType.STRING);
        return BookSignature.decodeJson(data);
    }

    public static void write(BookMeta bookMeta, BookSignature signature) {
        bookMeta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, signature.encodeJson());
    }

    public static String getContentHash(BookMeta bookMeta) {
        String jsonBookContent = GSON.toJson(bookMeta.getPages(), new TypeToken<List<String>>() {}.getType());
        HashCode hash = Hashing.md5().hashBytes(jsonBookContent.getBytes());
        return hash.toString();
    }

}