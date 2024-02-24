package net.minecraft.util;

public interface IRegistry<K, V> extends Iterable<V> {
  V getObject(K paramK);
  
  void putObject(K paramK, V paramV);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\IRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */