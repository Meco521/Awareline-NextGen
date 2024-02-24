/*     */ package awareline.main.mod.values;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ public class Mode<V extends String>
/*     */   extends Value<V> {
/*     */   public final V[] modes;
/*     */   
/*     */   public Mode(String name, V[] modes, V value) {
/*  11 */     super(name, value, () -> Boolean.valueOf(true), () -> Boolean.valueOf(true), () -> Boolean.valueOf(true));
/*  12 */     this.modes = modes;
/*  13 */     this.mode.addAll(Arrays.asList((String[])getModes()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Mode(String name, V[] modes) {
/*  18 */     super(name, modes[0], () -> Boolean.valueOf(true), () -> Boolean.valueOf(true), () -> Boolean.valueOf(true));
/*  19 */     this.modes = modes;
/*  20 */     this.mode.addAll(Arrays.asList((String[])getModes()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Mode(String name, V[] modes, V value, Supplier<Boolean> visitable) {
/*  25 */     super(name, value, visitable, () -> Boolean.valueOf(true), () -> Boolean.valueOf(true));
/*  26 */     this.modes = modes;
/*  27 */     this.mode.addAll(Arrays.asList((String[])getModes()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Mode(String name, V[] modes, Supplier<Boolean> visitable) {
/*  32 */     super(name, modes[0], visitable, () -> Boolean.valueOf(true), () -> Boolean.valueOf(true));
/*  33 */     this.modes = modes;
/*  34 */     this.mode.addAll(Arrays.asList((String[])getModes()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Mode(String name, V[] modes, V value, Supplier<Boolean> visitable, Supplier<Boolean> visitable2) {
/*  39 */     super(name, value, visitable, visitable2, () -> Boolean.valueOf(true));
/*  40 */     this.modes = modes;
/*  41 */     this.mode.addAll(Arrays.asList((String[])getModes()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Mode(String name, V[] modes, V value, Supplier<Boolean> visitable, Supplier<Boolean> visitable2, Supplier<Boolean> visitable3) {
/*  46 */     super(name, value, visitable, visitable2, visitable3);
/*  47 */     this.modes = modes;
/*  48 */     this.mode.addAll(Arrays.asList((String[])getModes()));
/*     */   }
/*     */   
/*     */   public V[] getModes() {
/*  52 */     return this.modes;
/*     */   }
/*     */   
/*     */   public void setCurrentMode(int current) {
/*  56 */     if (current > this.mode.size() - 1) {
/*  57 */       System.out.println("Value is to big! Set to 0. (" + this.mode.size() + ")");
/*     */       return;
/*     */     } 
/*  60 */     this.current = current;
/*  61 */     setValue(this.modes[current]);
/*     */   }
/*     */   
/*     */   public String getModeAsString() {
/*  65 */     return (String)getValue();
/*     */   }
/*     */   
/*     */   public void setMode(String mode) {
/*  69 */     V[] arrV = this.modes;
/*  70 */     int n = arrV.length;
/*  71 */     int n2 = 0;
/*  72 */     while (n2 < n) {
/*  73 */       V e = arrV[n2];
/*  74 */       if (e.equalsIgnoreCase(mode)) {
/*  75 */         setValue(e);
/*     */       }
/*  77 */       n2++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMode(int mode) {
/*  82 */     setValue(this.modes[mode]);
/*     */   }
/*     */   
/*     */   public boolean isValid(String name) {
/*  86 */     V[] arrV = this.modes;
/*  87 */     int n = arrV.length;
/*  88 */     int n2 = 0;
/*  89 */     while (n2 < n) {
/*  90 */       V e = arrV[n2];
/*  91 */       if (e.equalsIgnoreCase(name)) {
/*  92 */         return true;
/*     */       }
/*  94 */       n2++;
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public final boolean is(String mode) {
/* 100 */     return ((String)getValue()).equalsIgnoreCase(mode);
/*     */   }
/*     */   
/*     */   public boolean isCurrentMode(String mode) {
/* 104 */     return ((String)getValue()).equalsIgnoreCase(mode);
/*     */   }
/*     */   
/*     */   public boolean isCurrentModes(String mode) {
/* 108 */     return ((String)getValue()).toLowerCase().contains(mode.toLowerCase());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\values\Mode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */