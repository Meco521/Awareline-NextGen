package net.minecraft.world.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerFileData {
  void writePlayerData(EntityPlayer paramEntityPlayer);
  
  NBTTagCompound readPlayerData(EntityPlayer paramEntityPlayer);
  
  String[] getAvailablePlayerDat();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\storage\IPlayerFileData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */