/*      */ package java.awt;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GridBagLayout
/*      */   implements LayoutManager2, Serializable
/*      */ {
/*      */   static final int EMPIRICMULTIPLIER = 2;
/*      */   protected static final int MAXGRIDSIZE = 512;
/*      */   protected static final int MINSIZE = 1;
/*      */   protected static final int PREFERREDSIZE = 2;
/*      */   protected Hashtable<Component, GridBagConstraints> comptable;
/*      */   protected GridBagConstraints defaultConstraints;
/*      */   protected GridBagLayoutInfo layoutInfo;
/*      */   public int[] columnWidths;
/*      */   public int[] rowHeights;
/*      */   public double[] columnWeights;
/*      */   public double[] rowWeights;
/*      */   private Component componentAdjusting;
/*      */   
/*      */   public GridBagLayout()
/*      */   {
/*  495 */     this.comptable = new Hashtable();
/*  496 */     this.defaultConstraints = new GridBagConstraints();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setConstraints(Component paramComponent, GridBagConstraints paramGridBagConstraints)
/*      */   {
/*  505 */     this.comptable.put(paramComponent, (GridBagConstraints)paramGridBagConstraints.clone());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GridBagConstraints getConstraints(Component paramComponent)
/*      */   {
/*  517 */     GridBagConstraints localGridBagConstraints = (GridBagConstraints)this.comptable.get(paramComponent);
/*  518 */     if (localGridBagConstraints == null) {
/*  519 */       setConstraints(paramComponent, this.defaultConstraints);
/*  520 */       localGridBagConstraints = (GridBagConstraints)this.comptable.get(paramComponent);
/*      */     }
/*  522 */     return (GridBagConstraints)localGridBagConstraints.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected GridBagConstraints lookupConstraints(Component paramComponent)
/*      */   {
/*  539 */     GridBagConstraints localGridBagConstraints = (GridBagConstraints)this.comptable.get(paramComponent);
/*  540 */     if (localGridBagConstraints == null) {
/*  541 */       setConstraints(paramComponent, this.defaultConstraints);
/*  542 */       localGridBagConstraints = (GridBagConstraints)this.comptable.get(paramComponent);
/*      */     }
/*  544 */     return localGridBagConstraints;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void removeConstraints(Component paramComponent)
/*      */   {
/*  552 */     this.comptable.remove(paramComponent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Point getLayoutOrigin()
/*      */   {
/*  568 */     Point localPoint = new Point(0, 0);
/*  569 */     if (this.layoutInfo != null) {
/*  570 */       localPoint.x = this.layoutInfo.startx;
/*  571 */       localPoint.y = this.layoutInfo.starty;
/*      */     }
/*  573 */     return localPoint;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[][] getLayoutDimensions()
/*      */   {
/*  586 */     if (this.layoutInfo == null) {
/*  587 */       return new int[2][0];
/*      */     }
/*  589 */     int[][] arrayOfInt = new int[2][];
/*  590 */     arrayOfInt[0] = new int[this.layoutInfo.width];
/*  591 */     arrayOfInt[1] = new int[this.layoutInfo.height];
/*      */     
/*  593 */     System.arraycopy(this.layoutInfo.minWidth, 0, arrayOfInt[0], 0, this.layoutInfo.width);
/*  594 */     System.arraycopy(this.layoutInfo.minHeight, 0, arrayOfInt[1], 0, this.layoutInfo.height);
/*      */     
/*  596 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double[][] getLayoutWeights()
/*      */   {
/*  612 */     if (this.layoutInfo == null) {
/*  613 */       return new double[2][0];
/*      */     }
/*  615 */     double[][] arrayOfDouble = new double[2][];
/*  616 */     arrayOfDouble[0] = new double[this.layoutInfo.width];
/*  617 */     arrayOfDouble[1] = new double[this.layoutInfo.height];
/*      */     
/*  619 */     System.arraycopy(this.layoutInfo.weightX, 0, arrayOfDouble[0], 0, this.layoutInfo.width);
/*  620 */     System.arraycopy(this.layoutInfo.weightY, 0, arrayOfDouble[1], 0, this.layoutInfo.height);
/*      */     
/*  622 */     return arrayOfDouble;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Point location(int paramInt1, int paramInt2)
/*      */   {
/*  653 */     Point localPoint = new Point(0, 0);
/*      */     
/*      */ 
/*  656 */     if (this.layoutInfo == null) {
/*  657 */       return localPoint;
/*      */     }
/*  659 */     int j = this.layoutInfo.startx;
/*  660 */     if (!this.rightToLeft) {
/*  661 */       for (i = 0; i < this.layoutInfo.width; i++) {
/*  662 */         j += this.layoutInfo.minWidth[i];
/*  663 */         if (j > paramInt1)
/*      */           break;
/*      */       }
/*      */     }
/*  667 */     for (int i = this.layoutInfo.width - 1; i >= 0; i--) {
/*  668 */       if (j > paramInt1)
/*      */         break;
/*  670 */       j += this.layoutInfo.minWidth[i];
/*      */     }
/*  672 */     i++;
/*      */     
/*  674 */     localPoint.x = i;
/*      */     
/*  676 */     j = this.layoutInfo.starty;
/*  677 */     for (i = 0; i < this.layoutInfo.height; i++) {
/*  678 */       j += this.layoutInfo.minHeight[i];
/*  679 */       if (j > paramInt2)
/*      */         break;
/*      */     }
/*  682 */     localPoint.y = i;
/*      */     
/*  684 */     return localPoint;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addLayoutComponent(Component paramComponent, Object paramObject)
/*      */   {
/*  705 */     if ((paramObject instanceof GridBagConstraints)) {
/*  706 */       setConstraints(paramComponent, (GridBagConstraints)paramObject);
/*  707 */     } else if (paramObject != null) {
/*  708 */       throw new IllegalArgumentException("cannot add to layout: constraints must be a GridBagConstraint");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeLayoutComponent(Component paramComponent)
/*      */   {
/*  721 */     removeConstraints(paramComponent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Dimension preferredLayoutSize(Container paramContainer)
/*      */   {
/*  736 */     GridBagLayoutInfo localGridBagLayoutInfo = getLayoutInfo(paramContainer, 2);
/*  737 */     return getMinSize(paramContainer, localGridBagLayoutInfo);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Dimension minimumLayoutSize(Container paramContainer)
/*      */   {
/*  750 */     GridBagLayoutInfo localGridBagLayoutInfo = getLayoutInfo(paramContainer, 1);
/*  751 */     return getMinSize(paramContainer, localGridBagLayoutInfo);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Dimension maximumLayoutSize(Container paramContainer)
/*      */   {
/*  764 */     return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getLayoutAlignmentX(Container paramContainer)
/*      */   {
/*  777 */     return 0.5F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getLayoutAlignmentY(Container paramContainer)
/*      */   {
/*  790 */     return 0.5F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void invalidateLayout(Container paramContainer) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void layoutContainer(Container paramContainer)
/*      */   {
/*  812 */     arrangeGrid(paramContainer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*  820 */     return getClass().getName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected GridBagLayoutInfo getLayoutInfo(Container paramContainer, int paramInt)
/*      */   {
/*  916 */     return GetLayoutInfo(paramContainer, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private long[] preInitMaximumArraySizes(Container paramContainer)
/*      */   {
/*  926 */     Component[] arrayOfComponent = paramContainer.getComponents();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  931 */     int n = 0;
/*  932 */     int i1 = 0;
/*  933 */     long[] arrayOfLong = new long[2];
/*      */     
/*  935 */     for (int i2 = 0; i2 < arrayOfComponent.length; i2++) {
/*  936 */       Component localComponent = arrayOfComponent[i2];
/*  937 */       if (localComponent.isVisible())
/*      */       {
/*      */ 
/*      */ 
/*  941 */         GridBagConstraints localGridBagConstraints = lookupConstraints(localComponent);
/*  942 */         int i = localGridBagConstraints.gridx;
/*  943 */         int j = localGridBagConstraints.gridy;
/*  944 */         int k = localGridBagConstraints.gridwidth;
/*  945 */         int m = localGridBagConstraints.gridheight;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  952 */         if (i < 0) {
/*  953 */           i1++;i = i1;
/*      */         }
/*  955 */         if (j < 0) {
/*  956 */           n++;j = n;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  961 */         if (k <= 0) {
/*  962 */           k = 1;
/*      */         }
/*  964 */         if (m <= 0) {
/*  965 */           m = 1;
/*      */         }
/*      */         
/*  968 */         n = Math.max(j + m, n);
/*  969 */         i1 = Math.max(i + k, i1);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  975 */     arrayOfLong[0] = n;
/*  976 */     arrayOfLong[1] = i1;
/*  977 */     return arrayOfLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected GridBagLayoutInfo GetLayoutInfo(Container paramContainer, int paramInt)
/*      */   {
/*  989 */     synchronized (paramContainer.getTreeLock())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  994 */       Component[] arrayOfComponent = paramContainer.getComponents();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1006 */       int i5 = 0;
/* 1007 */       int i6 = 0;
/* 1008 */       int i7 = 1;
/* 1009 */       int i8 = 1;
/*      */       
/*      */ 
/* 1012 */       int i11 = 0;
/* 1013 */       int i12 = 0;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       int j;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1023 */       int i = j = 0;
/* 1024 */       int i10; int i9 = i10 = -1;
/* 1025 */       long[] arrayOfLong = preInitMaximumArraySizes(paramContainer);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1034 */       i11 = 2L * arrayOfLong[0] > 2147483647L ? Integer.MAX_VALUE : 2 * (int)arrayOfLong[0];
/* 1035 */       i12 = 2L * arrayOfLong[1] > 2147483647L ? Integer.MAX_VALUE : 2 * (int)arrayOfLong[1];
/*      */       
/* 1037 */       if (this.rowHeights != null) {
/* 1038 */         i11 = Math.max(i11, this.rowHeights.length);
/*      */       }
/* 1040 */       if (this.columnWidths != null) {
/* 1041 */         i12 = Math.max(i12, this.columnWidths.length);
/*      */       }
/*      */       
/* 1044 */       int[] arrayOfInt1 = new int[i11];
/* 1045 */       int[] arrayOfInt2 = new int[i12];
/*      */       
/* 1047 */       int i14 = 0;
/* 1048 */       Component localComponent; GridBagConstraints localGridBagConstraints; int i1; int i2; for (int k = 0; k < arrayOfComponent.length; k++) {
/* 1049 */         localComponent = arrayOfComponent[k];
/* 1050 */         if (localComponent.isVisible())
/*      */         {
/* 1052 */           localGridBagConstraints = lookupConstraints(localComponent);
/*      */           
/* 1054 */           i5 = localGridBagConstraints.gridx;
/* 1055 */           i6 = localGridBagConstraints.gridy;
/* 1056 */           i7 = localGridBagConstraints.gridwidth;
/* 1057 */           if (i7 <= 0)
/* 1058 */             i7 = 1;
/* 1059 */           i8 = localGridBagConstraints.gridheight;
/* 1060 */           if (i8 <= 0) {
/* 1061 */             i8 = 1;
/*      */           }
/*      */           
/* 1064 */           if ((i5 < 0) && (i6 < 0)) {
/* 1065 */             if (i9 >= 0) {
/* 1066 */               i6 = i9;
/* 1067 */             } else if (i10 >= 0) {
/* 1068 */               i5 = i10;
/*      */             } else
/* 1070 */               i6 = 0;
/*      */           }
/* 1072 */           if (i5 < 0) {
/* 1073 */             i1 = 0;
/* 1074 */             for (m = i6; m < i6 + i8; m++) {
/* 1075 */               i1 = Math.max(i1, arrayOfInt1[m]);
/*      */             }
/*      */             
/* 1078 */             i5 = i1 - i5 - 1;
/* 1079 */             if (i5 < 0) {
/* 1080 */               i5 = 0;
/*      */             }
/* 1082 */           } else if (i6 < 0) {
/* 1083 */             i2 = 0;
/* 1084 */             for (m = i5; m < i5 + i7; m++) {
/* 1085 */               i2 = Math.max(i2, arrayOfInt2[m]);
/*      */             }
/* 1087 */             i6 = i2 - i6 - 1;
/* 1088 */             if (i6 < 0) {
/* 1089 */               i6 = 0;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1095 */           i1 = i5 + i7;
/* 1096 */           if (i < i1) {
/* 1097 */             i = i1;
/*      */           }
/* 1099 */           i2 = i6 + i8;
/* 1100 */           if (j < i2) {
/* 1101 */             j = i2;
/*      */           }
/*      */           
/*      */ 
/* 1105 */           for (m = i5; m < i5 + i7; m++) {
/* 1106 */             arrayOfInt2[m] = i2;
/*      */           }
/* 1108 */           for (m = i6; m < i6 + i8; m++) {
/* 1109 */             arrayOfInt1[m] = i1;
/*      */           }
/*      */           
/*      */           Dimension localDimension;
/*      */           
/* 1114 */           if (paramInt == 2) {
/* 1115 */             localDimension = localComponent.getPreferredSize();
/*      */           } else
/* 1117 */             localDimension = localComponent.getMinimumSize();
/* 1118 */           localGridBagConstraints.minWidth = localDimension.width;
/* 1119 */           localGridBagConstraints.minHeight = localDimension.height;
/* 1120 */           if (calculateBaseline(localComponent, localGridBagConstraints, localDimension)) {
/* 1121 */             i14 = 1;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1126 */           if ((localGridBagConstraints.gridheight == 0) && (localGridBagConstraints.gridwidth == 0)) {
/* 1127 */             i9 = i10 = -1;
/*      */           }
/*      */           
/* 1130 */           if ((localGridBagConstraints.gridheight == 0) && (i9 < 0)) {
/* 1131 */             i10 = i5 + i7;
/*      */ 
/*      */           }
/* 1134 */           else if ((localGridBagConstraints.gridwidth == 0) && (i10 < 0)) {
/* 1135 */             i9 = i6 + i8;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1142 */       if ((this.columnWidths != null) && (i < this.columnWidths.length))
/* 1143 */         i = this.columnWidths.length;
/* 1144 */       if ((this.rowHeights != null) && (j < this.rowHeights.length)) {
/* 1145 */         j = this.rowHeights.length;
/*      */       }
/* 1147 */       GridBagLayoutInfo localGridBagLayoutInfo = new GridBagLayoutInfo(i, j);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1158 */       i9 = i10 = -1;
/*      */       
/* 1160 */       Arrays.fill(arrayOfInt1, 0);
/* 1161 */       Arrays.fill(arrayOfInt2, 0);
/*      */       
/* 1163 */       int[] arrayOfInt3 = null;
/* 1164 */       int[] arrayOfInt4 = null;
/* 1165 */       short[] arrayOfShort = null;
/*      */       
/* 1167 */       if (i14 != 0) {
/* 1168 */         localGridBagLayoutInfo.maxAscent = (arrayOfInt3 = new int[j]);
/* 1169 */         localGridBagLayoutInfo.maxDescent = (arrayOfInt4 = new int[j]);
/* 1170 */         localGridBagLayoutInfo.baselineType = (arrayOfShort = new short[j]);
/* 1171 */         localGridBagLayoutInfo.hasBaseline = true;
/*      */       }
/*      */       
/*      */       int i3;
/* 1175 */       for (k = 0; k < arrayOfComponent.length; k++) {
/* 1176 */         localComponent = arrayOfComponent[k];
/* 1177 */         if (localComponent.isVisible())
/*      */         {
/* 1179 */           localGridBagConstraints = lookupConstraints(localComponent);
/*      */           
/* 1181 */           i5 = localGridBagConstraints.gridx;
/* 1182 */           i6 = localGridBagConstraints.gridy;
/* 1183 */           i7 = localGridBagConstraints.gridwidth;
/* 1184 */           i8 = localGridBagConstraints.gridheight;
/*      */           
/*      */ 
/* 1187 */           if ((i5 < 0) && (i6 < 0)) {
/* 1188 */             if (i9 >= 0) {
/* 1189 */               i6 = i9;
/* 1190 */             } else if (i10 >= 0) {
/* 1191 */               i5 = i10;
/*      */             } else {
/* 1193 */               i6 = 0;
/*      */             }
/*      */           }
/* 1196 */           if (i5 < 0) {
/* 1197 */             if (i8 <= 0) {
/* 1198 */               i8 += localGridBagLayoutInfo.height - i6;
/* 1199 */               if (i8 < 1) {
/* 1200 */                 i8 = 1;
/*      */               }
/*      */             }
/* 1203 */             i1 = 0;
/* 1204 */             for (m = i6; m < i6 + i8; m++) {
/* 1205 */               i1 = Math.max(i1, arrayOfInt1[m]);
/*      */             }
/* 1207 */             i5 = i1 - i5 - 1;
/* 1208 */             if (i5 < 0) {
/* 1209 */               i5 = 0;
/*      */             }
/* 1211 */           } else if (i6 < 0) {
/* 1212 */             if (i7 <= 0) {
/* 1213 */               i7 += localGridBagLayoutInfo.width - i5;
/* 1214 */               if (i7 < 1) {
/* 1215 */                 i7 = 1;
/*      */               }
/*      */             }
/* 1218 */             i2 = 0;
/* 1219 */             for (m = i5; m < i5 + i7; m++) {
/* 1220 */               i2 = Math.max(i2, arrayOfInt2[m]);
/*      */             }
/*      */             
/* 1223 */             i6 = i2 - i6 - 1;
/* 1224 */             if (i6 < 0) {
/* 1225 */               i6 = 0;
/*      */             }
/*      */           }
/* 1228 */           if (i7 <= 0) {
/* 1229 */             i7 += localGridBagLayoutInfo.width - i5;
/* 1230 */             if (i7 < 1) {
/* 1231 */               i7 = 1;
/*      */             }
/*      */           }
/* 1234 */           if (i8 <= 0) {
/* 1235 */             i8 += localGridBagLayoutInfo.height - i6;
/* 1236 */             if (i8 < 1) {
/* 1237 */               i8 = 1;
/*      */             }
/*      */           }
/* 1240 */           i1 = i5 + i7;
/* 1241 */           i2 = i6 + i8;
/*      */           
/* 1243 */           for (m = i5; m < i5 + i7; m++) arrayOfInt2[m] = i2;
/* 1244 */           for (m = i6; m < i6 + i8; m++) { arrayOfInt1[m] = i1;
/*      */           }
/*      */           
/* 1247 */           if ((localGridBagConstraints.gridheight == 0) && (localGridBagConstraints.gridwidth == 0))
/* 1248 */             i9 = i10 = -1;
/* 1249 */           if ((localGridBagConstraints.gridheight == 0) && (i9 < 0)) {
/* 1250 */             i10 = i5 + i7;
/* 1251 */           } else if ((localGridBagConstraints.gridwidth == 0) && (i10 < 0)) {
/* 1252 */             i9 = i6 + i8;
/*      */           }
/*      */           
/* 1255 */           localGridBagConstraints.tempX = i5;
/* 1256 */           localGridBagConstraints.tempY = i6;
/* 1257 */           localGridBagConstraints.tempWidth = i7;
/* 1258 */           localGridBagConstraints.tempHeight = i8;
/*      */           
/* 1260 */           int i13 = localGridBagConstraints.anchor;
/* 1261 */           if (i14 != 0) {
/* 1262 */             switch (i13) {
/*      */             case 256: 
/*      */             case 512: 
/*      */             case 768: 
/* 1266 */               if (localGridBagConstraints.ascent >= 0) {
/* 1267 */                 if (i8 == 1)
/*      */                 {
/* 1269 */                   arrayOfInt3[i6] = Math.max(arrayOfInt3[i6], localGridBagConstraints.ascent);
/*      */                   
/* 1271 */                   arrayOfInt4[i6] = 
/* 1272 */                     Math.max(arrayOfInt4[i6], localGridBagConstraints.descent);
/*      */ 
/*      */ 
/*      */                 }
/* 1276 */                 else if (localGridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.CONSTANT_DESCENT)
/*      */                 {
/*      */ 
/*      */ 
/* 1280 */                   arrayOfInt4[(i6 + i8 - 1)] = Math.max(arrayOfInt4[(i6 + i8 - 1)], localGridBagConstraints.descent);
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/* 1285 */                   arrayOfInt3[i6] = Math.max(arrayOfInt3[i6], localGridBagConstraints.ascent);
/*      */                 }
/*      */                 
/*      */ 
/* 1289 */                 if (localGridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.CONSTANT_DESCENT)
/*      */                 {
/* 1291 */                   int tmp1469_1468 = (i6 + i8 - 1); short[] tmp1469_1460 = arrayOfShort;tmp1469_1460[tmp1469_1468] = 
/*      */                   
/* 1293 */                     ((short)(tmp1469_1460[tmp1469_1468] | 1 << localGridBagConstraints.baselineResizeBehavior.ordinal()));
/*      */                 }
/*      */                 else {
/* 1296 */                   int tmp1491_1489 = i6; short[] tmp1491_1487 = arrayOfShort;tmp1491_1487[tmp1491_1489] = 
/* 1297 */                     ((short)(tmp1491_1487[tmp1491_1489] | 1 << localGridBagConstraints.baselineResizeBehavior.ordinal()));
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */               break;
/*      */             case 1024: 
/*      */             case 1280: 
/*      */             case 1536: 
/* 1308 */               i3 = localGridBagConstraints.minHeight + localGridBagConstraints.insets.top + localGridBagConstraints.ipady;
/*      */               
/*      */ 
/* 1311 */               arrayOfInt3[i6] = Math.max(arrayOfInt3[i6], i3);
/*      */               
/* 1313 */               arrayOfInt4[i6] = Math.max(arrayOfInt4[i6], localGridBagConstraints.insets.bottom);
/*      */               
/* 1315 */               break;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */             case 1792: 
/*      */             case 2048: 
/*      */             case 2304: 
/* 1323 */               i3 = localGridBagConstraints.minHeight + localGridBagConstraints.insets.bottom + localGridBagConstraints.ipady;
/*      */               
/* 1325 */               arrayOfInt4[i6] = Math.max(arrayOfInt4[i6], i3);
/*      */               
/* 1327 */               arrayOfInt3[i6] = Math.max(arrayOfInt3[i6], localGridBagConstraints.insets.top);
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1334 */       localGridBagLayoutInfo.weightX = new double[i12];
/* 1335 */       localGridBagLayoutInfo.weightY = new double[i11];
/* 1336 */       localGridBagLayoutInfo.minWidth = new int[i12];
/* 1337 */       localGridBagLayoutInfo.minHeight = new int[i11];
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1343 */       if (this.columnWidths != null)
/* 1344 */         System.arraycopy(this.columnWidths, 0, localGridBagLayoutInfo.minWidth, 0, this.columnWidths.length);
/* 1345 */       if (this.rowHeights != null)
/* 1346 */         System.arraycopy(this.rowHeights, 0, localGridBagLayoutInfo.minHeight, 0, this.rowHeights.length);
/* 1347 */       if (this.columnWeights != null)
/* 1348 */         System.arraycopy(this.columnWeights, 0, localGridBagLayoutInfo.weightX, 0, Math.min(localGridBagLayoutInfo.weightX.length, this.columnWeights.length));
/* 1349 */       if (this.rowWeights != null) {
/* 1350 */         System.arraycopy(this.rowWeights, 0, localGridBagLayoutInfo.weightY, 0, Math.min(localGridBagLayoutInfo.weightY.length, this.rowWeights.length));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1358 */       int i4 = Integer.MAX_VALUE;
/*      */       
/* 1360 */       int m = 1;
/* 1361 */       for (; m != Integer.MAX_VALUE; 
/* 1362 */           i4 = Integer.MAX_VALUE) {
/* 1363 */         for (k = 0; k < arrayOfComponent.length; k++) {
/* 1364 */           localComponent = arrayOfComponent[k];
/* 1365 */           if (localComponent.isVisible())
/*      */           {
/* 1367 */             localGridBagConstraints = lookupConstraints(localComponent);
/*      */             double d1;
/* 1369 */             int n; double d2; double d3; if (localGridBagConstraints.tempWidth == m) {
/* 1370 */               i1 = localGridBagConstraints.tempX + localGridBagConstraints.tempWidth;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1379 */               d1 = localGridBagConstraints.weightx;
/* 1380 */               for (n = localGridBagConstraints.tempX; n < i1; n++)
/* 1381 */                 d1 -= localGridBagLayoutInfo.weightX[n];
/* 1382 */               if (d1 > 0.0D) {
/* 1383 */                 d2 = 0.0D;
/* 1384 */                 for (n = localGridBagConstraints.tempX; n < i1; n++)
/* 1385 */                   d2 += localGridBagLayoutInfo.weightX[n];
/* 1386 */                 for (n = localGridBagConstraints.tempX; (d2 > 0.0D) && (n < i1); n++) {
/* 1387 */                   d3 = localGridBagLayoutInfo.weightX[n];
/* 1388 */                   double d4 = d3 * d1 / d2;
/* 1389 */                   localGridBagLayoutInfo.weightX[n] += d4;
/* 1390 */                   d1 -= d4;
/* 1391 */                   d2 -= d3;
/*      */                 }
/*      */                 
/* 1394 */                 localGridBagLayoutInfo.weightX[(i1 - 1)] += d1;
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1405 */               i3 = localGridBagConstraints.minWidth + localGridBagConstraints.ipadx + localGridBagConstraints.insets.left + localGridBagConstraints.insets.right;
/*      */               
/*      */ 
/*      */ 
/* 1409 */               for (n = localGridBagConstraints.tempX; n < i1; n++)
/* 1410 */                 i3 -= localGridBagLayoutInfo.minWidth[n];
/* 1411 */               if (i3 > 0) {
/* 1412 */                 d2 = 0.0D;
/* 1413 */                 for (n = localGridBagConstraints.tempX; n < i1; n++)
/* 1414 */                   d2 += localGridBagLayoutInfo.weightX[n];
/* 1415 */                 for (n = localGridBagConstraints.tempX; (d2 > 0.0D) && (n < i1); n++) {
/* 1416 */                   d3 = localGridBagLayoutInfo.weightX[n];
/* 1417 */                   int i15 = (int)(d3 * i3 / d2);
/* 1418 */                   localGridBagLayoutInfo.minWidth[n] += i15;
/* 1419 */                   i3 -= i15;
/* 1420 */                   d2 -= d3;
/*      */                 }
/*      */                 
/* 1423 */                 localGridBagLayoutInfo.minWidth[(i1 - 1)] += i3;
/*      */               }
/*      */             }
/* 1426 */             else if ((localGridBagConstraints.tempWidth > m) && (localGridBagConstraints.tempWidth < i4)) {
/* 1427 */               i4 = localGridBagConstraints.tempWidth;
/*      */             }
/*      */             
/* 1430 */             if (localGridBagConstraints.tempHeight == m) {
/* 1431 */               i2 = localGridBagConstraints.tempY + localGridBagConstraints.tempHeight;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1440 */               d1 = localGridBagConstraints.weighty;
/* 1441 */               for (n = localGridBagConstraints.tempY; n < i2; n++)
/* 1442 */                 d1 -= localGridBagLayoutInfo.weightY[n];
/* 1443 */               if (d1 > 0.0D) {
/* 1444 */                 d2 = 0.0D;
/* 1445 */                 for (n = localGridBagConstraints.tempY; n < i2; n++)
/* 1446 */                   d2 += localGridBagLayoutInfo.weightY[n];
/* 1447 */                 for (n = localGridBagConstraints.tempY; (d2 > 0.0D) && (n < i2); n++) {
/* 1448 */                   d3 = localGridBagLayoutInfo.weightY[n];
/* 1449 */                   double d5 = d3 * d1 / d2;
/* 1450 */                   localGridBagLayoutInfo.weightY[n] += d5;
/* 1451 */                   d1 -= d5;
/* 1452 */                   d2 -= d3;
/*      */                 }
/*      */                 
/* 1455 */                 localGridBagLayoutInfo.weightY[(i2 - 1)] += d1;
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1466 */               i3 = -1;
/* 1467 */               if (i14 != 0) {
/* 1468 */                 switch (localGridBagConstraints.anchor) {
/*      */                 case 256: 
/*      */                 case 512: 
/*      */                 case 768: 
/* 1472 */                   if (localGridBagConstraints.ascent >= 0) {
/* 1473 */                     if (localGridBagConstraints.tempHeight == 1) {
/* 1474 */                       i3 = arrayOfInt3[localGridBagConstraints.tempY] + arrayOfInt4[localGridBagConstraints.tempY];
/*      */ 
/*      */ 
/*      */                     }
/* 1478 */                     else if (localGridBagConstraints.baselineResizeBehavior != Component.BaselineResizeBehavior.CONSTANT_DESCENT)
/*      */                     {
/*      */ 
/* 1481 */                       i3 = arrayOfInt3[localGridBagConstraints.tempY] + localGridBagConstraints.descent;
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 1486 */                       i3 = localGridBagConstraints.ascent + arrayOfInt4[(localGridBagConstraints.tempY + localGridBagConstraints.tempHeight - 1)];
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/*      */                   break;
/*      */                 case 1024: 
/*      */                 case 1280: 
/*      */                 case 1536: 
/* 1495 */                   i3 = localGridBagConstraints.insets.top + localGridBagConstraints.minHeight + localGridBagConstraints.ipady + arrayOfInt4[localGridBagConstraints.tempY];
/*      */                   
/*      */ 
/*      */ 
/* 1499 */                   break;
/*      */                 case 1792: 
/*      */                 case 2048: 
/*      */                 case 2304: 
/* 1503 */                   i3 = arrayOfInt3[localGridBagConstraints.tempY] + localGridBagConstraints.minHeight + localGridBagConstraints.insets.bottom + localGridBagConstraints.ipady;
/*      */                 }
/*      */                 
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 1510 */               if (i3 == -1) {
/* 1511 */                 i3 = localGridBagConstraints.minHeight + localGridBagConstraints.ipady + localGridBagConstraints.insets.top + localGridBagConstraints.insets.bottom;
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 1516 */               for (n = localGridBagConstraints.tempY; n < i2; n++)
/* 1517 */                 i3 -= localGridBagLayoutInfo.minHeight[n];
/* 1518 */               if (i3 > 0) {
/* 1519 */                 d2 = 0.0D;
/* 1520 */                 for (n = localGridBagConstraints.tempY; n < i2; n++)
/* 1521 */                   d2 += localGridBagLayoutInfo.weightY[n];
/* 1522 */                 for (n = localGridBagConstraints.tempY; (d2 > 0.0D) && (n < i2); n++) {
/* 1523 */                   d3 = localGridBagLayoutInfo.weightY[n];
/* 1524 */                   int i16 = (int)(d3 * i3 / d2);
/* 1525 */                   localGridBagLayoutInfo.minHeight[n] += i16;
/* 1526 */                   i3 -= i16;
/* 1527 */                   d2 -= d3;
/*      */                 }
/*      */                 
/* 1530 */                 localGridBagLayoutInfo.minHeight[(i2 - 1)] += i3;
/*      */               }
/*      */             }
/* 1533 */             else if ((localGridBagConstraints.tempHeight > m) && (localGridBagConstraints.tempHeight < i4))
/*      */             {
/* 1535 */               i4 = localGridBagConstraints.tempHeight;
/*      */             }
/*      */           }
/*      */         }
/* 1362 */         m = i4;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1538 */       return localGridBagLayoutInfo;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean calculateBaseline(Component paramComponent, GridBagConstraints paramGridBagConstraints, Dimension paramDimension)
/*      */   {
/* 1552 */     int i = paramGridBagConstraints.anchor;
/* 1553 */     if ((i == 256) || (i == 512) || (i == 768))
/*      */     {
/*      */ 
/*      */ 
/* 1557 */       int j = paramDimension.width + paramGridBagConstraints.ipadx;
/* 1558 */       int k = paramDimension.height + paramGridBagConstraints.ipady;
/* 1559 */       paramGridBagConstraints.ascent = paramComponent.getBaseline(j, k);
/* 1560 */       if (paramGridBagConstraints.ascent >= 0)
/*      */       {
/* 1562 */         int m = paramGridBagConstraints.ascent;
/*      */         
/* 1564 */         paramGridBagConstraints.descent = (k - paramGridBagConstraints.ascent + paramGridBagConstraints.insets.bottom);
/*      */         
/* 1566 */         paramGridBagConstraints.ascent += paramGridBagConstraints.insets.top;
/*      */         
/* 1568 */         paramGridBagConstraints.baselineResizeBehavior = paramComponent.getBaselineResizeBehavior();
/* 1569 */         paramGridBagConstraints.centerPadding = 0;
/* 1570 */         if (paramGridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.CENTER_OFFSET)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1577 */           int n = paramComponent.getBaseline(j, k + 1);
/* 1578 */           paramGridBagConstraints.centerOffset = (m - k / 2);
/* 1579 */           if (k % 2 == 0) {
/* 1580 */             if (m != n) {
/* 1581 */               paramGridBagConstraints.centerPadding = 1;
/*      */             }
/*      */           }
/* 1584 */           else if (m == n) {
/* 1585 */             paramGridBagConstraints.centerOffset -= 1;
/* 1586 */             paramGridBagConstraints.centerPadding = 1;
/*      */           }
/*      */         }
/*      */       }
/* 1590 */       return true;
/*      */     }
/*      */     
/* 1593 */     paramGridBagConstraints.ascent = -1;
/* 1594 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void adjustForGravity(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle)
/*      */   {
/* 1610 */     AdjustForGravity(paramGridBagConstraints, paramRectangle);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void AdjustForGravity(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle)
/*      */   {
/* 1625 */     int k = paramRectangle.y;
/* 1626 */     int m = paramRectangle.height;
/*      */     
/* 1628 */     if (!this.rightToLeft) {
/* 1629 */       paramRectangle.x += paramGridBagConstraints.insets.left;
/*      */     } else {
/* 1631 */       paramRectangle.x -= paramRectangle.width - paramGridBagConstraints.insets.right;
/*      */     }
/* 1633 */     paramRectangle.width -= paramGridBagConstraints.insets.left + paramGridBagConstraints.insets.right;
/* 1634 */     paramRectangle.y += paramGridBagConstraints.insets.top;
/* 1635 */     paramRectangle.height -= paramGridBagConstraints.insets.top + paramGridBagConstraints.insets.bottom;
/*      */     
/* 1637 */     int i = 0;
/* 1638 */     if ((paramGridBagConstraints.fill != 2) && (paramGridBagConstraints.fill != 1) && (paramRectangle.width > paramGridBagConstraints.minWidth + paramGridBagConstraints.ipadx))
/*      */     {
/*      */ 
/* 1641 */       i = paramRectangle.width - (paramGridBagConstraints.minWidth + paramGridBagConstraints.ipadx);
/* 1642 */       paramRectangle.width = (paramGridBagConstraints.minWidth + paramGridBagConstraints.ipadx);
/*      */     }
/*      */     
/* 1645 */     int j = 0;
/* 1646 */     if ((paramGridBagConstraints.fill != 3) && (paramGridBagConstraints.fill != 1) && (paramRectangle.height > paramGridBagConstraints.minHeight + paramGridBagConstraints.ipady))
/*      */     {
/*      */ 
/* 1649 */       j = paramRectangle.height - (paramGridBagConstraints.minHeight + paramGridBagConstraints.ipady);
/* 1650 */       paramRectangle.height = (paramGridBagConstraints.minHeight + paramGridBagConstraints.ipady);
/*      */     }
/*      */     
/* 1653 */     switch (paramGridBagConstraints.anchor) {
/*      */     case 256: 
/* 1655 */       paramRectangle.x += i / 2;
/* 1656 */       alignOnBaseline(paramGridBagConstraints, paramRectangle, k, m);
/* 1657 */       break;
/*      */     case 512: 
/* 1659 */       if (this.rightToLeft) {
/* 1660 */         paramRectangle.x += i;
/*      */       }
/* 1662 */       alignOnBaseline(paramGridBagConstraints, paramRectangle, k, m);
/* 1663 */       break;
/*      */     case 768: 
/* 1665 */       if (!this.rightToLeft) {
/* 1666 */         paramRectangle.x += i;
/*      */       }
/* 1668 */       alignOnBaseline(paramGridBagConstraints, paramRectangle, k, m);
/* 1669 */       break;
/*      */     case 1024: 
/* 1671 */       paramRectangle.x += i / 2;
/* 1672 */       alignAboveBaseline(paramGridBagConstraints, paramRectangle, k, m);
/* 1673 */       break;
/*      */     case 1280: 
/* 1675 */       if (this.rightToLeft) {
/* 1676 */         paramRectangle.x += i;
/*      */       }
/* 1678 */       alignAboveBaseline(paramGridBagConstraints, paramRectangle, k, m);
/* 1679 */       break;
/*      */     case 1536: 
/* 1681 */       if (!this.rightToLeft) {
/* 1682 */         paramRectangle.x += i;
/*      */       }
/* 1684 */       alignAboveBaseline(paramGridBagConstraints, paramRectangle, k, m);
/* 1685 */       break;
/*      */     case 1792: 
/* 1687 */       paramRectangle.x += i / 2;
/* 1688 */       alignBelowBaseline(paramGridBagConstraints, paramRectangle, k, m);
/* 1689 */       break;
/*      */     case 2048: 
/* 1691 */       if (this.rightToLeft) {
/* 1692 */         paramRectangle.x += i;
/*      */       }
/* 1694 */       alignBelowBaseline(paramGridBagConstraints, paramRectangle, k, m);
/* 1695 */       break;
/*      */     case 2304: 
/* 1697 */       if (!this.rightToLeft) {
/* 1698 */         paramRectangle.x += i;
/*      */       }
/* 1700 */       alignBelowBaseline(paramGridBagConstraints, paramRectangle, k, m);
/* 1701 */       break;
/*      */     case 10: 
/* 1703 */       paramRectangle.x += i / 2;
/* 1704 */       paramRectangle.y += j / 2;
/* 1705 */       break;
/*      */     case 11: 
/*      */     case 19: 
/* 1708 */       paramRectangle.x += i / 2;
/* 1709 */       break;
/*      */     case 12: 
/* 1711 */       paramRectangle.x += i;
/* 1712 */       break;
/*      */     case 13: 
/* 1714 */       paramRectangle.x += i;
/* 1715 */       paramRectangle.y += j / 2;
/* 1716 */       break;
/*      */     case 14: 
/* 1718 */       paramRectangle.x += i;
/* 1719 */       paramRectangle.y += j;
/* 1720 */       break;
/*      */     case 15: 
/*      */     case 20: 
/* 1723 */       paramRectangle.x += i / 2;
/* 1724 */       paramRectangle.y += j;
/* 1725 */       break;
/*      */     case 16: 
/* 1727 */       paramRectangle.y += j;
/* 1728 */       break;
/*      */     case 17: 
/* 1730 */       paramRectangle.y += j / 2;
/* 1731 */       break;
/*      */     case 18: 
/*      */       break;
/*      */     case 21: 
/* 1735 */       if (this.rightToLeft) {
/* 1736 */         paramRectangle.x += i;
/*      */       }
/* 1738 */       paramRectangle.y += j / 2;
/* 1739 */       break;
/*      */     case 22: 
/* 1741 */       if (!this.rightToLeft) {
/* 1742 */         paramRectangle.x += i;
/*      */       }
/* 1744 */       paramRectangle.y += j / 2;
/* 1745 */       break;
/*      */     case 23: 
/* 1747 */       if (this.rightToLeft) {
/* 1748 */         paramRectangle.x += i;
/*      */       }
/*      */       break;
/*      */     case 24: 
/* 1752 */       if (!this.rightToLeft) {
/* 1753 */         paramRectangle.x += i;
/*      */       }
/*      */       break;
/*      */     case 25: 
/* 1757 */       if (this.rightToLeft) {
/* 1758 */         paramRectangle.x += i;
/*      */       }
/* 1760 */       paramRectangle.y += j;
/* 1761 */       break;
/*      */     case 26: 
/* 1763 */       if (!this.rightToLeft) {
/* 1764 */         paramRectangle.x += i;
/*      */       }
/* 1766 */       paramRectangle.y += j;
/* 1767 */       break;
/*      */     default: 
/* 1769 */       throw new IllegalArgumentException("illegal anchor value");
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void alignOnBaseline(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle, int paramInt1, int paramInt2)
/*      */   {
/* 1784 */     if (paramGridBagConstraints.ascent >= 0) { int i;
/* 1785 */       if (paramGridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.CONSTANT_DESCENT)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1793 */         i = paramInt1 + paramInt2 - this.layoutInfo.maxDescent[(paramGridBagConstraints.tempY + paramGridBagConstraints.tempHeight - 1)] + paramGridBagConstraints.descent - paramGridBagConstraints.insets.bottom;
/*      */         
/*      */ 
/* 1796 */         if (!paramGridBagConstraints.isVerticallyResizable())
/*      */         {
/*      */ 
/* 1799 */           paramRectangle.y = (i - paramGridBagConstraints.minHeight);
/* 1800 */           paramRectangle.height = paramGridBagConstraints.minHeight;
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/* 1806 */           paramRectangle.height = (i - paramInt1 - paramGridBagConstraints.insets.top);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1813 */         int j = paramGridBagConstraints.ascent;
/* 1814 */         if (this.layoutInfo.hasConstantDescent(paramGridBagConstraints.tempY))
/*      */         {
/*      */ 
/* 1817 */           i = paramInt2 - this.layoutInfo.maxDescent[paramGridBagConstraints.tempY];
/*      */         }
/*      */         else
/*      */         {
/* 1821 */           i = this.layoutInfo.maxAscent[paramGridBagConstraints.tempY]; }
/*      */         int k;
/* 1823 */         int m; if (paramGridBagConstraints.baselineResizeBehavior == Component.BaselineResizeBehavior.OTHER)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1828 */           k = 0;
/* 1829 */           j = this.componentAdjusting.getBaseline(paramRectangle.width, paramRectangle.height);
/* 1830 */           if (j >= 0)
/*      */           {
/*      */ 
/*      */ 
/* 1834 */             j += paramGridBagConstraints.insets.top;
/*      */           }
/* 1836 */           if ((j >= 0) && (j <= i))
/*      */           {
/*      */ 
/* 1839 */             if (i + (paramRectangle.height - j - paramGridBagConstraints.insets.top) <= paramInt2 - paramGridBagConstraints.insets.bottom)
/*      */             {
/*      */ 
/* 1842 */               k = 1;
/*      */             }
/* 1844 */             else if (paramGridBagConstraints.isVerticallyResizable())
/*      */             {
/*      */ 
/* 1847 */               m = this.componentAdjusting.getBaseline(paramRectangle.width, paramInt2 - paramGridBagConstraints.insets.bottom - i + j);
/*      */               
/*      */ 
/* 1850 */               if (m >= 0) {
/* 1851 */                 m += paramGridBagConstraints.insets.top;
/*      */               }
/* 1853 */               if ((m >= 0) && (m <= j))
/*      */               {
/* 1855 */                 paramRectangle.height = (paramInt2 - paramGridBagConstraints.insets.bottom - i + j);
/*      */                 
/* 1857 */                 j = m;
/* 1858 */                 k = 1;
/*      */               }
/*      */             }
/*      */           }
/* 1862 */           if (k == 0)
/*      */           {
/* 1864 */             j = paramGridBagConstraints.ascent;
/* 1865 */             paramRectangle.width = paramGridBagConstraints.minWidth;
/* 1866 */             paramRectangle.height = paramGridBagConstraints.minHeight;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1872 */         paramRectangle.y = (paramInt1 + i - j + paramGridBagConstraints.insets.top);
/* 1873 */         if (paramGridBagConstraints.isVerticallyResizable()) {
/* 1874 */           switch (paramGridBagConstraints.baselineResizeBehavior) {
/*      */           case CONSTANT_ASCENT: 
/* 1876 */             paramRectangle.height = Math.max(paramGridBagConstraints.minHeight, paramInt1 + paramInt2 - paramRectangle.y - paramGridBagConstraints.insets.bottom);
/*      */             
/* 1878 */             break;
/*      */           
/*      */           case CENTER_OFFSET: 
/* 1881 */             k = paramRectangle.y - paramInt1 - paramGridBagConstraints.insets.top;
/* 1882 */             m = paramInt1 + paramInt2 - paramRectangle.y - paramGridBagConstraints.minHeight - paramGridBagConstraints.insets.bottom;
/*      */             
/* 1884 */             int n = Math.min(k, m);
/* 1885 */             n += n;
/* 1886 */             if ((n > 0) && ((paramGridBagConstraints.minHeight + paramGridBagConstraints.centerPadding + n) / 2 + paramGridBagConstraints.centerOffset != i))
/*      */             {
/*      */ 
/*      */ 
/* 1890 */               n--;
/*      */             }
/* 1892 */             paramRectangle.height = (paramGridBagConstraints.minHeight + n);
/* 1893 */             paramRectangle.y = (paramInt1 + i - (paramRectangle.height + paramGridBagConstraints.centerPadding) / 2 - paramGridBagConstraints.centerOffset);
/*      */             
/*      */ 
/*      */ 
/* 1897 */             break;
/*      */           case OTHER: 
/*      */             break;
/*      */           
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1908 */       centerVertically(paramGridBagConstraints, paramRectangle, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void alignAboveBaseline(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle, int paramInt1, int paramInt2)
/*      */   {
/* 1919 */     if (this.layoutInfo.hasBaseline(paramGridBagConstraints.tempY)) {
/*      */       int i;
/* 1921 */       if (this.layoutInfo.hasConstantDescent(paramGridBagConstraints.tempY))
/*      */       {
/* 1923 */         i = paramInt1 + paramInt2 - this.layoutInfo.maxDescent[paramGridBagConstraints.tempY];
/*      */       }
/*      */       else
/*      */       {
/* 1927 */         i = paramInt1 + this.layoutInfo.maxAscent[paramGridBagConstraints.tempY];
/*      */       }
/* 1929 */       if (paramGridBagConstraints.isVerticallyResizable())
/*      */       {
/*      */ 
/* 1932 */         paramRectangle.y = (paramInt1 + paramGridBagConstraints.insets.top);
/* 1933 */         paramRectangle.height = (i - paramRectangle.y);
/*      */       }
/*      */       else
/*      */       {
/* 1937 */         paramRectangle.height = (paramGridBagConstraints.minHeight + paramGridBagConstraints.ipady);
/* 1938 */         paramRectangle.y = (i - paramRectangle.height);
/*      */       }
/*      */     }
/*      */     else {
/* 1942 */       centerVertically(paramGridBagConstraints, paramRectangle, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void alignBelowBaseline(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle, int paramInt1, int paramInt2)
/*      */   {
/* 1951 */     if (this.layoutInfo.hasBaseline(paramGridBagConstraints.tempY)) {
/* 1952 */       if (this.layoutInfo.hasConstantDescent(paramGridBagConstraints.tempY))
/*      */       {
/* 1954 */         paramRectangle.y = (paramInt1 + paramInt2 - this.layoutInfo.maxDescent[paramGridBagConstraints.tempY]);
/*      */       }
/*      */       else
/*      */       {
/* 1958 */         paramRectangle.y = (paramInt1 + this.layoutInfo.maxAscent[paramGridBagConstraints.tempY]);
/*      */       }
/* 1960 */       if (paramGridBagConstraints.isVerticallyResizable()) {
/* 1961 */         paramRectangle.height = (paramInt1 + paramInt2 - paramRectangle.y - paramGridBagConstraints.insets.bottom);
/*      */       }
/*      */     }
/*      */     else {
/* 1965 */       centerVertically(paramGridBagConstraints, paramRectangle, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */   private void centerVertically(GridBagConstraints paramGridBagConstraints, Rectangle paramRectangle, int paramInt)
/*      */   {
/* 1971 */     if (!paramGridBagConstraints.isVerticallyResizable()) {
/* 1972 */       paramRectangle.y += Math.max(0, (paramInt - paramGridBagConstraints.insets.top - paramGridBagConstraints.insets.bottom - paramGridBagConstraints.minHeight - paramGridBagConstraints.ipady) / 2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Dimension getMinSize(Container paramContainer, GridBagLayoutInfo paramGridBagLayoutInfo)
/*      */   {
/* 1991 */     return GetMinSize(paramContainer, paramGridBagLayoutInfo);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Dimension GetMinSize(Container paramContainer, GridBagLayoutInfo paramGridBagLayoutInfo)
/*      */   {
/* 2003 */     Dimension localDimension = new Dimension();
/*      */     
/* 2005 */     Insets localInsets = paramContainer.getInsets();
/*      */     
/* 2007 */     int j = 0;
/* 2008 */     for (int i = 0; i < paramGridBagLayoutInfo.width; i++)
/* 2009 */       j += paramGridBagLayoutInfo.minWidth[i];
/* 2010 */     localDimension.width = (j + localInsets.left + localInsets.right);
/*      */     
/* 2012 */     j = 0;
/* 2013 */     for (i = 0; i < paramGridBagLayoutInfo.height; i++)
/* 2014 */       j += paramGridBagLayoutInfo.minHeight[i];
/* 2015 */     localDimension.height = (j + localInsets.top + localInsets.bottom);
/*      */     
/* 2017 */     return localDimension;
/*      */   }
/*      */   
/* 2020 */   transient boolean rightToLeft = false;
/*      */   
/*      */ 
/*      */ 
/*      */   static final long serialVersionUID = 8838754796412211005L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void arrangeGrid(Container paramContainer)
/*      */   {
/* 2031 */     ArrangeGrid(paramContainer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void ArrangeGrid(Container paramContainer)
/*      */   {
/* 2046 */     Insets localInsets = paramContainer.getInsets();
/* 2047 */     Component[] arrayOfComponent = paramContainer.getComponents();
/*      */     
/* 2049 */     Rectangle localRectangle = new Rectangle();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2054 */     this.rightToLeft = (!paramContainer.getComponentOrientation().isLeftToRight());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2060 */     if ((arrayOfComponent.length == 0) && ((this.columnWidths == null) || (this.columnWidths.length == 0)) && ((this.rowHeights == null) || (this.rowHeights.length == 0)))
/*      */     {
/*      */ 
/* 2063 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2071 */     GridBagLayoutInfo localGridBagLayoutInfo = getLayoutInfo(paramContainer, 2);
/* 2072 */     Dimension localDimension = getMinSize(paramContainer, localGridBagLayoutInfo);
/*      */     
/* 2074 */     if ((paramContainer.width < localDimension.width) || (paramContainer.height < localDimension.height)) {
/* 2075 */       localGridBagLayoutInfo = getLayoutInfo(paramContainer, 1);
/* 2076 */       localDimension = getMinSize(paramContainer, localGridBagLayoutInfo);
/*      */     }
/*      */     
/* 2079 */     this.layoutInfo = localGridBagLayoutInfo;
/* 2080 */     localRectangle.width = localDimension.width;
/* 2081 */     localRectangle.height = localDimension.height;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2103 */     int k = paramContainer.width - localRectangle.width;
/* 2104 */     double d; int j; int n; if (k != 0) {
/* 2105 */       d = 0.0D;
/* 2106 */       for (j = 0; j < localGridBagLayoutInfo.width; j++)
/* 2107 */         d += localGridBagLayoutInfo.weightX[j];
/* 2108 */       if (d > 0.0D) {
/* 2109 */         for (j = 0; j < localGridBagLayoutInfo.width; j++) {
/* 2110 */           n = (int)(k * localGridBagLayoutInfo.weightX[j] / d);
/* 2111 */           localGridBagLayoutInfo.minWidth[j] += n;
/* 2112 */           localRectangle.width += n;
/* 2113 */           if (localGridBagLayoutInfo.minWidth[j] < 0) {
/* 2114 */             localRectangle.width -= localGridBagLayoutInfo.minWidth[j];
/* 2115 */             localGridBagLayoutInfo.minWidth[j] = 0;
/*      */           }
/*      */         }
/*      */       }
/* 2119 */       k = paramContainer.width - localRectangle.width;
/*      */     }
/*      */     else
/*      */     {
/* 2123 */       k = 0;
/*      */     }
/*      */     
/* 2126 */     int m = paramContainer.height - localRectangle.height;
/* 2127 */     if (m != 0) {
/* 2128 */       d = 0.0D;
/* 2129 */       for (j = 0; j < localGridBagLayoutInfo.height; j++)
/* 2130 */         d += localGridBagLayoutInfo.weightY[j];
/* 2131 */       if (d > 0.0D) {
/* 2132 */         for (j = 0; j < localGridBagLayoutInfo.height; j++) {
/* 2133 */           n = (int)(m * localGridBagLayoutInfo.weightY[j] / d);
/* 2134 */           localGridBagLayoutInfo.minHeight[j] += n;
/* 2135 */           localRectangle.height += n;
/* 2136 */           if (localGridBagLayoutInfo.minHeight[j] < 0) {
/* 2137 */             localRectangle.height -= localGridBagLayoutInfo.minHeight[j];
/* 2138 */             localGridBagLayoutInfo.minHeight[j] = 0;
/*      */           }
/*      */         }
/*      */       }
/* 2142 */       m = paramContainer.height - localRectangle.height;
/*      */     }
/*      */     else
/*      */     {
/* 2146 */       m = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2161 */     localGridBagLayoutInfo.startx = (k / 2 + localInsets.left);
/* 2162 */     localGridBagLayoutInfo.starty = (m / 2 + localInsets.top);
/*      */     
/* 2164 */     for (int i = 0; i < arrayOfComponent.length; i++) {
/* 2165 */       Component localComponent = arrayOfComponent[i];
/* 2166 */       if (localComponent.isVisible())
/*      */       {
/*      */ 
/* 2169 */         GridBagConstraints localGridBagConstraints = lookupConstraints(localComponent);
/*      */         
/* 2171 */         if (!this.rightToLeft) {
/* 2172 */           localRectangle.x = localGridBagLayoutInfo.startx;
/* 2173 */           for (j = 0; j < localGridBagConstraints.tempX; j++)
/* 2174 */             localRectangle.x += localGridBagLayoutInfo.minWidth[j];
/*      */         }
/* 2176 */         localRectangle.x = (paramContainer.width - (k / 2 + localInsets.right));
/* 2177 */         for (j = 0; j < localGridBagConstraints.tempX; j++) {
/* 2178 */           localRectangle.x -= localGridBagLayoutInfo.minWidth[j];
/*      */         }
/*      */         
/* 2181 */         localRectangle.y = localGridBagLayoutInfo.starty;
/* 2182 */         for (j = 0; j < localGridBagConstraints.tempY; j++) {
/* 2183 */           localRectangle.y += localGridBagLayoutInfo.minHeight[j];
/*      */         }
/* 2185 */         localRectangle.width = 0;
/* 2186 */         for (j = localGridBagConstraints.tempX; 
/* 2187 */             j < localGridBagConstraints.tempX + localGridBagConstraints.tempWidth; 
/* 2188 */             j++) {
/* 2189 */           localRectangle.width += localGridBagLayoutInfo.minWidth[j];
/*      */         }
/*      */         
/* 2192 */         localRectangle.height = 0;
/* 2193 */         for (j = localGridBagConstraints.tempY; 
/* 2194 */             j < localGridBagConstraints.tempY + localGridBagConstraints.tempHeight; 
/* 2195 */             j++) {
/* 2196 */           localRectangle.height += localGridBagLayoutInfo.minHeight[j];
/*      */         }
/*      */         
/* 2199 */         this.componentAdjusting = localComponent;
/* 2200 */         adjustForGravity(localGridBagConstraints, localRectangle);
/*      */         
/*      */ 
/*      */ 
/* 2204 */         if (localRectangle.x < 0) {
/* 2205 */           localRectangle.width += localRectangle.x;
/* 2206 */           localRectangle.x = 0;
/*      */         }
/*      */         
/* 2209 */         if (localRectangle.y < 0) {
/* 2210 */           localRectangle.height += localRectangle.y;
/* 2211 */           localRectangle.y = 0;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2220 */         if ((localRectangle.width <= 0) || (localRectangle.height <= 0)) {
/* 2221 */           localComponent.setBounds(0, 0, 0, 0);
/*      */ 
/*      */         }
/* 2224 */         else if ((localComponent.x != localRectangle.x) || (localComponent.y != localRectangle.y) || (localComponent.width != localRectangle.width) || (localComponent.height != localRectangle.height))
/*      */         {
/* 2226 */           localComponent.setBounds(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/GridBagLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */