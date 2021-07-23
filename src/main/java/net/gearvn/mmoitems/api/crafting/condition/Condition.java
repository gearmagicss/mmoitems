// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.condition;

import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.manager.CraftingManager;
import net.Indyuce.mmoitems.api.crafting.ConditionalDisplay;

public abstract class Condition
{
    private final String id;
    
    public Condition(final String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public ConditionalDisplay getDisplay() {
        return MMOItems.plugin.getCrafting().getConditions().stream().filter(loadedObject -> loadedObject.getId().equals(this.id)).findAny().orElse(null).getDisplay();
    }
    
    public abstract boolean isMet(final PlayerData p0);
    
    public abstract String formatDisplay(final String p0);
    
    public abstract void whenCrafting(final PlayerData p0);
    
    public CheckedCondition evaluateCondition(final PlayerData playerData) {
        return new CheckedCondition(this, this.isMet(playerData));
    }
    
    public static class CheckedCondition
    {
        private final Condition condition;
        private final boolean met;
        
        public CheckedCondition(final Condition condition, final boolean met) {
            this.condition = condition;
            this.met = met;
        }
        
        public boolean isMet() {
            return this.met;
        }
        
        public Condition getCondition() {
            return this.condition;
        }
        
        public String format() {
            return this.condition.formatDisplay(this.isMet() ? this.condition.getDisplay().getPositive() : this.condition.getDisplay().getNegative());
        }
    }
}
