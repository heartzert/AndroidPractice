package heartzert.test.all.uitest.bezier

import android.graphics.PointF
import androidx.annotation.IntDef

import java.util.ArrayList

/**
 * Created by heartzert on 2021/3/9.
 * Email: heartzert@163.com
 */
object BezierUtil {

    @IntDef(BezierType.X_TYPE, BezierType.Y_TYPE)
    annotation class BezierType {
        companion object {
            const val X_TYPE = 0
            const val Y_TYPE = 1
        }
    }

    /**
     * 构建贝塞尔曲线，具体点数由 参数frame 决定
     *
     * @param controlPointList 控制点的坐标
     * @param frame            帧数
     * @return
     */
    fun buildBezierPoint(
        controlPointList: List<PointF>,
        frame: Int
    ): List<PointF> {
        val pointList: MutableList<PointF> = ArrayList()
        val delta = 1f / frame
        // 阶数，阶数=绘制点数-1
        val order = controlPointList.size - 1
        // 循环递增
        var u = 0f
        while (u <= 1) {
            pointList.add(
                PointF(
                    calculatePointCoordinate(BezierType.X_TYPE, u, order, 0, controlPointList),
                    calculatePointCoordinate(BezierType.Y_TYPE, u, order, 0, controlPointList)
                )
            )
            u += delta
        }
        return pointList
    }

    /**
     * 计算坐标 [贝塞尔曲线的核心关键]
     *
     * @param type             [.X_TYPE] 表示x轴的坐标， [.Y_TYPE] 表示y轴的坐标
     * @param u                当前的比例
     * @param k                阶数
     * @param p                当前坐标（具体为 x轴 或 y轴）
     * @param controlPointList 控制点的坐标
     * @return
     */
    fun calculatePointCoordinate(
        r: ArrayList<Float>,
        u: Float,
        k: Int,
        p: Int,
        controlPointList: List<PointF>
    ) {
        /**
         * 公式解说：（p表示坐标点，后面的数字只是区分）
         * 场景：有一条线p1到p2，p0在中间，求p0的坐标
         * p1◉--------○----------------◉p2
         * u    p0
         *
         * 公式：p0 = p1+u*(p2-p1) 整理得出 p0 = (1-u)*p1+u*p2
         */
        // 一阶贝塞尔，直接返回
        if (r.size < 2) return
        return if (k == 1) {
            // 根据是 x轴 还是 y轴 进行赋值
            r[0] = (1 - u) * controlPointList[p].x + u * controlPointList[p + 1].x
            r[1] = (1 - u) * controlPointList[p].y + u * controlPointList[p + 1].y
        } else {
            /**
             * 这里应用了递归的思想：
             * 1阶贝塞尔曲线的端点 依赖于 2阶贝塞尔曲线
             * 2阶贝塞尔曲线的端点 依赖于 3阶贝塞尔曲线
             * ....
             * n-1阶贝塞尔曲线的端点 依赖于 n阶贝塞尔曲线
             *
             * 1阶贝塞尔曲线 则为 真正的贝塞尔曲线存在的点
             */
            val rr = r.clone() as ArrayList<Float>
            ((1 - u) * calculatePointCoordinate(rr, u, k - 1, p, controlPointList)
                    + u * calculatePointCoordinate(rr, u, k - 1, p + 1, controlPointList))
        }
    }
}