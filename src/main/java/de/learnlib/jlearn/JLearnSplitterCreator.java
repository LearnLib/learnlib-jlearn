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

import de.ls5.jlearn.interfaces.SplitterCreator;
import de.ls5.jlearn.splittercreators.KearnsStyleSplitterCreator;
import de.ls5.jlearn.splittercreators.MalerStyleSplitterCreator;
import de.ls5.jlearn.splittercreators.RivestAllSuffixesSplitterCreator;
import de.ls5.jlearn.splittercreators.RivestStyleSplitterCreator;
import de.ls5.jlearn.splittercreators.ShahbazStyleSplitterCreator;
import de.ls5.jlearn.splittercreators.SplitterCreatorAllSuffixes;

public enum JLearnSplitterCreator {
//	HOWAR_STYLE {
//		@Override
//		public SplitterCreator create() {
//			return new HowarStyleSplitterCreator();
//		}
//	},
	KEARNS_STYLE {
		@Override
		public SplitterCreator create() {
			return new KearnsStyleSplitterCreator();
		}
	},
	MALER_STYLE {
		@Override
		public SplitterCreator create() {
			return new MalerStyleSplitterCreator();
		}
	},
	RIVEST_ALL_SUFFIXES {
		@Override
		public SplitterCreator create() {
			return new RivestAllSuffixesSplitterCreator();
		}
	},
	RIVEST_STYLE {
		@Override
		public SplitterCreator create() {
			return new RivestStyleSplitterCreator();
		}
	},
	SHAHBAZ_STYLE {
		@Override
		public SplitterCreator create() {
			return new ShahbazStyleSplitterCreator();
		}
	},
	ALL_SUFFIXES {
		@Override
		public SplitterCreator create() {
			return new SplitterCreatorAllSuffixes();
		}
	};
	
	public abstract SplitterCreator create();
}
