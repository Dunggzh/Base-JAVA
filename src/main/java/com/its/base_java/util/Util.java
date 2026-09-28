package com.its.base_java.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.its.base_java.dto.request.DateRange;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Util {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String PHONE_PATTERN = "^0\\d{8,10}$";
    private static final Pattern P_PREFIX_TTDV_FULL = Pattern.compile(
            "(?i)([A-Za-z0-9][A-Za-z0-9.-]*)\\s+TTDV\\s+([A-Za-z0-9]{8})\\b"
    );

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_PATTERN);
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private static final Pattern[] inputRegexes = new Pattern[4];

    static {
        inputRegexes[0] = Pattern.compile(".*[A-Z].*");
        inputRegexes[1] = Pattern.compile(".*[a-z].*");
        inputRegexes[2] = Pattern.compile(".*\\d.*");
        inputRegexes[3] = Pattern.compile(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
    }

    public static Map<String, String> getUrlInfo(String url) {
        Map<String, String> map = new HashMap<>();
        try {
            String query = new URL(url).getQuery();
            String[] params = query.split("&");
            for (String param : params) {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
            }
            return map;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String genUUID() {
        return UUID.randomUUID().toString();
    }

    public static String formatNumber(double d) {
        DecimalFormat formatter = new DecimalFormat("###,###,###,###.###");
        try {
            return formatter.format(d);
        } catch (Exception e) {
            return "0";
        }
    }

    public static String convertDateToString(final Date date, String pattern) {
        return convertDateToString(date, pattern, null);
    }

    public static Date getTopDay(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return calendar.getTime();
    }

    public static Date getBotDay(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return calendar.getTime();
    }

    public static String convertDateToString(final Date date, String pattern, String timezoneId) {
        if (date == null) {
            return null;
        }
        if (timezoneId == null) {
            timezoneId = "GMT+7";
        } else {
            timezoneId = "GMT" + timezoneId;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(TimeZone.getTimeZone(timezoneId));
        return df.format(date);
    }

    public static Date convertStringToDate(String dateString, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isMatchingRegex(String input) {
        boolean inputMatches = true;
        for (Pattern inputRegex : inputRegexes) {
            if (!inputRegex.matcher(input).matches()) {
                inputMatches = false;
            }
        }
        return inputMatches;
    }

    public static long getDuration(Date date1, Date date2, TimeUnit unit) {
        long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
        return unit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    //Bỏ dấu tiếng việt
    public static String removeCharacterVn(String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        final Pattern NONLATIN = Pattern.compile("[^\\w-]");
        final Pattern WHITESPACE = Pattern.compile("[\\s]");
        String nowhitespace = WHITESPACE.matcher(input.replaceAll("đ", "d")).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized)
                .replaceAll("")
                .replaceAll("\"", "");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String objectToString(Object data) {
        if (data == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T stringToObject(Class<? extends T> type, String data) {
        if (data == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        try {
            return mapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T stringToArray(TypeReference<T> type, String data) {
        if (data == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        try {
            return mapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }

    public static double rounding(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(value));
    }

    public static String randomString(int count, String characters) {
        return RandomStringUtils.random(count, characters);
    }

    public static String randomString(int count) {
        return randomString(count, ALPHA_NUMERIC_STRING);
    }

    public static DateRange getAdjustedDateRange(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();

        if (startDate == null && endDate == null) {
            endDate = new Date();
            calendar.setTime(endDate);
            calendar.add(Calendar.MONTH, -1);
            startDate = calendar.getTime();
        } else if (endDate == null) {
            endDate = startDate;
            calendar.setTime(endDate);
            calendar.add(Calendar.MONTH, 1);
        } else if (startDate == null) {
            calendar.setTime(endDate);
            calendar.add(Calendar.MONTH, -1);
            startDate = calendar.getTime();
        }

        return new DateRange(startDate, endDate);
    }

    public static void createCell(Row row, String value, int index, CellStyle style) {
        Cell cell;
        try {
            double v = Double.parseDouble(value);
            cell = row.createCell(index, CellType.NUMERIC);
            cell.setCellValue(v);
        } catch (Exception e) {
            cell = row.createCell(index, CellType.STRING);
            cell.setCellValue(value);
        }
        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    public static CellStyle createStyleForTitle(SXSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static String extractFullPhraseRaw(String raw) {
        if (raw == null) return null;
        Matcher m = P_PREFIX_TTDV_FULL.matcher(raw);
        return m.find() ? m.group(0) : null;
    }

    public static String safeToJson(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (Exception e) {
//            log.warn("Serialize payload failed: {}", e.getMessage());
            return null;
        }
    }
}
