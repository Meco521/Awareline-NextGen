/*    */ package net.minecraft.entity.item;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityMinecartEmpty
/*    */   extends EntityMinecart
/*    */ {
/*    */   public EntityMinecartEmpty(World worldIn) {
/* 11 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMinecartEmpty(World worldIn, double x, double y, double z) {
/* 16 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean interactFirst(EntityPlayer playerIn) {
/* 24 */     if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != playerIn)
/*    */     {
/* 26 */       return true;
/*    */     }
/* 28 */     if (this.riddenByEntity != null && this.riddenByEntity != playerIn)
/*    */     {
/* 30 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 34 */     if (!this.worldObj.isRemote)
/*    */     {
/* 36 */       playerIn.mountEntity(this);
/*    */     }
/*    */     
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/* 48 */     if (receivingPower) {
/*    */       
/* 50 */       if (this.riddenByEntity != null)
/*    */       {
/* 52 */         this.riddenByEntity.mountEntity((Entity)null);
/*    */       }
/*    */       
/* 55 */       if (getRollingAmplitude() == 0) {
/*    */         
/* 57 */         setRollingDirection(-getRollingDirection());
/* 58 */         setRollingAmplitude(10);
/* 59 */         setDamage(50.0F);
/* 60 */         setBeenAttacked();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMinecart.EnumMinecartType getMinecartType() {
/* 67 */     return EntityMinecart.EnumMinecartType.RIDEABLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\item\EntityMinecartEmpty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */