/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyUtils
/*    */ {
/*    */   public static void fixKeyConflicts(KeyBinding[] keys, KeyBinding[] keysPrio) {
/* 13 */     Set<Integer> set = new HashSet<>();
/*    */     
/* 15 */     for (int i = 0; i < keysPrio.length; i++) {
/*    */       
/* 17 */       KeyBinding keybinding = keysPrio[i];
/* 18 */       set.add(Integer.valueOf(keybinding.getKeyCode()));
/*    */     } 
/*    */     
/* 21 */     Set<KeyBinding> set1 = new HashSet<>(Arrays.asList(keys));
/* 22 */     Arrays.<KeyBinding>asList(keysPrio).forEach(set1::remove);
/*    */     
/* 24 */     for (KeyBinding keybinding1 : set1) {
/*    */       
/* 26 */       Integer integer = Integer.valueOf(keybinding1.getKeyCode());
/*    */       
/* 28 */       if (set.contains(integer))
/*    */       {
/* 30 */         keybinding1.setKeyCode(0);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\KeyUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */