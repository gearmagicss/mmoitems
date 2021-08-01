// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player;

import net.Indyuce.mmoitems.stat.data.PotionEffectData;
import java.util.logging.Level;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.event.AbilityUseEvent;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.comp.flags.FlagPlugin;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.ability.Ability;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.StringListData;
import net.Indyuce.mmoitems.stat.data.AbilityListData;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.stat.data.PotionEffectListData;
import org.bukkit.event.Event;
import net.Indyuce.mmoitems.api.event.RefreshInventoryEvent;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.player.inventory.EquippedPlayerItem;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.player.inventory.EquippedItem;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.Material;
import net.Indyuce.mmoitems.ItemStats;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.ArrayList;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.configuration.file.FileConfiguration;
import net.Indyuce.mmoitems.api.ConfigFile;
import net.Indyuce.mmoitems.MMOItems;
import java.util.HashSet;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.ItemSet;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import net.Indyuce.mmoitems.particle.api.ParticleRunnable;
import java.util.Set;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.Indyuce.mmoitems.api.crafting.CraftingStatus;
import net.Indyuce.mmoitems.api.player.inventory.InventoryUpdateHandler;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import java.util.UUID;
import java.util.Map;

public class PlayerData
{
    private static final Map<UUID, PlayerData> data;
    @NotNull
    private final MMOPlayerData mmoData;
    private RPGPlayer rpgPlayer;
    private final InventoryUpdateHandler inventory;
    private final CraftingStatus craftingStatus;
    private final PlayerAbilityData playerAbilityData;
    private final Map<String, CooldownInformation> abilityCooldowns;
    private final Map<String, Long> itemCooldowns;
    private final Map<CooldownType, Long> extraCooldowns;
    private final Map<PotionEffectType, PotionEffect> permanentEffects;
    private final Set<ParticleRunnable> itemParticles;
    private ParticleRunnable overridingItemParticles;
    private final Set<AbilityData> itemAbilities;
    private boolean fullHands;
    private ItemSet.SetBonuses setBonuses;
    private final PlayerStats stats;
    private final Set<String> permissions;
    
    private PlayerData(@NotNull final MMOPlayerData mmoData) {
        this.inventory = new InventoryUpdateHandler(this);
        this.craftingStatus = new CraftingStatus();
        this.playerAbilityData = new PlayerAbilityData();
        this.abilityCooldowns = new HashMap<String, CooldownInformation>();
        this.itemCooldowns = new HashMap<String, Long>();
        this.extraCooldowns = new HashMap<CooldownType, Long>();
        this.permanentEffects = new HashMap<PotionEffectType, PotionEffect>();
        this.itemParticles = new HashSet<ParticleRunnable>();
        this.overridingItemParticles = null;
        this.itemAbilities = new HashSet<AbilityData>();
        this.fullHands = false;
        this.setBonuses = null;
        this.permissions = new HashSet<String>();
        this.mmoData = mmoData;
        this.rpgPlayer = MMOItems.plugin.getRPG().getInfo(this);
        this.stats = new PlayerStats(this);
        this.load(new ConfigFile("/userdata", this.getUniqueId().toString()).getConfig());
        this.updateInventory();
    }
    
    private void load(final FileConfiguration fileConfiguration) {
        if (fileConfiguration.contains("crafting-queue")) {
            this.craftingStatus.load(this, fileConfiguration.getConfigurationSection("crafting-queue"));
        }
        if (MMOItems.plugin.hasPermissions() && fileConfiguration.contains("permissions-from-items")) {
            final Permission permission;
            fileConfiguration.getStringList("permissions-from-items").forEach(s -> {
                MMOItems.plugin.getVault().getPermissions();
                if (permission.has(this.getPlayer(), s)) {
                    permission.playerRemove(this.getPlayer(), s);
                }
            });
        }
    }
    
    public void save() {
        this.cancelRunnables();
        final ConfigFile configFile = new ConfigFile("/userdata", this.getUniqueId().toString());
        configFile.getConfig().createSection("crafting-queue");
        configFile.getConfig().set("permissions-from-items", (Object)new ArrayList(this.permissions));
        this.craftingStatus.save(configFile.getConfig().getConfigurationSection("crafting-queue"));
        configFile.save();
    }
    
    public MMOPlayerData getMMOPlayerData() {
        return this.mmoData;
    }
    
    public UUID getUniqueId() {
        return this.mmoData.getUniqueId();
    }
    
    public boolean isOnline() {
        return this.mmoData.isOnline();
    }
    
    public Player getPlayer() {
        return this.mmoData.getPlayer();
    }
    
    public RPGPlayer getRPG() {
        return this.rpgPlayer;
    }
    
    public void cancelRunnables() {
        this.itemParticles.forEach(BukkitRunnable::cancel);
        if (this.overridingItemParticles != null) {
            this.overridingItemParticles.cancel();
        }
    }
    
    public boolean areHandsFull() {
        if (!this.mmoData.isOnline()) {
            return false;
        }
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(this.getPlayer().getInventory().getItemInMainHand());
        final NBTItem nbtItem2 = MythicLib.plugin.getVersion().getWrapper().getNBTItem(this.getPlayer().getInventory().getItemInOffHand());
        final boolean boolean1 = nbtItem.getBoolean(ItemStats.TWO_HANDED.getNBTPath());
        final boolean boolean2 = nbtItem2.getBoolean(ItemStats.TWO_HANDED.getNBTPath());
        final boolean b = nbtItem.getItem() != null && nbtItem.getItem().getType() != Material.AIR && !nbtItem.getBoolean(ItemStats.HANDWORN.getNBTPath());
        final boolean b2 = nbtItem2.getItem() != null && nbtItem2.getItem().getType() != Material.AIR && !nbtItem2.getBoolean(ItemStats.HANDWORN.getNBTPath());
        return (boolean1 && b2) || (b && boolean2);
    }
    
    public void setRPGPlayer(final RPGPlayer rpgPlayer) {
        this.rpgPlayer = rpgPlayer;
    }
    
    public void updateInventory() {
        if (!this.mmoData.isOnline()) {
            return;
        }
        this.inventory.getEquipped().clear();
        this.permanentEffects.clear();
        this.itemAbilities.clear();
        this.cancelRunnables();
        this.itemParticles.clear();
        this.overridingItemParticles = null;
        if (MMOItems.plugin.hasPermissions()) {
            final Permission permission;
            this.permissions.forEach(s -> {
                MMOItems.plugin.getVault().getPermissions();
                if (permission.has(this.getPlayer(), s)) {
                    permission.playerRemove(this.getPlayer(), s);
                }
                return;
            });
        }
        this.permissions.clear();
        this.fullHands = this.areHandsFull();
        for (final EquippedItem equippedItem : MMOItems.plugin.getInventory().getInventory(this.getPlayer())) {
            final NBTItem item = equippedItem.getItem();
            if (item.getItem() != null) {
                if (item.getItem().getType() == Material.AIR) {
                    continue;
                }
                final Type value = Type.get(item.getType());
                if (value == null || !equippedItem.matches(value)) {
                    continue;
                }
                if (!this.getRPG().canUse(item, false, false)) {
                    continue;
                }
                this.inventory.getEquipped().add(new EquippedPlayerItem(equippedItem));
            }
        }
        Bukkit.getPluginManager().callEvent((Event)new RefreshInventoryEvent(this.inventory.getEquipped(), this.getPlayer(), this));
        for (final EquippedPlayerItem equippedPlayerItem : this.inventory.getEquipped()) {
            final VolatileMMOItem item2 = equippedPlayerItem.getItem();
            if (item2.hasData(ItemStats.PERM_EFFECTS)) {
                ((PotionEffectListData)item2.getData(ItemStats.PERM_EFFECTS)).getEffects().forEach(potionEffectData -> {
                    if (this.getPermanentPotionEffectAmplifier(potionEffectData.getType()) < potionEffectData.getLevel() - 1) {
                        this.permanentEffects.put(potionEffectData.getType(), potionEffectData.toEffect());
                    }
                    return;
                });
            }
            if (item2.hasData(ItemStats.ITEM_PARTICLES)) {
                final ParticleData particleData = (ParticleData)item2.getData(ItemStats.ITEM_PARTICLES);
                if (particleData.getType().hasPriority()) {
                    if (this.overridingItemParticles == null) {
                        this.overridingItemParticles = particleData.start(this);
                    }
                }
                else {
                    this.itemParticles.add(particleData.start(this));
                }
            }
            if (item2.hasData(ItemStats.ABILITIES) && (MMOItems.plugin.getConfig().getBoolean("abilities-bypass-encumbering", false) || !this.fullHands) && (equippedPlayerItem.getSlot() != EquipmentSlot.OFF_HAND || !MMOItems.plugin.getConfig().getBoolean("disable-abilities-in-offhand"))) {
                this.itemAbilities.addAll(((AbilityListData)item2.getData(ItemStats.ABILITIES)).getAbilities());
            }
            if (MMOItems.plugin.hasPermissions() && item2.hasData(ItemStats.GRANTED_PERMISSIONS)) {
                this.permissions.addAll(((StringListData)item2.getData(ItemStats.GRANTED_PERMISSIONS)).getList());
                final Permission permission2;
                this.permissions.forEach(s2 -> {
                    MMOItems.plugin.getVault().getPermissions();
                    if (!permission2.has(this.getPlayer(), s2)) {
                        permission2.playerAdd(this.getPlayer(), s2);
                    }
                    return;
                });
            }
        }
        int n = 0;
        ItemSet set = null;
        final HashMap<ItemSet, Integer> hashMap = new HashMap<ItemSet, Integer>();
        final Iterator<EquippedPlayerItem> iterator3 = this.inventory.getEquipped().iterator();
        while (iterator3.hasNext()) {
            final ItemSet value2 = MMOItems.plugin.getSets().get(iterator3.next().getItem().getNBT().getString("MMOITEMS_ITEM_SET"));
            if (value2 == null) {
                continue;
            }
            final int i = hashMap.getOrDefault(value2, 0) + 1;
            hashMap.put(value2, i);
            if (i < n) {
                continue;
            }
            n = i;
            set = value2;
        }
        this.setBonuses = ((set == null) ? null : set.getBonuses(n));
        if (this.hasSetBonuses()) {
            this.itemAbilities.addAll(this.setBonuses.getAbilities());
            final Iterator<ParticleData> iterator4 = this.setBonuses.getParticles().iterator();
            while (iterator4.hasNext()) {
                this.itemParticles.add(iterator4.next().start(this));
            }
            for (final PotionEffect potionEffect : this.setBonuses.getPotionEffects()) {
                if (this.getPermanentPotionEffectAmplifier(potionEffect.getType()) < potionEffect.getAmplifier()) {
                    this.permanentEffects.put(potionEffect.getType(), potionEffect);
                }
            }
        }
        this.stats.updateStats();
        MMOItems.plugin.getRPG().refreshStats(this);
        this.inventory.helmet = this.getPlayer().getInventory().getHelmet();
        this.inventory.chestplate = this.getPlayer().getInventory().getChestplate();
        this.inventory.leggings = this.getPlayer().getInventory().getLeggings();
        this.inventory.boots = this.getPlayer().getInventory().getBoots();
        this.inventory.hand = this.getPlayer().getInventory().getItemInMainHand();
        this.inventory.offhand = this.getPlayer().getInventory().getItemInOffHand();
    }
    
    public void updateStats() {
        if (!this.mmoData.isOnline()) {
            return;
        }
        this.permanentEffects.keySet().forEach(potionEffectType -> this.getPlayer().addPotionEffect((PotionEffect)this.permanentEffects.get(potionEffectType)));
        if (this.fullHands) {
            this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, true, false));
        }
    }
    
    public InventoryUpdateHandler getInventory() {
        return this.inventory;
    }
    
    public ItemSet.SetBonuses getSetBonuses() {
        return this.setBonuses;
    }
    
    public boolean hasSetBonuses() {
        return this.setBonuses != null;
    }
    
    public CraftingStatus getCrafting() {
        return this.craftingStatus;
    }
    
    public PlayerAbilityData getAbilityData() {
        return this.playerAbilityData;
    }
    
    public int getPermanentPotionEffectAmplifier(final PotionEffectType potionEffectType) {
        return this.permanentEffects.containsKey(potionEffectType) ? this.permanentEffects.get(potionEffectType).getAmplifier() : -1;
    }
    
    public Collection<PotionEffect> getPermanentPotionEffects() {
        return this.permanentEffects.values();
    }
    
    public PlayerStats getStats() {
        return this.stats;
    }
    
    public Set<AbilityData> getItemAbilities() {
        return this.itemAbilities;
    }
    
    private boolean hasAbility(final Ability.CastingMode castingMode) {
        final Iterator<AbilityData> iterator = this.itemAbilities.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCastingMode() == castingMode) {
                return true;
            }
        }
        return false;
    }
    
    public ItemAttackResult castAbilities(@Nullable final LivingEntity livingEntity, @NotNull final ItemAttackResult itemAttackResult, @NotNull final Ability.CastingMode castingMode) {
        if (!this.hasAbility(castingMode)) {
            return itemAttackResult;
        }
        return this.castAbilities(this.getStats().newTemporary(EquipmentSlot.OTHER), livingEntity, itemAttackResult, castingMode);
    }
    
    public ItemAttackResult castAbilities(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final ItemAttackResult itemAttackResult, final Ability.CastingMode castingMode) {
        if (!this.mmoData.isOnline()) {
            return itemAttackResult;
        }
        Label_0080: {
            if (livingEntity == null) {
                if (MMOItems.plugin.getFlags().isFlagAllowed(this.getPlayer(), FlagPlugin.CustomFlag.MI_ABILITIES)) {
                    break Label_0080;
                }
            }
            else if (MMOItems.plugin.getFlags().isFlagAllowed(livingEntity.getLocation(), FlagPlugin.CustomFlag.MI_ABILITIES) && MMOUtils.canDamage(this.getPlayer(), (Entity)livingEntity)) {
                break Label_0080;
            }
            return itemAttackResult.setSuccessful(false);
        }
        for (final AbilityData abilityData : this.itemAbilities) {
            if (abilityData.getCastingMode() == castingMode) {
                this.cast(cachedStats, livingEntity, itemAttackResult, abilityData);
            }
        }
        return itemAttackResult;
    }
    
    public void cast(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final ItemAttackResult itemAttackResult, final AbilityData abilityData) {
        if (!this.rpgPlayer.canCast(abilityData)) {
            return;
        }
        final AbilityResult whenRan = abilityData.getAbility().whenRan(cachedStats, livingEntity, abilityData, itemAttackResult);
        if (!whenRan.isSuccessful()) {
            return;
        }
        final AbilityUseEvent abilityUseEvent = new AbilityUseEvent(this, abilityData, livingEntity);
        Bukkit.getPluginManager().callEvent((Event)abilityUseEvent);
        if (abilityUseEvent.isCancelled()) {
            return;
        }
        if (abilityData.hasModifier("mana")) {
            this.rpgPlayer.giveMana(-whenRan.getModifier("mana"));
        }
        if (abilityData.hasModifier("stamina")) {
            this.rpgPlayer.giveStamina(-whenRan.getModifier("stamina"));
        }
        final double n = whenRan.getModifier("cooldown") * (1.0 - Math.min(0.8, cachedStats.getStat(ItemStats.COOLDOWN_REDUCTION) / 100.0));
        if (n > 0.0) {
            this.applyAbilityCooldown(abilityData.getAbility(), n);
        }
        abilityData.getAbility().whenCast(cachedStats, whenRan, itemAttackResult);
    }
    
    public boolean isOnCooldown(final CooldownType cooldownType) {
        return this.extraCooldowns.containsKey(cooldownType) && this.extraCooldowns.get(cooldownType) > System.currentTimeMillis();
    }
    
    public void applyCooldown(final CooldownType cooldownType, final double n) {
        this.extraCooldowns.put(cooldownType, (long)(System.currentTimeMillis() + 1000.0 * n));
    }
    
    @Deprecated
    public boolean canUseItem(final String s) {
        return this.isOnCooldown(s);
    }
    
    public boolean isOnCooldown(final String key) {
        return this.itemCooldowns.getOrDefault(key, 0L) < System.currentTimeMillis();
    }
    
    public void applyItemCooldown(final String s, final double n) {
        this.itemCooldowns.put(s, (long)(System.currentTimeMillis() + n * 1000.0));
    }
    
    public void applyAbilityCooldown(final Ability ability, final double n) {
        this.applyAbilityCooldown(ability.getID(), n);
    }
    
    public void applyAbilityCooldown(final String s, final double n) {
        this.abilityCooldowns.put(s, new CooldownInformation(n));
    }
    
    public boolean hasCooldownInfo(final Ability ability) {
        return this.hasCooldownInfo(ability.getID());
    }
    
    public boolean hasCooldownInfo(final String s) {
        return this.abilityCooldowns.containsKey(s);
    }
    
    public CooldownInformation getCooldownInfo(final Ability ability) {
        return this.getCooldownInfo(ability.getID());
    }
    
    public CooldownInformation getCooldownInfo(final String s) {
        return this.abilityCooldowns.get(s);
    }
    
    public double getItemCooldown(final String s) {
        return Math.max(0.0, (this.itemCooldowns.get(s) - System.currentTimeMillis()) / 1000.0);
    }
    
    @NotNull
    public static PlayerData get(@NotNull final OfflinePlayer offlinePlayer) {
        return get(offlinePlayer.getUniqueId());
    }
    
    @NotNull
    public static PlayerData get(@NotNull final UUID uuid) {
        PlayerData playerData = PlayerData.data.get(uuid);
        if (playerData == null) {
            load(uuid);
            playerData = PlayerData.data.get(uuid);
        }
        if (playerData == null) {
            MMOItems.print(Level.SEVERE, "Incomplete initialization of PlayerData. This error is only a result of another one caused EARLIER (probably during server startup).", null, new String[0]);
        }
        return playerData;
    }
    
    public static void load(@NotNull final Player player) {
        load(player.getUniqueId());
    }
    
    public static void load(@NotNull final UUID uuid) {
        if (PlayerData.data.containsKey(uuid)) {
            final PlayerData value = get(uuid);
            value.rpgPlayer = MMOItems.plugin.getRPG().getInfo(value);
            return;
        }
        final MMOPlayerData value2 = MMOPlayerData.get(uuid);
        if (value2 == null) {
            final NullPointerException ex = new NullPointerException("");
            MMOItems.print(Level.SEVERE, "Erroneous initialization of PlayerData. This error is only a result of another one caused EARLIER (probably during server startup).", null, new String[0]);
            ex.printStackTrace();
            return;
        }
        PlayerData.data.put(uuid, new PlayerData(value2));
    }
    
    public static Collection<PlayerData> getLoaded() {
        return PlayerData.data.values();
    }
    
    static {
        data = new HashMap<UUID, PlayerData>();
    }
    
    public enum CooldownType
    {
        ATTACK, 
        ELEMENTAL_ATTACK, 
        SPECIAL_ATTACK, 
        SET_TYPE_ATTACK;
        
        private static /* synthetic */ CooldownType[] $values() {
            return new CooldownType[] { CooldownType.ATTACK, CooldownType.ELEMENTAL_ATTACK, CooldownType.SPECIAL_ATTACK, CooldownType.SET_TYPE_ATTACK };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
