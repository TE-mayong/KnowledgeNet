package org.kmnet.com.fw.common.mail;

import java.security.SecureRandom;

import org.apache.commons.lang.StringUtils;



/**
 * MailSender利用のUtility.
 * 
 * @author Li.Yufei
 * @version 1.0 2015/05/13 新規作成
 */
public final class MailUtils {

	//private static final String SJIS = "—〜‖−¢£¬";
	private static final String SJIS = "\u2014\u301C\u2016\u2212\242\243\254";
	
	//private static final String MS932 = "―～∥－￠￡￢";
	private static final String MS932 = "\u2015\uFF5E\u2225\uFF0D\uFFE0\uFFE1\uFFE2";

	/**
	 * Random object used by random method. This has to be not local to the
	 * random method so as to not return the same value in the same millisecond.
	 */
	private static final SecureRandom RANDOM = new SecureRandom();

	/**
	 * Constructs a new <code>EmailException</code> with no detail message.
	 */
	private MailUtils() {

		super();
	}

	static String convMS932ToSjis(String target) {

		return StringUtils.replaceChars(target, MS932, SJIS);
	}

	/**
	 * Creates a random string whose length is the number of characters
	 * specified.
	 *
	 * <p>
	 * Characters will be chosen from the set of alphabetic characters.
	 * </p>
	 *
	 * @return the random string
	 *
	 * @since Commons Lang v2.1, svn 201930
	 */
	public static String randomAlphabetic() {

		return random(10, 0, 0, true, false, null, RANDOM);
	}

	/**
	 * Creates a random string based on a variety of options, using supplied
	 * source of randomness.
	 *
	 * <p>
	 * If start and end are both <code>0</code>, start and end are set to
	 * <code>' '</code> and <code>'z'</code>, the ASCII printable characters,
	 * will be used, unless letters and numbers are both <code>false</code>, in
	 * which case, start and end are set to <code>0</code> and
	 * <code>Integer.MAX_VALUE</code>.
	 * </p>
	 *
	 * <p>
	 * If set is not <code>null</code>, characters between start and end are
	 * chosen.
	 * </p>
	 *
	 * <p>
	 * This method accepts a user-supplied {@link Random} instance to use as a
	 * source of randomness. By seeding a single {@link Random} instance with a
	 * fixed seed and using it for each call, the same random sequence of
	 * strings can be generated repeatedly and predictably.
	 * </p>
	 *
	 * @param count
	 *            the length of random string to create
	 * @param start
	 *            the position in set of chars to start at
	 * @param end
	 *            the position in set of chars to end before
	 * @param letters
	 *            only allow letters?
	 * @param numbers
	 *            only allow numbers?
	 * @param chars
	 *            the set of chars to choose randoms from. If <code>null</code>,
	 *            then it will use the set of all chars.
	 * @param random
	 *            a source of randomness.
	 *
	 * @return the random string
	 *
	 * @throws IllegalArgumentException
	 *             if <code>count</code> &lt; 0.
	 *
	 * @since Commons Lang v2.1, svn 201930
	 */
	private static String random(int count, int start, int end, final boolean letters, final boolean numbers,
			final char[] chars, final SecureRandom random) {

		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}

		if ((start == 0) && (end == 0)) {
			end = 'z' + 1;
			start = ' ';

			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}

		final StringBuffer buffer = new StringBuffer();
		final int gap = end - start;

		while (count-- != 0) {
			char ch;

			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}

			if ((letters && numbers && Character.isLetterOrDigit(ch)) || (letters && Character.isLetter(ch))
					|| (numbers && Character.isDigit(ch)) || (!letters && !numbers)) {
				buffer.append(ch);
			} else {
				count++;
			}
		}

		return buffer.toString();
	}
}
