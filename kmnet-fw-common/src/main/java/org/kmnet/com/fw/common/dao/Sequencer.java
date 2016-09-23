/*
 * Copyright (C) 2016 KnowledgeNet.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kmnet.com.fw.common.dao;

/**
 * Interface for Sequencing functionality
 * @param <T> Sequencer Type
 */
public interface Sequencer<T> {

	/**
	 * Returns the next value in the sequence
	 * @return T next value in that sequence
	 */
	T getNext();

	/**
	 * Returns the current value in the sequence
	 * @return T current value in that sequence
	 */
	T getCurrent();
}
