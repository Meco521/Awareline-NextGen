package net.minecraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public interface IHopper extends IInventory {
  World getWorld();
  
  double getXPos();
  
  double getYPos();
  
  double getZPos();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\IHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */