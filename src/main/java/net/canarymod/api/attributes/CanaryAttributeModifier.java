package net.canarymod.api.attributes;

import java.util.UUID;

/**
 * @author Jason (darkdiplomat)
 */
public class CanaryAttributeModifier implements AttributeModifier {
    private final net.minecraft.entity.ai.attributes.AttributeModifier attributeModifier;

    public CanaryAttributeModifier(net.minecraft.entity.ai.attributes.AttributeModifier attributeModifier) {
        this.attributeModifier = attributeModifier;
    }

    @Override
    public UUID getUUID() {
        return getNative().a();
    }

    @Override
    public UUID getId() {
        return this.getUUID();
    }

    @Override
    public String getName() {
        return getNative().b();
    }

    @Override
    public int getOperation() {
        return getNative().c();
    }

    @Override
    public double getValue() {
        return this.getAmount();
    }

    @Override
    public boolean getShouldSave() {
        return this.isSaved();
    }

    @Override
    public AttributeModifier setShouldSave(final boolean saved) {
        return this.setSaved(saved);
    }

    @Override
    public double getAmount() {
        return getNative().d();
    }

    @Override
    public boolean isSaved() {
        return getNative().e();
    }

    @Override
    public AttributeModifier setSaved(boolean saved) {
        return getNative().a(saved).getWrapper();
    }

    public net.minecraft.entity.ai.attributes.AttributeModifier getNative() {
        return attributeModifier;
    }
}
