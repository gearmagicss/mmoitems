// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.Optional;
import java.util.List;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.api.ability.Ability;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.AbilityListEdition;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import com.google.gson.JsonElement;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import net.Indyuce.mmoitems.stat.data.AbilityListData;
import com.google.gson.JsonArray;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.random.RandomAbilityListData;
import net.Indyuce.mmoitems.stat.data.random.RandomAbilityData;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import java.text.DecimalFormat;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class Abilities extends ItemStat
{
    private final DecimalFormat modifierFormat;
    
    public Abilities() {
        super("ABILITY", Material.BLAZE_POWDER, "Item Abilities", new String[] { "Make your item cast amazing abilities", "to kill monsters or buff yourself." }, new String[] { "!block", "all" }, new Material[0]);
        this.modifierFormat = new DecimalFormat("0.#");
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a valid config section");
        final ConfigurationSection configurationSection = (ConfigurationSection)o;
        final RandomAbilityListData randomAbilityListData = new RandomAbilityListData(new RandomAbilityData[0]);
        final Iterator iterator = configurationSection.getKeys(false).iterator();
        while (iterator.hasNext()) {
            randomAbilityListData.add(new RandomAbilityData(configurationSection.getConfigurationSection((String)iterator.next())));
        }
        return randomAbilityListData;
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder p0, @NotNull final StatData p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: astore_3       
        //     8: getstatic       net/Indyuce/mmoitems/MMOItems.plugin:Lnet/Indyuce/mmoitems/MMOItems;
        //    11: invokevirtual   net/Indyuce/mmoitems/MMOItems.getLanguage:()Lnet/Indyuce/mmoitems/manager/ConfigManager;
        //    14: getfield        net/Indyuce/mmoitems/manager/ConfigManager.abilitySplitter:Ljava/lang/String;
        //    17: ldc             ""
        //    19: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    22: ifne            29
        //    25: iconst_1       
        //    26: goto            30
        //    29: iconst_0       
        //    30: istore          4
        //    32: ldc             "ability-modifier"
        //    34: invokestatic    net/Indyuce/mmoitems/stat/type/ItemStat.translate:(Ljava/lang/String;)Ljava/lang/String;
        //    37: astore          5
        //    39: ldc             "ability-format"
        //    41: invokestatic    net/Indyuce/mmoitems/stat/type/ItemStat.translate:(Ljava/lang/String;)Ljava/lang/String;
        //    44: astore          6
        //    46: aload_2        
        //    47: checkcast       Lnet/Indyuce/mmoitems/stat/data/AbilityListData;
        //    50: invokevirtual   net/Indyuce/mmoitems/stat/data/AbilityListData.getAbilities:()Ljava/util/Set;
        //    53: aload_0        
        //    54: aload_3        
        //    55: aload           6
        //    57: aload_1        
        //    58: aload           5
        //    60: iload           4
        //    62: invokedynamic   BootstrapMethod #0, accept:(Lnet/Indyuce/mmoitems/stat/Abilities;Ljava/util/List;Ljava/lang/String;Lnet/Indyuce/mmoitems/api/item/build/ItemStackBuilder;Ljava/lang/String;Z)Ljava/util/function/Consumer;
        //    67: invokeinterface java/util/Set.forEach:(Ljava/util/function/Consumer;)V
        //    72: iload           4
        //    74: ifeq            101
        //    77: aload_3        
        //    78: invokeinterface java/util/List.size:()I
        //    83: ifle            101
        //    86: aload_3        
        //    87: aload_3        
        //    88: invokeinterface java/util/List.size:()I
        //    93: iconst_1       
        //    94: isub           
        //    95: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   100: pop            
        //   101: aload_1        
        //   102: invokevirtual   net/Indyuce/mmoitems/api/item/build/ItemStackBuilder.getLore:()Lnet/Indyuce/mmoitems/api/item/build/LoreBuilder;
        //   105: ldc             "abilities"
        //   107: aload_3        
        //   108: invokevirtual   net/Indyuce/mmoitems/api/item/build/LoreBuilder.insert:(Ljava/lang/String;Ljava/util/List;)V
        //   111: aload_1        
        //   112: aload_0        
        //   113: aload_2        
        //   114: invokevirtual   net/Indyuce/mmoitems/stat/Abilities.getAppliedNBT:(Lnet/Indyuce/mmoitems/stat/data/type/StatData;)Ljava/util/ArrayList;
        //   117: invokevirtual   net/Indyuce/mmoitems/api/item/build/ItemStackBuilder.addItemTag:(Ljava/util/List;)V
        //   120: return         
        //    StackMapTable: 00 03 FC 00 1D 07 00 88 40 01 FE 00 46 01 07 00 24 07 00 24
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
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        final JsonArray jsonArray = new JsonArray();
        final Iterator<AbilityData> iterator = ((AbilityListData)statData).getAbilities().iterator();
        while (iterator.hasNext()) {
            jsonArray.add((JsonElement)iterator.next().toJson());
        }
        list.add(new ItemTag(this.getNBTPath(), (Object)jsonArray.toString()));
        return list;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        new AbilityListEdition(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String str = (String)array[0];
        final String str2 = (String)array[1];
        final String replaceAll = s.toUpperCase().replace("-", "_").replace(" ", "_").replaceAll("[^A-Z_]", "");
        if (str2.equals("ability")) {
            Validate.isTrue(MMOItems.plugin.getAbilities().hasAbility(replaceAll), "format is not a valid ability! You may check the ability list using /mi list ability.");
            final Ability ability = MMOItems.plugin.getAbilities().getAbility(replaceAll);
            editionInventory.getEditedSection().set("ability." + str, (Object)null);
            editionInventory.getEditedSection().set("ability." + str + ".type", (Object)replaceAll);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully set the ability to " + ChatColor.GOLD + ability.getName() + ChatColor.GRAY + ".");
            return;
        }
        if (str2.equals("mode")) {
            final Ability.CastingMode value = Ability.CastingMode.valueOf(replaceAll);
            Validate.isTrue(MMOItems.plugin.getAbilities().getAbility(editionInventory.getEditedSection().getString("ability." + str + ".type").toUpperCase().replace("-", "_").replace(" ", "_").replaceAll("[^A-Z_]", "")).isAllowedMode(value), "This ability does not support this casting mode.");
            editionInventory.getEditedSection().set("ability." + str + ".mode", (Object)value.name());
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully set the casting mode to " + ChatColor.GOLD + value.getName() + ChatColor.GRAY + ".");
            return;
        }
        new NumericStatFormula(s).fillConfigurationSection(editionInventory.getEditedSection(), "ability." + str + "." + str2, NumericStatFormula.FormulaSaveOption.NONE);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GOLD + MMOUtils.caseOnWords(str2.replace("-", " ")) + ChatColor.GRAY + " successfully added.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        list.add(ChatColor.GRAY + "Current Abilities: " + ChatColor.GOLD + (optional.isPresent() ? optional.get().getAbilities().size() : 0));
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to edit the item abilities.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new AbilityListData(new AbilityData[0]);
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING));
        }
        final AbilityListData abilityListData = (AbilityListData)this.getLoadedNBT(list);
        if (abilityListData != null) {
            readMMOItem.setData(this, abilityListData);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            try {
                final AbilityListData abilityListData = new AbilityListData(new AbilityData[0]);
                for (final JsonElement jsonElement : new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonArray()) {
                    if (jsonElement.isJsonObject()) {
                        abilityListData.add(new AbilityData(jsonElement.getAsJsonObject()));
                    }
                }
                return abilityListData;
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
}
