// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.util.EulerAngle;
import org.bukkit.inventory.ItemStack;

public interface ArmorStand extends LivingEntity
{
    ItemStack getItemInHand();
    
    void setItemInHand(final ItemStack p0);
    
    ItemStack getBoots();
    
    void setBoots(final ItemStack p0);
    
    ItemStack getLeggings();
    
    void setLeggings(final ItemStack p0);
    
    ItemStack getChestplate();
    
    void setChestplate(final ItemStack p0);
    
    ItemStack getHelmet();
    
    void setHelmet(final ItemStack p0);
    
    EulerAngle getBodyPose();
    
    void setBodyPose(final EulerAngle p0);
    
    EulerAngle getLeftArmPose();
    
    void setLeftArmPose(final EulerAngle p0);
    
    EulerAngle getRightArmPose();
    
    void setRightArmPose(final EulerAngle p0);
    
    EulerAngle getLeftLegPose();
    
    void setLeftLegPose(final EulerAngle p0);
    
    EulerAngle getRightLegPose();
    
    void setRightLegPose(final EulerAngle p0);
    
    EulerAngle getHeadPose();
    
    void setHeadPose(final EulerAngle p0);
    
    boolean hasBasePlate();
    
    void setBasePlate(final boolean p0);
    
    boolean isVisible();
    
    void setVisible(final boolean p0);
    
    boolean hasArms();
    
    void setArms(final boolean p0);
    
    boolean isSmall();
    
    void setSmall(final boolean p0);
    
    boolean isMarker();
    
    void setMarker(final boolean p0);
}
