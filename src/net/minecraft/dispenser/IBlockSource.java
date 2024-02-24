package net.minecraft.dispenser;

import net.minecraft.util.BlockPos;

public interface IBlockSource extends ILocatableSource {
  double getX();
  
  double getY();
  
  double getZ();
  
  BlockPos getBlockPos();
  
  int getBlockMetadata();
  
  <T extends net.minecraft.tileentity.TileEntity> T getBlockTileEntity();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\dispenser\IBlockSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */