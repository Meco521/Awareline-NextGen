package net.minecraftforge.fml.common.registry;

import net.minecraft.util.ResourceLocation;

public interface RegistryDelegate<T> {
  T get();
  
  ResourceLocation name();
  
  Class<T> type();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraftforge\fml\common\registry\RegistryDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */