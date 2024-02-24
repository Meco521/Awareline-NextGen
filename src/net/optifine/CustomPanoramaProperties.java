/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.config.ConnectedParser;
/*    */ 
/*    */ 
/*    */ public class CustomPanoramaProperties
/*    */ {
/*    */   private final String path;
/*    */   private final ResourceLocation[] panoramaLocations;
/* 12 */   private int weight = 1;
/* 13 */   private int blur1 = 64;
/* 14 */   private int blur2 = 3;
/* 15 */   private int blur3 = 3;
/* 16 */   private int overlay1Top = -2130706433;
/* 17 */   private int overlay1Bottom = 16777215;
/* 18 */   private int overlay2Top = 0;
/* 19 */   private int overlay2Bottom = Integer.MIN_VALUE;
/*    */ 
/*    */   
/*    */   public CustomPanoramaProperties(String path, Properties props) {
/* 23 */     ConnectedParser connectedparser = new ConnectedParser("CustomPanorama");
/* 24 */     this.path = path;
/* 25 */     this.panoramaLocations = new ResourceLocation[6];
/*    */     
/* 27 */     for (int i = 0; i < this.panoramaLocations.length; i++)
/*    */     {
/* 29 */       this.panoramaLocations[i] = new ResourceLocation(path + "/panorama_" + i + ".png");
/*    */     }
/*    */     
/* 32 */     this.weight = connectedparser.parseInt(props.getProperty("weight"), 1);
/* 33 */     this.blur1 = connectedparser.parseInt(props.getProperty("blur1"), 64);
/* 34 */     this.blur2 = connectedparser.parseInt(props.getProperty("blur2"), 3);
/* 35 */     this.blur3 = connectedparser.parseInt(props.getProperty("blur3"), 3);
/* 36 */     this.overlay1Top = ConnectedParser.parseColor4(props.getProperty("overlay1.top"), -2130706433);
/* 37 */     this.overlay1Bottom = ConnectedParser.parseColor4(props.getProperty("overlay1.bottom"), 16777215);
/* 38 */     this.overlay2Top = ConnectedParser.parseColor4(props.getProperty("overlay2.top"), 0);
/* 39 */     this.overlay2Bottom = ConnectedParser.parseColor4(props.getProperty("overlay2.bottom"), -2147483648);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation[] getPanoramaLocations() {
/* 44 */     return this.panoramaLocations;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeight() {
/* 49 */     return this.weight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlur1() {
/* 54 */     return this.blur1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlur2() {
/* 59 */     return this.blur2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlur3() {
/* 64 */     return this.blur3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay1Top() {
/* 69 */     return this.overlay1Top;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay1Bottom() {
/* 74 */     return this.overlay1Bottom;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay2Top() {
/* 79 */     return this.overlay2Top;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay2Bottom() {
/* 84 */     return this.overlay2Bottom;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 89 */     return this.path + ", weight: " + this.weight + ", blur: " + this.blur1 + " " + this.blur2 + " " + this.blur3 + ", overlay: " + this.overlay1Top + " " + this.overlay1Bottom + " " + this.overlay2Top + " " + this.overlay2Bottom;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomPanoramaProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */