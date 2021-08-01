// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.listener;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.WorkbenchIngredient;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.recipe.workbench.CustomRecipe;
import org.bukkit.inventory.ItemStack;
import java.util.Optional;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.recipe.workbench.CachedRecipe;
import java.util.UUID;
import java.util.Map;
import org.bukkit.event.Listener;

public class CraftingListener implements Listener
{
    final Map<UUID, CachedRecipe> cachedRecipe;
    
    public CraftingListener() {
        this.cachedRecipe = new HashMap<UUID, CachedRecipe>();
    }
    
    @EventHandler
    public void calculateCrafting(final PrepareItemCraftEvent prepareItemCraftEvent) {
        if (!(prepareItemCraftEvent.getView().getPlayer() instanceof Player)) {
            return;
        }
        this.handleCustomCrafting(prepareItemCraftEvent.getInventory(), (Player)prepareItemCraftEvent.getView().getPlayer());
    }
    
    @EventHandler
    public void getResult(final InventoryClickEvent p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getView:()Lorg/bukkit/inventory/InventoryView;
        //     4: invokevirtual   org/bukkit/inventory/InventoryView.getPlayer:()Lorg/bukkit/entity/HumanEntity;
        //     7: instanceof      Lorg/bukkit/entity/Player;
        //    10: ifeq            23
        //    13: aload_1        
        //    14: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getInventory:()Lorg/bukkit/inventory/Inventory;
        //    17: instanceof      Lorg/bukkit/inventory/CraftingInventory;
        //    20: ifne            24
        //    23: return         
        //    24: aload_1        
        //    25: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getView:()Lorg/bukkit/inventory/InventoryView;
        //    28: invokevirtual   org/bukkit/inventory/InventoryView.getPlayer:()Lorg/bukkit/entity/HumanEntity;
        //    31: checkcast       Lorg/bukkit/entity/Player;
        //    34: astore_2       
        //    35: aload_1        
        //    36: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getSlotType:()Lorg/bukkit/event/inventory/InventoryType$SlotType;
        //    39: getstatic       org/bukkit/event/inventory/InventoryType$SlotType.CRAFTING:Lorg/bukkit/event/inventory/InventoryType$SlotType;
        //    42: if_acmpne       79
        //    45: aload_1        
        //    46: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getAction:()Lorg/bukkit/event/inventory/InventoryAction;
        //    49: getstatic       org/bukkit/event/inventory/InventoryAction.PLACE_ONE:Lorg/bukkit/event/inventory/InventoryAction;
        //    52: if_acmpne       79
        //    55: invokestatic    org/bukkit/Bukkit.getScheduler:()Lorg/bukkit/scheduler/BukkitScheduler;
        //    58: getstatic       net/Indyuce/mmoitems/MMOItems.plugin:Lnet/Indyuce/mmoitems/MMOItems;
        //    61: aload_0        
        //    62: aload_1        
        //    63: aload_2        
        //    64: invokedynamic   BootstrapMethod #0, run:(Lnet/Indyuce/mmoitems/listener/CraftingListener;Lorg/bukkit/event/inventory/InventoryClickEvent;Lorg/bukkit/entity/Player;)Ljava/lang/Runnable;
        //    69: lconst_1       
        //    70: invokeinterface org/bukkit/scheduler/BukkitScheduler.runTaskLater:(Lorg/bukkit/plugin/Plugin;Ljava/lang/Runnable;J)Lorg/bukkit/scheduler/BukkitTask;
        //    75: pop            
        //    76: goto            363
        //    79: aload_1        
        //    80: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getSlotType:()Lorg/bukkit/event/inventory/InventoryType$SlotType;
        //    83: getstatic       org/bukkit/event/inventory/InventoryType$SlotType.RESULT:Lorg/bukkit/event/inventory/InventoryType$SlotType;
        //    86: if_acmpne       363
        //    89: aload_1        
        //    90: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getInventory:()Lorg/bukkit/inventory/Inventory;
        //    93: checkcast       Lorg/bukkit/inventory/CraftingInventory;
        //    96: astore_3       
        //    97: aload_1        
        //    98: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getCurrentItem:()Lorg/bukkit/inventory/ItemStack;
        //   101: ifnull          125
        //   104: aload_0        
        //   105: getfield        net/Indyuce/mmoitems/listener/CraftingListener.cachedRecipe:Ljava/util/Map;
        //   108: aload_1        
        //   109: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getWhoClicked:()Lorg/bukkit/entity/HumanEntity;
        //   112: invokeinterface org/bukkit/entity/HumanEntity.getUniqueId:()Ljava/util/UUID;
        //   117: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   122: ifne            126
        //   125: return         
        //   126: aload_1        
        //   127: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getAction:()Lorg/bukkit/event/inventory/InventoryAction;
        //   130: getstatic       org/bukkit/event/inventory/InventoryAction.PICKUP_ALL:Lorg/bukkit/event/inventory/InventoryAction;
        //   133: if_acmpeq       142
        //   136: aload_1        
        //   137: iconst_1       
        //   138: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.setCancelled:(Z)V
        //   141: return         
        //   142: aload_0        
        //   143: getfield        net/Indyuce/mmoitems/listener/CraftingListener.cachedRecipe:Ljava/util/Map;
        //   146: aload_1        
        //   147: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getWhoClicked:()Lorg/bukkit/entity/HumanEntity;
        //   150: invokeinterface org/bukkit/entity/HumanEntity.getUniqueId:()Ljava/util/UUID;
        //   155: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   160: checkcast       Lnet/Indyuce/mmoitems/api/recipe/workbench/CachedRecipe;
        //   163: astore          4
        //   165: aload_0        
        //   166: getfield        net/Indyuce/mmoitems/listener/CraftingListener.cachedRecipe:Ljava/util/Map;
        //   169: aload_1        
        //   170: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.getWhoClicked:()Lorg/bukkit/entity/HumanEntity;
        //   173: invokeinterface org/bukkit/entity/HumanEntity.getUniqueId:()Ljava/util/UUID;
        //   178: invokeinterface java/util/Map.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //   183: pop            
        //   184: aload           4
        //   186: aload_3        
        //   187: invokeinterface org/bukkit/inventory/CraftingInventory.getMatrix:()[Lorg/bukkit/inventory/ItemStack;
        //   192: invokevirtual   net/Indyuce/mmoitems/api/recipe/workbench/CachedRecipe.isValid:([Lorg/bukkit/inventory/ItemStack;)Z
        //   195: ifne            204
        //   198: aload_1        
        //   199: iconst_1       
        //   200: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.setCancelled:(Z)V
        //   203: return         
        //   204: new             Lnet/Indyuce/mmoitems/api/event/CraftMMOItemEvent;
        //   207: dup            
        //   208: aload_2        
        //   209: invokestatic    net/Indyuce/mmoitems/api/player/PlayerData.get:(Lorg/bukkit/OfflinePlayer;)Lnet/Indyuce/mmoitems/api/player/PlayerData;
        //   212: aload           4
        //   214: invokevirtual   net/Indyuce/mmoitems/api/recipe/workbench/CachedRecipe.getResult:()Lorg/bukkit/inventory/ItemStack;
        //   217: invokespecial   net/Indyuce/mmoitems/api/event/CraftMMOItemEvent.<init>:(Lnet/Indyuce/mmoitems/api/player/PlayerData;Lorg/bukkit/inventory/ItemStack;)V
        //   220: astore          5
        //   222: invokestatic    org/bukkit/Bukkit.getPluginManager:()Lorg/bukkit/plugin/PluginManager;
        //   225: aload           5
        //   227: invokeinterface org/bukkit/plugin/PluginManager.callEvent:(Lorg/bukkit/event/Event;)V
        //   232: aload           5
        //   234: invokevirtual   net/Indyuce/mmoitems/api/event/CraftMMOItemEvent.isCancelled:()Z
        //   237: ifeq            246
        //   240: aload_1        
        //   241: iconst_1       
        //   242: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.setCancelled:(Z)V
        //   245: return         
        //   246: aload           4
        //   248: aload_3        
        //   249: invokeinterface org/bukkit/inventory/CraftingInventory.getMatrix:()[Lorg/bukkit/inventory/ItemStack;
        //   254: invokevirtual   net/Indyuce/mmoitems/api/recipe/workbench/CachedRecipe.generateMatrix:([Lorg/bukkit/inventory/ItemStack;)[Lorg/bukkit/inventory/ItemStack;
        //   257: astore          6
        //   259: aload_3        
        //   260: bipush          9
        //   262: anewarray       Lorg/bukkit/inventory/ItemStack;
        //   265: dup            
        //   266: iconst_0       
        //   267: aconst_null    
        //   268: aastore        
        //   269: dup            
        //   270: iconst_1       
        //   271: aconst_null    
        //   272: aastore        
        //   273: dup            
        //   274: iconst_2       
        //   275: aconst_null    
        //   276: aastore        
        //   277: dup            
        //   278: iconst_3       
        //   279: aconst_null    
        //   280: aastore        
        //   281: dup            
        //   282: iconst_4       
        //   283: aconst_null    
        //   284: aastore        
        //   285: dup            
        //   286: iconst_5       
        //   287: aconst_null    
        //   288: aastore        
        //   289: dup            
        //   290: bipush          6
        //   292: aconst_null    
        //   293: aastore        
        //   294: dup            
        //   295: bipush          7
        //   297: aconst_null    
        //   298: aastore        
        //   299: dup            
        //   300: bipush          8
        //   302: aconst_null    
        //   303: aastore        
        //   304: invokeinterface org/bukkit/inventory/CraftingInventory.setMatrix:([Lorg/bukkit/inventory/ItemStack;)V
        //   309: invokestatic    org/bukkit/Bukkit.getScheduler:()Lorg/bukkit/scheduler/BukkitScheduler;
        //   312: getstatic       net/Indyuce/mmoitems/MMOItems.plugin:Lnet/Indyuce/mmoitems/MMOItems;
        //   315: aload           6
        //   317: aload_3        
        //   318: invokedynamic   BootstrapMethod #1, run:([Lorg/bukkit/inventory/ItemStack;Lorg/bukkit/inventory/CraftingInventory;)Ljava/lang/Runnable;
        //   323: lconst_1       
        //   324: invokeinterface org/bukkit/scheduler/BukkitScheduler.runTaskLater:(Lorg/bukkit/plugin/Plugin;Ljava/lang/Runnable;J)Lorg/bukkit/scheduler/BukkitTask;
        //   329: pop            
        //   330: aload_1        
        //   331: aload           5
        //   333: invokevirtual   net/Indyuce/mmoitems/api/event/CraftMMOItemEvent.getResult:()Lorg/bukkit/inventory/ItemStack;
        //   336: invokevirtual   org/bukkit/event/inventory/InventoryClickEvent.setCurrentItem:(Lorg/bukkit/inventory/ItemStack;)V
        //   339: invokestatic    org/bukkit/Bukkit.getScheduler:()Lorg/bukkit/scheduler/BukkitScheduler;
        //   342: getstatic       net/Indyuce/mmoitems/MMOItems.plugin:Lnet/Indyuce/mmoitems/MMOItems;
        //   345: aload_2        
        //   346: dup            
        //   347: invokestatic    java/util/Objects.requireNonNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //   350: pop            
        //   351: invokedynamic   BootstrapMethod #2, run:(Lorg/bukkit/entity/Player;)Ljava/lang/Runnable;
        //   356: lconst_1       
        //   357: invokeinterface org/bukkit/scheduler/BukkitScheduler.runTaskLater:(Lorg/bukkit/plugin/Plugin;Ljava/lang/Runnable;J)Lorg/bukkit/scheduler/BukkitTask;
        //   362: pop            
        //   363: return         
        //    StackMapTable: 00 09 17 00 FC 00 36 07 00 33 FC 00 2D 07 00 45 00 0F FC 00 3D 07 00 9B FC 00 29 07 00 A8 F8 00 74
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void handleCustomCrafting(final CraftingInventory craftingInventory, final Player player) {
        this.cachedRecipe.remove(player.getUniqueId());
        final Optional<CachedRecipe> checkRecipes = this.checkRecipes(player, craftingInventory.getMatrix());
        if (checkRecipes.isPresent()) {
            this.cachedRecipe.put(player.getUniqueId(), checkRecipes.get());
            craftingInventory.setResult(checkRecipes.get().getResult());
            Bukkit.getScheduler().runTaskLater((Plugin)MMOItems.plugin, () -> {
                craftingInventory.setItem(0, checkRecipes.get().getResult());
                player.updateInventory();
            }, 1L);
        }
    }
    
    public Optional<CachedRecipe> checkRecipes(final Player player, final ItemStack[] array) {
        for (final CustomRecipe customRecipe : MMOItems.plugin.getRecipes().getLegacyCustomRecipes()) {
            if (customRecipe.fitsPlayerCrafting() || array.length != 4) {
                if (!customRecipe.checkPermission(player)) {
                    continue;
                }
                boolean b = true;
                for (int length = array.length, i = 0; i < length; ++i) {
                    if (array[i] != null) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    return Optional.empty();
                }
                final CachedRecipe value = new CachedRecipe();
                boolean b2 = true;
                final ArrayList<Integer> list = new ArrayList<Integer>();
                for (final Map.Entry<Integer, WorkbenchIngredient> entry : customRecipe.getIngredients()) {
                    if (customRecipe.isShapeless()) {
                        boolean b3 = false;
                        int n = 0;
                        for (int j = 0; j < array.length; ++j) {
                            if (!list.contains(j)) {
                                final ItemStack itemStack = array[j];
                                if (itemStack == null) {
                                    list.add(j);
                                }
                                else {
                                    ++n;
                                    if (entry.getValue().matches(itemStack)) {
                                        value.add(j, entry.getValue().getAmount());
                                        list.add(j);
                                        b3 = true;
                                    }
                                    value.clean();
                                    if (n > customRecipe.getIngredients().size()) {
                                        b3 = false;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!b3) {
                            b2 = false;
                        }
                    }
                    else if (!entry.getValue().matches(array[entry.getKey()])) {
                        b2 = false;
                    }
                    else {
                        value.add(entry.getKey(), entry.getValue().getAmount());
                    }
                    if (!b2) {
                        break;
                    }
                }
                if (b2) {
                    value.setResult(customRecipe.getResult(player));
                    return Optional.of(value);
                }
                continue;
            }
        }
        return Optional.empty();
    }
}
