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

import net.automatalib.words.Alphabet;
import net.automatalib.words.Word;
import net.automatalib.words.WordBuilder;
import de.ls5.jlearn.interfaces.Symbol;
import de.ls5.jlearn.shared.AlphabetImpl;
import de.ls5.jlearn.shared.SymbolImpl;
import de.ls5.jlearn.shared.WordImpl;

abstract class JLearnGlue {
	
	@SuppressWarnings("unchecked")
	public static <I> Word<I> fromJLearnWord(de.ls5.jlearn.interfaces.Word jlearnWord) {
		Symbol[] jlearnSyms = jlearnWord.getSymbolArray();
		WordBuilder<I> wb = new WordBuilder<>(jlearnSyms.length);
		
		for (Symbol jlearnSym : jlearnSyms) {
			wb.add((I)jlearnSym.getUserObject());
		}
		
		return wb.toWord();
	}
	
	public static de.ls5.jlearn.interfaces.Word toJLearnWord(Word<?> word) {
		int len = word.length();
		Symbol[] jlearnSyms = new Symbol[len];
		
		int i = 0;
		for (Object sym : word) {
			jlearnSyms[i++] = new SymbolImpl(sym);
		}
		
		return new WordImpl(jlearnSyms);
	}

	public static de.ls5.jlearn.interfaces.Alphabet createJLearnAlphabet(Alphabet<?> alphabet) {
		de.ls5.jlearn.interfaces.Alphabet result = new AlphabetImpl();
		for (Object sym : alphabet) {
			result.addSymbol(new SymbolImpl(sym));
		}
		return result;
	}

}
