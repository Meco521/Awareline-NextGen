/*    */ package awareline.main.ui.gui.guimainmenu.shader;
/*    */ 
/*    */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientSetting;
/*    */ import awareline.main.ui.simplecore.SimpleRender;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class MenuShader
/*    */ {
/* 11 */   public final ShaderProgram menuShader = new ShaderProgram("fragment/bpurple.frag");
/* 12 */   protected final Minecraft mc = Minecraft.getMinecraft();
/* 13 */   public final ShaderProgram menuCustomShader = new ShaderProgram("fragment/" + ((String)ClientSetting.shaderMode.get()).toLowerCase() + ".frag");
/*    */   private float time;
/*    */   
/*    */   public final void render(ScaledResolution scaledResolution, boolean useCustomFrag) {
/* 17 */     if (useCustomFrag) {
/* 18 */       this.menuCustomShader.init();
/* 19 */       setupUniforms();
/* 20 */       GL11.glEnable(3042);
/* 21 */       GL11.glBlendFunc(770, 771);
/* 22 */       this.menuCustomShader.renderCanvas(scaledResolution);
/* 23 */       GL11.glDisable(3042);
/* 24 */       this.menuCustomShader.uninit();
/*    */     } else {
/* 26 */       this.menuShader.init();
/* 27 */       setupUniforms();
/* 28 */       GL11.glEnable(3042);
/* 29 */       GL11.glBlendFunc(770, 771);
/* 30 */       this.menuShader.renderCanvas(scaledResolution);
/* 31 */       GL11.glDisable(3042);
/* 32 */       this.menuShader.uninit();
/*    */     } 
/* 34 */     this.time += (Minecraft.getDebugFPS() < 60) ? 0.0F : (float)(0.002D * SimpleRender.delta);
/*    */   }
/*    */   
/*    */   public void setupUniforms() {
/* 38 */     this.menuShader.setUniformf("time", new float[] { this.time });
/* 39 */     this.menuShader.setUniformf("resolution", new float[] { this.mc.displayWidth, this.mc.displayHeight });
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\guimainmenu\shader\MenuShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */