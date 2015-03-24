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
import de.learnlib.api.MembershipOracle.MealyMembershipOracle;
import de.learnlib.testsupport.it.learner.AbstractMealyLearnerIT;
import de.learnlib.testsupport.it.learner.LearnerVariantList.MealyLearnerVariantList;

public class JLearnDHCMealyIT extends AbstractMealyLearnerIT {

	@Override
	protected <I, O> void addLearnerVariants(Alphabet<I> alphabet,
			MealyMembershipOracle<I, O> mqOracle,
			MealyLearnerVariantList<I, O> variants) {
		for (JLearnSplitterCreator sc : JLearnSplitterCreator.values()) {
			String name = "splitterCreator=" + sc.toString();
			variants.addLearnerVariant(name, new JLearnDHCMealy<>(alphabet, mqOracle, sc));
		}
	}
}
