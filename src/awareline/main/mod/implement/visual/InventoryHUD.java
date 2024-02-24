/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.ketaShaderCall.EventShader;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.globals.Shader;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class InventoryHUD extends Module {
/*  20 */   public final Option<Boolean> blur = new Option("Blur", 
/*  21 */       Boolean.valueOf(true));
/*  22 */   public final Option<Boolean> shadow = new Option("Shadow", Boolean.valueOf(true));
/*  23 */   private final Numbers<Double> xe = new Numbers("X", 
/*  24 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*  25 */   private final Numbers<Double> ye = new Numbers("Y", 
/*  26 */       Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
/*     */   float x2;
/*     */   
/*     */   public InventoryHUD() {
/*  30 */     super("InventoryHUD", ModuleType.Render);
/*  31 */     addSettings(new Value[] { (Value)this.xe, (Value)this.ye, (Value)this.blur, (Value)this.shadow });
/*     */   }
/*     */   float y2;
/*     */   
/*     */   public void onDisable() {
/*  36 */     this.x2 = this.y2 = 0.0F;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void on2D(EventRender2D e) {
/*  41 */     if (mc.gameSettings.showDebugInfo) {
/*     */       return;
/*     */     }
/*  44 */     this.x2 = AnimationUtil.moveUD(this.x2, ((Double)this.xe.get()).floatValue());
/*  45 */     this.y2 = AnimationUtil.moveUD(this.y2, ((Double)this.ye.get()).floatValue());
/*     */     
/*  47 */     SimpleRender.drawRectFloat(this.x2 + 2.0F, 365.0F - this.y2, 163.0F + this.x2, 366.0F - this.y2, Client.instance.getClientColor(255));
/*  48 */     SimpleRender.drawRectFloat(this.x2 + 2.0F, 366.0F - this.y2, 163.0F + this.x2, 434.0F - this.y2, (new Color(0, 0, 0, 150)).getRGB());
/*     */     
/*  50 */     Client.instance.FontLoaders.bold16.drawString("Inventory", (5.0F + this.x2), (270.0F - this.y2) + 0.5D + 100.0D, (new Color(255, 255, 255))
/*  51 */         .getRGB());
/*     */     
/*  53 */     if (emptyInventory()) {
/*  54 */       Client.instance.FontLoaders.regular16.drawCenteredString("Empty ", 81.5F + this.x2, 301.5F - this.y2 + 100.0F, (new Color(0, 0, 0, 150)).getRGB());
/*     */       
/*     */       return;
/*     */     } 
/*  58 */     float x = 3.0F;
/*  59 */     float y = 201.0F;
/*  60 */     for (int i = 0; i < mc.thePlayer.inventory.mainInventory.length; i++) {
/*  61 */       if (i >= 9) {
/*     */ 
/*     */         
/*  64 */         ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
/*  65 */         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
/*  66 */           GlStateManager.pushMatrix();
/*  67 */           GlStateManager.enableLighting();
/*  68 */           mc.getRenderItem().renderItemAndEffectIntoGUI(stack, (int)(x + this.x2), (int)(y + 79.0F - this.y2 + 100.0F));
/*  69 */           GlStateManager.popMatrix();
/*  70 */           mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, (int)(x + this.x2), (int)(y + 79.0F - this.y2 + 100.0F));
/*  71 */           GlStateManager.disableLighting();
/*     */         } 
/*     */         
/*  74 */         if (x < 130.0F) {
/*  75 */           x += 18.0F;
/*     */         } else {
/*  77 */           x = 2.0F;
/*  78 */           y += 18.0F;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void bloom(EventShader event) {
/*  86 */     if (mc.gameSettings.showDebugInfo) {
/*     */       return;
/*     */     }
/*     */     
/*  90 */     if (!Shader.getInstance.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     if (event.onBloom() && !((Boolean)this.shadow.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     if (event.onBlur() && !((Boolean)this.blur.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     this.x2 = AnimationUtil.moveUD(this.x2, ((Double)this.xe.get()).floatValue());
/* 103 */     this.y2 = AnimationUtil.moveUD(this.y2, ((Double)this.ye.get()).floatValue());
/*     */     
/* 105 */     SimpleRender.drawRectFloat(this.x2 + 2.0F, 365.0F - this.y2, 163.0F + this.x2, 366.0F - this.y2, Client.instance
/*     */         
/* 107 */         .getClientColor(255));
/* 108 */     SimpleRender.drawRectFloat(this.x2 + 2.0F, 366.0F - this.y2, 163.0F + this.x2, 434.0F - this.y2, Client.instance.getClientColor(255));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean emptyInventory() {
/* 113 */     for (int i = 0; i < 45; i++) {
/* 114 */       if (i >= 9)
/*     */       {
/*     */         
/* 117 */         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
/* 118 */           return false; 
/*     */       }
/*     */     } 
/* 121 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\InventoryHUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */