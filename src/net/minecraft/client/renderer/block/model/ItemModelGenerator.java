/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ public class ItemModelGenerator
/*     */ {
/*  16 */   public static final List<String> LAYERS = Lists.newArrayList((Object[])new String[] { "layer0", "layer1", "layer2", "layer3", "layer4" });
/*     */ 
/*     */   
/*     */   public ModelBlock makeItemModel(TextureMap textureMapIn, ModelBlock blockModel) {
/*  20 */     Map<String, String> map = Maps.newHashMap();
/*  21 */     List<BlockPart> list = Lists.newArrayList();
/*     */     
/*  23 */     for (int i = 0; i < LAYERS.size(); i++) {
/*     */       
/*  25 */       String s = LAYERS.get(i);
/*     */       
/*  27 */       if (!blockModel.isTexturePresent(s)) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/*  32 */       String s1 = blockModel.resolveTextureName(s);
/*  33 */       map.put(s, s1);
/*  34 */       TextureAtlasSprite textureatlassprite = textureMapIn.getAtlasSprite((new ResourceLocation(s1)).toString());
/*  35 */       list.addAll(func_178394_a(i, s, textureatlassprite));
/*     */     } 
/*     */     
/*  38 */     if (list.isEmpty())
/*     */     {
/*  40 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  44 */     map.put("particle", blockModel.isTexturePresent("particle") ? blockModel.resolveTextureName("particle") : map.get("layer0"));
/*  45 */     return new ModelBlock(list, map, false, false, blockModel.getAllTransforms());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<BlockPart> func_178394_a(int p_178394_1_, String p_178394_2_, TextureAtlasSprite p_178394_3_) {
/*  51 */     Map<EnumFacing, BlockPartFace> map = Maps.newHashMap();
/*  52 */     map.put(EnumFacing.SOUTH, new BlockPartFace((EnumFacing)null, p_178394_1_, p_178394_2_, new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0)));
/*  53 */     map.put(EnumFacing.NORTH, new BlockPartFace((EnumFacing)null, p_178394_1_, p_178394_2_, new BlockFaceUV(new float[] { 16.0F, 0.0F, 0.0F, 16.0F }, 0)));
/*  54 */     List<BlockPart> list = Lists.newArrayList();
/*  55 */     list.add(new BlockPart(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), map, (BlockPartRotation)null, true));
/*  56 */     list.addAll(func_178397_a(p_178394_3_, p_178394_2_, p_178394_1_));
/*  57 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<BlockPart> func_178397_a(TextureAtlasSprite p_178397_1_, String p_178397_2_, int p_178397_3_) {
/*  62 */     float f = p_178397_1_.getIconWidth();
/*  63 */     float f1 = p_178397_1_.getIconHeight();
/*  64 */     List<BlockPart> list = Lists.newArrayList();
/*     */     
/*  66 */     for (Span itemmodelgenerator$span : func_178393_a(p_178397_1_)) {
/*     */       
/*  68 */       float f2 = 0.0F;
/*  69 */       float f3 = 0.0F;
/*  70 */       float f4 = 0.0F;
/*  71 */       float f5 = 0.0F;
/*  72 */       float f6 = 0.0F;
/*  73 */       float f7 = 0.0F;
/*  74 */       float f8 = 0.0F;
/*  75 */       float f9 = 0.0F;
/*  76 */       float f10 = 0.0F;
/*  77 */       float f11 = 0.0F;
/*  78 */       float f12 = itemmodelgenerator$span.func_178385_b();
/*  79 */       float f13 = itemmodelgenerator$span.func_178384_c();
/*  80 */       float f14 = itemmodelgenerator$span.func_178381_d();
/*  81 */       SpanFacing itemmodelgenerator$spanfacing = itemmodelgenerator$span.func_178383_a();
/*     */       
/*  83 */       switch (itemmodelgenerator$spanfacing) {
/*     */         
/*     */         case UP:
/*  86 */           f6 = f12;
/*  87 */           f2 = f12;
/*  88 */           f4 = f7 = f13 + 1.0F;
/*  89 */           f8 = f14;
/*  90 */           f3 = f14;
/*  91 */           f9 = f14;
/*  92 */           f5 = f14;
/*  93 */           f10 = 16.0F / f;
/*  94 */           f11 = 16.0F / (f1 - 1.0F);
/*     */           break;
/*     */         
/*     */         case DOWN:
/*  98 */           f9 = f14;
/*  99 */           f8 = f14;
/* 100 */           f6 = f12;
/* 101 */           f2 = f12;
/* 102 */           f4 = f7 = f13 + 1.0F;
/* 103 */           f3 = f14 + 1.0F;
/* 104 */           f5 = f14 + 1.0F;
/* 105 */           f10 = 16.0F / f;
/* 106 */           f11 = 16.0F / (f1 - 1.0F);
/*     */           break;
/*     */         
/*     */         case LEFT:
/* 110 */           f6 = f14;
/* 111 */           f2 = f14;
/* 112 */           f7 = f14;
/* 113 */           f4 = f14;
/* 114 */           f9 = f12;
/* 115 */           f3 = f12;
/* 116 */           f5 = f8 = f13 + 1.0F;
/* 117 */           f10 = 16.0F / (f - 1.0F);
/* 118 */           f11 = 16.0F / f1;
/*     */           break;
/*     */         
/*     */         case RIGHT:
/* 122 */           f7 = f14;
/* 123 */           f6 = f14;
/* 124 */           f2 = f14 + 1.0F;
/* 125 */           f4 = f14 + 1.0F;
/* 126 */           f9 = f12;
/* 127 */           f3 = f12;
/* 128 */           f5 = f8 = f13 + 1.0F;
/* 129 */           f10 = 16.0F / (f - 1.0F);
/* 130 */           f11 = 16.0F / f1;
/*     */           break;
/*     */       } 
/* 133 */       float f15 = 16.0F / f;
/* 134 */       float f16 = 16.0F / f1;
/* 135 */       f2 *= f15;
/* 136 */       f4 *= f15;
/* 137 */       f3 *= f16;
/* 138 */       f5 *= f16;
/* 139 */       f3 = 16.0F - f3;
/* 140 */       f5 = 16.0F - f5;
/* 141 */       f6 *= f10;
/* 142 */       f7 *= f10;
/* 143 */       f8 *= f11;
/* 144 */       f9 *= f11;
/* 145 */       Map<EnumFacing, BlockPartFace> map = Maps.newHashMap();
/* 146 */       map.put(itemmodelgenerator$spanfacing.getFacing(), new BlockPartFace((EnumFacing)null, p_178397_3_, p_178397_2_, new BlockFaceUV(new float[] { f6, f8, f7, f9 }, 0)));
/*     */       
/* 148 */       switch (itemmodelgenerator$spanfacing) {
/*     */         
/*     */         case UP:
/* 151 */           list.add(new BlockPart(new Vector3f(f2, f3, 7.5F), new Vector3f(f4, f3, 8.5F), map, (BlockPartRotation)null, true));
/*     */ 
/*     */         
/*     */         case DOWN:
/* 155 */           list.add(new BlockPart(new Vector3f(f2, f5, 7.5F), new Vector3f(f4, f5, 8.5F), map, (BlockPartRotation)null, true));
/*     */ 
/*     */         
/*     */         case LEFT:
/* 159 */           list.add(new BlockPart(new Vector3f(f2, f3, 7.5F), new Vector3f(f2, f5, 8.5F), map, (BlockPartRotation)null, true));
/*     */ 
/*     */         
/*     */         case RIGHT:
/* 163 */           list.add(new BlockPart(new Vector3f(f4, f3, 7.5F), new Vector3f(f4, f5, 8.5F), map, (BlockPartRotation)null, true));
/*     */       } 
/*     */     
/*     */     } 
/* 167 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Span> func_178393_a(TextureAtlasSprite p_178393_1_) {
/* 172 */     int i = p_178393_1_.getIconWidth();
/* 173 */     int j = p_178393_1_.getIconHeight();
/* 174 */     List<Span> list = Lists.newArrayList();
/*     */     
/* 176 */     for (int k = 0; k < p_178393_1_.getFrameCount(); k++) {
/*     */       
/* 178 */       int[] aint = p_178393_1_.getFrameTextureData(k)[0];
/*     */       
/* 180 */       for (int l = 0; l < j; l++) {
/*     */         
/* 182 */         for (int i1 = 0; i1 < i; i1++) {
/*     */           
/* 184 */           boolean flag = !func_178391_a(aint, i1, l, i, j);
/* 185 */           func_178396_a(SpanFacing.UP, list, aint, i1, l, i, j, flag);
/* 186 */           func_178396_a(SpanFacing.DOWN, list, aint, i1, l, i, j, flag);
/* 187 */           func_178396_a(SpanFacing.LEFT, list, aint, i1, l, i, j, flag);
/* 188 */           func_178396_a(SpanFacing.RIGHT, list, aint, i1, l, i, j, flag);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178396_a(SpanFacing p_178396_1_, List<Span> p_178396_2_, int[] p_178396_3_, int p_178396_4_, int p_178396_5_, int p_178396_6_, int p_178396_7_, boolean p_178396_8_) {
/* 198 */     boolean flag = (func_178391_a(p_178396_3_, p_178396_4_ + p_178396_1_.func_178372_b(), p_178396_5_ + p_178396_1_.func_178371_c(), p_178396_6_, p_178396_7_) && p_178396_8_);
/*     */     
/* 200 */     if (flag)
/*     */     {
/* 202 */       func_178395_a(p_178396_2_, p_178396_1_, p_178396_4_, p_178396_5_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178395_a(List<Span> p_178395_1_, SpanFacing p_178395_2_, int p_178395_3_, int p_178395_4_) {
/* 208 */     Span itemmodelgenerator$span = null;
/*     */     
/* 210 */     for (Span itemmodelgenerator$span1 : p_178395_1_) {
/*     */       
/* 212 */       if (itemmodelgenerator$span1.func_178383_a() == p_178395_2_) {
/*     */         
/* 214 */         int i = p_178395_2_.func_178369_d() ? p_178395_4_ : p_178395_3_;
/*     */         
/* 216 */         if (itemmodelgenerator$span1.func_178381_d() == i) {
/*     */           
/* 218 */           itemmodelgenerator$span = itemmodelgenerator$span1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 224 */     int j = p_178395_2_.func_178369_d() ? p_178395_4_ : p_178395_3_;
/* 225 */     int k = p_178395_2_.func_178369_d() ? p_178395_3_ : p_178395_4_;
/*     */     
/* 227 */     if (itemmodelgenerator$span == null) {
/*     */       
/* 229 */       p_178395_1_.add(new Span(p_178395_2_, k, j));
/*     */     }
/*     */     else {
/*     */       
/* 233 */       itemmodelgenerator$span.func_178382_a(k);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_178391_a(int[] p_178391_1_, int p_178391_2_, int p_178391_3_, int p_178391_4_, int p_178391_5_) {
/* 239 */     return (p_178391_2_ >= 0 && p_178391_3_ >= 0 && p_178391_2_ < p_178391_4_ && p_178391_3_ < p_178391_5_) ? (((p_178391_1_[p_178391_3_ * p_178391_4_ + p_178391_2_] >> 24 & 0xFF) == 0)) : true;
/*     */   }
/*     */ 
/*     */   
/*     */   static class Span
/*     */   {
/*     */     private final ItemModelGenerator.SpanFacing spanFacing;
/*     */     private int field_178387_b;
/*     */     private int field_178388_c;
/*     */     private final int field_178386_d;
/*     */     
/*     */     public Span(ItemModelGenerator.SpanFacing spanFacingIn, int p_i46216_2_, int p_i46216_3_) {
/* 251 */       this.spanFacing = spanFacingIn;
/* 252 */       this.field_178387_b = p_i46216_2_;
/* 253 */       this.field_178388_c = p_i46216_2_;
/* 254 */       this.field_178386_d = p_i46216_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_178382_a(int p_178382_1_) {
/* 259 */       if (p_178382_1_ < this.field_178387_b) {
/*     */         
/* 261 */         this.field_178387_b = p_178382_1_;
/*     */       }
/* 263 */       else if (p_178382_1_ > this.field_178388_c) {
/*     */         
/* 265 */         this.field_178388_c = p_178382_1_;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemModelGenerator.SpanFacing func_178383_a() {
/* 271 */       return this.spanFacing;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_178385_b() {
/* 276 */       return this.field_178387_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_178384_c() {
/* 281 */       return this.field_178388_c;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_178381_d() {
/* 286 */       return this.field_178386_d;
/*     */     }
/*     */   }
/*     */   
/*     */   enum SpanFacing
/*     */   {
/* 292 */     UP((String)EnumFacing.UP, 0, -1),
/* 293 */     DOWN((String)EnumFacing.DOWN, 0, 1),
/* 294 */     LEFT((String)EnumFacing.EAST, -1, 0),
/* 295 */     RIGHT((String)EnumFacing.WEST, 1, 0);
/*     */     
/*     */     private final EnumFacing facing;
/*     */     
/*     */     private final int field_178373_f;
/*     */     private final int field_178374_g;
/*     */     
/*     */     SpanFacing(EnumFacing facing, int p_i46215_4_, int p_i46215_5_) {
/* 303 */       this.facing = facing;
/* 304 */       this.field_178373_f = p_i46215_4_;
/* 305 */       this.field_178374_g = p_i46215_5_;
/*     */     }
/*     */ 
/*     */     
/*     */     public EnumFacing getFacing() {
/* 310 */       return this.facing;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_178372_b() {
/* 315 */       return this.field_178373_f;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_178371_c() {
/* 320 */       return this.field_178374_g;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean func_178369_d() {
/* 325 */       return (this == DOWN || this == UP);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\block\model\ItemModelGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */