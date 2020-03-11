
/* README of Logger.java
 * 
 * -- Logger Setup
 * 
 * Logger.setLogLevel(1); - INFO
 * 
 * Logger.setLogLevel(2); - WARNING
 * 
 * Logger.setLogLevel(3); - WARNING & INFO
 * 
 * Logger.setLogLevel(4); - ALERT
 * 
 * Logger.setLogLevel(5); - ALERT & INFO
 * 
 * Logger.setLogLevel(6); - ALERT & WARNING
 * 
 * Logger.setLogLevel(7); - ALERT & WARNING & INFO
 * 
 * 
 * -- Examples:
 * 
 * if (Logger.need(Logger.LEVEL_INFO)) Logger.getInstance().infoArrayInt(array_integer);
 * 
 * if (Logger.need(Logger.LEVEL_INFO)) Logger.getInstance().infoArrayStr(array_string);
 * 
 * if (Logger.need(Logger.LEVEL_INFO)) Logger.getInstance().infoBoolean(boolean);
 * 
 * if (Logger.need(Logger.LEVEL_INFO)) Logger.getInstance().infoChar(char);
 * 
 * if (Logger.need(Logger.LEVEL_INFO)) Logger.getInstance().infoDouble(double);
 * 
 * if (Logger.need(Logger.LEVEL_INFO)) Logger.getInstance().infoInt(integer);
 * 
 * if (Logger.need(Logger.LEVEL_INFO)) Logger.getInstance().infoObject(object);
 * 
 * if (Logger.need(Logger.LEVEL_INFO)) Logger.getInstance().infoStr("string");
 * 
 * if (Logger.need(Logger.LEVEL_INFO)) Logger.getInstance().infoText("TEXT");
 * 
 * if (Logger.need(Logger.LEVEL_WARNING)) Logger.getInstance().warning("TEXT");
 * 
 * if (Logger.need(Logger.LEVEL_ALERT)) Logger.getInstance().alert("TEXT");
 * 
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 
 * @author Gábor
 *
 */

public class Logger {

	final static int LEVEL_INFO = 1;
	final static int LEVEL_WARNING = 2;
	final static int LEVEL_ALERT = 4;
	final static Path DIR = Paths.get("log");

	private static Logger instance = null;
	private static int enabled_level = 0;

	public static Logger getInstance() {

		if (instance == null) {
			instance = new Logger();
			setFilePath();
		}

		return instance;
	}

	public static void setLogLevel(int level) {

		int min_level = 0;
		int max_level = LEVEL_INFO | LEVEL_WARNING | LEVEL_ALERT;

		if (level > min_level && level <= max_level) {
			enabled_level = level;
		}
	}

	public static boolean need(int level) {

		return (level & enabled_level) != 0 ? true : false;
	}

	private static String timeStamp() {

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	private static String setFileName() {

		return new SimpleDateFormat("yyyy-MM-dd").format(new Date()).concat("_logfile.log");
	}

	private static String setFilePath() {

		if (!Files.exists(DIR)) {
			try {
				Files.createDirectories(DIR);
			} catch (IOException e) {
				System.out.println(
						"Ooops, hiba történt! Nem sikerült létrehozni a(z) " + DIR.toString() + " nevû könyvtárat...");
				e.printStackTrace();
			}
		}

		return DIR + File.separator + setFileName();
	}

	private static boolean isArrayOfInt(Integer[] i) {

		return (i.getClass().isArray());
	}

	private static boolean isArrayOfStr(String[] str) {

		return (str.getClass().isArray());
	}

	private static boolean isBoolean(Boolean b) {

		return (b == true) ? true : ((b == false) ? false : null);
	}

	private static boolean isChar(char ch) {

		return Character.isLetter(ch);
	}

	private static boolean isDouble(Double d) {

		return (d == Math.floor(d)) && !Double.isInfinite(d);
	}

	private static boolean isInteger(Integer i) {

		return (i instanceof Integer);
	}

	private static boolean isObject(Object obj) {

		return (obj instanceof Object);
	}

	private static boolean isString(String str) {

		return (str instanceof String);
	}

	private static void writerCheck() {

		if (!Files.isWritable(Paths.get(setFilePath()))) {
			System.out.println("Ooops, hiba történt! Változtasd meg a(z)" + setFilePath().toString()
					+ " állomány hozzáférési jogait.");
			System.exit(0);
		}
	}

	private static String messageComposer(String log_type, String result) {

		return timeStamp() + " - " + log_type + " - " + result + "\n";
	}

	private static void putContent(String content) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(setFilePath(), true));
		writer.append(content);

		writer.close();
	}

	private static void logWriterFail(String result, String log_type) throws IOException {

		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	private static void logWriterArrayInt(Integer[] array, String log_type) throws IOException {

		String array_to_str = Arrays.toString(array);
		String result = "Integer tömb tartalma: " + array_to_str;
		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	private static void logWriterArrayStr(String[] array, String log_type) throws IOException {

		String array_to_str = Arrays.toString(array);
		String result = "String tömb tartalma: " + array_to_str;
		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	private static void logWriterBoolean(Boolean b, String log_type) throws IOException {

		String boolean_to_str = b.toString();
		String result = "Boolean tartalma: " + boolean_to_str;
		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	private static void logWriterChar(char ch, String log_type) throws IOException {

		String result = "Char tartalma: " + ch;
		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	private static void logWriterDouble(Double d, String log_type) throws IOException {

		String double_to_str = d.toString();
		String result = "Double tartalma: " + double_to_str;
		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	private static void logWriterInt(Integer i, String log_type) throws IOException {

		String int_to_str = i.toString();
		String result = "Integer tartalma: " + int_to_str;
		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	private static void logWriterObj(Object obj, String log_type) throws IOException {

		String obj_to_str = obj.toString();
		String result = "Objektum tartalma: " + obj_to_str;
		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	private static void logWriterStr(String str, String log_type) throws IOException {

		String result = "String tartalma: " + str;
		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	private static void logWriterText(String result, String log_type) throws IOException {

		putContent(messageComposer(log_type, result));
		writerCheck();
	}

	public void infoArrayInt(Integer[] array) throws IOException {

		if (!isArrayOfInt(array)) {
			logWriterFail("A paraméter nem Integer tömb típus...", "error_array MESSAGE");
		} else {
			logWriterArrayInt(array, "INTEGER_ARRAY-INFO");
		}
	}

	public void infoArrayStr(String[] array) throws IOException {

		if (!isArrayOfStr(array)) {
			logWriterFail("A paraméter nem String tömb típus...", "error_array MESSAGE");
		} else {
			logWriterArrayStr(array, "STRING_ARRAY-INFO");
		}
	}

	public void infoBoolean(Boolean b) throws IOException {

		if (!isBoolean(b)) {
			logWriterFail("A paraméter nem Boolean típus...", "error_boolean MESSAGE");
		} else {
			logWriterBoolean(b, "BOOLEAN-INFO");
		}
	}

	public void infoChar(char ch) throws IOException {

		if (!isChar(ch)) {
			logWriterFail("A paraméter nem 'char' típus...", "error_char MESSAGE");
		} else {
			logWriterChar(ch, "CHAR-INFO");
		}
	}

	public void infoDouble(Double d) throws IOException {

		if (!isDouble(d)) {
			logWriterFail("A paraméter nem Double típus...", "error_double MESSAGE");
		} else {
			logWriterDouble(d, "DOUBLE-INFO");
		}
	}

	public void infoInt(Integer i) throws IOException {

		if (!isInteger(i)) {
			logWriterFail("A paraméter nem Integer típus...", "error_integer MESSAGE");
		} else {
			logWriterInt(i, "INTEGER-INFO");
		}
	}

	public void infoObject(Object obj) throws IOException {

		if (!isObject(obj)) {
			logWriterFail("A paraméter nem Objektum típus...", "error_object MESSAGE");
		} else {
			logWriterObj(obj, "OBJECT-INFO");
		}
	}

	public void infoStr(String str) throws IOException {

		if (!isString(str)) {
			logWriterFail("A paraméter nem String típus...", "error_string MESSAGE");
		} else {
			logWriterStr(str, "STRING-INFO");
		}
	}

	public void infoText(String text) throws IOException {

		if (!isString(text)) {
			logWriterFail("A paraméter nem szöveg...", "error_text MESSAGE");
		} else {
			logWriterText(text, "TEXT-INFO");
		}
	}

	public void warning(String log) throws IOException {

		if (!isString(log)) {
			logWriterFail("A paraméter nem szöveg...", "error_warning MESSAGE");
		} else {
			logWriterText(log, "WARNING");
		}
	}

	public void alert(String log) throws IOException {

		if (!isString(log)) {
			logWriterFail("A paraméter nem szöveg...", "error_alert MESSAGE");
		} else {
			logWriterText(log, "ALERT");
		}
	}
}

//	private Byte inRangeOfByte(Byte b) {
//
//		return (b <= Byte.MAX_VALUE) ? b : ((b >= Byte.MIN_VALUE) ? b : null);
//	}
//
//	private Double inRangeOfDouble(Double d) {
//
//		return (d <= Double.MAX_VALUE) ? d : ((d >= Double.MIN_VALUE) ? d : null);
//	}
//
//	private Float inRangeOfFloat(Float f) {
//
//		return (f <= Float.MAX_VALUE) ? f : ((f >= Float.MIN_VALUE) ? f : null);
//	}
//
//	private Integer inRangeOfInt(Integer i) {
//
//		return (i <= Integer.MAX_VALUE) ? i : ((i >= Integer.MIN_VALUE) ? i : null);
//	}
//
//	private Long inRangeOfLong(Long l) {
//
//		return (l <= Long.MAX_VALUE) ? l : ((l >= Long.MIN_VALUE) ? l : null);
//	}
//
//	private Short inRangeOfShort(Short s) {
//
//		return (s <= Short.MAX_VALUE) ? s : ((s >= Short.MIN_VALUE) ? s : null);
//	}