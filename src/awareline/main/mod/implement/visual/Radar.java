/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import awareline.main.utility.PlayerUtil;
/*     */ import awareline.main.utility.math.RotationUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Radar
/*     */   extends Module {
/*  25 */   private final Numbers<Double> scale = new Numbers("Scale", Double.valueOf(2.0D), Double.valueOf(1.0D), Double.valueOf(5.0D), Double.valueOf(0.1D));
/*  26 */   private final Numbers<Double> size = new Numbers("Size", Double.valueOf(125.0D), Double.valueOf(50.0D), Double.valueOf(500.0D), Double.valueOf(5.0D));
/*  27 */   public final Option<Boolean> disOnChat = new Option("DisableOnChat", Boolean.valueOf(true));
/*     */   public static Radar getInstance;
/*     */   
/*     */   public Radar() {
/*  31 */     super("Radar", ModuleType.Render);
/*  32 */     addSettings(new Value[] { (Value)this.scale, (Value)this.size, (Value)this.disOnChat });
/*  33 */     getInstance = this;
/*     */   }
/*     */   float x;
/*     */   float y;
/*     */   float sizes;
/*     */   
/*     */   public void onDisable() {
/*  40 */     this.x = this.y = this.sizes = 0.0F;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void radar(EventRender2D e) {
/*  45 */     if (!((Boolean)this.disOnChat.get()).booleanValue() || !(mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
/*     */       
/*  47 */       awareline.main.ui.draghud.component.impl.Radar dwm = (awareline.main.ui.draghud.component.impl.Radar)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.Radar.class);
/*     */       
/*  49 */       dwm.setWidth(155.0F);
/*  50 */       dwm.setHeight(56);
/*  51 */       this.x = AnimationUtil.moveUDFaster(this.x, dwm.getX());
/*  52 */       this.y = AnimationUtil.moveUDFaster(this.y, dwm.getY());
/*  53 */       if (this.sizes != ((Double)this.size.get()).floatValue()) {
/*  54 */         this.sizes = AnimationUtil.moveUD(this.sizes, ((Double)this.size.get()).floatValue(), SimpleRender.processFPS(0.013F), SimpleRender.processFPS(0.011F));
/*     */       }
/*  56 */       renderRadar();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderRadar() {
/*  61 */     GL11.glPushMatrix();
/*     */     
/*  63 */     Gui.drawRect((this.x - 1.0F), (this.y - 1.0F), (this.x + this.sizes + 1.0F), (this.y + this.sizes + 1.0F), (new Color(0, 0, 0, 180))
/*  64 */         .getRGB());
/*  65 */     Gui.drawRect(this.x, this.y, (this.x + this.sizes), (this.y + this.sizes), (new Color(0, 0, 0, 180))
/*  66 */         .getRGB());
/*  67 */     Gui.drawRect((this.x + this.sizes / 2.0F) - 0.05D + 0.0D, this.y, (this.x + this.sizes / 2.0F) + 0.05D + 0.0D, (this.y + this.sizes), (new Color(255, 255, 255, 80))
/*  68 */         .getRGB());
/*  69 */     Gui.drawRect(this.x, (this.y + this.sizes / 2.0F) - 0.05D, (this.x + this.sizes), (this.y + this.sizes / 2.0F) + 0.05D, (new Color(255, 255, 255, 80))
/*  70 */         .getRGB());
/*     */     
/*  72 */     for (Entity entity : mc.theWorld.getLoadedEntityList()) {
/*  73 */       if (entity != mc.thePlayer && 
/*  74 */         entity instanceof EntityPlayer && !entity.isInvisible()) {
/*     */         int color;
/*  76 */         float yawToEntity = RotationUtil.getYawToPoint(entity.posX, entity.posZ);
/*  77 */         float yawDiff = -(yawToEntity - mc.thePlayer.rotationYaw + 180.0F);
/*  78 */         double yawDiffRad = Math.toRadians(yawDiff);
/*     */         
/*  80 */         double x = Math.abs(mc.thePlayer.posX - entity.posX);
/*  81 */         double z = Math.abs(mc.thePlayer.posZ - entity.posZ);
/*  82 */         double distance = Math.sqrt(x * x + z * z) / ((Double)this.scale.get()).doubleValue();
/*     */ 
/*     */         
/*  85 */         if (PlayerUtil.inTeam((ICommandSender)entity, (ICommandSender)mc.thePlayer)) {
/*  86 */           color = (new Color(0, 231, 255, 255)).getRGB();
/*     */         }
/*  88 */         else if (((EntityPlayer)entity).getHealth() < 10.0F) {
/*  89 */           color = (new Color(255, 59, 59, 255)).getRGB();
/*     */         } else {
/*  91 */           color = (new Color(169, 255, 43, 255)).getRGB();
/*     */         } 
/*     */ 
/*     */         
/*  95 */         if ((Math.sin(yawDiffRad) > 0.0D && Math.cos(yawDiffRad) < 0.0D && 
/*  96 */           Math.sin(yawDiffRad) * distance < (this.sizes / 2.0F) && -Math.cos(yawDiffRad) * distance < (this.sizes / 2.0F)) || (
/*  97 */           Math.sin(yawDiffRad) < 0.0D && Math.cos(yawDiffRad) < 0.0D && 
/*  98 */           -Math.sin(yawDiffRad) * distance < (this.sizes / 2.0F) && -Math.cos(yawDiffRad) * distance < (this.sizes / 2.0F)) || (
/*  99 */           Math.sin(yawDiffRad) > 0.0D && Math.cos(yawDiffRad) > 0.0D && 
/* 100 */           Math.sin(yawDiffRad) * distance < (this.sizes / 2.0F) && Math.cos(yawDiffRad) * distance < (this.sizes / 2.0F)) || (
/* 101 */           Math.sin(yawDiffRad) < 0.0D && Math.cos(yawDiffRad) > 0.0D && 
/* 102 */           -Math.sin(yawDiffRad) * distance < (this.sizes / 2.0F) && Math.cos(yawDiffRad) * distance < (this.sizes / 2.0F)))
/*     */         {
/* 104 */           RenderUtil.drawBorderedRect((this.x + this.sizes / 2.0F) + 
/*     */               
/* 106 */               Math.sin(yawDiffRad) * distance - 1.0D, (this.y + this.sizes / 2.0F) + 
/*     */               
/* 108 */               Math.cos(yawDiffRad) * distance - 1.0D, (this.x + this.sizes / 2.0F) + 
/*     */ 
/*     */               
/* 111 */               Math.sin(yawDiffRad) * distance + 1.0D, (this.y + this.sizes / 2.0F) + 
/*     */ 
/*     */               
/* 114 */               Math.cos(yawDiffRad) * distance + 1.0D, 0.1F, color, color);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 121 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Radar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */