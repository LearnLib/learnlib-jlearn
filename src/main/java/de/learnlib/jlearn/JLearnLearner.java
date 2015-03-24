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
import de.learnlib.api.LearningAlgorithm;
import de.learnlib.oracles.DefaultQuery;
import de.learnlib.oracles.MQUtil;
import de.ls5.jlearn.abstractclasses.LearningException;
import de.ls5.jlearn.interfaces.Learner;
import de.ls5.jlearn.interfaces.SplitterCreator;

abstract class JLearnLearner<M,I,D> implements LearningAlgorithm<M, I, D> {
	
	protected final Alphabet<I> alphabet;
	protected final de.ls5.jlearn.interfaces.Alphabet jlearnAlphabet;
	protected final OracleWrapper<I, D> oracleWrapper;
	protected final Learner jlearnLearner;
	
	private M hypothesis = null;
	

	public JLearnLearner(Alphabet<I> alphabet,
			OracleWrapper<I,D> oracleWrapper,
			Learner jlearnLearner,
			SplitterCreator sc) {
		this.alphabet = alphabet;
		this.oracleWrapper = oracleWrapper;
		this.jlearnLearner = jlearnLearner;
		
		this.jlearnAlphabet = JLearnGlue.createJLearnAlphabet(alphabet);
		jlearnLearner.setAlphabet(jlearnAlphabet);
		jlearnLearner.setOracle(oracleWrapper);
		if (sc != null) {
			jlearnLearner.setSplitterCreator(sc);
		}
	}
	
	public JLearnLearner(Alphabet<I> alphabet,
			OracleWrapper<I,D> oracleWrapper,
			Learner jlearnLearner,
			JLearnSplitterCreator sc) {
		this(alphabet, oracleWrapper, jlearnLearner, (sc != null) ? sc.create() : null);
	}

	@Override
	public void startLearning() {
		try {
			jlearnLearner.learn();
		}
		catch (LearningException ex) {
			throw new JLearnException(ex);
		}
		this.hypothesis = null;
	}

	@Override
	public boolean refineHypothesis(DefaultQuery<I, D> ceQuery) {
		if (!ceQuery.getPrefix().isEmpty()) {
			ceQuery = MQUtil.query(oracleWrapper, ceQuery.getInput());
		}
		Word<I> ceWord = ceQuery.getInput();
		de.ls5.jlearn.interfaces.Word jlearnWord = JLearnGlue.toJLearnWord(ceWord);
		de.ls5.jlearn.interfaces.Word jlearnOut = oracleWrapper.decodeOutput(jlearnWord, ceWord, ceQuery.getOutput());
		
		try {
			jlearnLearner.addCounterExample(jlearnWord, jlearnOut);
			jlearnLearner.learn();
		}
		catch (LearningException ex) {
			throw new JLearnException(ex);
		}
		this.hypothesis = null;
		
		return true;
	}

	@Override
	public M getHypothesisModel() {
		if (hypothesis == null) {
			hypothesis = translateModel(jlearnLearner.getResult());
		}
		
		return hypothesis;
	}
	
	protected abstract M translateModel(de.ls5.jlearn.interfaces.Automaton jlearnAutomaton);

}
