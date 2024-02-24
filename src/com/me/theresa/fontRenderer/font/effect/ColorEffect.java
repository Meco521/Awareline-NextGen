/*    */ package com.me.theresa.fontRenderer.font.effect;
/*    */ 
/*    */ import com.me.theresa.fontRenderer.font.Glyph;
/*    */ import com.me.theresa.fontRenderer.font.UnicodeFont;
/*    */ import com.me.theresa.fontRenderer.font.util.EffectUtil;
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class ColorEffect
/*    */   implements ConfigurableEffect
/*    */ {
/* 17 */   private Color color = Color.white;
/*    */ 
/*    */ 
/*    */   
/*    */   public ColorEffect() {}
/*    */ 
/*    */   
/*    */   public ColorEffect(Color color) {
/* 25 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph) {
/* 30 */     g.setColor(this.color);
/* 31 */     g.fill(glyph.getShape());
/*    */   }
/*    */ 
/*    */   
/*    */   public Color getColor() {
/* 36 */     return this.color;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setColor(Color color) {
/* 41 */     if (color == null) throw new IllegalArgumentException("color cannot be null."); 
/* 42 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return "Color";
/*    */   }
/*    */ 
/*    */   
/*    */   public List getValues() {
/* 52 */     List<ConfigurableEffect.Value> values = new ArrayList();
/* 53 */     values.add(EffectUtil.colorValue("Color", this.color));
/* 54 */     return values;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValues(List values) {
/* 59 */     for (Iterator<ConfigurableEffect.Value> iter = values.iterator(); iter.hasNext(); ) {
/* 60 */       ConfigurableEffect.Value value = iter.next();
/* 61 */       if (value.getName().equals("Color"))
/* 62 */         setColor((Color)value.getObject()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\effect\ColorEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */