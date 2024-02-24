/*    */ package net.minecraft.client.player.inventory;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.InventoryBasic;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.ILockableContainer;
/*    */ import net.minecraft.world.LockCode;
/*    */ 
/*    */ public class ContainerLocalMenu
/*    */   extends InventoryBasic
/*    */   implements ILockableContainer {
/*    */   private final String guiID;
/* 17 */   private final Map<Integer, Integer> field_174895_b = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public ContainerLocalMenu(String id, IChatComponent title, int slotCount) {
/* 21 */     super(title, slotCount);
/* 22 */     this.guiID = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getField(int id) {
/* 27 */     return ((Integer)this.field_174895_b.getOrDefault(Integer.valueOf(id), Integer.valueOf(0))).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setField(int id, int value) {
/* 32 */     this.field_174895_b.put(Integer.valueOf(id), Integer.valueOf(value));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFieldCount() {
/* 37 */     return this.field_174895_b.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLocked() {
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLockCode(LockCode code) {}
/*    */ 
/*    */   
/*    */   public LockCode getLockCode() {
/* 51 */     return LockCode.EMPTY_CODE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGuiID() {
/* 56 */     return this.guiID;
/*    */   }
/*    */ 
/*    */   
/*    */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 61 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\player\inventory\ContainerLocalMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */