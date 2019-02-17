package voicebattle.com.shaya.voicebattle

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_main.*

class GraphSample(val context: Activity) {
    fun addGraph(value:Float){
        val mChart = context.line_chart
        val data = mChart.getData() ?: return

        var set: LineDataSet = data.getDataSetByIndex(0) as LineDataSet
        if (set == null) {
            set = LineDataSet(null, "サンプルデータ") //  解説
            set.color = Color.BLUE
            set.setDrawValues(false)
            data.addDataSet(set)
        }

        //  追加描画するデータを追加
//        val format = SimpleDateFormat("HH:mm:ss")
//        data.addXValue(format.format(date))
        data.addEntry(Entry( set.entryCount.toFloat(),value ), 0)

        //  データを追加したら必ずよばないといけない
        mChart.notifyDataSetChanged()
        mChart.invalidate()

//        mChart.setVisibleXRangeMaximum(60) //  解説
//
//        mChart.moveViewToX(data.getXValCount() - 61)   //  移動する
    }
    fun addGraph(values:ShortArray){
        val mChart = context.line_chart
        val data = mChart.getData() ?: return

        var set: LineDataSet = data.getDataSetByIndex(0) as LineDataSet
        if (set == null) {
            set = LineDataSet(null, "サンプルデータ") //  解説
            set.color = Color.BLUE
            set.setDrawValues(false)
            data.addDataSet(set)
        }
        values.forEach {
            data.addEntry(Entry( set.entryCount.toFloat(),it.toFloat() ), 0)
        }


        //  データを追加したら必ずよばないといけない
        mChart.notifyDataSetChanged()
    }
    fun setGraph(){
        val mChart: LineChart = context.line_chart

        // Grid背景色
        mChart.setDrawGridBackground(true)

        // no description text
        mChart.getDescription().setEnabled(true)

        // Grid縦軸を破線
        val xAxis = mChart.getXAxis()
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)

        val leftAxis = mChart.getAxisLeft()
        // Y軸最大最小設定
        leftAxis.setAxisMaximum(1000f)
        leftAxis.setAxisMinimum(-1000f)
        // Grid横軸を破線
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(true)

        // 右側の目盛り
        mChart.getAxisRight().setEnabled(false)

        setData(mChart)

//        mChart.animateX(2500)
    }

    private fun setData(mChart: LineChart) {
        // Entry()を使ってLineDataSetに設定できる形に変更してarrayを新しく作成
        val data = intArrayOf(0)

        val values = ArrayList<Entry>()

        for (i in data.indices) {
            values.add(Entry(i.toFloat(), data[i].toFloat(), null, null))
        }

        val set1: LineDataSet

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = mChart.data.getDataSetByIndex(0) as LineDataSet

            set1.values = values
            mChart.getData().notifyDataChanged()
            mChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet")

            set1.setDrawIcons(false)
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.valueTextSize = 0f
            set1.setDrawFilled(true)
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            set1.fillColor = Color.BLUE

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the datasets

            // create a data object with the datasets
            val lineData = LineData(dataSets)

            // set data
            mChart.setData(lineData)
        }
    }
}