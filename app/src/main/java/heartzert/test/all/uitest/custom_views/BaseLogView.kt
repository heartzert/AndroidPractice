package heartzert.test.all.uitest.custom_views

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection

/**
 * Created by heartzert on 2019/9/27.
 * Email: heartzert@163.com
 */
class BaseLogView : View {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
        defStyleAttr)


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        Log.d("========", "onScrollChanged")
        super.onScrollChanged(l, t, oldl, oldt)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.d("========", "onSizeChanged")
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("========", "onTouchEvent")

        return super.onTouchEvent(event)
    }

    override fun onAnimationEnd() {
        Log.d("========", "onAnimationEnd")

        super.onAnimationEnd()
    }

    override fun onAnimationStart() {
        Log.d("========", "onAnimationStart")

        super.onAnimationStart()
    }

    override fun onFinishInflate() {
        Log.d("========", "onFinishInflate")

        super.onFinishInflate()
    }

    override fun onSetAlpha(alpha: Int): Boolean {
        Log.d("========", "onSetAlpha")

        return super.onSetAlpha(alpha)
    }

    override fun onAttachedToWindow() {
        Log.d("========", "")

        super.onAttachedToWindow()
    }


    override fun onDraw(canvas: Canvas) {
        Log.d("========", "")

        super.onDraw(canvas)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        Log.d("========", "")

        super.onVisibilityChanged(changedView, visibility)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        Log.d("========", "")

        super.onWindowFocusChanged(hasWindowFocus)
    }

    override fun onDrawForeground(canvas: Canvas) {
        Log.d("========", "")

        super.onDrawForeground(canvas)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("========", "")

        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("========", "")

        return super.onKeyLongPress(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("========", "")

        return super.onKeyUp(keyCode, event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("========", "")

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSaveInstanceState(): Parcelable? {
        Log.d("========", "")

        return super.onSaveInstanceState()
    }


    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        Log.d("========", "")

        return super.onApplyWindowInsets(insets)
    }

    override fun onCancelPendingInputEvents() {
        Log.d("========", "")

        super.onCancelPendingInputEvents()
    }

    override fun onCapturedPointerEvent(event: MotionEvent?): Boolean {
        Log.d("========", "")

        return super.onCapturedPointerEvent(event)
    }

    override fun onCheckIsTextEditor(): Boolean {
        Log.d("========", "")

        return super.onCheckIsTextEditor()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        Log.d("========", "")

        super.onConfigurationChanged(newConfig)
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        Log.d("========", "")

        return super.onCreateDrawableState(extraSpace)
    }

    override fun onStartTemporaryDetach() {
        Log.d("========", "")

        super.onStartTemporaryDetach()
    }


    override fun onPopulateAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d("========", "")

        super.onPopulateAccessibilityEvent(event)
    }

    override fun onFinishTemporaryDetach() {
        Log.d("========", "")

        super.onFinishTemporaryDetach()
    }

    override fun onResolvePointerIcon(event: MotionEvent?, pointerIndex: Int): PointerIcon {
        Log.d("========", "")

        return super.onResolvePointerIcon(event, pointerIndex)
    }

    override fun onCreateContextMenu(menu: ContextMenu?) {
        Log.d("========", "")

        super.onCreateContextMenu(menu)
    }

    override fun onRtlPropertiesChanged(layoutDirection: Int) {
        Log.d("========", "")

        super.onRtlPropertiesChanged(layoutDirection)
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        Log.d("========", "")

        return super.onCreateInputConnection(outAttrs)
    }

    override fun onDragEvent(event: DragEvent?): Boolean {
        Log.d("========", "")

        return super.onDragEvent(event)
    }

    override fun onDetachedFromWindow() {
        Log.d("========", "")

        super.onDetachedFromWindow()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        Log.d("========", "")

        super.onWindowVisibilityChanged(visibility)
    }

    override fun onHoverEvent(event: MotionEvent?): Boolean {
        Log.d("========", "")

        return super.onHoverEvent(event)
    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d("========", "")

        super.onInitializeAccessibilityEvent(event)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.d("========", "")

        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        Log.d("========", "")

        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }

    override fun onFilterTouchEventForSecurity(event: MotionEvent?): Boolean {
        Log.d("========", "")

        return super.onFilterTouchEventForSecurity(event)
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        Log.d("========", "")

        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
    }

    override fun onScreenStateChanged(screenState: Int) {
        Log.d("========", "")

        super.onScreenStateChanged(screenState)
    }

    override fun onPointerCaptureChange(hasCapture: Boolean) {
        Log.d("========", "")

        super.onPointerCaptureChange(hasCapture)
    }

    override fun onKeyMultiple(keyCode: Int, repeatCount: Int, event: KeyEvent?): Boolean {
        Log.d("========", "")

        return super.onKeyMultiple(keyCode, repeatCount, event)
    }

    override fun onDisplayHint(hint: Int) {
        Log.d("========", "")

        super.onDisplayHint(hint)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        Log.d("========", "")

        return super.onGenericMotionEvent(event)
    }

    override fun onHoverChanged(hovered: Boolean) {
        Log.d("========", "")

        super.onHoverChanged(hovered)
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo?) {
        Log.d("========", "")

        super.onInitializeAccessibilityNodeInfo(info)
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("========", "")

        return super.onKeyPreIme(keyCode, event)
    }

    override fun onKeyShortcut(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("========", "")

        return super.onKeyShortcut(keyCode, event)
    }

    override fun onProvideAutofillStructure(structure: ViewStructure?, flags: Int) {
        Log.d("========", "")

        super.onProvideAutofillStructure(structure, flags)
    }

    override fun onProvideAutofillVirtualStructure(structure: ViewStructure?, flags: Int) {
        Log.d("========", "")

        super.onProvideAutofillVirtualStructure(structure, flags)
    }

    override fun onProvideStructure(structure: ViewStructure?) {
        Log.d("========", "")

        super.onProvideStructure(structure)
    }

    override fun onProvideVirtualStructure(structure: ViewStructure?) {
        Log.d("========", "")

        super.onProvideVirtualStructure(structure)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        Log.d("========", "")

        super.onRestoreInstanceState(state)
    }

    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        Log.d("========", "")

        return super.onTrackballEvent(event)
    }

    override fun onVisibilityAggregated(isVisible: Boolean) {
        Log.d("========", "")

        super.onVisibilityAggregated(isVisible)
    }

    override fun onWindowSystemUiVisibilityChanged(visible: Int) {
        Log.d("========", "")

        super.onWindowSystemUiVisibilityChanged(visible)
    }

}
