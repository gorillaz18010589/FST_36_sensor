package tw.brad.apps.brad36;
//1.android developer sensor(感應器)  :https://developer.android.com/guide/topics/sensors/sensors_overview
//        *側移動
//        *方向
//        *環境參數
//現在的版本幾乎全支援軟體部分,但硬體手機未必有感應器
//        TYPE_ACCELEROMETER:三軸加速感應器,翻轉手機
//        TYPE_GRAVITY:重力感應器
//        TYPE_LIGHT:光度感應器
//        TYPE_LINEAR_ACCELERATION:加速度感應器
//        TYPE_MAGNETIC_FIELD:磁極感應器
//        TYPE_ORIENTATION:方向感應器
//        TYPE_PRESSURE:大氣壓力感應器
//        TYPE_PROXIMITY:接近感應器
//        TYPE_RELATIVE_HUMIDITY:濕度感應器
//        TYPE_TEMPERATURE:環境溫度感應器(並不是手機cpu)
//擺在andorid.hardware
//SensorManager:感應器管理員
// Sensor:拿到了要有一個事件發生由SensorEventListener去聽
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private MyListener myListener;
    private MyListener2 myListener2;
    private TextView x, y, z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = findViewById(R.id.x);
        y = findViewById(R.id.y);
        z = findViewById(R.id.z);

        sensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);//取得感應器管理員

        //取得感應器內容
        List<Sensor> sensors =  sensorManager.getSensorList(Sensor.TYPE_ALL); //取得手機上的感應器(全部感應器),傳回list<Sensir>泛型
        for(Sensor sensor : sensors){
            Log.v("brad",sensor.getName() + ":" + sensor.getStringType() +
                    ":" +sensor.getVendor()); //getName():取得名字 ,getStringType() :取得感應器種類
        }
        //選擇操控得感應方式
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//getDefaultSensor():取得感應器物件 ,TYPE_ACCELEROMETER:三軸加速感應器
    }
    //手機開始時抓延遲時間
    @Override
    protected void onResume() {
        super.onResume();
        myListener = new MyListener();
        sensorManager.registerListener(myListener //事件監聽
                ,sensor //sensor
                ,SensorManager.SENSOR_DELAY_NORMAL); //取得較慢的延遲
        Log.v("brad","onResume");
    }
    //手機暫停時
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(myListener);//unregisterListener(myListener):取消註冊(字寫得事件)
    }

    //3.session事件監聽
    private class MyListener implements SensorEventListener {
        //值改變時
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            x.setText("X: " + (int)(values[0]));
            y.setText("Y: " + (int)(values[1]));
            z.setText("Z: " + (int)(values[2]));
        }
        //精確度改變時
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

    private class MyListener2 implements SensorEventListener2 {

        @Override
        public void onFlushCompleted(Sensor sensor) {

        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}