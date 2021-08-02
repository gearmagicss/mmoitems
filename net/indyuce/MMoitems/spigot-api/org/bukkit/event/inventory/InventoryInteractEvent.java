// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryView;
import org.bukkit.event.Event;
import org.bukkit.event.Cancellable;

public abstract class InventoryInteractEvent extends InventoryEvent implements Cancellable
{
    private Result result;
    
    public InventoryInteractEvent(final InventoryView transaction) {
        super(transaction);
        this.result = Result.DEFAULT;
    }
    
    public HumanEntity getWhoClicked() {
        return this.getView().getPlayer();
    }
    
    public void setResult(final Result newResult) {
        this.result = newResult;
    }
    
    public Result getResult() {
        return this.result;
    }
    
    @Override
    public boolean isCancelled() {
        return this.getResult() == Result.DENY;
    }
    
    @Override
    public void setCancelled(final boolean toCancel) {
        this.setResult(toCancel ? Result.DENY : Result.ALLOW);
    }
}
