/*    */ package awareline.main.utility;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ 
/*    */ public interface Utils {
/*  8 */   public static final Minecraft mc = Minecraft.getMinecraft();
/*  9 */   public static final Tessellator tessellator = Tessellator.getInstance();
/* 10 */   public static final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */