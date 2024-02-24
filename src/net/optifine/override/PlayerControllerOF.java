/*     */ package net.optifine.override;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class PlayerControllerOF
/*     */   extends PlayerControllerMP {
/*     */   private boolean acting = false;
/*  20 */   private BlockPos lastClickBlockPos = null;
/*  21 */   private Entity lastClickEntity = null;
/*     */ 
/*     */   
/*     */   public PlayerControllerOF(Minecraft mcIn, NetHandlerPlayClient netHandler) {
/*  25 */     super(mcIn, netHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean clickBlock(BlockPos loc, EnumFacing face) {
/*  33 */     this.acting = true;
/*  34 */     this.lastClickBlockPos = loc;
/*  35 */     boolean flag = super.clickBlock(loc, face);
/*  36 */     this.acting = false;
/*  37 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
/*  42 */     this.acting = true;
/*  43 */     this.lastClickBlockPos = posBlock;
/*  44 */     boolean flag = super.onPlayerDamageBlock(posBlock, directionFacing);
/*  45 */     this.acting = false;
/*  46 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendUseItem(EntityPlayer player, World worldIn, ItemStack stack) {
/*  54 */     this.acting = true;
/*  55 */     boolean flag = super.sendUseItem(player, worldIn, stack);
/*  56 */     this.acting = false;
/*  57 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerRightClick(EntityPlayerSP p_178890_1, WorldClient p_178890_2, ItemStack p_178890_3, BlockPos p_178890_4, EnumFacing p_178890_5, Vec3 p_178890_6) {
/*  62 */     this.acting = true;
/*  63 */     this.lastClickBlockPos = p_178890_4;
/*  64 */     boolean flag = super.onPlayerRightClick(p_178890_1, p_178890_2, p_178890_3, p_178890_4, p_178890_5, p_178890_6);
/*  65 */     this.acting = false;
/*  66 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactWithEntitySendPacket(EntityPlayer player, Entity target) {
/*  74 */     this.lastClickEntity = target;
/*  75 */     return super.interactWithEntitySendPacket(player, target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerRightClickingOnEntity(EntityPlayer player, Entity target, MovingObjectPosition ray) {
/*  87 */     this.lastClickEntity = target;
/*  88 */     return super.isPlayerRightClickingOnEntity(player, target, ray);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActing() {
/*  93 */     return this.acting;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getLastClickBlockPos() {
/*  98 */     return this.lastClickBlockPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getLastClickEntity() {
/* 103 */     return this.lastClickEntity;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\override\PlayerControllerOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */