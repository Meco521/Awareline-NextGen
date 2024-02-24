/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class VertexFormat
/*     */ {
/*  11 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexFormat(VertexFormat vertexFormatIn) {
/*  23 */     this();
/*     */     
/*  25 */     for (int i = 0; i < vertexFormatIn.getElementCount(); i++)
/*     */     {
/*  27 */       addElement(vertexFormatIn.getElement(i));
/*     */     }
/*     */     
/*  30 */     this.nextOffset = vertexFormatIn.nextOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  35 */   private final List<VertexFormatElement> elements = Lists.newArrayList();
/*  36 */   private final List<Integer> offsets = Lists.newArrayList();
/*  37 */   private int nextOffset = 0;
/*  38 */   private int colorElementOffset = -1;
/*  39 */   private final List<Integer> uvOffsetsById = Lists.newArrayList();
/*  40 */   private int normalElementOffset = -1;
/*     */   
/*     */   public VertexFormat() {}
/*     */   
/*     */   public void clear() {
/*  45 */     this.elements.clear();
/*  46 */     this.offsets.clear();
/*  47 */     this.colorElementOffset = -1;
/*  48 */     this.uvOffsetsById.clear();
/*  49 */     this.normalElementOffset = -1;
/*  50 */     this.nextOffset = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexFormat addElement(VertexFormatElement element) {
/*  56 */     if (element.isPositionElement() && hasPosition()) {
/*     */       
/*  58 */       LOGGER.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
/*  59 */       return this;
/*     */     } 
/*     */ 
/*     */     
/*  63 */     this.elements.add(element);
/*  64 */     this.offsets.add(Integer.valueOf(this.nextOffset));
/*     */     
/*  66 */     switch (element.getUsage()) {
/*     */       
/*     */       case NORMAL:
/*  69 */         this.normalElementOffset = this.nextOffset;
/*     */         break;
/*     */       
/*     */       case COLOR:
/*  73 */         this.colorElementOffset = this.nextOffset;
/*     */         break;
/*     */       
/*     */       case UV:
/*  77 */         this.uvOffsetsById.add(element.getIndex(), Integer.valueOf(this.nextOffset));
/*     */         break;
/*     */     } 
/*  80 */     this.nextOffset += element.getSize();
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNormal() {
/*  87 */     return (this.normalElementOffset >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNormalOffset() {
/*  92 */     return this.normalElementOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasColor() {
/*  97 */     return (this.colorElementOffset >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorOffset() {
/* 102 */     return this.colorElementOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasUvOffset(int id) {
/* 107 */     return (this.uvOffsetsById.size() - 1 >= id);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUvOffsetById(int id) {
/* 112 */     return ((Integer)this.uvOffsetsById.get(id)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 117 */     StringBuilder s = new StringBuilder("format: " + this.elements.size() + " elements: ");
/*     */     
/* 119 */     for (int i = 0; i < this.elements.size(); i++) {
/*     */       
/* 121 */       s.append(((VertexFormatElement)this.elements.get(i)).toString());
/*     */       
/* 123 */       if (i != this.elements.size() - 1)
/*     */       {
/* 125 */         s.append(" ");
/*     */       }
/*     */     } 
/*     */     
/* 129 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasPosition() {
/* 134 */     int i = 0;
/*     */     
/* 136 */     for (int j = this.elements.size(); i < j; i++) {
/*     */       
/* 138 */       VertexFormatElement vertexformatelement = this.elements.get(i);
/*     */       
/* 140 */       if (vertexformatelement.isPositionElement())
/*     */       {
/* 142 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntegerSize() {
/* 151 */     return this.nextOffset / 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNextOffset() {
/* 156 */     return this.nextOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<VertexFormatElement> getElements() {
/* 161 */     return this.elements;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getElementCount() {
/* 166 */     return this.elements.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormatElement getElement(int index) {
/* 171 */     return this.elements.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOffset(int p_181720_1_) {
/* 176 */     return ((Integer)this.offsets.get(p_181720_1_)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 181 */     if (this == p_equals_1_)
/*     */     {
/* 183 */       return true;
/*     */     }
/* 185 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/* 187 */       VertexFormat vertexformat = (VertexFormat)p_equals_1_;
/* 188 */       return (this.nextOffset != vertexformat.nextOffset) ? false : (!this.elements.equals(vertexformat.elements) ? false : this.offsets.equals(vertexformat.offsets));
/*     */     } 
/*     */ 
/*     */     
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 198 */     int i = this.elements.hashCode();
/* 199 */     i = 31 * i + this.offsets.hashCode();
/* 200 */     i = 31 * i + this.nextOffset;
/* 201 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\vertex\VertexFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */