package net.canarymod.api.world.blocks;

import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;
import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.item.ItemStack;

/**
 * Jukebox wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryJukebox extends CanaryTileEntity implements Jukebox {

    /**
     * Constructs a new wrapper for TileEntityRecordPlayer
     *
     * @param tileentity
     *         the TileEntityRecordPlayer to be wrapped
     */
    public CanaryJukebox(TileEntityJukebox tileentity) {
        super(tileentity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getDisc() {
        ItemStack disc = getTileEntity().a();
        return disc == null ? null : disc.getCanaryItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisc(Item disc) {
        if (disc != null && (disc.getId() < 2256 || disc.getId() > 2267)) {
            return;
        }
        getTileEntity().a(disc == null ? null : ((CanaryItem) disc).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileEntityJukebox getTileEntity() {
        return (TileEntityJukebox) tileentity;
    }
}
