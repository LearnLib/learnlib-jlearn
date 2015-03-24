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

import net.automatalib.automata.transout.MealyMachine;
import net.automatalib.automata.transout.impl.compact.CompactMealy;
import net.automatalib.words.Alphabet;
import net.automatalib.words.Word;
import de.learnlib.api.LearningAlgorithm;
import de.learnlib.api.MembershipOracle;
import de.ls5.jlearn.interfaces.Automaton;
import de.ls5.jlearn.interfaces.Learner;

class JLearnMealyLearner<I,O> extends JLearnLearner<MealyMachine<?,I,?,O>, I, Word<O>>
		implements LearningAlgorithm.MealyLearner<I, O> {

	public JLearnMealyLearner(Alphabet<I> alphabet,
			MembershipOracle<I, Word<O>> oracle,
			Learner jlearnLearner,
			JLearnSplitterCreator sc) {
		super(alphabet, new MealyOracleWrapper<>(oracle), jlearnLearner, sc);
	}

	@Override
	protected CompactMealy<I,O> translateModel(Automaton jlearnAutomaton) {
		return JLearnMealyGlue.translateAutomaton(alphabet, jlearnAlphabet, jlearnAutomaton);
	}
}
