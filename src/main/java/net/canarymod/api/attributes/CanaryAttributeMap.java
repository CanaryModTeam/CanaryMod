package net.canarymod.api.attributes;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jason (darkdiplomat)
 */
public class CanaryAttributeMap implements AttributeMap {
    private final BaseAttributeMap attributeMap;

    public CanaryAttributeMap(BaseAttributeMap attributeMap) {
        this.attributeMap = attributeMap;
    }

    @Override
    public ModifiedAttribute getModifiedAttribute(Attribute attribute) {
        return getNative().a(((CanaryAttribute) attribute).getNative()).getWrapper();
    }

    @Override
    public ModifiedAttribute getModifiedAttributeByName(String name) {
        IAttributeInstance IAttributeInstance = getNative().a(name);
        return IAttributeInstance != null ? IAttributeInstance.getWrapper() : null;
    }

    @Override
    public ModifiedAttribute registerAttribute(Attribute attribute) {
        return getNative().b(((CanaryAttribute) attribute).getNative()).getWrapper();
    }

    @Override
    public Collection<ModifiedAttribute> getAllAttributes() {
        ArrayList<ModifiedAttribute> attributeList = new ArrayList<ModifiedAttribute>();

        for (ModifiableAttributeInstance modifiableAttributeInstance : (Collection<ModifiableAttributeInstance>) getNative().a()) {
            attributeList.add(modifiableAttributeInstance.getWrapper());
        }
        return attributeList;
    }

    @Override
    public void addModifier(ModifiedAttribute modifiableAttribute) {
        getNative().a(((CanaryModifiedAttribute) modifiableAttribute).getNative());
    }

    @Override
    public ModifiableAttribute getAttribute(final Attribute attribute) {
        return this.getModifiedAttribute(attribute);
    }

    @Override
    public ModifiableAttribute getAttributeByName(final String name) {
        return this.getModifiedAttributeByName(name);
    }

    @Override
    public ModifiableAttribute register(final Attribute attribute) {
        return this.registerAttribute(attribute);
    }

    @Override
    public Collection<ModifiableAttribute> getAttributes() {
        return (Collection<ModifiableAttribute>) (Collection) this.getAllAttributes();
    }

    @Override
    public void addModifier(final ModifiableAttribute modifiableAttribute) {
        this.addModifier((ModifiedAttribute) modifiableAttribute);
    }

    @Override
    public void removeModifiers(Multimap<String, AttributeModifier> map) {
        Iterator<Map.Entry<String, AttributeModifier>> iterator = map.entries().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, AttributeModifier> entry = iterator.next();
            ModifiedAttribute modAttribute = getModifiedAttributeByName(entry.getKey());
            if (modAttribute != null) {
                modAttribute.remove(entry.getValue());
            }
        }
    }

    @Override
    public void applyModifiers(Multimap<String, AttributeModifier> map) {
        Iterator<Map.Entry<String, AttributeModifier>> iterator = map.entries().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, AttributeModifier> entry = iterator.next();
            ModifiedAttribute modAttribute = getModifiedAttributeByName(entry.getKey());
            if (modAttribute != null) {
                modAttribute.remove(entry.getValue());
                modAttribute.apply(entry.getValue());
            }
        }
    }

    public BaseAttributeMap getNative() {
        return attributeMap;
    }
}
