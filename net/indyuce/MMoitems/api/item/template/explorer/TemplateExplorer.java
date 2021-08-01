// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.template.explorer;

import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import java.util.Optional;
import java.util.function.Predicate;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import java.util.Collection;
import java.util.Random;

public class TemplateExplorer
{
    private final Random random;
    private final Collection<MMOItemTemplate> all;
    
    public TemplateExplorer() {
        this.random = new Random();
        this.all = MMOItems.plugin.getTemplates().collectTemplates();
    }
    
    public int count() {
        return this.all.size();
    }
    
    public TemplateExplorer applyFilter(final Predicate<MMOItemTemplate> predicate) {
        this.all.removeIf(this.not((Predicate<? super MMOItemTemplate>)predicate));
        return this;
    }
    
    public Optional<MMOItemTemplate> rollLoot() {
        switch (this.count()) {
            case 0: {
                return Optional.empty();
            }
            case 1: {
                return this.all.stream().findFirst();
            }
            default: {
                return this.all.stream().skip(this.random.nextInt(this.count())).findFirst();
            }
        }
    }
    
    public Optional<MMOItem> rollItem(final RPGPlayer rpgPlayer) {
        return this.rollLoot().map(mmoItemTemplate -> mmoItemTemplate.newBuilder(rpgPlayer).build());
    }
    
    private <T> Predicate<T> not(final Predicate<T> predicate) {
        return t -> !predicate.test(t);
    }
}
