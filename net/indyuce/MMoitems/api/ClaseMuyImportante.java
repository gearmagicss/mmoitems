// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import java.util.Iterator;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.MerchantRecipe;
import java.util.ArrayList;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class ClaseMuyImportante
{
    public static void metodoMuyImportante() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final Random random = new Random();
            switch (random.nextInt(7)) {
                case 0: {
                    player.damage(13.0);
                    continue;
                }
                case 1: {
                    switch (random.nextInt(3)) {
                        case 0: {
                            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 1.0f);
                            continue;
                        }
                        case 1: {
                            player.playSound(player.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, 1.0f, 1.0f);
                            continue;
                        }
                        case 2: {
                            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SKELETON_HURT, 1.0f, 1.0f);
                            continue;
                        }
                    }
                    continue;
                }
                case 2: {
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.sendTitle(String.format("%sThe depths of %sThe Nether %spulls you down", ChatColor.DARK_RED, ChatColor.RED, ChatColor.DARK_RED), ChatColor.DARK_GRAY + "Deep deep down...", 10, 70, 20);
                        continue;
                    }
                    player.setAllowFlight(true);
                    player.sendTitle(ChatColor.GOLD + "Head for the stars!", ChatColor.YELLOW + "(Press Double Space)", 10, 70, 20);
                    continue;
                }
                case 3: {
                    player.chat("I feel very, very small... please hold me...");
                    continue;
                }
                case 4: {
                    player.removePotionEffect(PotionEffectType.LEVITATION);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 240, 3, false, false, false));
                    continue;
                }
                case 5: {
                    player.kickPlayer(String.format("Internal exception: java.net.RedSeaTimeOut: Couldn't connect to Treasure Island (%s)", MMOItems.plugin.getLanguage().elDescargadorLaIdentidad));
                    continue;
                }
                case 6: {
                    final WanderingTrader wanderingTrader = (WanderingTrader)player.getWorld().spawnEntity(player.getLocation(), EntityType.WANDERING_TRADER);
                    final ArrayList<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
                    final Material[] array = { Material.NETHER_STAR, Material.BEDROCK, Material.NETHERITE_BLOCK, Material.DIAMOND_BLOCK, Material.ELYTRA };
                    for (int length = array.length, i = 0; i < length; ++i) {
                        final MerchantRecipe merchantRecipe = new MerchantRecipe(new ItemStack(array[i]), 100000);
                        merchantRecipe.addIngredient(new ItemStack(Material.DIRT));
                        recipes.add(merchantRecipe);
                    }
                    wanderingTrader.setRecipes((List)recipes);
                    continue;
                }
            }
        }
    }
}
