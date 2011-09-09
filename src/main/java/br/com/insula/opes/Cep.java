/*
    This file is part of Opes.

    Opes is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Opes is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Opes.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.insula.opes;

import static java.util.FormattableFlags.ALTERNATE;
import static java.util.FormattableFlags.LEFT_JUSTIFY;

import java.io.Serializable;
import java.util.Formattable;
import java.util.Formatter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import br.com.insula.opes.util.Assert;

public class Cep implements Serializable, Formattable {

	private static final long serialVersionUID = 1L;

	private final String numero;

	private Cep(String numero) {
		this.numero = numero;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Cep) {
			Cep other = (Cep) obj;
			return new EqualsBuilder().append(this.numero, other.numero).isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.numero).toHashCode();
	}

	@Override
	public String toString() {
		return this.numero;
	}

	@Override
	public void formatTo(Formatter formatter, int flags, int width, int precision) {
		StringBuilder sb = new StringBuilder();
		boolean alternate = (flags & ALTERNATE) == ALTERNATE;
		if (alternate) {
			sb.append(numero);
		}
		else {
			sb.append(numero.substring(0, 5));
			sb.append("-");
			sb.append(numero.substring(5));
		}
		int length = sb.length();
		if (length < width) {
			for (int i = 0; i < width - length; i++) {
				if (alternate) {
					sb.insert(0, '0');
				}
				else {
					boolean leftJustified = (flags & LEFT_JUSTIFY) == LEFT_JUSTIFY;
					if (leftJustified) {
						sb.append(' ');
					}
					else {
						sb.insert(0, ' ');
					}
				}
			}
		}
		formatter.format(sb.toString());
	}

	public static Cep fromString(String s) {
		Assert.notNull(s);
		String digits = s.replaceAll("\\D", "");
		Assert.matches("\\d{8}", digits);

		return new Cep(digits);
	}

}
