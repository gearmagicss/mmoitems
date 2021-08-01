// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems;

import org.bukkit.command.ConsoleCommandSender;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackMessage;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.ItemTier;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import javax.annotation.Nullable;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.comp.rpg.DefaultHook;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.bukkit.event.HandlerList;
import org.apache.commons.lang.Validate;
import java.io.File;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.gui.PluginInventory;
import net.Indyuce.mmoitems.api.SoulboundInfo;
import net.Indyuce.mmoitems.api.ConfigFile;
import java.util.Iterator;
import java.util.logging.Logger;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.CommandExecutor;
import net.Indyuce.mmoitems.command.MMOItemsCommandTreeRoot;
import net.Indyuce.mmoitems.listener.CraftingListener;
import java.util.function.Consumer;
import net.Indyuce.mmoitems.comp.MMOItemsRewardTypes;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.comp.RealDualWieldHook;
import net.Indyuce.mmoitems.comp.itemglow.NoGlowListener;
import net.Indyuce.mmoitems.comp.itemglow.ItemGlowListener;
import net.Indyuce.mmoitems.comp.parse.placeholders.PlaceholderAPIParser;
import net.Indyuce.mmoitems.comp.holograms.TrHologramPlugin;
import net.Indyuce.mmoitems.comp.holograms.HologramsPlugin;
import net.Indyuce.mmoitems.comp.holograms.CMIPlugin;
import net.Indyuce.mmoitems.comp.holograms.HolographicDisplaysPlugin;
import net.Indyuce.mmoitems.comp.parse.IridescentParser;
import net.Indyuce.mmoitems.comp.inventory.OrnamentPlayerInventory;
import net.Indyuce.mmoitems.comp.inventory.RPGInventoryHook;
import net.Indyuce.mmoitems.comp.inventory.PlayerInventory;
import net.Indyuce.mmoitems.comp.inventory.DefaultPlayerInventory;
import net.Indyuce.mmoitems.comp.McMMONonRPGHook;
import net.Indyuce.mmoitems.comp.flags.ResidenceFlags;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.crafting.MMOItemUIFilter;
import net.Indyuce.mmoitems.comp.PhatLootsHook;
import net.Indyuce.mmoitems.listener.CustomBlockListener;
import net.Indyuce.mmoitems.listener.ElementListener;
import net.Indyuce.mmoitems.gui.listener.GuiListener;
import net.Indyuce.mmoitems.listener.DisableInteractions;
import net.Indyuce.mmoitems.listener.DurabilityListener;
import net.Indyuce.mmoitems.listener.CustomSoundListener;
import net.Indyuce.mmoitems.listener.PlayerListener;
import net.Indyuce.mmoitems.listener.ItemListener;
import net.Indyuce.mmoitems.listener.ItemUse;
import net.Indyuce.mmoitems.api.util.MMOItemReforger;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.comp.mmoinventory.MMOInventorySupport;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.comp.mythicmobs.LootsplosionListener;
import net.Indyuce.mmoitems.comp.mythicmobs.MythicMobsLoader;
import java.util.Objects;
import net.Indyuce.mmoitems.gui.edition.recipe.RecipeBrowserGUI;
import net.Indyuce.mmoitems.comp.MMOItemsMetrics;
import org.bukkit.plugin.java.JavaPlugin;
import io.lumine.mythic.lib.version.SpigotPlugin;
import net.Indyuce.mmoitems.comp.AdvancedEnchantmentsHook;
import net.Indyuce.mmoitems.comp.rpg.McMMOHook;
import net.Indyuce.mmoitems.comp.mmocore.MMOCoreMMOLoader;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.comp.WorldEditSupport;
import java.util.logging.Level;
import net.Indyuce.mmoitems.comp.flags.WorldGuardFlags;
import net.Indyuce.mmoitems.comp.flags.DefaultFlags;
import net.Indyuce.mmoitems.comp.parse.placeholders.DefaultPlaceholderParser;
import java.util.ArrayList;
import net.Indyuce.mmoitems.comp.mythicenchants.MythicEnchantsSupport;
import net.Indyuce.mmoitems.comp.rpg.RPGHandler;
import net.Indyuce.mmoitems.comp.eco.VaultSupport;
import net.Indyuce.mmoitems.comp.holograms.HologramSupport;
import net.Indyuce.mmoitems.comp.flags.FlagPlugin;
import net.Indyuce.mmoitems.comp.parse.placeholders.PlaceholderParser;
import net.Indyuce.mmoitems.listener.EquipListener;
import net.Indyuce.mmoitems.manager.SetManager;
import net.Indyuce.mmoitems.manager.StatManager;
import net.Indyuce.mmoitems.manager.TierManager;
import net.Indyuce.mmoitems.manager.BlockManager;
import net.Indyuce.mmoitems.manager.ConfigManager;
import net.Indyuce.mmoitems.manager.UpgradeManager;
import net.Indyuce.mmoitems.manager.WorldGenManager;
import net.Indyuce.mmoitems.manager.DropTableManager;
import net.Indyuce.mmoitems.comp.parse.StringInputParser;
import java.util.List;
import net.Indyuce.mmoitems.comp.inventory.PlayerInventoryHandler;
import net.Indyuce.mmoitems.manager.ItemManager;
import net.Indyuce.mmoitems.manager.TypeManager;
import net.Indyuce.mmoitems.manager.LayoutManager;
import net.Indyuce.mmoitems.manager.RecipeManager;
import net.Indyuce.mmoitems.manager.EntityManager;
import net.Indyuce.mmoitems.manager.AbilityManager;
import net.Indyuce.mmoitems.manager.TemplateManager;
import net.Indyuce.mmoitems.manager.LoreFormatManager;
import net.Indyuce.mmoitems.manager.CraftingManager;
import net.Indyuce.mmoitems.manager.PluginUpdateManager;
import io.lumine.mythic.utils.plugin.LuminePlugin;

public class MMOItems extends LuminePlugin
{
    public static MMOItems plugin;
    public static final int INTERNAL_REVISION_ID = 1;
    private final PluginUpdateManager pluginUpdateManager;
    private final CraftingManager stationRecipeManager;
    private final LoreFormatManager formatManager;
    private final TemplateManager templateManager;
    private final AbilityManager abilityManager;
    private final EntityManager entityManager;
    private final RecipeManager recipeManager;
    private final LayoutManager layoutManager;
    private final TypeManager typeManager;
    private final ItemManager itemManager;
    private final PlayerInventoryHandler inventory;
    private final List<StringInputParser> stringInputParsers;
    private DropTableManager dropTableManager;
    private WorldGenManager worldGenManager;
    private UpgradeManager upgradeManager;
    private ConfigManager configManager;
    private BlockManager blockManager;
    private TierManager tierManager;
    private StatManager statManager;
    private SetManager setManager;
    private EquipListener equipListener;
    private PlaceholderParser placeholderParser;
    private FlagPlugin flagPlugin;
    private HologramSupport hologramSupport;
    private VaultSupport vaultSupport;
    private RPGHandler rpgPlugin;
    private MythicEnchantsSupport mythicEnchantsSupport;
    
    public MMOItems() {
        this.pluginUpdateManager = new PluginUpdateManager();
        this.stationRecipeManager = new CraftingManager();
        this.formatManager = new LoreFormatManager();
        this.templateManager = new TemplateManager();
        this.abilityManager = new AbilityManager();
        this.entityManager = new EntityManager();
        this.recipeManager = new RecipeManager();
        this.layoutManager = new LayoutManager();
        this.typeManager = new TypeManager();
        this.itemManager = new ItemManager();
        this.inventory = new PlayerInventoryHandler();
        this.stringInputParsers = new ArrayList<StringInputParser>();
        this.placeholderParser = new DefaultPlaceholderParser();
        this.flagPlugin = new DefaultFlags();
    }
    
    public void load() {
        MMOItems.plugin = this;
        if (this.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            try {
                this.flagPlugin = new WorldGuardFlags();
                this.getLogger().log(Level.INFO, "Hooked onto WorldGuard");
            }
            catch (Exception ex) {
                this.getLogger().log(Level.WARNING, "Could not initialize support with WorldGuard 7: " + ex.getMessage());
            }
        }
        if (this.getServer().getPluginManager().getPlugin("WorldEdit") != null) {
            try {
                new WorldEditSupport();
                this.getLogger().log(Level.INFO, "Hooked onto WorldEdit");
            }
            catch (Exception ex2) {
                this.getLogger().log(Level.WARNING, "Could not initialize support with WorldEdit 7: " + ex2.getMessage());
            }
        }
        this.saveDefaultConfig();
        this.statManager = new StatManager();
        this.typeManager.reload();
        this.templateManager.preloadTemplates();
        if (Bukkit.getPluginManager().getPlugin("MMOCore") != null) {
            new MMOCoreMMOLoader();
        }
        if (Bukkit.getPluginManager().getPlugin("mcMMO") != null) {
            this.statManager.register(McMMOHook.disableMcMMORepair);
        }
        if (Bukkit.getPluginManager().getPlugin("AdvancedEnchantments") != null) {
            this.statManager.register(AdvancedEnchantmentsHook.ADVANCED_ENCHANTMENTS);
        }
        if (Bukkit.getPluginManager().getPlugin("MythicEnchants") != null) {
            this.mythicEnchantsSupport = new MythicEnchantsSupport();
        }
    }
    
    public void enable() {
        new SpigotPlugin(39267, (JavaPlugin)this).checkForUpdate();
        new MMOItemsMetrics();
        RecipeBrowserGUI.registerNativeRecipes();
        this.abilityManager.initialize();
        this.configManager = new ConfigManager();
        final int i = this.getConfig().contains("config-version", true) ? this.getConfig().getInt("config-version") : -1;
        final int int1 = this.getConfig().getDefaults().getInt("config-version");
        if (i == int1) {
            Objects.requireNonNull(MMOItems.plugin.getLanguage());
        }
        else {
            this.getLogger().warning("You may be using an outdated config.yml!");
            final Logger logger = this.getLogger();
            final StringBuilder append = new StringBuilder().append("(Your config version: '").append(i).append("' | Expected config version: '");
            Objects.requireNonNull(MMOItems.plugin.getLanguage());
            logger.warning(append.append((Object)int1).append("')").toString());
        }
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
            new MythicMobsLoader();
            if (this.getConfig().getBoolean("lootsplosion.enabled")) {
                Bukkit.getPluginManager().registerEvents((Listener)new LootsplosionListener(), (Plugin)this);
            }
            this.getLogger().log(Level.INFO, "Hooked onto MythicMobs");
        }
        if (Bukkit.getPluginManager().getPlugin("MMOInventory") != null) {
            new MMOInventorySupport();
            this.getLogger().log(Level.INFO, "Hooked onto MMOInventory");
        }
        this.findRpgPlugin();
        this.formatManager.reload();
        this.tierManager = new TierManager();
        this.setManager = new SetManager();
        this.upgradeManager = new UpgradeManager();
        this.templateManager.postloadTemplates();
        this.dropTableManager = new DropTableManager();
        this.worldGenManager = new WorldGenManager();
        this.blockManager = new BlockManager();
        this.equipListener = new EquipListener();
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            this.vaultSupport = new VaultSupport();
        }
        this.getLogger().log(Level.INFO, "Loading crafting stations, please wait..");
        this.layoutManager.reload();
        this.stationRecipeManager.reload();
        NumericStatFormula.reload();
        MMOItemReforger.reload();
        Bukkit.getPluginManager().registerEvents((Listener)this.entityManager, (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)this.dropTableManager, (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ItemUse(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ItemListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new CustomSoundListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new DurabilityListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new DisableInteractions(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new GuiListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ElementListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new CustomBlockListener(), (Plugin)this);
        if (Bukkit.getPluginManager().getPlugin("PhatLoots") != null) {
            Bukkit.getPluginManager().registerEvents((Listener)new PhatLootsHook(), (Plugin)this);
        }
        MMOItemUIFilter.register();
        Bukkit.getScheduler().runTaskTimer((Plugin)this, () -> Bukkit.getOnlinePlayers().forEach(offlinePlayer -> PlayerData.get(offlinePlayer).updateStats()), 100L, 20L);
        Objects.requireNonNull(MMOItems.plugin.getLanguage());
        final Iterator<Player> iterator;
        Bukkit.getScheduler().runTaskTimer((Plugin)this, () -> {
            Bukkit.getOnlinePlayers().iterator();
            while (iterator.hasNext()) {
                PlayerData.get((OfflinePlayer)iterator.next()).getInventory().updateCheck();
            }
            return;
        }, 100L, (long)this.getConfig().getInt("inventory-update-delay"));
        if (Bukkit.getPluginManager().getPlugin("Residence") != null) {
            this.flagPlugin = new ResidenceFlags();
            this.getLogger().log(Level.INFO, "Hooked onto Residence");
        }
        if (Bukkit.getPluginManager().getPlugin("mcMMO") != null) {
            Bukkit.getPluginManager().registerEvents((Listener)new McMMONonRPGHook(), (Plugin)this);
        }
        this.getInventory().register(new DefaultPlayerInventory());
        if (Bukkit.getPluginManager().getPlugin("RPGInventory") != null) {
            this.getInventory().register(new RPGInventoryHook());
            this.getLogger().log(Level.INFO, "Hooked onto RPGInventory");
        }
        if (MMOItems.plugin.getConfig().getBoolean("iterate-whole-inventory")) {
            this.getInventory().register(new OrnamentPlayerInventory());
        }
        if (Bukkit.getPluginManager().getPlugin("AdvancedEnchantments") != null) {
            Bukkit.getPluginManager().registerEvents((Listener)new AdvancedEnchantmentsHook(), (Plugin)this);
            this.getLogger().log(Level.INFO, "Hooked onto AdvancedEnchantments");
        }
        if (Bukkit.getPluginManager().getPlugin("Iridescent") != null) {
            this.stringInputParsers.add(new IridescentParser());
            this.getLogger().log(Level.INFO, "Hooked onto Iridescent");
        }
        if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
            this.hologramSupport = new HolographicDisplaysPlugin();
            this.getLogger().log(Level.INFO, "Hooked onto HolographicDisplays");
        }
        else if (Bukkit.getPluginManager().getPlugin("CMI") != null) {
            this.hologramSupport = new CMIPlugin();
            this.getLogger().log(Level.INFO, "Hooked onto CMI Holograms");
        }
        else if (Bukkit.getPluginManager().getPlugin("Holograms") != null) {
            this.hologramSupport = new HologramsPlugin();
            this.getLogger().log(Level.INFO, "Hooked onto Holograms");
        }
        else if (Bukkit.getPluginManager().getPlugin("TrHologram") != null) {
            this.hologramSupport = new TrHologramPlugin();
            this.getLogger().log(Level.INFO, "Hooked onto TrHologram");
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.getLogger().log(Level.INFO, "Hooked onto PlaceholderAPI");
            this.placeholderParser = new PlaceholderAPIParser();
        }
        if (this.getConfig().getBoolean("item-glow")) {
            if (Bukkit.getPluginManager().getPlugin("GlowAPI") != null && Bukkit.getPluginManager().getPlugin("PacketListenerApi") != null) {
                Bukkit.getPluginManager().registerEvents((Listener)new ItemGlowListener(), (Plugin)this);
                this.getLogger().log(Level.INFO, "Hooked onto GlowAPI (Item Glow)");
            }
            else {
                Bukkit.getPluginManager().registerEvents((Listener)new NoGlowListener(), (Plugin)this);
            }
        }
        if (Bukkit.getPluginManager().getPlugin("RealDualWield") != null) {
            Bukkit.getPluginManager().registerEvents((Listener)new RealDualWieldHook(), (Plugin)this);
            this.getLogger().log(Level.INFO, "Hooked onto RealDualWield");
        }
        if (Bukkit.getPluginManager().getPlugin("BossShopPro") != null) {
            this.getLogger().log(Level.INFO, "Hooked onto BossShopPro");
            new BukkitRunnable() {
                public void run() {
                    try {
                        new MMOItemsRewardTypes().register();
                    }
                    catch (NullPointerException ex) {
                        MMOItems.this.getLogger().log(Level.INFO, "Could not Hook onto BossShopPro");
                    }
                }
            }.runTaskLater((Plugin)this, 1L);
        }
        Bukkit.getScheduler().runTask((Plugin)this, () -> Bukkit.getOnlinePlayers().forEach(PlayerData::load));
        boolean boolean1 = this.getConfig().getBoolean("recipes.use-recipe-book");
        boolean boolean2 = this.getConfig().getBoolean("recipes.recipe-amounts");
        if (boolean1 && boolean2) {
            this.getLogger().warning("Tried to enable recipe book while amounts are active!");
            this.getLogger().warning("Please use only ONE of these options!");
            this.getLogger().warning("Disabling both options for now...");
            boolean1 = false;
            boolean2 = false;
        }
        this.recipeManager.load(boolean1, boolean2);
        if (boolean2) {
            Bukkit.getPluginManager().registerEvents((Listener)new CraftingListener(), (Plugin)this);
        }
        this.getLogger().log(Level.INFO, "Loading recipes, please wait...");
        this.recipeManager.loadRecipes();
        final MMOItemsCommandTreeRoot mmoItemsCommandTreeRoot = new MMOItemsCommandTreeRoot();
        this.getCommand("mmoitems").setExecutor((CommandExecutor)mmoItemsCommandTreeRoot);
        this.getCommand("mmoitems").setTabCompleter((TabCompleter)mmoItemsCommandTreeRoot);
    }
    
    public void disable() {
        PlayerData.getLoaded().forEach(PlayerData::save);
        final ConfigFile configFile = new ConfigFile("/dynamic", "updater");
        configFile.getConfig().getKeys(false).forEach(s -> configFile.getConfig().set(s, (Object)null));
        configFile.save();
        SoulboundInfo.getAbandonnedInfo().forEach(SoulboundInfo::dropItems);
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory() != null && player.getOpenInventory().getTopInventory().getHolder() instanceof PluginInventory) {
                player.closeInventory();
            }
        }
    }
    
    public String getPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "MMOItems" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    }
    
    public File getJarFile() {
        return MMOItems.plugin.getFile();
    }
    
    public CraftingManager getCrafting() {
        return this.stationRecipeManager;
    }
    
    public LayoutManager getLayouts() {
        return this.layoutManager;
    }
    
    public SetManager getSets() {
        return this.setManager;
    }
    
    public FlagPlugin getFlags() {
        return this.flagPlugin;
    }
    
    public void setFlags(final FlagPlugin flagPlugin) {
        this.flagPlugin = flagPlugin;
    }
    
    public RPGHandler getRPG() {
        return this.rpgPlugin;
    }
    
    public void setRPG(final RPGHandler rpgPlugin) {
        Validate.notNull((Object)rpgPlugin, "RPGHandler cannot be null");
        if (this.rpgPlugin != null && this.rpgPlugin instanceof Listener && this.isEnabled()) {
            HandlerList.unregisterAll((Listener)this.rpgPlugin);
        }
        this.rpgPlugin = rpgPlugin;
        if (rpgPlugin instanceof Listener && this.isEnabled()) {
            Bukkit.getPluginManager().registerEvents((Listener)rpgPlugin, (Plugin)this);
        }
    }
    
    public PluginUpdateManager getUpdates() {
        return this.pluginUpdateManager;
    }
    
    public PlayerInventoryHandler getInventory() {
        return this.inventory;
    }
    
    public void registerPlayerInventory(final PlayerInventory playerInventory) {
        this.getInventory().register(playerInventory);
    }
    
    @Deprecated
    public void setPlayerInventory(final PlayerInventory playerInventory) {
        this.getInventory().unregisterAll();
        this.getInventory().register(playerInventory);
    }
    
    public StatManager getStats() {
        return this.statManager;
    }
    
    public TierManager getTiers() {
        return this.tierManager;
    }
    
    public EntityManager getEntities() {
        return this.entityManager;
    }
    
    public DropTableManager getDropTables() {
        return this.dropTableManager;
    }
    
    public AbilityManager getAbilities() {
        return this.abilityManager;
    }
    
    public BlockManager getCustomBlocks() {
        return this.blockManager;
    }
    
    public WorldGenManager getWorldGen() {
        return this.worldGenManager;
    }
    
    public RecipeManager getRecipes() {
        return this.recipeManager;
    }
    
    public ConfigManager getLanguage() {
        return this.configManager;
    }
    
    public TypeManager getTypes() {
        return this.typeManager;
    }
    
    public UpgradeManager getUpgrades() {
        return this.upgradeManager;
    }
    
    public PlaceholderParser getPlaceholderParser() {
        return this.placeholderParser;
    }
    
    public HologramSupport getHolograms() {
        return this.hologramSupport;
    }
    
    public EquipListener getEquipListener() {
        return this.equipListener;
    }
    
    public TemplateManager getTemplates() {
        return this.templateManager;
    }
    
    public LoreFormatManager getFormats() {
        return this.formatManager;
    }
    
    @Deprecated
    public ItemManager getItems() {
        return this.itemManager;
    }
    
    public boolean hasPermissions() {
        return this.vaultSupport != null && this.vaultSupport.getPermissions() != null;
    }
    
    public MythicEnchantsSupport getMythicEnchantsSupport() {
        return this.mythicEnchantsSupport;
    }
    
    public boolean hasEconomy() {
        return this.vaultSupport != null && this.vaultSupport.getEconomy() != null;
    }
    
    public VaultSupport getVault() {
        return this.vaultSupport;
    }
    
    public List<StringInputParser> getStringInputParsers() {
        return this.stringInputParsers;
    }
    
    public void findRpgPlugin() {
        if (this.rpgPlugin != null) {
            return;
        }
        final String string = MMOItems.plugin.getConfig().getString("preferred-rpg-provider", (String)null);
        if (string != null) {
            try {
                final RPGHandler.PluginEnum value = RPGHandler.PluginEnum.valueOf(string.toUpperCase());
                if (Bukkit.getPluginManager().getPlugin(value.getName()) != null) {
                    this.setRPG(value.load());
                    print(null, "Using $s{0}$b as RPGPlayer provider", "RPG Provider", value.getName());
                    return;
                }
                print(null, "Preferred RPGPlayer provider $r{0}$b is not installed!", "RPG Provider", string);
            }
            catch (IllegalArgumentException ex) {
                final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
                friendlyFeedbackProvider.activatePrefix(true, "RPG Provider");
                friendlyFeedbackProvider.log(FriendlyFeedbackCategory.ERROR, "Invalid RPG Provider '$u{0}$b' --- These are the supported ones:", new String[] { string });
                final RPGHandler.PluginEnum[] values = RPGHandler.PluginEnum.values();
                for (int length = values.length, i = 0; i < length; ++i) {
                    friendlyFeedbackProvider.log(FriendlyFeedbackCategory.ERROR, " $r+ $b{0}", new String[] { values[i].getName() });
                }
                friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.ERROR, getConsole());
            }
        }
        for (final RPGHandler.PluginEnum pluginEnum : RPGHandler.PluginEnum.values()) {
            if (Bukkit.getPluginManager().getPlugin(pluginEnum.getName()) != null) {
                this.setRPG(pluginEnum.load());
                print(null, "Using $s{0}$b as RPGPlayer provider", "RPG Provider", pluginEnum.getName());
                return;
            }
        }
        this.setRPG(new DefaultHook());
    }
    
    @Nullable
    public MMOItem getMMOItem(@Nullable final Type type, @Nullable final String s, @Nullable final PlayerData playerData) {
        final MMOItemTemplate template = this.getTemplates().getTemplate(type, s);
        if (template == null) {
            return null;
        }
        return template.newBuilder(playerData).build();
    }
    
    @Nullable
    public ItemStack getItem(@NotNull final Type type, @NotNull final String s, @NotNull final PlayerData playerData) {
        Validate.notNull((Object)type, "Type cannot be null");
        Validate.notNull((Object)s, "ID cannot be null");
        final MMOItem mmoItem = this.getMMOItem(type, s, playerData);
        if (mmoItem == null) {
            return null;
        }
        return mmoItem.newBuilder().build();
    }
    
    @Nullable
    public MMOItem getMMOItem(@NotNull final Type type, @NotNull final String s, final int n, @Nullable final ItemTier itemTier) {
        Validate.notNull((Object)type, "Type cannot be null");
        Validate.notNull((Object)s, "ID cannot be null");
        final MMOItemTemplate template = this.getTemplates().getTemplate(type, s);
        if (template == null) {
            return null;
        }
        return template.newBuilder(n, itemTier).build();
    }
    
    @Nullable
    public ItemStack getItem(@NotNull final Type type, @NotNull final String s, final int n, @Nullable final ItemTier itemTier) {
        Validate.notNull((Object)type, "Type cannot be null");
        Validate.notNull((Object)s, "ID cannot be null");
        final MMOItem mmoItem = this.getMMOItem(type, s, n, itemTier);
        if (mmoItem == null) {
            return null;
        }
        return mmoItem.newBuilder().build();
    }
    
    @Nullable
    public MMOItem getMMOItem(@NotNull final Type type, @NotNull final String s) {
        Validate.notNull((Object)type, "Type cannot be null");
        Validate.notNull((Object)s, "ID cannot be null");
        final MMOItemTemplate template = this.getTemplates().getTemplate(type, s);
        if (template == null) {
            return null;
        }
        return template.newBuilder(0, null).build();
    }
    
    @Nullable
    public ItemStack getItem(@Nullable final String s, @Nullable final String s2) {
        if (s == null || s2 == null) {
            return null;
        }
        return this.getItem(this.getTypes().get(s), s2);
    }
    
    @Nullable
    public ItemStack getItem(@Nullable final Type type, @Nullable final String s) {
        if (type == null || s == null) {
            return null;
        }
        final MMOItem mmoItem = this.getMMOItem(type, s);
        if (mmoItem == null) {
            return null;
        }
        return mmoItem.newBuilder().build();
    }
    
    @Nullable
    public static Type getType(@Nullable final ItemStack itemStack) {
        return getType(NBTItem.get(itemStack));
    }
    
    @Nullable
    public static Type getType(@Nullable final NBTItem nbtItem) {
        return MMOItems.plugin.getTypes().get(getTypeName(nbtItem));
    }
    
    @Nullable
    public static String getTypeName(@Nullable final ItemStack itemStack) {
        return getTypeName(NBTItem.get(itemStack));
    }
    
    @Nullable
    public static String getTypeName(@Nullable final NBTItem nbtItem) {
        if (nbtItem == null) {
            return null;
        }
        if (!nbtItem.hasType()) {
            return null;
        }
        return nbtItem.getType();
    }
    
    @Nullable
    public static String getID(@Nullable final ItemStack itemStack) {
        return getID(NBTItem.get(itemStack));
    }
    
    @Nullable
    public static String getID(@Nullable final NBTItem nbtItem) {
        if (nbtItem == null) {
            return null;
        }
        return nbtItem.getString("MMOITEMS_ITEM_ID");
    }
    
    public static void log(@Nullable String str) {
        if (str == null) {
            str = "< null >";
        }
        MMOItems.plugin.getServer().getConsoleSender().sendMessage("" + str);
    }
    
    public static void print(@Nullable final Level level, @Nullable String s, @Nullable final String s2, @NotNull final String... array) {
        if (s == null) {
            s = "< null >";
        }
        if (level != null) {
            MMOItems.plugin.getLogger().log(level, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), s, array));
        }
        else {
            getConsole().sendMessage(FriendlyFeedbackProvider.generateMessage(new FriendlyFeedbackMessage("", s2), s, array).forConsole((FriendlyFeedbackPalette)FFPMMOItems.get()));
        }
    }
    
    @NotNull
    public static ConsoleCommandSender getConsole() {
        return MMOItems.plugin.getServer().getConsoleSender();
    }
}
