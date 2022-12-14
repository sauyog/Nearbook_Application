package org.briarproject.briar.test;

import static org.briarproject.bramble.api.identity.AuthorConstants.MAX_AUTHOR_NAME_LENGTH;
import static org.briarproject.bramble.util.StringUtils.getRandomString;
import static org.briarproject.masterproject.api.forum.ForumConstants.MAX_FORUM_NAME_LENGTH;

public interface TestData {

	String AUTHOR_NAMES[] = {
			"Thales",
			"Pythagoras",
			"Plato",
			"Aristotle",
			"Euclid",
			"Archimedes",
			"Hipparchus",
			"Ptolemy",
			"Sun Tzu",
			"Ibrahim ibn Sinan",
			"Muhammad Al-Karaji",
			"Yang Hui",
			"Ren\u00e9 Descartes",
			"Pierre de Fermat",
			"Blaise Pascal",
			"Jacob Bernoulli",
			"Christian Goldbach",
			"Leonhard Euler",
			"Joseph Louis Lagrange",
			"Pierre-Simon Laplace",
			"Joseph Fourier",
			"Carl Friedrich Gauss",
			"Charles Babbage",
			"George Boole",
			"John Venn",
			"Gottlob Frege",
			"Henri Poincar\u00e9",
			"David Hilbert",
			"Bertrand Russell",
			"John von Neumann",
			"Kurt G\u00f6del",
			"Alan Turing",
			"Beno\u00eet Mandelbrot",
			"John Nash",
			getRandomString(MAX_AUTHOR_NAME_LENGTH),
			getRandomString(MAX_AUTHOR_NAME_LENGTH),
			getRandomString(MAX_AUTHOR_NAME_LENGTH),
			getRandomString(MAX_AUTHOR_NAME_LENGTH),
			getRandomString(MAX_AUTHOR_NAME_LENGTH),
			getRandomString(MAX_AUTHOR_NAME_LENGTH),
			getRandomString(MAX_AUTHOR_NAME_LENGTH),
	};

	String GROUP_NAMES[] = {
			"Private Messengers",
			"The Darknet",
			"Bletchley Park",
			"Acropolis",
			"General Discussion",
			"The Undiscovered Country",
			"The Place to Be",
			"Forum Romanum",
			getRandomString(MAX_FORUM_NAME_LENGTH),
			getRandomString(MAX_FORUM_NAME_LENGTH),
			getRandomString(MAX_FORUM_NAME_LENGTH),
			getRandomString(MAX_FORUM_NAME_LENGTH),
	};

}
