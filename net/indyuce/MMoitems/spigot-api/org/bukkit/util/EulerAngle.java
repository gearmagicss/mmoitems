// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.util;

public class EulerAngle
{
    public static final EulerAngle ZERO;
    private final double x;
    private final double y;
    private final double z;
    
    static {
        ZERO = new EulerAngle(0.0, 0.0, 0.0);
    }
    
    public EulerAngle(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public EulerAngle setX(final double x) {
        return new EulerAngle(x, this.y, this.z);
    }
    
    public EulerAngle setY(final double y) {
        return new EulerAngle(this.x, y, this.z);
    }
    
    public EulerAngle setZ(final double z) {
        return new EulerAngle(this.x, this.y, z);
    }
    
    public EulerAngle add(final double x, final double y, final double z) {
        return new EulerAngle(this.x + x, this.y + y, this.z + z);
    }
    
    public EulerAngle subtract(final double x, final double y, final double z) {
        return this.add(-x, -y, -z);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final EulerAngle that = (EulerAngle)o;
        return Double.compare(that.x, this.x) == 0 && Double.compare(that.y, this.y) == 0 && Double.compare(that.z, this.z) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.x);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.y);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.z);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
}
