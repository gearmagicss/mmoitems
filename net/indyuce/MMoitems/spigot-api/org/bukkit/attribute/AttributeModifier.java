// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.attribute;

import org.bukkit.util.NumberConversions;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.Validate;
import java.util.UUID;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class AttributeModifier implements ConfigurationSerializable
{
    private final UUID uuid;
    private final String name;
    private final double amount;
    private final Operation operation;
    
    public AttributeModifier(final String name, final double amount, final Operation operation) {
        this(UUID.randomUUID(), name, amount, operation);
    }
    
    public AttributeModifier(final UUID uuid, final String name, final double amount, final Operation operation) {
        Validate.notNull(uuid, "uuid");
        Validate.notEmpty(name, "Name cannot be empty");
        Validate.notNull(operation, "operation");
        this.uuid = uuid;
        this.name = name;
        this.amount = amount;
        this.operation = operation;
    }
    
    public UUID getUniqueId() {
        return this.uuid;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    public Operation getOperation() {
        return this.operation;
    }
    
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> data = new HashMap<String, Object>();
        data.put("uuid", this.uuid);
        data.put("name", this.name);
        data.put("operation", this.operation.ordinal());
        data.put("amount", this.amount);
        return data;
    }
    
    public static AttributeModifier deserialize(final Map<String, Object> args) {
        return new AttributeModifier(args.get("uuid"), args.get("name"), NumberConversions.toDouble(args.get("amount")), Operation.values()[NumberConversions.toInt(args.get("operation"))]);
    }
    
    public enum Operation
    {
        ADD_NUMBER("ADD_NUMBER", 0), 
        ADD_SCALAR("ADD_SCALAR", 1), 
        MULTIPLY_SCALAR_1("MULTIPLY_SCALAR_1", 2);
        
        private Operation(final String name, final int ordinal) {
        }
    }
}
