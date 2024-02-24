/*    */ package awareline.main.mod.values;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public abstract class Value<V> {
/*    */   private String name;
/*    */   private String splicedName;
/*    */   private V value;
/*    */   
/* 11 */   public String getName() { return this.name; } private final Supplier<Boolean> visitable; private final Supplier<Boolean> visitable2; private final Supplier<Boolean> visitable3; public boolean Downopen; public String getSplicedName() { return this.splicedName; }
/* 12 */   public V getValue() { return this.value; }
/* 13 */   public Supplier<Boolean> getVisitable() { return this.visitable; } public Supplier<Boolean> getVisitable2() { return this.visitable2; } public Supplier<Boolean> getVisitable3() { return this.visitable3; }
/*    */    public boolean isDownopen() {
/* 15 */     return this.Downopen;
/*    */   }
/* 17 */   public final ArrayList<String> mode = new ArrayList<>(); public int current; public ArrayList<String> getMode() { return this.mode; } public int getCurrent() {
/* 18 */     return this.current;
/*    */   }
/*    */   
/*    */   public Value(String name, V value, Supplier<Boolean> visitable, Supplier<Boolean> visitable2, Supplier<Boolean> visitable3) {
/* 22 */     this.name = name;
/* 23 */     this.splicedName = "";
/* 24 */     this.visitable = visitable;
/* 25 */     this.visitable2 = visitable2;
/* 26 */     this.visitable3 = visitable3;
/* 27 */     this.value = value;
/* 28 */     EventManager.register(new Object[] { this });
/*    */   }
/*    */   
/*    */   public final String getBreakName() {
/* 32 */     if (this.splicedName.isEmpty()) {
/* 33 */       StringBuilder sb = new StringBuilder();
/* 34 */       char[] arr = this.name.toCharArray();
/* 35 */       int j = 0;
/* 36 */       while (j < arr.length) {
/* 37 */         int i = j;
/* 38 */         j++;
/* 39 */         char char1 = arr[i];
/* 40 */         if (i != 0 && !Character.isLowerCase(char1) && Character.isLowerCase(arr[i - 1])) {
/* 41 */           sb.append(' ');
/*    */         }
/* 43 */         sb.append(char1);
/*    */       } 
/* 45 */       this.splicedName = sb.toString();
/*    */     } 
/* 47 */     return this.splicedName;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setName(String name) {
/* 52 */     this.name = name;
/*    */   }
/*    */   
/*    */   public V get() {
/* 56 */     return this.value;
/*    */   }
/*    */   
/*    */   public V isEnabled() {
/* 60 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(V value) {
/* 64 */     this.value = value;
/*    */   }
/*    */   
/*    */   public boolean isVisitable() {
/* 68 */     return (((Boolean)this.visitable.get()).booleanValue() && ((Boolean)this.visitable2.get()).booleanValue() && ((Boolean)this.visitable3.get()).booleanValue());
/*    */   }
/*    */   
/*    */   public ArrayList<String> listModes() {
/* 72 */     return this.mode;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModeAt(int index) {
/* 77 */     return this.mode.get(index);
/*    */   }
/*    */   
/*    */   public String getDisplayTitle() {
/* 81 */     if (this.value instanceof Mode) {
/* 82 */       return this.name;
/*    */     }
/* 84 */     return this.name.split("_")[1];
/*    */   }
/*    */   
/*    */   public void setDownopen(boolean b) {
/* 88 */     this.Downopen = b;
/*    */   }
/*    */   
/*    */   public V getValueState() {
/* 92 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\values\Value.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */