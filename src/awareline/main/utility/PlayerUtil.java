/*     */ package awareline.main.utility;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerUtil
/*     */   implements Utils
/*     */ {
/*     */   public static boolean MovementInput() {
/*  23 */     return (mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindBack.pressed);
/*     */   }
/*     */   
/*     */   public static String teamColor(ICommandSender player) {
/*  27 */     Matcher matcher = Pattern.compile("§(.).*§r").matcher(player.getDisplayName().getFormattedText());
/*  28 */     return matcher.find() ? matcher.group(1) : "f";
/*     */   }
/*     */   
/*     */   public static boolean inTeam(ICommandSender entity0, ICommandSender entity1) {
/*  32 */     String s = "§" + teamColor(entity0);
/*     */     
/*  34 */     return (entity0.getDisplayName().getFormattedText().contains(s) && entity1
/*  35 */       .getDisplayName().getFormattedText().contains(s));
/*     */   }
/*     */   
/*     */   private static int getItemFromHotbar(int id) {
/*  39 */     for (int i = 0; i < 9; i++) {
/*  40 */       if (mc.thePlayer.inventory.mainInventory[i] != null) {
/*  41 */         ItemStack is = mc.thePlayer.inventory.mainInventory[i];
/*  42 */         Item item = is.getItem();
/*  43 */         if (Item.getIdFromItem(item) == id) {
/*  44 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/*  48 */     return -1;
/*     */   }
/*     */   
/*     */   public static int findGap() {
/*  52 */     for (int i = 36; i < 45; i++) {
/*  53 */       ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/*     */       
/*  55 */       if (itemStack != null && itemStack.getDisplayName().contains("Golden") && itemStack.stackSize > 0 && itemStack.getItem() instanceof net.minecraft.item.ItemFood) {
/*  56 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  60 */     return -1;
/*     */   }
/*     */   
/*     */   public static int findHead() {
/*  64 */     for (int i = 36; i < 45; i++) {
/*  65 */       ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/*     */       
/*  67 */       if (itemStack != null && itemStack.getDisplayName().contains("Head") && itemStack.stackSize > 0) {
/*  68 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  72 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean isItemEmpty(Item item) {
/*  76 */     return (item == null || Item.getIdFromItem(item) == 0);
/*     */   }
/*     */   
/*     */   public static boolean isAirUnder(Entity ent) {
/*  80 */     return (mc.theWorld.getBlockState(new BlockPos(ent.posX, ent.posY - 1.0D, ent.posZ)).getBlock() == Blocks.air);
/*     */   }
/*     */   
/*     */   public static Block block(BlockPos blockPos) {
/*  84 */     return mc.theWorld.getBlockState(blockPos).getBlock();
/*     */   }
/*     */   
/*     */   public static boolean onLiquid() {
/*  88 */     boolean onLiquid = false;
/*  89 */     AxisAlignedBB playerBB = mc.thePlayer.getEntityBoundingBox();
/*  90 */     WorldClient world = mc.theWorld;
/*  91 */     int y = (int)(playerBB.offset(0.0D, -0.01D, 0.0D)).minY;
/*  92 */     for (int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; x++) {
/*  93 */       for (int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; z++) {
/*  94 */         Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
/*  95 */         if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
/*  96 */           if (!(block instanceof net.minecraft.block.BlockLiquid)) {
/*  97 */             return false;
/*     */           }
/*  99 */           onLiquid = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 103 */     return onLiquid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean inLiquid() {
/* 112 */     return (mc.thePlayer.isInWater() || mc.thePlayer.isInLava());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Block blockRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
/* 121 */     return mc.theWorld.getBlockState((new BlockPos((Entity)mc.thePlayer)).add(offsetX, offsetY, offsetZ)).getBlock();
/*     */   }
/*     */   
/*     */   public static int findEmptySlot() {
/* 125 */     for (int i = 0; i < 8; i++) {
/* 126 */       if (mc.thePlayer.inventory.mainInventory[i] == null) {
/* 127 */         return i;
/*     */       }
/*     */     } 
/* 130 */     return mc.thePlayer.inventory.currentItem + ((mc.thePlayer.inventory.getCurrentItem() == null) ? 0 : ((mc.thePlayer.inventory.currentItem < 8) ? 4 : -1));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findEmptySlot(int priority) {
/* 135 */     if (mc.thePlayer.inventory.mainInventory[priority] == null) {
/* 136 */       return priority;
/*     */     }
/* 138 */     return findEmptySlot();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBlockUnder(double height) {
/* 147 */     return isBlockUnder(height, true);
/*     */   }
/*     */   
/*     */   public static boolean isBlockUnder(double height, boolean boundingBox) {
/* 151 */     if (boundingBox) {
/* 152 */       for (int offset = 0; offset < height; offset += 2) {
/* 153 */         AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0.0D, -offset, 0.0D);
/*     */         
/* 155 */         if (!mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, bb).isEmpty()) {
/* 156 */           return true;
/*     */         }
/*     */       } 
/*     */     } else {
/* 160 */       for (int offset = 0; offset < height; offset++) {
/* 161 */         if (blockRelativeToPlayer(0.0D, -offset, 0.0D).isFullBlock()) {
/* 162 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 166 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isBlockUnder() {
/* 170 */     return isBlockUnder(mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean blockNear(int range) {
/* 179 */     for (int x = -range; x <= range; x++) {
/* 180 */       for (int y = -range; y <= range; y++) {
/* 181 */         for (int z = -range; z <= range; z++) {
/* 182 */           Block block = blockRelativeToPlayer(x, y, z);
/*     */           
/* 184 */           if (!(block instanceof net.minecraft.block.BlockAir)) {
/* 185 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\PlayerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */