package telran.time.applications;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PrintCalendar {
	private static final int TITLE_OFFSET = 10;
	private static final int WEEK_DAYS_OFFSET = 2;
	private static final int COLUMN_WIDTH = 4;
	private static DayOfWeek[] weekDays = DayOfWeek.values();
	private static Locale LOCALE = Locale.getDefault();

	public static void main(String[] args) {
		try {
			RecordArguments recordArguments = getRecordArguments(args);
			printCalendar(recordArguments);

		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void printCalendar(RecordArguments recordArguments) {
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(recordArguments.firstDay());
		printDays(recordArguments.month(), recordArguments.year(), recordArguments.firstDay());

	}

	private static void printDays(int month, int year, DayOfWeek dayOfWeek) {
		int firstDayOfWeek = dayOfWeek.getValue();
		int nDays = getMonthDays(month, year);
		int currentWeekDay = getFirstMonthWeekDay(month, year);
		int correctionValue = currentWeekDay - firstDayOfWeek - 1;
		if (correctionValue < 0) {
			correctionValue += 7;
		}
		currentWeekDay += correctionValue;
		System.out.printf("%s", " ".repeat(getFirstColumnOffset(currentWeekDay)));
		for (int day = 1; day <= nDays; day++) {
			System.out.printf("%4d", day);
			if (currentWeekDay == 7) {
				currentWeekDay = 0;
				System.out.println();
			}
			currentWeekDay++;
		}

	}

	private static int getFirstColumnOffset(int currentWeekDay) {

		return COLUMN_WIDTH * (currentWeekDay - 1);
	}

	private static int getFirstMonthWeekDay(int month, int year) {
		LocalDate ld = LocalDate.of(year, month, 1);
		return ld.get(ChronoField.DAY_OF_WEEK);
	}

	private static int getMonthDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}

	private static void printWeekDays(DayOfWeek firstDayOfWeek) {
		DayOfWeek[] newWeekDays = newWeekDaysOrder(firstDayOfWeek);
		System.out.printf("%s", " ".repeat(WEEK_DAYS_OFFSET));
		for (DayOfWeek dayWeek : newWeekDays) {
			System.out.printf("%s ", dayWeek.getDisplayName(TextStyle.SHORT, LOCALE));
		}
		System.out.println();

	}

	private static DayOfWeek[] newWeekDaysOrder(DayOfWeek firstDayOfWeek) {
		if (weekDays[0] == firstDayOfWeek) {
			return weekDays;
		}
		DayOfWeek[] newWeekDays = new DayOfWeek[weekDays.length];
		boolean reverseOrder = false;
		int index = 0;
		for (int i = 0; i < weekDays.length; i++) {
			if (weekDays[i] == firstDayOfWeek) {
				reverseOrder = true;
			}
			if (reverseOrder) {
				newWeekDays[index++] = weekDays[i];
			}
		}
		for (int i = 0; index < newWeekDays.length; i++) {
			newWeekDays[index++] = weekDays[i];
		}
		return newWeekDays;
	}

	private static void printTitle(int month, int year) {
		Month monthEn = Month.of(month);
		System.out.printf("%s%s %d\n", " ".repeat(TITLE_OFFSET), monthEn.getDisplayName(TextStyle.FULL, LOCALE), year);

	}

	private static RecordArguments getRecordArguments(String[] args) throws Exception {

		int month = getMonthArg(args);
		int year = getYearArg(args);
		DayOfWeek dayOfWeek = getFirstDayOfWeek(args);
		return new RecordArguments(month, year, dayOfWeek);
	}

	private static DayOfWeek getFirstDayOfWeek(String[] args) {
		if (args.length < 3) {
			return DayOfWeek.MONDAY;
		}
		DayOfWeek dayOfWeek = null;
		String firstDayOfWeek = args[2].toUpperCase();
		try {
			dayOfWeek = DayOfWeek.valueOf(firstDayOfWeek);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("argument not a day of the week: " + args[2]);
		}
		return dayOfWeek;
	}

	private static int getYearArg(String[] args) throws Exception {
		int yearRes = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				yearRes = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				throw new Exception("year must be a number");
			}
		}
		return yearRes;
	}

	private static int getMonthArg(String[] args) throws Exception {
		int monthRes = LocalDate.now().getMonthValue();
		if (args.length > 0) {
			try {
				monthRes = Integer.parseInt(args[0]);
				if (monthRes < 1) {
					throw new Exception("Month value must not be less than 1");
				}
				if (monthRes > 12) {
					throw new Exception("Month value must not be greater than 12");
				}
			} catch (NumberFormatException e) {
				throw new Exception("Month value must be a number");
			}
		}
		return monthRes;
	}

}