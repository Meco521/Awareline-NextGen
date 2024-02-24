package net.minecraftforge.client.model;

import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;

public interface ISmartItemModel extends IBakedModel {
  IBakedModel handleItemState(ItemStack paramItemStack);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraftforge\client\model\ISmartItemModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */