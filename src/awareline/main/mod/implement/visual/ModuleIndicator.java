/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.combat.KillAura;
/*    */ import awareline.main.mod.implement.move.Flight;
/*    */ import awareline.main.mod.implement.move.Longjump;
/*    */ import awareline.main.mod.implement.move.Speed;
/*    */ import awareline.main.mod.implement.world.Scaffold;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.ui.animations.AnimationUtil;
/*    */ import awareline.main.ui.font.cfont.CFontRenderer;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class ModuleIndicator extends Module {
/* 22 */   private final EnumChatFormatting green = EnumChatFormatting.GREEN; private final EnumChatFormatting red = EnumChatFormatting.RED;
/* 23 */   private final String greenText = this.green + " Enable"; private final String redText = this.red + " Disable";
/* 24 */   private final Color white = new Color(255, 255, 255);
/*    */   
/* 26 */   private final Option<Boolean> lowerCase = new Option("LowerCase", Boolean.valueOf(false)); public static ModuleIndicator getInstance; private float x;
/*    */   private float y;
/*    */   final CFontRenderer font;
/*    */   
/* 30 */   public ModuleIndicator() { super("ModuleIndicator", ModuleType.Render);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     this.font = Client.instance.FontLoaders.regular17;
/*    */     addSettings(new Value[] { (Value)this.lowerCase });
/*    */     getInstance = this; } @EventHandler
/*    */   public void onRender2D(EventRender2D e) {
/* 46 */     if (mc.gameSettings.showDebugInfo) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 51 */     awareline.main.ui.draghud.component.impl.ModuleIndicator dwm = (awareline.main.ui.draghud.component.impl.ModuleIndicator)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.ModuleIndicator.class);
/*    */     
/* 53 */     dwm.setWidth(155.0F);
/* 54 */     dwm.setHeight(56);
/* 55 */     this.x = AnimationUtil.moveUDFaster(this.x, dwm.getX());
/* 56 */     this.y = AnimationUtil.moveUDFaster(this.y, dwm.getY());
/*    */     
/* 58 */     KillAura customAura = KillAura.getInstance;
/* 59 */     Speed speed = Speed.getInstance;
/* 60 */     Scaffold scaffold = Scaffold.getInstance;
/* 61 */     Flight fly = Flight.getInstance;
/* 62 */     Longjump lj = Longjump.getInstance;
/*    */     
/* 64 */     float Y = this.y, globalY = 160.0F;
/*    */ 
/*    */     
/* 67 */     String killauraModName = "KillAura" + (customAura.isEnabled() ? this.greenText : this.redText) + " " + EnumChatFormatting.WHITE + Keyboard.getKeyName(customAura.getKey());
/* 68 */     this.font.drawStringWithShadow(useFontLowerCase(killauraModName), (5.0F + this.x), (160.0F - globalY + Y), this.white
/* 69 */         .getRGB());
/*    */     
/* 71 */     String speedModName = "Speed" + (speed.isEnabled() ? this.greenText : this.redText) + " " + EnumChatFormatting.WHITE + Keyboard.getKeyName(speed.getKey());
/* 72 */     this.font.drawStringWithShadow(useFontLowerCase(speedModName), (5.0F + this.x), (172.0F - globalY + Y), this.white
/* 73 */         .getRGB());
/*    */     
/* 75 */     String scaffoldModName = "Scaffold" + (scaffold.isEnabled() ? this.greenText : this.redText) + " " + EnumChatFormatting.WHITE + Keyboard.getKeyName(scaffold.getKey());
/* 76 */     this.font.drawStringWithShadow(useFontLowerCase(scaffoldModName), (5.0F + this.x), (184.0F - globalY + Y), this.white
/* 77 */         .getRGB());
/*    */     
/* 79 */     String flyModName = "Fly" + (fly.isEnabled() ? this.greenText : this.redText) + " " + EnumChatFormatting.WHITE + Keyboard.getKeyName(fly.getKey());
/* 80 */     this.font.drawStringWithShadow(useFontLowerCase(flyModName), (5.0F + this.x), (196.0F - globalY + Y), this.white
/* 81 */         .getRGB());
/*    */     
/* 83 */     String longJumpModName = "LongJump" + (lj.isEnabled() ? this.greenText : this.redText) + " " + EnumChatFormatting.WHITE + Keyboard.getKeyName(lj.getKey());
/* 84 */     this.font.drawStringWithShadow(useFontLowerCase(longJumpModName), (5.0F + this.x), (208.0F - globalY + Y), this.white
/* 85 */         .getRGB());
/*    */   } public void onDisable() {
/*    */     this.x = this.y = 0.0F;
/*    */   } private String useFontLowerCase(String name) {
/* 89 */     return ((Boolean)this.lowerCase.get()).booleanValue() ? name.toLowerCase() : name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ModuleIndicator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */