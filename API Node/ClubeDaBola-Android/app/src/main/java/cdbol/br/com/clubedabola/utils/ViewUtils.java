package cdbol.br.com.clubedabola.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;


public abstract class ViewUtils {

    public static Locale locale = new Locale("pt", "BR");
    public static final DecimalFormat FORMAT_CURRENCY = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);

    static {
        final String symbol = FORMAT_CURRENCY.getCurrency().getSymbol();
        FORMAT_CURRENCY.setNegativePrefix("-" + symbol + " ");
        FORMAT_CURRENCY.setNegativeSuffix("");
        FORMAT_CURRENCY.setPositivePrefix(symbol + " ");
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    public static String formatValueCurrency(String valor) {
        try {
            return FORMAT_CURRENCY.format(new BigDecimal(valor));
        } catch (NullPointerException | NumberFormatException e) {
            return "";
        }
    }

    public static String formatValueCurrency(Double valor) {
        return FORMAT_CURRENCY.format(new BigDecimal(valor));
    }

    public static String formatPercentageValue(Double value) {
        return formatPercentageValue(String.valueOf(value));
    }

    public static String formatPercentageValue(String value) {
        if (value == null)
            return "%";

        String cleanValue = value.replace("%", "");
        String percent = cleanValue + "%";
        percent = percent.replace(".", ",");
        return percent;
    }


    public static String formatDate(Long data) {
        Date dt = new Date();
        dt.setTime(data);
        SimpleDateFormat df = new SimpleDateFormat("dd.MM");
        return df.format(dt.getTime());
    }


    public static void hiddenKeyboard(Activity context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static String removeNonDigits(final String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return str.replaceAll("[^0-9]+", "");
    }


    public static Boolean isSequenceNumber(final String number) {
        boolean isSequence = false;

        char[] chars = number.toCharArray();
        int first = Character.getNumericValue(chars[0]);
        int last = Character.getNumericValue(chars[chars.length - 1]);


        //validate only if there are more than 2 characters and be different
        if (number.length() > 2 && first != last) {
            //validate increasing sequence
            if (first < last) {
                for (int i = 0; i < chars.length - 1; i++) {
                    int current = Character.getNumericValue(chars[i]);
                    int pos = Character.getNumericValue(chars[i + 1]);
                    if ((current + 1) == pos) {
                        isSequence = true;
                    } else {
                        return false;
                    }
                }
                //validate decreasing sequence
            } else {
                for (int i = 0; i < chars.length - 1; i++) {
                    int current = Character.getNumericValue(chars[i]);
                    int pos = Character.getNumericValue(chars[i + 1]);
                    if ((current - 1) == pos) {
                        isSequence = true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return isSequence;
    }

    public static boolean isRepeatedNumber(String number) {
        Pattern repetition = Pattern.compile("([0-9])\\1+");
        return repetition.matcher(number).matches();
    }

    public static Boolean validSequencePass(final String passwd) {
        String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\\d]{6,}$";
        return passwd.matches(pattern);
    }

    public static String upperCaseFirstLetter(String name) {
        StringBuilder sb = new StringBuilder(name);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public static String capitalizeAllWords(@NonNull String input) {

        String[] words = input.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (i > 0 && word.length() > 0)
                builder.append(" ");

            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            builder.append(cap);
        }
        return builder.toString();
    }

    public static Boolean validateAddressNumber(String number) {

        int length = number.toCharArray().length;
        if (StringUtils.isEmpty(number) || length > 7)
            return false;


        try {
            int num = Integer.parseInt(number);
            return num != 0;

        } catch (NumberFormatException e) {


            if (number.equalsIgnoreCase("km") || number.equalsIgnoreCase("s/n"))
                return true;

            return hasNumber(number);

        }

    }

    private static boolean hasNumber(String number) {
        boolean hasNumber = false;

        for (Character c : number.toCharArray()) {
            if (StringUtils.isNumeric(c.toString())) {
                hasNumber = true;
            }
        }
        return hasNumber;
    }

    public static float getDimentionValue(Context context, int resDimenId) {
        TypedValue outValue = new TypedValue();
        context.getResources().getValue(resDimenId, outValue, true);
        float value = outValue.getFloat();
        return value;
    }


    public static void setAlpha(float alpha, @NonNull View... views) {
        for (View view : views) {
            view.setAlpha(alpha);
        }
    }

    public static void setEnabled(boolean enabled, View... views) {
        for (View view : views) {
            view.setEnabled(enabled);
        }
    }

    public static int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(px);
    }

    public static float pxToDp(Context context, float px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return px * displayMetrics.density;
    }


    public static <E extends View> E loadView(Activity act, int id) {
        return (E) act.findViewById(id);
    }

    public static <E extends View> E loadView(View view, int id) {
        return (E) view.findViewById(id);
    }

    public static double calculateDistance(Location location1, Location location2) {
        return location1.distanceTo(location2) / 1000;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        Location location1 = new Location("");
        location1.setLatitude(lat1);
        location1.setLongitude(lon1);

        Location location2 = new Location("");
        location2.setLatitude(lat2);
        location2.setLongitude(lon2);

        return location1.distanceTo(location2) / 1000;
    }


}
