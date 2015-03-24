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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.automatalib.words.Word;
import de.learnlib.api.MembershipOracle;
import de.learnlib.api.Query;
import de.learnlib.oracles.DefaultQuery;
import de.learnlib.oracles.MQUtil;
import de.ls5.jlearn.abstractclasses.LearningException;
import de.ls5.jlearn.interfaces.BatchOracle;

abstract class OracleWrapper<I,D> implements MembershipOracle<I, D>, BatchOracle {
	
	private static final long serialVersionUID = 1L;
	
	private final MembershipOracle<I, D> oracle;

	public OracleWrapper(MembershipOracle<I, D> oracle) {
		this.oracle = oracle;
	}
	
	protected abstract de.ls5.jlearn.interfaces.Word decodeOutput(
			de.ls5.jlearn.interfaces.Word jlearnWord,
			Word<I> word,
			D output);
	
	protected de.ls5.jlearn.interfaces.Word decodeQuery(DefaultQuery<I,D> query) {
		if (!query.getPrefix().isEmpty()) {
			query = MQUtil.query(this, query.getInput());
		}
		Word<I> word = query.getInput();
		de.ls5.jlearn.interfaces.Word jlearnWord = JLearnGlue.toJLearnWord(word);
		return decodeOutput(jlearnWord, word, query.getOutput());
	}

	@Override
	public de.ls5.jlearn.interfaces.Word processQuery(de.ls5.jlearn.interfaces.Word jlearnWord) throws LearningException {
		Word<I> word = JLearnGlue.fromJLearnWord(jlearnWord);
		DefaultQuery<I, D> query = new DefaultQuery<>(word);
		oracle.processQueries(Collections.singleton(query));
		return decodeOutput(jlearnWord, word, query.getOutput());
	}

	@Override
	public Map<de.ls5.jlearn.interfaces.Word, de.ls5.jlearn.interfaces.Word>
	processQueries(List<de.ls5.jlearn.interfaces.Word> jlearnWords)
			throws LearningException {
		List<DefaultQuery<I,D>> queries = new ArrayList<>(jlearnWords.size());
		for (de.ls5.jlearn.interfaces.Word jlearnWord : jlearnWords) {
			Word<I> word = JLearnGlue.fromJLearnWord(jlearnWord);
			queries.add(new DefaultQuery<>(word));
		}
		oracle.processQueries(queries);
		Iterator<de.ls5.jlearn.interfaces.Word> jlearnWordsIt = jlearnWords.iterator();
		
		Map<de.ls5.jlearn.interfaces.Word,de.ls5.jlearn.interfaces.Word> result
			= new HashMap<>();
		for (DefaultQuery<I,D> query : queries) {
			de.ls5.jlearn.interfaces.Word jlearnWord = jlearnWordsIt.next();
			Word<I> word = query.getSuffix();
			D output = query.getOutput();
			
			de.ls5.jlearn.interfaces.Word jlearnOut = decodeOutput(jlearnWord, word, output);
			result.put(jlearnWord, jlearnOut);
		}
		return result;
	}
	
	@Override
	public void processQueries(Collection<? extends Query<I,D>> queries) {
		oracle.processQueries(queries);
	}

}
