package net.minecraft.entity.ai.attributes;

public interface IAttribute {
  String getAttributeUnlocalizedName();
  
  double clampValue(double paramDouble);
  
  double getDefaultValue();
  
  boolean getShouldWatch();
  
  IAttribute func_180372_d();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\attributes\IAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */