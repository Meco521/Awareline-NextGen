/*     */ package awareline.main.cmd;
/*     */ 
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class CommandModule
/*     */   extends Command {
/*     */   private final Module m;
/*     */   
/*     */   public CommandModule(Module module, String name, String[] alias) {
/*  17 */     super(name, alias);
/*  18 */     this.m = module;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(String[] args) {
/*  24 */     if (args.length >= 2) {
/*  25 */       Option<Boolean> option = null;
/*  26 */       Numbers<Double> fuck = null;
/*  27 */       Mode<? extends String> xd = null;
/*  28 */       Iterator<Value> var6 = this.m.getValues().iterator();
/*     */ 
/*     */       
/*  31 */       while (var6.hasNext()) {
/*  32 */         Value<?> v = var6.next();
/*  33 */         if (v instanceof Option && v.getName().equalsIgnoreCase(args[0])) {
/*  34 */           option = (Option)v;
/*     */         }
/*     */       } 
/*     */       
/*  38 */       if (option != null) {
/*  39 */         switch (args.length) {
/*     */           case 2:
/*  41 */             option.setValue(Boolean.valueOf(!((Boolean)option.getValue()).booleanValue()));
/*  42 */             Helper.sendMessage(String.format("%s> %s has been set to %s", new Object[] { getName(), option.getName(), option.getValue() }));
/*     */             break;
/*     */           case 3:
/*  45 */             if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
/*  46 */               option.setValue(Boolean.valueOf(Boolean.parseBoolean(args[2])));
/*  47 */               Helper.sendMessage(String.format("%s> %s has been set to %s", new Object[] { getName(), option.getName(), option.getValue() }));
/*     */               break;
/*     */             } 
/*  50 */             Helper.sendMessage(String.format("> %s is not a boolean value!", new Object[] { args[2] }));
/*     */             break;
/*     */         } 
/*     */       } else {
/*  54 */         var6 = this.m.values.iterator();
/*     */         
/*  56 */         while (var6.hasNext()) {
/*  57 */           Value<?> v = var6.next();
/*  58 */           if (v instanceof Numbers && v.getName().equalsIgnoreCase(args[0])) {
/*  59 */             fuck = (Numbers)v;
/*     */           }
/*     */         } 
/*     */         
/*  63 */         if (fuck != null) {
/*  64 */           if (MathUtil.parsable(args[1], (byte)4)) {
/*  65 */             double v1 = MathUtil.round(Double.parseDouble(args[1]), 1);
/*  66 */             fuck.setValue((v1 > ((Double)fuck.getMaximum()).doubleValue()) ? fuck.getMaximum() : Double.valueOf(v1));
/*  67 */             Helper.sendMessage(String.format("> %s has been set to %s", new Object[] { fuck.getName(), fuck.getValue() }));
/*     */           } else {
/*  69 */             Helper.sendMessage("> " + args[1] + " is not a number!");
/*     */           } 
/*     */         }
/*     */         
/*  73 */         var6 = this.m.getValues().iterator();
/*     */         
/*  75 */         while (var6.hasNext()) {
/*  76 */           Value<?> v = var6.next();
/*  77 */           if (args[0].equalsIgnoreCase(v.getName()) && v instanceof Mode) {
/*  78 */             xd = (Mode)v;
/*     */           }
/*     */         } 
/*     */         
/*  82 */         if (xd != null) {
/*  83 */           if (xd.isValid(args[1])) {
/*  84 */             xd.setMode(args[1]);
/*  85 */             Helper.sendMessage(String.format("> %s set to %s", new Object[] { xd.getName(), xd.getModeAsString() }));
/*     */           } else {
/*  87 */             Helper.sendMessage("> " + args[1] + " is an invalid mode");
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/*  92 */       if (fuck == null && option == null && xd == null) {
/*  93 */         Command.syntaxError();
/*     */       }
/*  95 */     } else if (args.length == 1) {
/*  96 */       Option<Boolean> option = null;
/*     */       
/*  98 */       for (Value fuck1 : this.m.getValues()) {
/*  99 */         if (fuck1 instanceof Option && fuck1.getName().equalsIgnoreCase(args[0])) {
/* 100 */           option = (Option<Boolean>)fuck1;
/*     */         }
/*     */       } 
/*     */       
/* 104 */       if (option != null) {
/* 105 */         option.setValue(Boolean.valueOf(!((Boolean)option.getValue()).booleanValue()));
/* 106 */         String fuck2 = option.getName().substring(1);
/* 107 */         String xd2 = option.getName().substring(0, 1).toUpperCase();
/* 108 */         if (((Boolean)option.getValue()).booleanValue()) {
/* 109 */           Helper.sendMessage(String.format("> %s has been set to §a%s", new Object[] { xd2 + fuck2, option.getValue() }));
/*     */         } else {
/* 111 */           Helper.sendMessage(String.format("> %s has been set to §c%s", new Object[] { xd2 + fuck2, option.getValue() }));
/*     */         } 
/*     */       } else {
/* 114 */         Command.syntaxError();
/*     */       } 
/*     */     } else {
/* 117 */       StringBuilder stringBuilder = new StringBuilder();
/* 118 */       for (Value v : this.m.getValues()) {
/* 119 */         stringBuilder.append(v.getName()).append(", ");
/*     */       }
/* 121 */       Helper.sendMessage(String.format("%s Values: \n %s", new Object[] { getName().substring(0, 1).toUpperCase() + getName().substring(1).toLowerCase(), stringBuilder.substring(0, stringBuilder.toString().length() - 2) }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\cmd\CommandModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */