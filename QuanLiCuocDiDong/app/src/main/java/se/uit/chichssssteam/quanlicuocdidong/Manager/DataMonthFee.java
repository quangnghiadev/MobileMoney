package se.uit.chichssssteam.quanlicuocdidong.Manager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.uit.chichssssteam.quanlicuocdidong.DB.DAO_Statistic;
import se.uit.chichssssteam.quanlicuocdidong.DB.Statistic;

/**
 * Created by QNghi on 23/10/2015.
 */
public class DataMonthFee {
    public static final String TAG = DataMonthFee.class.getSimpleName();
    public static Context _myContext;
    public static List<Statistic> monthStatistics;
    public static List<MonthFee> monthFeeList;
    public static String[] titles;
    public static MonthFee[][] monthFeess;
    public static List<Pair<String, List<MonthFee>>> getAllData(Context myContext) {
        _myContext = myContext;
        DAO_Statistic dao_statistic = new DAO_Statistic(_myContext);
        dao_statistic.Open();
        monthStatistics = dao_statistic.GetAllStatistic();
        int n = monthStatistics.size();
        titles = new String[n];
        monthFeess = new MonthFee[n][1];
        for (int i = 0; i < n; i++) {
            monthFeeList.add(new MonthFee(monthStatistics.get(i)));
            titles[i] = "Tháng " + monthFeeList.get(i).getMonth() + "/" +  monthFeeList.get(i).getYear();
            monthFeess[i][1] = new MonthFee("10", "2015","1",12,"2",23,3,34,4,45);
        }
        List<Pair<String, List<MonthFee>>> res = new ArrayList<Pair<String, List<MonthFee>>>();

        for (int i = 0; i < n; i++) {
            res.add(getOneSection(i));
        }

        return res;
    }

    public static List<MonthFee> getFlattenedData() {
        List<MonthFee> res = new ArrayList<MonthFee>();

        for (int i = 0; i < 4; i++) {
            res.addAll(getOneSection(i).second);
        }

        return res;
    }

    public static Pair<Boolean, List<MonthFee>> getRows(int page) {
        List<MonthFee> flattenedData = getFlattenedData();
        if (page == 1) {
            return new Pair<Boolean, List<MonthFee>>(true, flattenedData.subList(0, 5));
        } else {
            SystemClock.sleep(2000); // simulate loading
            return new Pair<Boolean, List<MonthFee>>(page * 5 < flattenedData.size(), flattenedData.subList((page - 1) * 5, Math.min(page * 5, flattenedData.size())));
        }
    }

    public static Pair<String, List<MonthFee>> getOneSection(int index) {
        return new Pair<String, List<MonthFee>>(titles[index], Arrays.asList(monthFeess[index]));
    }
}
