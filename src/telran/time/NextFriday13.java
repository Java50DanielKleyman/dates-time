package telran.time;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		if (!temporal.isSupported(ChronoUnit.YEARS)) {
			throw new UnsupportedTemporalTypeException("must support years");
		}

		if (temporal.get(ChronoField.DAY_OF_MONTH) == 13) {
			return temporal = monthPlus(temporal);
		}
		;
		do {
			temporal = temporal.plus(1, ChronoUnit.DAYS);
		} while (temporal.get(ChronoField.DAY_OF_MONTH) != 13);
		if (temporal.get(ChronoField.DAY_OF_WEEK) == 5) {
			return temporal;
		} else {
			temporal = monthPlus(temporal);
		}

		return temporal;
	}

	private Temporal monthPlus(Temporal temporal) {
		do {
			temporal = temporal.plus(1, ChronoUnit.MONTHS);
		} while (temporal.get(ChronoField.DAY_OF_WEEK) != 5);
		return temporal;
	}
}
