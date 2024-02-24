/*     */ package awareline.main.mod.implement.combat.auto;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.updateEvents.MotionUpdateEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.world.ChestStealer;
/*     */ import awareline.main.mod.implement.world.utils.StealerUtils.Timer;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class AutoArmor
/*     */   extends Module
/*     */ {
/*     */   public static AutoArmor getInstance;
/*  24 */   public final Numbers<Double> equipDelay = new Numbers("Delay", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
/*  25 */   private final Option<Boolean> open_inv = new Option("OpenInventory", Boolean.valueOf(false));
/*  26 */   private final Option<Boolean> onlyNoMove = new Option("OnlyNoMove", Boolean.valueOf(false));
/*  27 */   private final Option<Boolean> onlyGround = new Option("OnlyGround", Boolean.valueOf(false));
/*  28 */   private final Option<Boolean> checkTarget = new Option("CheckTarget", Boolean.valueOf(false));
/*  29 */   private final Option<Boolean> stuckMove = new Option("StuckMove", Boolean.valueOf(false));
/*  30 */   private final Timer timer = new Timer();
/*     */   
/*     */   public AutoArmor() {
/*  33 */     super("AutoArmor", ModuleType.Combat);
/*  34 */     addSettings(new Value[] { (Value)this.equipDelay, (Value)this.open_inv, (Value)this.checkTarget, (Value)this.onlyGround, (Value)this.stuckMove, (Value)this.onlyNoMove });
/*  35 */     getInstance = this;
/*     */   }
/*     */   
/*     */   boolean stuck;
/*     */   
/*     */   private float getProtection(ItemStack stack) {
/*  41 */     float protection = 0.0F;
/*     */     
/*  43 */     if (stack.getItem() instanceof ItemArmor) {
/*  44 */       ItemArmor armor = (ItemArmor)stack.getItem();
/*     */       
/*  46 */       protection = (float)(protection + armor.damageReduceAmount + ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D);
/*     */       
/*  48 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0D);
/*  49 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0D);
/*  50 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0D);
/*  51 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0D);
/*  52 */       protection = (float)(protection + EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) / 100.0D);
/*     */     } 
/*     */     
/*  55 */     return protection;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onMove(EventMove event) {
/*  60 */     if (((Boolean)this.stuckMove.get()).booleanValue() && 
/*  61 */       this.stuck) {
/*  62 */       this; mc.thePlayer.motionX = 0.0D;
/*  63 */       this; mc.thePlayer.motionZ = 0.0D;
/*  64 */       event.x = 0.0D;
/*  65 */       event.z = 0.0D;
/*  66 */       if (this.timer.check((float)(((Double)this.equipDelay.get()).doubleValue() * 50.0D))) {
/*  67 */         this.stuck = false;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onEvent(MotionUpdateEvent event) {
/*  75 */     if (((Boolean)this.checkTarget.get()).booleanValue() && 
/*  76 */       KillAura.getInstance.getTarget() != null) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     if (((Boolean)this.onlyNoMove.get()).booleanValue() && isMoving()) {
/*     */       return;
/*     */     }
/*  83 */     if (((Boolean)this.onlyGround.get()).booleanValue() && !mc.thePlayer.onGround) {
/*     */       return;
/*     */     }
/*  86 */     if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/*  87 */       if ((!(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) && ((Boolean)this.open_inv.get()).booleanValue()) || ChestStealer.getInstance
/*  88 */         .isStealing()) {
/*     */         return;
/*     */       }
/*  91 */       if (mc.currentScreen == null || mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory || mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)
/*  92 */         for (int type = 1; type < 5; type++) {
/*  93 */           if (mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
/*  94 */             ItemStack slotStack = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
/*     */             
/*  96 */             if (isBestArmor(slotStack, type)) {
/*     */               continue;
/*     */             }
/*  99 */             mc.thePlayer.drop(4 + type);
/*     */           } 
/*     */ 
/*     */           
/* 103 */           for (int i = 9; i < 45; i++) {
/* 104 */             if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
/* 105 */               ItemStack slotStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/*     */               
/* 107 */               if (isBestArmor(slotStack, type) && getProtection(slotStack) > 0.0F) {
/* 108 */                 this.stuck = true;
/* 109 */                 if (this.timer.check((float)(((Double)this.equipDelay.get()).doubleValue() * 50.0D))) {
/* 110 */                   mc.thePlayer.shiftClick(i);
/* 111 */                   this.timer.reset();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWorking() {
/* 122 */     return !this.timer.check(((Double)this.equipDelay.get()).floatValue() * 100.0F);
/*     */   }
/*     */   
/*     */   public boolean isBestArmor(ItemStack stack, int type) {
/* 126 */     String strType = "";
/*     */     
/* 128 */     switch (type) {
/*     */       case 1:
/* 130 */         strType = "helmet";
/*     */         break;
/*     */       case 2:
/* 133 */         strType = "chestplate";
/*     */         break;
/*     */       case 3:
/* 136 */         strType = "leggings";
/*     */         break;
/*     */       case 4:
/* 139 */         strType = "boots";
/*     */         break;
/*     */     } 
/*     */     
/* 143 */     if (!stack.getUnlocalizedName().contains(strType)) {
/* 144 */       return false;
/*     */     }
/*     */     
/* 147 */     float protection = getProtection(stack);
/*     */     
/* 149 */     for (int i = 5; i < 45; i++) {
/* 150 */       if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
/* 151 */         ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/* 152 */         if (getProtection(is) > protection && is.getUnlocalizedName().contains(strType)) return false;
/*     */       
/*     */       } 
/*     */     } 
/* 156 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\auto\AutoArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */