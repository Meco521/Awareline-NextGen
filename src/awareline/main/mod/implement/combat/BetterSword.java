/*     */ package awareline.main.mod.implement.combat;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.EventTick;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.InventoryUtils;
/*     */ import awareline.main.mod.implement.world.utils.DelayTimer;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ 
/*     */ public class BetterSword extends Module {
/*  17 */   private final Option<Boolean> AutoSet = new Option("AutoSet", Boolean.valueOf(false));
/*  18 */   private final Option<Boolean> onlyNoMove = new Option("OnlyNoMove", Boolean.valueOf(false));
/*  19 */   private final DelayTimer timer = new DelayTimer();
/*     */   
/*     */   public BetterSword() {
/*  22 */     super("BetterSword", ModuleType.Combat);
/*  23 */     addSettings(new Value[] { (Value)this.AutoSet, (Value)this.onlyNoMove });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTick(EventTick event) {
/*  28 */     if (KillAura.getInstance.target != null) {
/*     */       return;
/*     */     }
/*  31 */     if (((Boolean)this.onlyNoMove.get()).booleanValue() && isMoving()) {
/*     */       return;
/*     */     }
/*  34 */     this; if (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer) {
/*     */       return;
/*     */     }
/*  37 */     if (!this.timer.hasPassed(200.0D)) {
/*     */       return;
/*     */     }
/*  40 */     if (((Boolean)this.AutoSet.getValue()).booleanValue()) {
/*  41 */       int slot = getBestSword(getScoreForSword(InventoryUtils.getItemBySlot(0)));
/*     */       
/*  43 */       if (slot == -1) {
/*     */         return;
/*     */       }
/*  46 */       swap(slot, 0);
/*     */     } else {
/*  48 */       if (!(InventoryUtils.getCurrentItem().getItem() instanceof ItemSword)) {
/*     */         return;
/*     */       }
/*  51 */       int slot = getBestSword(getScoreForSword(InventoryUtils.getCurrentItem()));
/*     */       
/*  53 */       if (slot == -1) {
/*     */         return;
/*     */       }
/*  56 */       this; swap(slot, mc.thePlayer.inventory.currentItem);
/*     */     } 
/*     */     
/*  59 */     this.timer.reset();
/*     */   }
/*     */   
/*     */   public int getBestSword(double minimum) {
/*  63 */     for (int i = 0; i < 36; i++) {
/*  64 */       this; if (mc.thePlayer.inventory.currentItem != i) {
/*     */ 
/*     */         
/*  67 */         this; ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
/*     */         
/*  69 */         if (itemStack != null)
/*     */         {
/*     */           
/*  72 */           if (itemStack.getItem() instanceof ItemSword)
/*     */           {
/*     */             
/*  75 */             if (minimum < getScoreForSword(itemStack))
/*     */             {
/*     */               
/*  78 */               return i; }  }  } 
/*     */       } 
/*     */     } 
/*  81 */     return -1;
/*     */   }
/*     */   
/*     */   public double getScoreForSword(ItemStack itemStack) {
/*  85 */     if (!(itemStack.getItem() instanceof ItemSword)) {
/*  86 */       return 0.0D;
/*     */     }
/*  88 */     ItemSword itemSword = (ItemSword)itemStack.getItem();
/*     */     
/*  90 */     double result = 1.0D;
/*     */     
/*  92 */     result += itemSword.getDamageVsEntity();
/*     */     
/*  94 */     result += 1.25D * EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
/*  95 */     result += 0.5D * EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack);
/*     */     
/*  97 */     return result;
/*     */   }
/*     */   
/*     */   public void swap(int from, int to) {
/* 101 */     if (from <= 8) {
/* 102 */       from = 36 + from;
/*     */     }
/*     */     
/* 105 */     this; this; this; mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, from, to, 2, (EntityPlayer)mc.thePlayer);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\BetterSword.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */