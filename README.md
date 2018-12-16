# DragView
可拖拽的view,继承自RelativeLayout。A draggable viewgroup which is extended from RelativeLayout.

![](https://raw.githubusercontent.com/Ccapton/DragView/master/dragview_demo.gif)

### 特性
- 可放置在任意类型的父布局中
- 可贴合父类布局的四个边界
- 可设置触发贴合的距离

### 示例布局
```
<com.ccapton.dragview.DragView
    android:id="@+id/dragview"
    app:alignDistance="45dp"
    app:bottomAlign="true"
    app:topAlign="true"
    app:leftAlign="true"
    app:rightAlign="true"
    android:layout_width="80dp"
    android:layout_height="80dp">
    <!--这里可放置你的子布局-->
</com.ccapton.dragview.DragView>
```

### 方法
``` bash
setAlignDistance(float); // 触发贴合四周的距离（dp）

setTopAlign(boolean);    // 是否贴合顶部
setLeftAlign(boolean);   // 是否贴合左边
setRightAlign(boolean);  // 是否贴合右边
setBottomAlign(boolean); // 是否贴合底部

setOnDragViewClickListener(OnDragViewClickListener); // 设置DragView的单击事件
```
