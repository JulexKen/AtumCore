package me.phoenixra.core;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.phoenixra.core.utils.PhoenixUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {
    private final ItemStack itemStack;
    public ItemBuilder() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }
    public ItemBuilder makeUnbreakable(){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setType(Material material) {
        this.itemStack.setType(material);
        return this;
    }
    public ItemBuilder setType(XMaterial material) {
        this.itemStack.setType(material.parseItem().getType());
        return this;
    }

    @SuppressWarnings({})
    public ItemBuilder setOwner(String string) {
            this.itemStack.setType(Objects.requireNonNull(XMaterial.PLAYER_HEAD.parseMaterial(true)));
            SkullMeta skullMeta = (SkullMeta)this.itemStack.getItemMeta();
            skullMeta.setOwner(string);
            this.itemStack.setItemMeta(skullMeta);
            return this;
    }
    public ItemBuilder setCustomOwner(String string) {
        this.itemStack.setType(Objects.requireNonNull(XMaterial.PLAYER_HEAD.parseMaterial(true)));
        SkullMeta skullMeta = (SkullMeta)this.itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", string));
        try {
            Field field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, gameProfile);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        this.itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)this.itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        this.itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }
    public ItemBuilder setAmount(int n) {
        this.itemStack.setAmount(n);
        return this;
    }
    public ItemBuilder setDisplayName(String string) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(PhoenixUtils.colorFormat(string));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLores(List<String> list) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<>();
        for (String string : list) {
            arrayList.add(PhoenixUtils.colorFormat(string));
        }
        itemMeta.setLore(arrayList);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLores(String ... arrstring) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> list = new ArrayList<>();
        for (String string : arrstring) {
            if(string.contains("\\n"))
                list.addAll(PhoenixUtils.colorFormat(Arrays.asList(string.split("\\n"))));
            else
                list.add(PhoenixUtils.colorFormat(string));
        }
        itemMeta.setLore(list);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag ... arritemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(arritemFlag);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int n) {
        this.itemStack.addUnsafeEnchantment(enchantment, n);
        return this;
    }

    public ItemBuilder addFakeEnchant() {
        this.itemStack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        this.addItemFlag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStack getItem() {
        return this.itemStack;
    }
}
