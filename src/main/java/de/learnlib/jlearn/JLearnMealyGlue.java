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

import java.util.List;

import net.automatalib.automata.transout.impl.compact.CompactMealy;
import net.automatalib.words.Alphabet;
import de.ls5.jlearn.interfaces.State;
import de.ls5.jlearn.interfaces.Symbol;
import de.ls5.jlearn.shared.AutomatonImpl;
import de.ls5.jlearn.util.AutomatonUtil;

abstract class JLearnMealyGlue {
	
	@SuppressWarnings("unchecked")
	public static <O> O extractOutput(Symbol jlearnOutSym) {
		return (O)jlearnOutSym.getUserObject();
	}
	
	public static <I,O> CompactMealy<I,O> translateAutomaton(
			Alphabet<I> alphabet,
			de.ls5.jlearn.interfaces.Alphabet jlearnAlphabet,
			de.ls5.jlearn.interfaces.Automaton jlearnAutomaton) {
		assert alphabet.size() == jlearnAlphabet.size();
		
		((AutomatonImpl)jlearnAutomaton).reassignStateIds();
		List<State> states = AutomatonUtil.getStatesInBFSOrder(jlearnAutomaton);
		
		int numStates = states.size();
		
		CompactMealy<I,O> result = new CompactMealy<>(alphabet, numStates);
		for (State jlearnState : states) {
			int id = jlearnState.getId();
			int state = result.addState();
			assert state == id;
		}
		
		for (State jlearnState : states) {
			int stateId = jlearnState.getId();
			int inputId = 0;
			for (Symbol jlearnSym : jlearnAlphabet.getSymbolList()) {
				State target = jlearnState.getTransitionState(jlearnSym);
				if (target != null) {
					int targetId = target.getId();
					Symbol jlearnTransOut = jlearnState.getTransitionOutput(jlearnSym);
					O transOut = extractOutput(jlearnTransOut);
					result.setTransition(stateId, inputId, targetId, transOut);
				}
				inputId++;
			}
		}
		
		State init = jlearnAutomaton.getStart();
		int initId = init.getId();
		
		result.setInitialState(initId);
		
		return result;
	}
	
	private JLearnMealyGlue() {
		throw new AssertionError();
	}

}
