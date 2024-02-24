/*    */ package awareline.main.mod.implement.misc;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class Spammer extends Module {
/* 16 */   private final TimeHelper timer = new TimeHelper();
/* 17 */   private final Mode<String> spammer = new Mode("SpammerText", new String[] { "Normal", "HmXix" }, "Normal");
/* 18 */   private final Numbers<Double> frequency = new Numbers("Frequency", Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
/* 19 */   private final Numbers<Double> delay = new Numbers("Delay", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(10.0D), Double.valueOf(0.1D));
/* 20 */   private final Numbers<Double> random = new Numbers("Random", Double.valueOf(6.0D), Double.valueOf(0.0D), Double.valueOf(36.0D), Double.valueOf(1.0D));
/* 21 */   private final Option<Boolean> chatPostfix = new Option("ChatPostfix", Boolean.valueOf(false));
/* 22 */   private final Mode<String> chatPostfixMode = new Mode("ChatPostfixMode", new String[] { "锟斤拷锟斤拷", "...........", "锟斤拷" }, "锟斤拷锟斤拷");
/*    */   public String message;
/*    */   public static String bindmessage;
/*    */   
/*    */   public Spammer() {
/* 27 */     super("Spammer", ModuleType.Misc);
/* 28 */     addSettings(new Value[] { (Value)this.spammer, (Value)this.delay, (Value)this.frequency, (Value)this.chatPostfix, (Value)this.chatPostfixMode, (Value)this.random });
/*    */   }
/*    */   
/*    */   private String getPostfix() {
/* 32 */     if (this.chatPostfixMode.is("锟斤拷锟斤拷"))
/* 33 */       return "锟斤拷锟斤拷"; 
/* 34 */     if (this.chatPostfixMode.is("..........."))
/* 35 */       return "..........."; 
/* 36 */     if (this.chatPostfixMode.is("锟斤拷")) {
/* 37 */       return "锟斤拷";
/*    */     }
/* 39 */     return ".";
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate e) {
/* 44 */     if (this.spammer.is("Normal")) {
/* 45 */       if (bindmessage != null)
/* 46 */       { Client.instance.getClass(); this.message = "[" + "Awareline" + "] " + bindmessage; }
/*    */       else
/* 48 */       { Client.instance.getClass(); this.message = "[" + "Awareline" + "] Best hacker client by Awareline"; } 
/* 49 */     } else if (this.spammer.is("HmXix")) {
/* 50 */       this.message = "&4&n&l&o&k321&b&n&l锟斤拷锟斤拷&4&n&lAwareline &aHmXix锟斤拷强锟酵伙拷锟斤拷 &d锟斤拷锟斤拷锟斤拷证 &c&n&l+Q 3368354014 锟斤拷时70r&4&n&l&o&k123";
/*    */     } 
/* 52 */     long delayNew = (long)(((Double)this.delay.getValue()).doubleValue() * 1000.0D);
/* 53 */     if (this.timer.isDelayComplete(delayNew)) {
/* 54 */       for (int i = 0; i < ((Double)this.frequency.getValue()).intValue(); i++) {
/* 55 */         mc.thePlayer.sendChatMessage(this.message + (((Boolean)this.chatPostfix.get()).booleanValue() ? getPostfix() : "") + " <" + getRandomString(((Double)this.random.getValue()).doubleValue()) + ">");
/*    */       }
/* 57 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getRandomString(double d) {
/* 62 */     String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
/* 63 */     Random random = new Random();
/* 64 */     StringBuilder sb = new StringBuilder();
/* 65 */     for (int i = 0; i < d; i++) {
/* 66 */       int number = random.nextInt(62);
/* 67 */       sb.append(str.charAt(number));
/*    */     } 
/* 69 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\Spammer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */