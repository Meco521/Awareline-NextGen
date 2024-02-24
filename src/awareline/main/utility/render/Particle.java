/*    */ package awareline.main.utility.render;
/*    */ 
/*    */ import awareline.main.utility.ghosthitutily.DrawUtil;
/*    */ import java.awt.Color;
/*    */ 
/*    */ public class Particle {
/*    */   public double x;
/*    */   public double y;
/*    */   public double deltaX;
/*    */   
/*    */   public void render2D() {
/* 12 */     DrawUtil.circle(this.x, this.y, this.size, new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), (int)this.opacity));
/*    */   }
/*    */   public double deltaY; public double size; public double opacity; public Color color;
/*    */   public void updatePosition() {
/* 16 */     this.x += this.deltaX * 2.0D;
/* 17 */     this.y += this.deltaY * 2.0D;
/* 18 */     this.deltaY *= 0.95D;
/* 19 */     this.deltaX *= 0.95D;
/* 20 */     this.opacity -= 2.0D;
/* 21 */     if (this.opacity < 1.0D) this.opacity = 1.0D; 
/*    */   }
/*    */   
/*    */   public void init(double x, double y, double deltaX, double deltaY, double size, Color color) {
/* 25 */     this.x = x;
/* 26 */     this.y = y;
/* 27 */     this.deltaX = deltaX;
/* 28 */     this.deltaY = deltaY;
/* 29 */     this.size = size;
/* 30 */     this.opacity = 254.0D;
/* 31 */     this.color = color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\Particle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */