package gr01

import java.text.SimpleDateFormat

/**
 * The <i>Date</i> tag library supplies date formatting routines.
 * 
 * @author Jeffrey Risberg
 * @since 09/27/10
 */
class DateTagLib {
	static namespace = "h"

	static dateF = new SimpleDateFormat("EEEE, MMM, d, yyyy")

	def formattedDate = { attrs ->
		def date = attrs.date

		out << dateF.format(date)
	}
}
