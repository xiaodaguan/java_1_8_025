/*     */ package java.beans;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectOutputStream.PutField;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.util.EventListener;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VetoableChangeSupport
/*     */   implements Serializable
/*     */ {
/*  82 */   private VetoableChangeListenerMap map = new VetoableChangeListenerMap(null);
/*     */   
/*     */ 
/*     */   private Object source;
/*     */   
/*     */ 
/*     */   public VetoableChangeSupport(Object paramObject)
/*     */   {
/*  90 */     if (paramObject == null) {
/*  91 */       throw new NullPointerException();
/*     */     }
/*  93 */     this.source = paramObject;
/*     */   }
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
/*     */   public void addVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener)
/*     */   {
/* 107 */     if (paramVetoableChangeListener == null) {
/* 108 */       return;
/*     */     }
/* 110 */     if ((paramVetoableChangeListener instanceof VetoableChangeListenerProxy)) {
/* 111 */       VetoableChangeListenerProxy localVetoableChangeListenerProxy = (VetoableChangeListenerProxy)paramVetoableChangeListener;
/*     */       
/*     */ 
/* 114 */       addVetoableChangeListener(localVetoableChangeListenerProxy.getPropertyName(), 
/* 115 */         (VetoableChangeListener)localVetoableChangeListenerProxy.getListener());
/*     */     } else {
/* 117 */       this.map.add(null, paramVetoableChangeListener);
/*     */     }
/*     */   }
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
/*     */ 
/*     */   public void removeVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener)
/*     */   {
/* 133 */     if (paramVetoableChangeListener == null) {
/* 134 */       return;
/*     */     }
/* 136 */     if ((paramVetoableChangeListener instanceof VetoableChangeListenerProxy)) {
/* 137 */       VetoableChangeListenerProxy localVetoableChangeListenerProxy = (VetoableChangeListenerProxy)paramVetoableChangeListener;
/*     */       
/*     */ 
/* 140 */       removeVetoableChangeListener(localVetoableChangeListenerProxy.getPropertyName(), 
/* 141 */         (VetoableChangeListener)localVetoableChangeListenerProxy.getListener());
/*     */     } else {
/* 143 */       this.map.remove(null, paramVetoableChangeListener);
/*     */     }
/*     */   }
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
/*     */ 
/*     */   public VetoableChangeListener[] getVetoableChangeListeners()
/*     */   {
/* 179 */     return (VetoableChangeListener[])this.map.getListeners();
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener)
/*     */   {
/* 198 */     if ((paramVetoableChangeListener == null) || (paramString == null)) {
/* 199 */       return;
/*     */     }
/* 201 */     paramVetoableChangeListener = this.map.extract(paramVetoableChangeListener);
/* 202 */     if (paramVetoableChangeListener != null) {
/* 203 */       this.map.add(paramString, paramVetoableChangeListener);
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener)
/*     */   {
/* 223 */     if ((paramVetoableChangeListener == null) || (paramString == null)) {
/* 224 */       return;
/*     */     }
/* 226 */     paramVetoableChangeListener = this.map.extract(paramVetoableChangeListener);
/* 227 */     if (paramVetoableChangeListener != null) {
/* 228 */       this.map.remove(paramString, paramVetoableChangeListener);
/*     */     }
/*     */   }
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
/*     */ 
/*     */   public VetoableChangeListener[] getVetoableChangeListeners(String paramString)
/*     */   {
/* 244 */     return (VetoableChangeListener[])this.map.getListeners(paramString);
/*     */   }
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
/*     */ 
/*     */   public void fireVetoableChange(String paramString, Object paramObject1, Object paramObject2)
/*     */     throws PropertyVetoException
/*     */   {
/* 270 */     if ((paramObject1 == null) || (paramObject2 == null) || (!paramObject1.equals(paramObject2))) {
/* 271 */       fireVetoableChange(new PropertyChangeEvent(this.source, paramString, paramObject1, paramObject2));
/*     */     }
/*     */   }
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
/*     */ 
/*     */   public void fireVetoableChange(String paramString, int paramInt1, int paramInt2)
/*     */     throws PropertyVetoException
/*     */   {
/* 298 */     if (paramInt1 != paramInt2) {
/* 299 */       fireVetoableChange(paramString, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
/*     */     }
/*     */   }
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
/*     */ 
/*     */   public void fireVetoableChange(String paramString, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws PropertyVetoException
/*     */   {
/* 326 */     if (paramBoolean1 != paramBoolean2) {
/* 327 */       fireVetoableChange(paramString, Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2));
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void fireVetoableChange(PropertyChangeEvent paramPropertyChangeEvent)
/*     */     throws PropertyVetoException
/*     */   {
/* 349 */     Object localObject1 = paramPropertyChangeEvent.getOldValue();
/* 350 */     Object localObject2 = paramPropertyChangeEvent.getNewValue();
/* 351 */     if ((localObject1 == null) || (localObject2 == null) || (!localObject1.equals(localObject2))) {
/* 352 */       String str = paramPropertyChangeEvent.getPropertyName();
/*     */       
/* 354 */       VetoableChangeListener[] arrayOfVetoableChangeListener = (VetoableChangeListener[])this.map.get(null);
/*     */       
/* 356 */       Object localObject3 = str != null ? (VetoableChangeListener[])this.map.get(str) : null;
/*     */       
/*     */       Object localObject4;
/*     */       
/* 360 */       if (arrayOfVetoableChangeListener == null) {
/* 361 */         localObject4 = localObject3;
/*     */       }
/* 363 */       else if (localObject3 == null) {
/* 364 */         localObject4 = arrayOfVetoableChangeListener;
/*     */       }
/*     */       else {
/* 367 */         localObject4 = new VetoableChangeListener[arrayOfVetoableChangeListener.length + localObject3.length];
/* 368 */         System.arraycopy(arrayOfVetoableChangeListener, 0, localObject4, 0, arrayOfVetoableChangeListener.length);
/* 369 */         System.arraycopy(localObject3, 0, localObject4, arrayOfVetoableChangeListener.length, localObject3.length);
/*     */       }
/* 371 */       if (localObject4 != null) {
/* 372 */         int i = 0;
/*     */         try {
/* 374 */           while (i < localObject4.length) {
/* 375 */             localObject4[i].vetoableChange(paramPropertyChangeEvent);
/* 376 */             i++;
/*     */           }
/*     */         }
/*     */         catch (PropertyVetoException localPropertyVetoException1) {
/* 380 */           paramPropertyChangeEvent = new PropertyChangeEvent(this.source, str, localObject2, localObject1);
/* 381 */           for (int j = 0; j < i; j++) {
/*     */             try {
/* 383 */               localObject4[j].vetoableChange(paramPropertyChangeEvent);
/*     */             }
/*     */             catch (PropertyVetoException localPropertyVetoException2) {}
/*     */           }
/*     */           
/*     */ 
/* 389 */           throw localPropertyVetoException1;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasListeners(String paramString)
/*     */   {
/* 404 */     return this.map.hasListeners(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 414 */     Hashtable localHashtable = null;
/* 415 */     VetoableChangeListener[] arrayOfVetoableChangeListener = null;
/* 416 */     Object localObject1; VetoableChangeSupport localVetoableChangeSupport; synchronized (this.map) {
/* 417 */       for (localObject1 = this.map.getEntries().iterator(); ((Iterator)localObject1).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject1).next();
/* 418 */         String str = (String)localEntry.getKey();
/* 419 */         if (str == null) {
/* 420 */           arrayOfVetoableChangeListener = (VetoableChangeListener[])localEntry.getValue();
/*     */         } else {
/* 422 */           if (localHashtable == null) {
/* 423 */             localHashtable = new Hashtable();
/*     */           }
/* 425 */           localVetoableChangeSupport = new VetoableChangeSupport(this.source);
/* 426 */           localVetoableChangeSupport.map.set(null, (EventListener[])localEntry.getValue());
/* 427 */           localHashtable.put(str, localVetoableChangeSupport);
/*     */         }
/*     */       }
/*     */     }
/* 431 */     ??? = paramObjectOutputStream.putFields();
/* 432 */     ((ObjectOutputStream.PutField)???).put("children", localHashtable);
/* 433 */     ((ObjectOutputStream.PutField)???).put("source", this.source);
/* 434 */     ((ObjectOutputStream.PutField)???).put("vetoableChangeSupportSerializedDataVersion", 2);
/* 435 */     paramObjectOutputStream.writeFields();
/*     */     
/* 437 */     if (arrayOfVetoableChangeListener != null) {
/* 438 */       for (localVetoableChangeSupport : arrayOfVetoableChangeListener) {
/* 439 */         if ((localVetoableChangeSupport instanceof Serializable)) {
/* 440 */           paramObjectOutputStream.writeObject(localVetoableChangeSupport);
/*     */         }
/*     */       }
/*     */     }
/* 444 */     paramObjectOutputStream.writeObject(null);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 448 */     this.map = new VetoableChangeListenerMap(null);
/*     */     
/* 450 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*     */     
/*     */ 
/* 453 */     Hashtable localHashtable = (Hashtable)localGetField.get("children", null);
/* 454 */     this.source = localGetField.get("source", null);
/* 455 */     localGetField.get("vetoableChangeSupportSerializedDataVersion", 2);
/*     */     
/*     */     Object localObject;
/* 458 */     while (null != (localObject = paramObjectInputStream.readObject())) {
/* 459 */       this.map.add(null, (VetoableChangeListener)localObject);
/*     */     }
/* 461 */     if (localHashtable != null) {
/* 462 */       for (Map.Entry localEntry : localHashtable.entrySet()) {
/* 463 */         for (VetoableChangeListener localVetoableChangeListener : ((VetoableChangeSupport)localEntry.getValue()).getVetoableChangeListeners()) {
/* 464 */           this.map.add((String)localEntry.getKey(), localVetoableChangeListener);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
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
/*     */ 
/* 480 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("children", Hashtable.class), new ObjectStreamField("source", Object.class), new ObjectStreamField("vetoableChangeSupportSerializedDataVersion", Integer.TYPE) };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final long serialVersionUID = -5090210921595982017L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class VetoableChangeListenerMap
/*     */     extends ChangeListenerMap<VetoableChangeListener>
/*     */   {
/* 496 */     private static final VetoableChangeListener[] EMPTY = new VetoableChangeListener[0];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected VetoableChangeListener[] newArray(int paramInt)
/*     */     {
/* 508 */       return 0 < paramInt ? new VetoableChangeListener[paramInt] : EMPTY;
/*     */     }
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
/*     */ 
/*     */     protected VetoableChangeListener newProxy(String paramString, VetoableChangeListener paramVetoableChangeListener)
/*     */     {
/* 523 */       return new VetoableChangeListenerProxy(paramString, paramVetoableChangeListener);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public final VetoableChangeListener extract(VetoableChangeListener paramVetoableChangeListener)
/*     */     {
/* 530 */       while ((paramVetoableChangeListener instanceof VetoableChangeListenerProxy)) {
/* 531 */         paramVetoableChangeListener = (VetoableChangeListener)((VetoableChangeListenerProxy)paramVetoableChangeListener).getListener();
/*     */       }
/* 533 */       return paramVetoableChangeListener;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/beans/VetoableChangeSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */