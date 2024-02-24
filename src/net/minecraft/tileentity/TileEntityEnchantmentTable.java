/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerEnchantment;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IInteractionObject;
/*     */ 
/*     */ public class TileEntityEnchantmentTable
/*     */   extends TileEntity
/*     */   implements ITickable, IInteractionObject
/*     */ {
/*     */   public int tickCount;
/*     */   public float pageFlip;
/*     */   public float pageFlipPrev;
/*     */   public float field_145932_k;
/*     */   public float field_145929_l;
/*  25 */   private static final Random rand = new Random(); public float bookSpread; public float bookSpreadPrev; public float bookRotation; public float bookRotationPrev;
/*     */   public float field_145924_q;
/*     */   private String customName;
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  30 */     super.writeToNBT(compound);
/*     */     
/*  32 */     if (hasCustomName())
/*     */     {
/*  34 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  40 */     super.readFromNBT(compound);
/*     */     
/*  42 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/*  44 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  53 */     this.bookSpreadPrev = this.bookSpread;
/*  54 */     this.bookRotationPrev = this.bookRotation;
/*  55 */     EntityPlayer entityplayer = this.worldObj.getClosestPlayer((this.pos.getX() + 0.5F), (this.pos.getY() + 0.5F), (this.pos.getZ() + 0.5F), 3.0D);
/*     */     
/*  57 */     if (entityplayer != null) {
/*     */       
/*  59 */       double d0 = entityplayer.posX - (this.pos.getX() + 0.5F);
/*  60 */       double d1 = entityplayer.posZ - (this.pos.getZ() + 0.5F);
/*  61 */       this.field_145924_q = (float)MathHelper.atan2(d1, d0);
/*  62 */       this.bookSpread += 0.1F;
/*     */       
/*  64 */       if (this.bookSpread < 0.5F || rand.nextInt(40) == 0)
/*     */       {
/*  66 */         float f1 = this.field_145932_k;
/*     */ 
/*     */         
/*     */         do {
/*  70 */           this.field_145932_k += (rand.nextInt(4) - rand.nextInt(4));
/*     */         }
/*  72 */         while (f1 == this.field_145932_k);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  81 */       this.field_145924_q += 0.02F;
/*  82 */       this.bookSpread -= 0.1F;
/*     */     } 
/*     */     
/*  85 */     while (this.bookRotation >= 3.1415927F)
/*     */     {
/*  87 */       this.bookRotation -= 6.2831855F;
/*     */     }
/*     */     
/*  90 */     while (this.bookRotation < -3.1415927F)
/*     */     {
/*  92 */       this.bookRotation += 6.2831855F;
/*     */     }
/*     */     
/*  95 */     while (this.field_145924_q >= 3.1415927F)
/*     */     {
/*  97 */       this.field_145924_q -= 6.2831855F;
/*     */     }
/*     */     
/* 100 */     while (this.field_145924_q < -3.1415927F)
/*     */     {
/* 102 */       this.field_145924_q += 6.2831855F;
/*     */     }
/*     */     
/*     */     float f2;
/*     */     
/* 107 */     for (f2 = this.field_145924_q - this.bookRotation; f2 >= 3.1415927F; f2 -= 6.2831855F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     while (f2 < -3.1415927F)
/*     */     {
/* 114 */       f2 += 6.2831855F;
/*     */     }
/*     */     
/* 117 */     this.bookRotation += f2 * 0.4F;
/* 118 */     this.bookSpread = MathHelper.clamp_float(this.bookSpread, 0.0F, 1.0F);
/* 119 */     this.tickCount++;
/* 120 */     this.pageFlipPrev = this.pageFlip;
/* 121 */     float f = (this.field_145932_k - this.pageFlip) * 0.4F;
/* 122 */     float f3 = 0.2F;
/* 123 */     f = MathHelper.clamp_float(f, -f3, f3);
/* 124 */     this.field_145929_l += (f - this.field_145929_l) * 0.9F;
/* 125 */     this.pageFlip += this.field_145929_l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 133 */     return hasCustomName() ? this.customName : "container.enchant";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 141 */     return (this.customName != null && !this.customName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(String customNameIn) {
/* 146 */     this.customName = customNameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 154 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 159 */     return (Container)new ContainerEnchantment(playerInventory, this.worldObj, this.pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 164 */     return "minecraft:enchanting_table";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntityEnchantmentTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */