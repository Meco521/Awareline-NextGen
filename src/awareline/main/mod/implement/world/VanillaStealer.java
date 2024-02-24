/*    */ package awareline.main.mod.implement.world;
/*    */ import awareline.main.Client;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.display.DisplayChestGuiEvent;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.utility.timer.TimerUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class VanillaStealer extends Module {
/* 19 */   private final Option<Boolean> zoomHack = new Option("ZoomHack", Boolean.valueOf(true));
/* 20 */   private final Option<Boolean> silent = new Option("SilentGui", Boolean.valueOf(true));
/* 21 */   private final Numbers<Double> delay = new Numbers("Delay", 
/* 22 */       Double.valueOf(200.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(!((Boolean)this.zoomHack.get()).booleanValue()));
/* 23 */   private final TimerUtil timer = new TimerUtil();
/*    */   
/*    */   public VanillaStealer() {
/* 26 */     super("VanillaStealer", ModuleType.World);
/* 27 */     addSettings(new Value[] { (Value)this.delay, (Value)this.zoomHack, (Value)this.silent });
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onDisplayGuiChest(DisplayChestGuiEvent event) {
/* 32 */     if ((((Boolean)this.silent.get()).booleanValue() && event.getString().equals("minecraft:chest")) || mc.thePlayer.openContainer instanceof ContainerChest) {
/* 33 */       mc.displayGuiScreen(null);
/* 34 */       ScaledResolution sr = new ScaledResolution(mc);
/* 35 */       Client.instance.FontLoaders.Comfortaa18.drawCenteredStringWithShadow("Stealing chest...", (sr
/* 36 */           .getScaledWidth() / 2), (sr.getScaledHeight() / 2), (new Color(200, 200, 200)).getRGB());
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onUpdate(EventTick event) {
/* 42 */     if (mc.thePlayer.openContainer instanceof ContainerChest) {
/* 43 */       ContainerChest container = (ContainerChest)mc.thePlayer.openContainer;
/* 44 */       int i = 0;
/* 45 */       while (i < container.getLowerChestInventory().getSizeInventory()) {
/* 46 */         if (container.getLowerChestInventory().getStackInSlot(i) != null && (this.timer
/* 47 */           .hasReached(((Double)this.delay.getValue()).doubleValue()) || ((Boolean)this.zoomHack.get()).booleanValue())) {
/* 48 */           mc.playerController.windowClick(container.windowId, i, 0, 1, (EntityPlayer)mc.thePlayer);
/* 49 */           this.timer.reset();
/*    */         } 
/* 51 */         i++;
/*    */       } 
/* 53 */       if (isEmpty()) {
/* 54 */         mc.thePlayer.closeScreen();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean isEmpty() {
/* 60 */     if (mc.thePlayer.openContainer instanceof ContainerChest) {
/* 61 */       ContainerChest container = (ContainerChest)mc.thePlayer.openContainer;
/* 62 */       int i = 0;
/* 63 */       while (i < container.getLowerChestInventory().getSizeInventory()) {
/* 64 */         ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(i);
/* 65 */         if (itemStack != null && itemStack.getItem() != null) {
/* 66 */           return false;
/*    */         }
/* 68 */         i++;
/*    */       } 
/*    */     } 
/* 71 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\VanillaStealer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */