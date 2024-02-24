package net.minecraftforge.common.property;

public interface IUnlistedProperty<V> {
  String getName();
  
  boolean isValid(V paramV);
  
  Class<V> getType();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraftforge\common\property\IUnlistedProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */