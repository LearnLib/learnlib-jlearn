/* Copyright (C) 2015 TU Dortmund
 * This file is part of LearnLib, http://www.learnlib.de/.
 *
 * LearnLib is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 3.0 as published by the Free Software Foundation.
 *
 * LearnLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with LearnLib; if not, see
 * <http://www.gnu.de/documents/lgpl.en.html>.
 */
package de.learnlib.jlearn;

import net.automatalib.words.Word;
import de.learnlib.api.MembershipOracle;
import de.ls5.jlearn.interfaces.Symbol;
import de.ls5.jlearn.shared.SymbolImpl;
import de.ls5.jlearn.shared.WordImpl;


public class MealyOracleWrapper<I,O> extends OracleWrapper<I,Word<O>> {
	
	private static final long serialVersionUID = 1L;
	
	public MealyOracleWrapper(MembershipOracle<I, Word<O>> oracle) {
		super(oracle);
	}

	@Override
	protected de.ls5.jlearn.interfaces.Word decodeOutput(de.ls5.jlearn.interfaces.Word jlearnWord,
			Word<I> word, Word<O> output) {
		Symbol[] syms = new Symbol[output.length()];
		int i = 0;
		for (O outSym : output) {
			syms[i++] = new SymbolImpl(outSym);
		}
		return new WordImpl(syms);
	}

	
}
