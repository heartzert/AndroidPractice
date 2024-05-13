package heartzert.test.all;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import android.util.Log;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by heartzert on 3/6/21.
 * Email: heartzert@163.com
 */
public class JavaTest {

    public static void testMAC() {
        try {
            Log.d("==========wxzmac1", "${JavaTest.mac1(this)} ");
        } catch (Exception e) {
            Log.d("==========wxz", "1崩了");
        }

        try {
            Log.d("==========wxzmac2", "${JavaTest.mac2()} ");
        } catch (Exception e) {
            Log.d("==========wxz", "2崩了");
        }

        try {
            Log.d("==========wxzmac3", "${JavaTest.mac3()} ");
        } catch (Exception e) {
            Log.d("==========wxz", "3崩了");
        }
        try {
            Log.d("==========wxzmac4", "${JavaTest.mac4()} ");
        } catch (Exception e) {
            Log.d("==========wxz", "4崩了");
        }
        try {
            Log.d("==========wxzmac5", "${JavaTest.mac5()} ");
        } catch (Exception e) {
            Log.d("==========wxz", "5崩了");
        }
    }

    /**
     * 通过WiFiManager获取mac地址
     * @param context
     * @return
     */
    public static String mac1(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null || wi.getMacAddress() == null) {
            return null;
        }
        return wi.getMacAddress().trim();
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    public static String mac2() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {

        }

        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    public static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 通过网络接口取
     * @return
     */
    public static String mac3() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String mac4() {
        return BluetoothAdapter.getDefaultAdapter().getAddress();
    }

    /**
     * 这是使用adb shell命令来获取mac地址的方式
     */
    public static String mac5() {
        String macSerial = null;
        String str = "";

        /**
         * String[] strArr = {"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"};
         */
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }
}
