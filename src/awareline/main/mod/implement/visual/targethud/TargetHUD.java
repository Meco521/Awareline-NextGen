/*    */ package awareline.main.mod.implement.visual.targethud;
/*    */ 
/*    */ import awareline.main.utility.Utils;
/*    */ import awareline.main.utility.render.render.blur.util.GLUtil;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public abstract class TargetHUD implements Utils {
/*    */   private float width;
/*    */   private float height;
/*    */   private final String name;
/*    */   
/*    */   public String getName() {
/* 16 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TargetHUD(String name) {
/* 22 */     this.name = name;
/*    */   }
/*    */   
/*    */   protected void renderPlayer2D(float x, float y, float width, float height, AbstractClientPlayer player) {
/* 26 */     GLUtil.startBlend();
/* 27 */     mc.getTextureManager().bindTexture(player.getLocationSkin());
/* 28 */     Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8.0F, 8.0F, width, height, 64.0F, 64.0F);
/* 29 */     GLUtil.endBlend();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   private static final HashMap<Class<? extends TargetHUD>, TargetHUD> targetHuds = new HashMap<>();
/*    */   
/*    */   public static TargetHUD get(String name) {
/* 39 */     return targetHuds.values().stream().filter(hud -> hud.name.equals(name)).findFirst().orElse(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends TargetHUD> T get(Class<T> clazz) {
/* 44 */     return (T)targetHuds.get(clazz);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void init() {
/* 49 */     targetHuds.put(OldTenacityTargetHUD.class, new OldTenacityTargetHUD());
/* 50 */     targetHuds.put(RiseTargetHUD.class, new RiseTargetHUD());
/*    */ 
/*    */     
/* 53 */     targetHuds.put(AkrienTargetHUD.class, new AkrienTargetHUD());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWidth(float width) {
/* 59 */     this.width = width;
/*    */   }
/*    */   
/*    */   public void setHeight(float height) {
/* 63 */     this.height = height;
/*    */   }
/*    */   
/*    */   public float getWidth() {
/* 67 */     return this.width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 71 */     return (int)this.height;
/*    */   }
/*    */   
/*    */   public abstract void render(float paramFloat1, float paramFloat2, float paramFloat3, EntityLivingBase paramEntityLivingBase);
/*    */   
/*    */   public abstract void renderEffects(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\targethud\TargetHUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */