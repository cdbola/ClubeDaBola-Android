package cdbol.br.com.clubedabola.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;



import org.apache.commons.lang3.StringUtils;
import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.util.Locale;


public abstract class MaskUtils {

    @NonNull
    public static String unmask(@NonNull String s) {
        if (StringUtils.isNotEmpty(s)) {
            return s.replaceAll("[.]", "").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[)]", "").replaceAll("[ ]", "");
        } else {
            return "";
        }
    }

    @NonNull
    public static String unmaskCurrent(@NonNull String s) {
        if (StringUtils.isNotEmpty(s)) {
            return s.replaceAll("[.]", "").replaceAll("[R]", "")
                    .replaceAll("[$]", "").replaceAll("[,]", ".")
                    .replaceAll("B", "").replaceAll("L", "")
                    .replaceAll(" ", "");
        } else {
            return "";
        }
    }

    public static Double currencyToDouble(String str) {
        boolean hasMask = str.contains("BRL") || str.contains("R$") || str.contains("R") || str.contains(
                "$") || str.contains(" ") || str.contains(".") || str.contains(",");
        if (hasMask) {
            str = str.replaceAll("[BRL]", "").replaceAll("[R]", "").replaceAll("[$]", "").replaceAll("[,]", "")
                    .replaceAll("[.]", "").replaceAll(" ", "").replace(Character.toString((char)160),"");
        }

        return Double.valueOf(str) / 100;
    }

    public static TextWatcher insert(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = MaskUtils.unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches() && !target.toString().contains("__");
        }
    }

    public static boolean isValidFullName(String name) {
        return name.matches("^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$");
    }

    public static boolean isPhoneValid(String name) {
        return name.matches("^\\(?\\d{2}\\)?[\\s-]?[\\s9]?\\d{4}-?\\d{4}$");
    }

    public static boolean isCellphoneValid(String name) {
        return name.matches("^\\(?\\d{2}\\)\\s?9\\d{4}-?\\d{4}$");
    }

    public static boolean isCellPhoneValidate(String numberPhone) {
        return numberPhone.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}") ||
                numberPhone.matches("((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}");
    }

    public static String formatCurrency(@NonNull Double value) {
        Locale ptBr = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(ptBr);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(value);
    }

    public static String formatNumber(Double value) {
        Locale ptBr = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getNumberInstance(ptBr);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(value);
    }

    protected static String padNumber(String number, int maxLength) {
        String padded = new String(number);
        for (int i = 0; i < maxLength - number.length(); i++) {
            padded += " ";
        }
        return padded;
    }

    public static String formatCellPhone(String current) {
        return formatCellPhone(current, false);
    }

    public static String formatCellPhone(String current, boolean mask) {
        String numberCopy = current;
        String number = current.replaceAll("[^0-9]*", "");
        while (number.startsWith("0")) {
            number = number.substring(1);
        }

        if (number.length() > 11) {
            number = number.substring(0, 11);
        }

        if(number.length() == 9) {
            String paddedNumber = padNumber(number, 9);
            String part1 = paddedNumber.substring(0, 5);
            if (mask) {
                part1 = "*****";
            }
            String part2 = paddedNumber.substring(5, 9);

            return "(  ) " + part1 + (mask ? "" : "-") + part2;
        } else {
            String paddedNumber = padNumber(number, 11);
            String ddd = paddedNumber.substring(0, 2);
            String part1 = paddedNumber.substring(2, 7);
            if (mask) {
                part1 = "*****";
            }
            String part2 = paddedNumber.substring(7, 11);
            return "(" + ddd + ") " + part1 + (mask ? "" : "-") + part2;
        }
    }

    public static String formatSecurityAddress(String street, String number) {

        if (street == null) {
            street = "";
        }
        if (number == null) {
            number = "";
        }

        if (street.isEmpty() && number.isEmpty()) {
            return "";
        }

        String address = street + ", " + number;
        String[] addressArray = address.split(" ");
        int initIndex = addressArray.length > 3 ? 2 : 1;

        String security = "";

        for (int i = 0; i < addressArray.length; i++) {
            String str = addressArray[i];
            if (i >= initIndex && i < addressArray.length - 1) {
                security += securityString(str);
            } else if (i < initIndex) {
                security += str + " ";
            } else if (i == addressArray.length - 1) {
                security += " " + str;
            }
        }

        return security;

    }

    private static String securityString(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            sb.append('*');
        }
        return sb.toString();
    }

    public static String formatPhone(String current) {
        return formatPhone(current, false);
    }

    public static String formatPhone(String current, boolean mask) {
        String number = current.replaceAll("[^0-9]*", "");
        while (number.startsWith("0")) {
            number = number.substring(1);
        }

        if (number.length() > 10) {
            number = number.substring(0, 10);
        }

        String paddedNumber = padNumber(number, 10);

        String ddd = paddedNumber.substring(0, 2);
        String part1 = paddedNumber.substring(2, 6);
        if (mask) {
            part1 = "*****";
        }
        String part2 = paddedNumber.substring(6, 10);

        return "(" + ddd + ") " + part1 + (mask ? "" : "-") + part2;
    }

    public static String maskEmail(String email) {
        int posAd = email.indexOf("@");

        String user = email.substring(0, posAd);

        if (user.length() < 2) {
            user = user + "***";
        } else {
            user = user.substring(0, 2) + "****";
        }

        String provider = email.substring(posAd);
        return user + provider;
    }

    public static boolean validateCpf(String CPF) {
        if (CPF == null || (CPF.length() != 11) || CPF.equals("00000000000")
                || CPF.equals("11111111111") || CPF.equals("22222222222")
                || CPF.equals("33333333333") || CPF.equals("44444444444")
                || CPF.equals("55555555555") || CPF.equals("66666666666")
                || CPF.equals("77777777777") || CPF.equals("88888888888")
                || CPF.equals("99999999999")) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;
        try {

            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);
            }
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (Exception e) {
            return (false);
        }

    }

    public static boolean validateCnpj(final String cnpj) {
        int[] weightTin = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        if ((cnpj == null) || (cnpj.length() != 14) || cnpj.matches(cnpj.charAt(0) + "{14}"))
            return false;

        final Integer digit1 = calculate(cnpj.substring(0, 12), weightTin);
        final Integer digit2 = calculate(cnpj.substring(0, 12) + digit1, weightTin);
        return cnpj.equals(cnpj.substring(0, 12) + digit1.toString() + digit2.toString());
    }

    private static int calculate(final String str, final int[] weight) {
        int sum = 0;
        for (int i = str.length() - 1, digit; i >= 0; i--) {
            digit = Integer.parseInt(str.substring(i, i + 1));
            sum += digit * weight[weight.length - str.length() + i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    /**
     * separate full name to first name
     *
     * @param fullName
     * @return string first name
     */
    public static String getFirstName(String fullName) {
        if (!fullName.contains(" ")) {
            return fullName;
        }

        int start = fullName.indexOf(' ');

        String firstName = "";
        if (start >= 0) {
            firstName = fullName.substring(0, start);
            return firstName;
        }

        return firstName;
    }

    /**
     * separate full name to last name
     *
     * @param fullName
     * @return string last name
     */
    public static String getLastName(String fullName) {

        int start = fullName.indexOf(' ');
        int end = fullName.length();

        String lastName = " ";

        if (start > 0) {
            if (end > start) {
                lastName = fullName.substring(start + 1, end);
                return lastName;
            }
        }

        return lastName;
    }

    public static String formatCPF(String cpf) {
        if (cpf != null) {
            if (!(cpf.length() == 11)) {
                return cpf;
            }

            String blockOne = cpf.substring(0, 3);
            String blockTwo = cpf.substring(3, 6);
            String blockThree = cpf.substring(6, 9);
            String blockFour = cpf.substring(9, 11);

            return blockOne + "." + blockTwo + "." + blockThree + "-" + blockFour;
        }
        return "";
    }

    public static String completeCNPJ(String cnpjString) {

        if (cnpjString != null) {
            if (cnpjString.length() < 14) {
                return String.format("%014d",Long.valueOf(cnpjString).longValue());
            }
        }
        return cnpjString;
    }

    public static String formatCNPJ(String cnpj) {
        if (cnpj != null) {
            if (!(cnpj.length() == 14)) {
                return cnpj;
            }

            String blockOne = cnpj.substring(0, 2);
            String blockTwo = cnpj.substring(2, 5);
            String blockThree = cnpj.substring(5, 8);
            String blockFour = cnpj.substring(8, 12);
            String blockFive = cnpj.substring(12, 14);

            return blockOne + "." + blockTwo + "." + blockThree + "/" + blockFour + "-" + blockFive;
        }
        return "";
    }

    public static boolean isSequence(String string) {
        return string.matches("\\b(\\d)\\1+\\b");
    }

    public static String getValue(String value) {
        if (value != null) {
            return value;
        }
        return "";
    }

    public static String getValue(Integer value) {
        if (value != null) {
            return String.valueOf(value);
        }
        return "";
    }

    public static String getValue(Long value) {
        if (value != null) {
            return String.valueOf(value);
        }
        return "";
    }

    public static String maskAccount(String account) {
        String mask = "";

        if (StringUtils.isBlank(account) || (account.length() <= 2)) {
            return mask;
        }

        mask = account.substring(0, (account.length() - 1));
        mask = mask + "-";
        mask = mask + account.substring((account.length() - 1), account.length());
        return mask;
    }

    public static String getNameBank(Integer code, String name) {
        String bankName = StringUtils.isNotEmpty(name) ? name : "";
        return code + " - " + bankName;
    }

    public static String maskPerMonth(String info) {
        if (StringUtils.isBlank(info)) {
            return "";
        }

        return info + " a.m.";
    }

    public static String maskPerYear(String info) {
        if (StringUtils.isBlank(info)) {
            return "";
        }

        return info + " a.a";
    }

    public static String formatStringFirstPositionUpperCase(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String capUppercaseFirstChar(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }

        if (name.length() == 1) {
            return name.toUpperCase();
        }

        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static String getTwoInitial(String name) {

        if (name == null) {
            return "";
        }

        if (StringUtils.isBlank(name)) return "";

        if (name.length() < 2) return name;

        String[] split = name.split(" ");

        String temp = "";

        if (split.length == 1) {
            return split[0].substring(0, 2);
        }

        for (String a : split) {
            if (StringUtils.isNotBlank(a)) {
                temp = temp + a.substring(0, 1);
            }

            if (temp.length() == 2) {
                return temp;
            }
        }

        return temp;

    }


    public static String maskAgency(String agency) {
        String mask = "";
        if (StringUtils.isBlank(agency) || (agency.length() <= 2)) {
            return mask;
        }
        mask = agency.substring(0, (agency.length() - 1));
        mask = mask + "-";
        mask = mask + agency.substring((agency.length() - 1), agency.length());
        return mask;
    }

    public static String maskHideInfo(String info) {
        return info.replaceAll("(?s).", "*");
    }

    public static String maskCardNumber(String cardNumber) {
        String endGroup = cardNumber.substring(cardNumber.length() - 4);
        String number = cardNumber.replaceAll("\\d", "*")
                .substring(0, cardNumber.length() - 4)
                .concat(endGroup)
                .replaceAll("-", " ");
        return number.substring(0, 4) + " " + number.substring(4, 8) + " " +
                number.substring(8, 12) + " " + number.substring(12);
    }

    public static String getAreaCode(String phoneString) {
        return phoneString.subSequence(1, 3).toString();
    }


    public static boolean isBetweenLimits(String text, int min, int max) {
        if (min > max)
            throw new InvalidParameterException("MIN can't be hither than MAX");

        return (text.length() >= min && text.length() <= max);
    }
}
