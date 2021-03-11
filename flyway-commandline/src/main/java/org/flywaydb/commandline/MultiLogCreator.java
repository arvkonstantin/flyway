/*
 * Copyright © Red Gate Software Ltd 2010-2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flywaydb.commandline;

import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Log creator for a MultiLog
 */
class MultiLogCreator implements LogCreator {
    private final List<LogCreator> logCreators;

    MultiLogCreator(List<LogCreator> logCreators) {
        this.logCreators = logCreators;
    }

    @Override
    public Log createLogger(Class<?> clazz) {
        List<Log> logs = new ArrayList<>();

        for (LogCreator logCreator : logCreators) {
            logs.add(logCreator.createLogger(clazz));
        }

        return new MultiLogger(logs);
    }

    static MultiLogCreator empty() {
        return new MultiLogCreator(new ArrayList<>());
    }
}