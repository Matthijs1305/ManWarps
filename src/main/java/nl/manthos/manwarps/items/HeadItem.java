package nl.manthos.manwarps.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public enum HeadItem {
    //https://minecraft-heads.com

    CHECKMARK("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0"),
    ARROW_DOWN_BLUE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjBkMWRmODA0NmYwYjVkOTM0YzNlMDU3OThlYWNmZWVhNmQ3YjU5NWRiZTI2ZGViZjdkYjlhY2M4YzRmYTc5OCJ9fX0="),
    HOUSE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0="),
    EAGLE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTA5MTFiZmMxZjdjMWFjOTRkMTkzNGM1MzY1ZWMzZTZiNWRhNTJiNjk2MTkxNWZhMTZkZWI1M2UwMjRjMWEifX19");

    private final String value;

    private HeadItem(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public ItemStack head(){
        String value = this.value;
        UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        return Bukkit.getUnsafe().modifyItemStack(skull,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}"
        );
    }
}
