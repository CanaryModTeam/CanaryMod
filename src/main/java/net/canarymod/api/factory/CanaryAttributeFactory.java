package net.canarymod.api.factory;

import net.canarymod.api.attributes.Attribute;
import net.canarymod.api.attributes.AttributeModifier;
import net.canarymod.api.attributes.CanaryAttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jason (darkdiplomat)
 */
public class CanaryAttributeFactory implements AttributeFactory {
    private final Map<String, Attribute> mappedGeneric; // Cause there is no map for it

    CanaryAttributeFactory() {
        mappedGeneric = new HashMap<String, Attribute>();
        mappedGeneric.put("generic.maxHealth", SharedMonsterAttributes.a.getWrapper());
        mappedGeneric.put("generic.followRange", SharedMonsterAttributes.b.getWrapper());
        mappedGeneric.put("generic.knockbackResistance", SharedMonsterAttributes.c.getWrapper());
        mappedGeneric.put("generic.movementSpeed", SharedMonsterAttributes.d.getWrapper());
        mappedGeneric.put("generic.attackDamage", SharedMonsterAttributes.e.getWrapper());

        mappedGeneric.put("horse.jumpStrength", EntityHorse.br.getWrapper());

        mappedGeneric.put("zombie.spawnReinforcements", EntityZombie.b.getWrapper());
    }

    @Override
    public Attribute getGenericAttribute(String name) {
        return mappedGeneric.get(name);
    }

    @Override
    public AttributeModifier createModifier(final String name, final double value, final int operation) {
        return new CanaryAttributeModifier(new net.minecraft.entity.ai.attributes.AttributeModifier(name, value, operation));
    }

    @Override
    public AttributeModifier createModifier(final UUID id, final String name, final double value, final int operation) {
        return new CanaryAttributeModifier(new net.minecraft.entity.ai.attributes.AttributeModifier(id, name, value, operation));
    }

}
