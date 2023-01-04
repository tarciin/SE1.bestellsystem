package system.impl;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import system.TablePrinter;

/**
 * Interface of configurable TablePrinter.
 * 
 * TablePrinter allows column-based output of rows into a table. Column
 * configuration is achieved by a builder during instantiation where columns can
 * be defined specified by width, borders and alignment.
 * 
 * <pre>
 * Example: configuration of orderTable with builder:
 * TablePrinter orderTable = createTablePrinter(sb, builder {@code -> } builder
 *   // builder defining table columns with width and alignment (R: right aligned)
 *   .column("|",  11)	// "Bestell-ID"
 *   .column("|",  28)	// "Bestellungen", descriptions
 *   .column("R",   7)	// "MwSt", VAT tax for each item
 *   .column(" ",   1)	// " ", marker (*) for reduced VAT tax rate
 *   .column("R",  10)	// "Preis", price for each item
 *   .column("|R", 10)	// "MwSt", VAT tax for whole order
 *   .column(" |R",12)	// "Gesamt", price for whole order
 * );
 * </pre>
 * 
 * {@code orderTable} can be used to fill in columns.
 * 
 * <pre>
 * Example: orderTable with two orders:
 * +----------+---------------------------------------------+--------------------+
 * |Bestell-ID|Bestellungen                  MwSt      Preis|     MwSt     Gesamt|
 * +----------+---------------------------------------------+--------------------+
 * |8592356245|Eric's Bestellung:                           |                    |
 * |          | - 4 Teller, 4x 6.49          4.14     25.96�|                    |
 * |          | - 8 Becher, 8x 1.49          1.90     11.92�|                    |
 * |          | - 1 Buch "OOP"               5.23*    79.95�|                    |
 * |          | - 4 Tasse, 4x 2.99           1.91     11.96�|   13.18�    129.79�|
 * +----------+---------------------------------------------+--------------------+
 * |3563561357|Anne's Bestellung:                           |                    |
 * |          | - 2 Teller, 2x 6.49          2.07     12.98�|                    |
 * |          | - 2 Tasse, 2x 2.99           0.95      5.98�|    3.02�     18.96�|
 * +----------+---------------------------------------------+--------------------+
 *  {@code >>>>>>>>>>}                                       Gesamt:|   16.20�    148.75�|
 *                                                          +====================+
 * </pre>
 * 
 * @version <code style=
 *          color:green>{@value application.package_info#Version}</code>
 * @author <code style=
 *         color:blue>{@value application.package_info#Author}</code>
 */

class TablePrinterImpl implements TablePrinter {
	final List<Column> columns = new ArrayList<Column>();
	final String rowSpec; // default row spec: "| | | |"
	final String lineSpec; // default line spec: "+-+-+-+"
	final StringBuffer sb;
	final static char SPACE = 0x20;
	final static char NUL = 0x00;
	final static char L = 'L';
	final static char R = 'R';

	enum ALIGN {
		L, R
	}; // left, right column alignment

	class Column {
		final boolean lb, rb; // has left, right column border
		final char fill; // fill character, default is SPACE
		final ALIGN align; // column alignment
		final int width; // column width
		//

		Column(Column prev, String spec, int width) {
			int len = spec != null ? spec.length() : -1;
			boolean prevColHasRightBorder = prev != null && prev.rb;
			this.lb = !prevColHasRightBorder && len > 0 && spec.charAt(0) == '|';
			int fi = len > 0 && spec.charAt(0) == '|' ? 1 : 0;
			char cf = len > fi ? spec.charAt(fi) : NUL;
			this.fill = cf < SPACE || cf == L || cf == R || cf == '|' ? SPACE : cf;
			char cl = len > 0 ? spec.charAt(len - 1) : NUL;
			this.align = cl == R ? ALIGN.R : ALIGN.L; // left-aligned column default
			this.rb = cl != L && cl != R ? len > 1 && cl == '|' : (len > 2 ? spec.charAt(len - 2) : NUL) == '|';
			this.width = width > 0 ? width - (lb ? 1 : 0) - (rb ? 1 : 0) : 0;
		}
	}

	TablePrinterImpl(StringBuffer sb, Consumer<Builder> builder) {
		this.sb = sb == null ? new StringBuffer() : sb;
		builder.accept(new Builder() {
			@Override
			public Builder column(String spec, int width) {
				Column prev = columns.size() > 0 ? columns.get(columns.size() - 1) : null;
				columns.add(new Column(prev, spec, width));
				return this;
			}
		});
		this.rowSpec = "| ".repeat(columns.size()) + "|";
		this.lineSpec = "+-".repeat(columns.size()) + "+";
	}

	/**
	 * Insert horizontal line into table.
	 * 
	 * @return chainable self-reference.
	 */
	@Override
	public TablePrinter line() {
		return render(lineSpec);
	}

	/**
	 * Insert line spec pattern " |.|+| |" with "|" vertical bar and fill
	 * characters.
	 * 
	 * <pre>
	 * |.....|+++++|       |
	 * </pre>
	 * 
	 * @param spec spec String.
	 * @return chainable self-reference.
	 */
	@Override
	public TablePrinter line(String spec) {
		return render(spec);
	}

	/**
	 * Insert content line with arg[i] for column[i].
	 * 
	 * @param args content line as varargs with arg[i] for column[i].
	 * @return chainable self-reference.
	 */
	@Override
	public TablePrinter row(String... args) {
		int lena = args != null ? args.length : -1;
		String arg0 = lena > 0 ? args[0] : "";
		boolean hasSpec = arg0.startsWith("@"); // shift args[] << 1
		String[] args2 = hasSpec ? Arrays.copyOfRange(args, 1, lena) : args;
		return render(hasSpec ? arg0 : rowSpec, args2);
	}

	/**
	 * Output table to PrintStream.
	 * 
	 * @param ps output destination.
	 */
	@Override
	public void print(PrintStream ps) {
		ps.print(sb);
	}

	private TablePrinter render(String spec, String... args) {
		int lens = spec != null ? spec.length() : -1;
		int lena = args != null ? args.length : -1;
		spec = lens > 0 && spec.startsWith("@") ? spec.substring(1) : spec;
		// IntStream.range(0, columns.size()).forEach( i -> {
		for (int i = 0; i < columns.size() && i < lens / 2; i++) {
			Column col = columns.get(i);
			int j = i * 2;
			if (col.lb && j < lens) {
				sb.append(String.valueOf(spec.charAt(j)));
			}
			if (++j < lens || col.fill != SPACE) {
				String text = i < lena && args[i] != null ? args[i] : "";
				int d = col.width - text.length();
				if (d > 0) { // fill to width from left or right
					char fc = spec == rowSpec && col.fill != SPACE ? col.fill : NUL;
					fc = fc == NUL && i < lens ? spec.charAt(j) : fc;
					String fill = String.valueOf(fc).repeat(d);
					boolean left = col.align == ALIGN.L;
					sb.append(left ? text : fill).append(left ? fill : text);
				}
				if (d < 0) { // cut to width
					sb.append(col.align == ALIGN.R ? text.substring(-d)// cut from left
							: text.substring(0, text.length() + d)); // cut from right
				}
				if (d == 0) {
					sb.append(text);
				}
			}
			if (col.rb && ++j < lens) {
				sb.append(String.valueOf(spec.charAt(j)));
			}
		}
		;
		sb.append("\n");
		return this;
	}
}
